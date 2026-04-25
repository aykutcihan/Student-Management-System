import React, { useEffect, useState } from "react";
import DataTable from "react-data-table-component";
import { useFormik } from "formik";
import * as Yup from "yup";
import Swal from "sweetalert2";
import { Modal } from "react-bootstrap";
import { FiPlus, FiEdit2, FiTrash2, FiSearch } from "react-icons/fi";
import { getAllTeachers, saveTeacher, updateTeacher, deleteTeacher } from "../../../services/teacherService";
import { searchLessons } from "../../../services/lessonService";

const schema = Yup.object({
    username: Yup.string().min(4).max(16).required("Required"),
    name: Yup.string().min(4).max(16).required("Required"),
    surname: Yup.string().min(4).max(16).required("Required"),
    password: Yup.string().min(8).max(60).required("Required"),
    birthDay: Yup.string().required("Required"),
    birthPlace: Yup.string().min(2).max(16).required("Required"),
    ssn: Yup.string().matches(/^\d{3}-\d{2}-\d{4}$/, "Format: ###-##-####").required("Required"),
    phoneNumber: Yup.string().length(12, "Must be 12 chars").required("Required"),
    gender: Yup.string().required("Required"),
    email: Yup.string().email("Invalid email").min(5).max(50).required("Required"),
});

const INIT = { username: "", name: "", surname: "", password: "", birthDay: "", birthPlace: "", ssn: "", phoneNumber: "", gender: "", email: "", lessonsIdList: [], isAdvisorTeacher: false };

const TeacherPage = () => {
    const [data, setData] = useState([]);
    const [lessons, setLessons] = useState([]);
    const [loading, setLoading] = useState(false);
    const [showModal, setShowModal] = useState(false);
    const [editId, setEditId] = useState(null);
    const [filter, setFilter] = useState("");

    const load = async () => {
        setLoading(true);
        try { const res = await getAllTeachers(); setData(res.data || []); }
        catch { setData([]); }
        finally { setLoading(false); }
    };

    useEffect(() => {
        load();
        searchLessons(0, 100).then((res) => setLessons(res.data?.content || res.data || [])).catch(() => {});
    }, []);

    const openAdd = () => { setEditId(null); formik.resetForm(); setShowModal(true); };
    const openEdit = (row) => {
        setEditId(row.userId);
        formik.setValues({ username: row.username, name: row.name, surname: row.surname, password: "", birthDay: row.birthDay || "", birthPlace: row.birthPlace || "", ssn: row.ssn || "", phoneNumber: row.phoneNumber || "", gender: row.gender || "", email: row.email || "", lessonsIdList: [], isAdvisorTeacher: row.isAdvisoryTeacher || false });
        setShowModal(true);
    };

    const formik = useFormik({
        initialValues: INIT,
        validationSchema: schema,
        onSubmit: async (values, { resetForm, setSubmitting }) => {
            try {
                const payload = { ...values, lessonsIdList: values.lessonsIdList.map(Number) };
                if (editId) await updateTeacher(editId, payload);
                else await saveTeacher(payload);
                resetForm(); setShowModal(false); load();
                Swal.fire({ icon: "success", title: editId ? "Teacher updated!" : "Teacher saved!", timer: 1500, showConfirmButton: false });
            } catch (err) {
                Swal.fire({ icon: "error", title: "Error", text: err.response?.data?.message || "Operation failed.", confirmButtonColor: "#1e40af" });
            } finally { setSubmitting(false); }
        },
    });

    const handleDelete = (id) => {
        Swal.fire({ title: "Delete this teacher?", icon: "warning", showCancelButton: true, confirmButtonColor: "#dc2626", confirmButtonText: "Yes, Delete" })
            .then(async (r) => {
                if (!r.isConfirmed) return;
                try { await deleteTeacher(id); load(); }
                catch (err) { Swal.fire({ icon: "error", title: "Error", text: err.response?.data?.message || "Could not delete.", confirmButtonColor: "#1e40af" }); }
            });
    };

    const toggleLesson = (id) => {
        const list = formik.values.lessonsIdList;
        formik.setFieldValue("lessonsIdList", list.includes(id) ? list.filter((x) => x !== id) : [...list, id]);
    };

    const f = (name, type = "text") => ({
        type, className: `form-control ${formik.touched[name] && formik.errors[name] ? "is-invalid" : ""}`,
        ...formik.getFieldProps(name),
    });

    const filtered = data.filter((r) => `${r.name} ${r.surname} ${r.username} ${r.email || ""}`.toLowerCase().includes(filter.toLowerCase()));

    const columns = [
        { name: "#", selector: (_, i) => i + 1, width: "60px" },
        { name: "Username", selector: (r) => r.username, sortable: true },
        { name: "Full Name", selector: (r) => `${r.name} ${r.surname}`, sortable: true },
        { name: "Email", selector: (r) => r.email },
        { name: "Phone", selector: (r) => r.phoneNumber },
        { name: "Advisory", cell: (r) => r.isAdvisoryTeacher ? <span className="badge bg-success">Yes</span> : <span className="badge bg-secondary">No</span>, width: "90px" },
        {
            name: "Actions", cell: (r) => (
                <div className="d-flex gap-1">
                    <button className="btn btn-sm btn-outline-primary d-flex align-items-center gap-1" onClick={() => openEdit(r)}><FiEdit2 size={12} /> Edit</button>
                    <button className="btn btn-sm btn-outline-danger d-flex align-items-center gap-1" onClick={() => handleDelete(r.userId)}><FiTrash2 size={12} /> Del</button>
                </div>
            ), width: "160px"
        },
    ];

    return (
        <div>
            <div className="page-header">
                <h4>Teacher Management</h4>
                <button className="btn btn-primary d-flex align-items-center gap-2" onClick={openAdd}><FiPlus size={15} /> Add Teacher</button>
            </div>
            <div className="table-card">
                <div className="p-3 border-bottom">
                    <div className="input-group" style={{ maxWidth: 320 }}>
                        <span className="input-group-text bg-white border-end-0"><FiSearch size={14} color="#64748b" /></span>
                        <input className="form-control border-start-0" placeholder="Search teachers..." value={filter} onChange={(e) => setFilter(e.target.value)} />
                    </div>
                </div>
                <DataTable columns={columns} data={filtered} progressPending={loading} pagination dense highlightOnHover responsive noDataComponent={<div className="p-4 text-muted">No teachers found.</div>} />
            </div>
            <Modal show={showModal} onHide={() => setShowModal(false)} size="lg" centered>
                <Modal.Header closeButton><Modal.Title>{editId ? "Edit Teacher" : "Add New Teacher"}</Modal.Title></Modal.Header>
                <Modal.Body>
                    <form id="teacherForm" onSubmit={formik.handleSubmit} noValidate>
                        <div className="row g-3">
                            {[["username","text","Username"],["name","text","First Name"],["surname","text","Last Name"],["password","password","Password"],["email","email","Email Address"],["birthDay","date","Date of Birth"],["birthPlace","text","Birth Place"],["ssn","text","SSN (###-##-####)"],["phoneNumber","text","Phone Number"]].map(([name, type, label]) => (
                                <div className="col-md-6" key={name}>
                                    <label className="form-label">{label}</label>
                                    <input {...f(name, type)} />
                                    {formik.touched[name] && formik.errors[name] && <div className="invalid-feedback">{formik.errors[name]}</div>}
                                </div>
                            ))}
                            <div className="col-md-6">
                                <label className="form-label">Gender</label>
                                <select className={`form-select ${formik.touched.gender && formik.errors.gender ? "is-invalid" : ""}`} {...formik.getFieldProps("gender")}>
                                    <option value="">Select gender</option>
                                    <option value="MALE">Male</option>
                                    <option value="FEMALE">Female</option>
                                </select>
                                {formik.touched.gender && formik.errors.gender && <div className="invalid-feedback">{formik.errors.gender}</div>}
                            </div>
                            <div className="col-md-6 d-flex align-items-center pt-4">
                                <div className="form-check">
                                    <input type="checkbox" className="form-check-input" id="isAdvisor" checked={formik.values.isAdvisorTeacher} onChange={(e) => formik.setFieldValue("isAdvisorTeacher", e.target.checked)} />
                                    <label className="form-check-label" htmlFor="isAdvisor">Advisory Teacher</label>
                                </div>
                            </div>
                            <div className="col-12">
                                <label className="form-label">Assign Lessons</label>
                                <div className="border rounded p-2 d-flex flex-wrap gap-2" style={{ maxHeight: 140, overflowY: "auto" }}>
                                    {lessons.map((l) => (
                                        <div key={l.lessonId} className="form-check form-check-inline m-0">
                                            <input type="checkbox" className="form-check-input" id={`l${l.lessonId}`} checked={formik.values.lessonsIdList.includes(l.lessonId)} onChange={() => toggleLesson(l.lessonId)} />
                                            <label className="form-check-label" htmlFor={`l${l.lessonId}`} style={{ fontSize: ".82rem" }}>{l.lessonName}</label>
                                        </div>
                                    ))}
                                </div>
                            </div>
                        </div>
                    </form>
                </Modal.Body>
                <Modal.Footer>
                    <button className="btn btn-outline-secondary" onClick={() => setShowModal(false)}>Cancel</button>
                    <button className="btn btn-primary" form="teacherForm" type="submit" disabled={formik.isSubmitting}>
                        {formik.isSubmitting ? "Saving..." : editId ? "Update Teacher" : "Save Teacher"}
                    </button>
                </Modal.Footer>
            </Modal>
        </div>
    );
};

export default TeacherPage;

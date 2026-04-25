import React, { useEffect, useState } from "react";
import DataTable from "react-data-table-component";
import { useFormik } from "formik";
import * as Yup from "yup";
import Swal from "sweetalert2";
import { Modal } from "react-bootstrap";
import { FiPlus, FiEdit2, FiTrash2, FiSearch } from "react-icons/fi";
import { useSelector } from "react-redux";
import { getMeetsByTeacher, saveMeet, updateMeet, deleteMeet, getAllMeets } from "../../../services/meetService";
import { getStudentsByAdvisor } from "../../../services/studentService";

const schema = Yup.object({
    description: Yup.string().min(2).max(250).required("Required"),
    date: Yup.string().required("Required"),
    startTime: Yup.string().required("Required"),
    stopTime: Yup.string().required("Required"),
});

const INIT = { description: "", date: "", startTime: "", stopTime: "", studentIds: [] };

const MeetPage = () => {
    const { user } = useSelector((s) => s.auth);
    const isAdmin = user?.role === "ADMIN";
    const [data, setData] = useState([]);
    const [students, setStudents] = useState([]);
    const [loading, setLoading] = useState(false);
    const [showModal, setShowModal] = useState(false);
    const [editId, setEditId] = useState(null);
    const [filter, setFilter] = useState("");

    const load = async () => {
        setLoading(true);
        try { let res = isAdmin ? await getAllMeets() : await getMeetsByTeacher(user.username); setData(res.data || []); }
        catch { setData([]); }
        finally { setLoading(false); }
    };

    useEffect(() => {
        load();
        if (!isAdmin) getStudentsByAdvisor(user.username).then((r) => setStudents(r.data || [])).catch(() => {});
    }, []);

    const openAdd = () => { setEditId(null); formik.resetForm(); setShowModal(true); };
    const openEdit = (row) => { setEditId(row.id); formik.setValues({ description: row.description, date: row.date, startTime: row.startTime, stopTime: row.stopTime, studentIds: row.students?.map((s) => s.userId) || [] }); setShowModal(true); };

    const formik = useFormik({
        initialValues: INIT,
        validationSchema: schema,
        onSubmit: async (values, { resetForm, setSubmitting }) => {
            try {
                const p = { ...values, studentIds: values.studentIds.map(Number) };
                if (editId) await updateMeet(editId, p); else await saveMeet(p);
                resetForm(); setShowModal(false); load();
                Swal.fire({ icon: "success", title: editId ? "Updated!" : "Saved!", timer: 1500, showConfirmButton: false });
            } catch (err) { Swal.fire({ icon: "error", title: "Error", text: err.response?.data?.message || "Failed.", confirmButtonColor: "#1e40af" }); }
            finally { setSubmitting(false); }
        },
    });

    const handleDelete = (id) => Swal.fire({ title: "Delete meeting?", icon: "warning", showCancelButton: true, confirmButtonColor: "#dc2626" }).then(async (r) => { if (r.isConfirmed) { try { await deleteMeet(id); load(); } catch (e) { Swal.fire({ icon: "error", title: "Error", text: e.response?.data?.message || "Failed.", confirmButtonColor: "#1e40af" }); } } });
    const toggleSt = (id) => { const l = formik.values.studentIds; formik.setFieldValue("studentIds", l.includes(id) ? l.filter((x) => x !== id) : [...l, id]); };
    const f = (n, t = "text") => ({ type: t, className: `form-control ${formik.touched[n] && formik.errors[n] ? "is-invalid" : ""}`, ...formik.getFieldProps(n) });

    const columns = [
        { name: "#", selector: (_, i) => i + 1, width: "60px" },
        { name: "Description", selector: (r) => r.description, grow: 2, wrap: true },
        { name: "Date", selector: (r) => r.date, width: "110px" },
        { name: "Start", selector: (r) => r.startTime, width: "80px" },
        { name: "Stop", selector: (r) => r.stopTime, width: "80px" },
        { name: "Students", selector: (r) => r.students?.length || 0, width: "80px" },
        { name: "Actions", cell: (r) => <div className="d-flex gap-1">{!isAdmin&&<button className="btn btn-sm btn-outline-primary" onClick={() => openEdit(r)}><FiEdit2 size={12}/></button>}<button className="btn btn-sm btn-outline-danger" onClick={() => handleDelete(r.id)}><FiTrash2 size={12}/></button></div>, width: "100px" },
    ];

    return (
        <div>
            <div className="page-header">
                <h4>Meeting Management</h4>
                {!isAdmin && <button className="btn btn-primary d-flex align-items-center gap-2" onClick={openAdd}><FiPlus size={15}/> Schedule Meeting</button>}
            </div>
            <div className="table-card">
                <div className="p-3 border-bottom"><div className="input-group" style={{ maxWidth: 320 }}><span className="input-group-text bg-white border-end-0"><FiSearch size={14}/></span><input className="form-control border-start-0" placeholder="Search..." value={filter} onChange={(e) => setFilter(e.target.value)}/></div></div>
                <DataTable columns={columns} data={data.filter((r) => `${r.description} ${r.date}`.toLowerCase().includes(filter.toLowerCase()))} progressPending={loading} pagination dense highlightOnHover responsive noDataComponent={<div className="p-4 text-muted">No meetings found.</div>}/>
            </div>
            {!isAdmin && (
                <Modal show={showModal} onHide={() => setShowModal(false)} size="lg" centered>
                    <Modal.Header closeButton><Modal.Title>{editId ? "Edit Meeting" : "Schedule Meeting"}</Modal.Title></Modal.Header>
                    <Modal.Body>
                        <form id="mf" onSubmit={formik.handleSubmit} noValidate>
                            <div className="row g-3">
                                <div className="col-12"><label className="form-label">Description</label><textarea rows={3} className={`form-control ${formik.touched.description && formik.errors.description ? "is-invalid" : ""}`} {...formik.getFieldProps("description")} style={{ resize: "none" }}/>{formik.touched.description && formik.errors.description && <div className="invalid-feedback">{formik.errors.description}</div>}</div>
                                <div className="col-md-4"><label className="form-label">Date</label><input {...f("date","date")}/>{formik.touched.date&&formik.errors.date&&<div className="invalid-feedback">{formik.errors.date}</div>}</div>
                                <div className="col-md-4"><label className="form-label">Start</label><input {...f("startTime","time")}/></div>
                                <div className="col-md-4"><label className="form-label">Stop</label><input {...f("stopTime","time")}/></div>
                                {students.length > 0 && <div className="col-12"><label className="form-label">Select Students</label><div className="border rounded p-2" style={{ maxHeight: 150, overflowY: "auto" }}>{students.map((s) => <div key={s.userId} className="form-check"><input type="checkbox" className="form-check-input" id={`ms${s.userId}`} checked={formik.values.studentIds.includes(s.userId)} onChange={() => toggleSt(s.userId)}/><label className="form-check-label" htmlFor={`ms${s.userId}`} style={{ fontSize: ".82rem" }}>{s.name} {s.surname}</label></div>)}</div></div>}
                            </div>
                        </form>
                    </Modal.Body>
                    <Modal.Footer><button className="btn btn-outline-secondary" onClick={() => setShowModal(false)}>Cancel</button><button className="btn btn-primary" form="mf" type="submit" disabled={formik.isSubmitting}>{formik.isSubmitting ? "Saving..." : editId ? "Update" : "Save"}</button></Modal.Footer>
                </Modal>
            )}
        </div>
    );
};

export default MeetPage;

import React, { useEffect, useState } from "react";
import DataTable from "react-data-table-component";
import { useFormik } from "formik";
import * as Yup from "yup";
import Swal from "sweetalert2";
import { Modal } from "react-bootstrap";
import { FiPlus, FiEdit2, FiTrash2, FiSearch } from "react-icons/fi";
import { useSelector } from "react-redux";
import { saveStudentInfo, updateStudentInfo, deleteStudentInfo, getStudentInfoForTeacher, searchStudentInfo } from "../../../services/studentInfoService";
import { getAllEducationTerms } from "../../../services/educationTermService";
import { searchLessons } from "../../../services/lessonService";
import { getStudentsByAdvisor } from "../../../services/studentService";

const schema = Yup.object({
    educationTermId: Yup.number().required("Required"),
    midtermExam: Yup.number().min(0).max(100).required("Required"),
    finalExam: Yup.number().min(0).max(100).required("Required"),
    absentee: Yup.number().required("Required"),
    infoNote: Yup.string().min(10).max(200).required("Required (10-200 chars)"),
    lessonId: Yup.number().required("Required"),
    studentId: Yup.number().required("Required"),
});

const INIT = { educationTermId: "", midtermExam: "", finalExam: "", absentee: "", infoNote: "", lessonId: "", studentId: "" };
const noteColor = { AA:"success",BA:"success",BB:"primary",CB:"primary",CC:"info",DC:"warning",DD:"warning",DZ:"danger",FF:"danger" };

const StudentInfoPage = () => {
    const { user } = useSelector((s) => s.auth);
    const isAdmin = user?.role === "ADMIN";
    const [data, setData] = useState([]);
    const [terms, setTerms] = useState([]);
    const [lessons, setLessons] = useState([]);
    const [students, setStudents] = useState([]);
    const [loading, setLoading] = useState(false);
    const [showModal, setShowModal] = useState(false);
    const [editId, setEditId] = useState(null);
    const [filter, setFilter] = useState("");
    const [page, setPage] = useState(0);
    const [totalRows, setTotalRows] = useState(0);

    const load = async (p = 0) => {
        setLoading(true);
        try {
            let res = isAdmin ? await searchStudentInfo(p, 10) : await getStudentInfoForTeacher(user.username, p, 10);
            setData(res.data?.content || []); setTotalRows(res.data?.totalElements || 0);
        } catch { setData([]); }
        finally { setLoading(false); }
    };

    useEffect(() => {
        load();
        getAllEducationTerms().then((r) => setTerms(r.data || [])).catch(() => {});
        searchLessons(0, 100).then((r) => setLessons(r.data?.content || r.data || [])).catch(() => {});
        if (!isAdmin) getStudentsByAdvisor(user.username).then((r) => setStudents(r.data || [])).catch(() => {});
    }, []);

    const openAdd = () => { setEditId(null); formik.resetForm(); setShowModal(true); };
    const openEdit = (row) => {
        setEditId(row.id);
        formik.setValues({ educationTermId: terms.find(t => t.term === row.educationTerm)?.id || "", midtermExam: row.midtermExam || "", finalExam: row.finalExam || "", absentee: row.absentee || "", infoNote: row.infoNote || "", lessonId: lessons.find(l => l.lessonName === row.lessonName)?.lessonId || "", studentId: row.studentResponse?.userId || "" });
        setShowModal(true);
    };

    const formik = useFormik({
        initialValues: INIT,
        validationSchema: schema,
        onSubmit: async (values, { resetForm, setSubmitting }) => {
            try {
                const payload = { ...values, educationTermId: Number(values.educationTermId), lessonId: Number(values.lessonId), studentId: Number(values.studentId) };
                if (editId) { const { studentId, ...rest } = payload; await updateStudentInfo(editId, rest); }
                else await saveStudentInfo(payload);
                resetForm(); setShowModal(false); load(page);
                Swal.fire({ icon: "success", title: editId ? "Updated!" : "Saved!", timer: 1500, showConfirmButton: false });
            } catch (err) { Swal.fire({ icon: "error", title: "Error", text: err.response?.data?.message || "Failed.", confirmButtonColor: "#1e40af" }); }
            finally { setSubmitting(false); }
        },
    });

    const handleDelete = (id) => Swal.fire({ title: "Delete record?", icon: "warning", showCancelButton: true, confirmButtonColor: "#dc2626" }).then(async (r) => { if (r.isConfirmed) { try { await deleteStudentInfo(id); load(page); } catch (e) { Swal.fire({ icon: "error", title: "Error", text: e.response?.data?.message || "Failed.", confirmButtonColor: "#1e40af" }); } } });
    const f = (n, t = "text") => ({ type: t, className: `form-control ${formik.touched[n] && formik.errors[n] ? "is-invalid" : ""}`, ...formik.getFieldProps(n) });

    const columns = [
        { name: "#", selector: (_, i) => i + 1, width: "60px" },
        { name: "Student", selector: (r) => `${r.studentResponse?.name || ""} ${r.studentResponse?.surname || ""}`, sortable: true, grow: 2 },
        { name: "Lesson", selector: (r) => r.lessonName },
        { name: "Midterm", selector: (r) => r.midtermExam, width: "85px" },
        { name: "Final", selector: (r) => r.finalExam, width: "85px" },
        { name: "Avg", selector: (r) => r.average?.toFixed(1), width: "75px" },
        { name: "Grade", cell: (r) => <span className={`badge bg-${noteColor[r.note]||"secondary"}`}>{r.note}</span>, width: "75px" },
        { name: "Actions", cell: (r) => <div className="d-flex gap-1"><button className="btn btn-sm btn-outline-primary" onClick={() => openEdit(r)}><FiEdit2 size={12}/></button><button className="btn btn-sm btn-outline-danger" onClick={() => handleDelete(r.id)}><FiTrash2 size={12}/></button></div>, width: "100px" },
    ];

    return (
        <div>
            <div className="page-header">
                <h4>Student Info</h4>
                {!isAdmin && <button className="btn btn-primary d-flex align-items-center gap-2" onClick={openAdd}><FiPlus size={15}/> Add Record</button>}
            </div>
            <div className="table-card">
                <div className="p-3 border-bottom"><div className="input-group" style={{ maxWidth: 320 }}><span className="input-group-text bg-white border-end-0"><FiSearch size={14}/></span><input className="form-control border-start-0" placeholder="Search..." value={filter} onChange={(e) => setFilter(e.target.value)}/></div></div>
                <DataTable columns={columns} data={data.filter(r=>`${r.studentResponse?.name||""} ${r.lessonName||""}`.toLowerCase().includes(filter.toLowerCase()))} progressPending={loading} pagination paginationServer paginationTotalRows={totalRows} onChangePage={(p) => { setPage(p-1); load(p-1); }} dense highlightOnHover responsive noDataComponent={<div className="p-4 text-muted">No records.</div>}/>
            </div>
            {!isAdmin && (
                <Modal show={showModal} onHide={() => setShowModal(false)} size="lg" centered>
                    <Modal.Header closeButton><Modal.Title>{editId ? "Edit Record" : "Add Student Info"}</Modal.Title></Modal.Header>
                    <Modal.Body>
                        <form id="sif" onSubmit={formik.handleSubmit} noValidate>
                            <div className="row g-3">
                                <div className="col-md-6"><label className="form-label">Education Term</label><select className={`form-select ${formik.touched.educationTermId&&formik.errors.educationTermId?"is-invalid":""}`} {...formik.getFieldProps("educationTermId")}><option value="">Select</option>{terms.map(t=><option key={t.id} value={t.id}>{t.term?.replace("_"," ")}</option>)}</select>{formik.touched.educationTermId&&formik.errors.educationTermId&&<div className="invalid-feedback">{formik.errors.educationTermId}</div>}</div>
                                <div className="col-md-6"><label className="form-label">Lesson</label><select className={`form-select ${formik.touched.lessonId&&formik.errors.lessonId?"is-invalid":""}`} {...formik.getFieldProps("lessonId")}><option value="">Select</option>{lessons.map(l=><option key={l.lessonId} value={l.lessonId}>{l.lessonName}</option>)}</select>{formik.touched.lessonId&&formik.errors.lessonId&&<div className="invalid-feedback">{formik.errors.lessonId}</div>}</div>
                                {!editId&&<div className="col-md-6"><label className="form-label">Student</label><select className={`form-select ${formik.touched.studentId&&formik.errors.studentId?"is-invalid":""}`} {...formik.getFieldProps("studentId")}><option value="">Select</option>{students.map(s=><option key={s.userId} value={s.userId}>{s.name} {s.surname}</option>)}</select>{formik.touched.studentId&&formik.errors.studentId&&<div className="invalid-feedback">{formik.errors.studentId}</div>}</div>}
                                <div className="col-md-4"><label className="form-label">Midterm (0–100)</label><input type="number" min={0} max={100} {...f("midtermExam","number")}/>{formik.touched.midtermExam&&formik.errors.midtermExam&&<div className="invalid-feedback">{formik.errors.midtermExam}</div>}</div>
                                <div className="col-md-4"><label className="form-label">Final (0–100)</label><input type="number" min={0} max={100} {...f("finalExam","number")}/>{formik.touched.finalExam&&formik.errors.finalExam&&<div className="invalid-feedback">{formik.errors.finalExam}</div>}</div>
                                <div className="col-md-4"><label className="form-label">Absentee</label><input type="number" min={0} {...f("absentee","number")}/>{formik.touched.absentee&&formik.errors.absentee&&<div className="invalid-feedback">{formik.errors.absentee}</div>}</div>
                                <div className="col-12"><label className="form-label">Info Note (10–200 chars)</label><textarea rows={3} className={`form-control ${formik.touched.infoNote&&formik.errors.infoNote?"is-invalid":""}`} {...formik.getFieldProps("infoNote")} style={{resize:"none"}}/>{formik.touched.infoNote&&formik.errors.infoNote&&<div className="invalid-feedback">{formik.errors.infoNote}</div>}</div>
                            </div>
                        </form>
                    </Modal.Body>
                    <Modal.Footer><button className="btn btn-outline-secondary" onClick={() => setShowModal(false)}>Cancel</button><button className="btn btn-primary" form="sif" type="submit" disabled={formik.isSubmitting}>{formik.isSubmitting?"Saving...":editId?"Update":"Save"}</button></Modal.Footer>
                </Modal>
            )}
        </div>
    );
};

export default StudentInfoPage;

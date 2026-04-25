import React, { useEffect, useState } from "react";
import DataTable from "react-data-table-component";
import Swal from "sweetalert2";
import { Tabs, Tab } from "react-bootstrap";
import { FiCheckCircle } from "react-icons/fi";
import { useSelector } from "react-redux";
import { getAllUnassigned, getAllAssigned } from "../../../services/lessonProgramService";
import { chooseLesson } from "../../../services/studentService";

const ChooseLessonPage = () => {
    const { user } = useSelector((s) => s.auth);
    const [unassigned, setUnassigned] = useState([]);
    const [assigned, setAssigned] = useState([]);
    const [selected, setSelected] = useState([]);
    const [loading, setLoading] = useState(false);
    const [submitting, setSubmitting] = useState(false);

    const load = async () => {
        setLoading(true);
        try {
            const [u, a] = await Promise.all([getAllUnassigned(), getAllAssigned()]);
            setUnassigned(u.data || []);
            setAssigned(a.data?.filter((p) => p.students?.some((s) => s.username === user.username)) || []);
        } catch { }
        finally { setLoading(false); }
    };

    useEffect(() => { load(); }, []);

    const handleChoose = async () => {
        if (!selected.length) { Swal.fire({ icon: "warning", title: "No programs selected", confirmButtonColor: "#1e40af" }); return; }
        setSubmitting(true);
        try {
            await chooseLesson({ lessonProgramId: selected }, user.username);
            setSelected([]); load();
            Swal.fire({ icon: "success", title: "Lessons registered!", timer: 1500, showConfirmButton: false });
        } catch (err) { Swal.fire({ icon: "error", title: "Error", text: err.response?.data?.message || "Could not register.", confirmButtonColor: "#1e40af" }); }
        finally { setSubmitting(false); }
    };

    const toggleSel = (id) => setSelected((p) => p.includes(id) ? p.filter((x) => x !== id) : [...p, id]);

    const cols = (sel) => [
        ...(sel ? [{ name: "✓", cell: (r) => <input type="checkbox" checked={selected.includes(r.lessonProgramId)} onChange={() => toggleSel(r.lessonProgramId)}/>, width: "60px" }] : []),
        { name: "Day", selector: (r) => r.day, sortable: true, width: "120px" },
        { name: "Start", selector: (r) => r.startTime, width: "90px" },
        { name: "Stop", selector: (r) => r.stopTime, width: "90px" },
        { name: "Lessons", selector: (r) => r.lessonName?.map((l) => l.lessonName).join(", "), grow: 2, wrap: true },
        { name: "Term", selector: (r) => r.educationTerm?.term?.replace("_", " ") },
    ];

    return (
        <div>
            <div className="page-header">
                <h4>Choose Lessons</h4>
                <button className="btn btn-primary d-flex align-items-center gap-2" onClick={handleChoose} disabled={submitting || !selected.length}>
                    <FiCheckCircle size={15}/> {submitting ? "Registering..." : `Register (${selected.length})`}
                </button>
            </div>
            {selected.length > 0 && <div className="alert alert-info mb-3 py-2">{selected.length} program(s) selected. Click "Register" to confirm.</div>}
            <Tabs defaultActiveKey="available" className="mb-3">
                <Tab eventKey="available" title={`Available (${unassigned.length})`}>
                    <div className="table-card"><DataTable columns={cols(true)} data={unassigned} progressPending={loading} pagination dense highlightOnHover responsive noDataComponent={<div className="p-4 text-muted">No available programs.</div>}/></div>
                </Tab>
                <Tab eventKey="enrolled" title={`My Programs (${assigned.length})`}>
                    <div className="table-card"><DataTable columns={cols(false)} data={assigned} progressPending={loading} pagination dense highlightOnHover responsive noDataComponent={<div className="p-4 text-muted">No enrolled programs.</div>}/></div>
                </Tab>
            </Tabs>
        </div>
    );
};

export default ChooseLessonPage;

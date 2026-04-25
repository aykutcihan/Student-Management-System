import React, { useEffect, useState } from "react";
import DataTable from "react-data-table-component";
import { Tabs, Tab } from "react-bootstrap";
import { useSelector } from "react-redux";
import { getStudentInfoForStudent, getStudentInfoForTeacher } from "../../../services/studentInfoService";
import { getMeetsByStudent, getMeetsByTeacher } from "../../../services/meetService";

const noteColor = { AA:"success",BA:"success",BB:"primary",CB:"primary",CC:"info",DC:"warning",DD:"warning",DZ:"danger",FF:"danger" };

const GradesMeetsPage = () => {
    const { user } = useSelector((s) => s.auth);
    const isTeacher = user?.role === "TEACHER";
    const [grades, setGrades] = useState([]);
    const [meets, setMeets] = useState([]);
    const [loadingG, setLoadingG] = useState(false);
    const [loadingM, setLoadingM] = useState(false);
    const [page, setPage] = useState(0);
    const [total, setTotal] = useState(0);

    const loadGrades = async (p = 0) => {
        setLoadingG(true);
        try {
            const res = isTeacher ? await getStudentInfoForTeacher(user.username, p, 10) : await getStudentInfoForStudent(user.username, p, 10);
            setGrades(res.data?.content || []); setTotal(res.data?.totalElements || 0);
        } catch { setGrades([]); }
        finally { setLoadingG(false); }
    };

    const loadMeets = async () => {
        setLoadingM(true);
        try { const res = isTeacher ? await getMeetsByTeacher(user.username) : await getMeetsByStudent(user.username); setMeets(res.data || []); }
        catch { setMeets([]); }
        finally { setLoadingM(false); }
    };

    useEffect(() => { loadGrades(); loadMeets(); }, []);

    const gradeColumns = isTeacher ? [
        { name: "#", selector: (_, i) => i + 1, width: "60px" },
        { name: "Student", selector: (r) => `${r.studentResponse?.name||""} ${r.studentResponse?.surname||""}`, sortable: true, grow: 2 },
        { name: "Lesson", selector: (r) => r.lessonName },
        { name: "Midterm", selector: (r) => r.midtermExam, width: "85px" },
        { name: "Final", selector: (r) => r.finalExam, width: "85px" },
        { name: "Avg", selector: (r) => r.average?.toFixed(1), width: "75px" },
        { name: "Grade", cell: (r) => <span className={`badge bg-${noteColor[r.note]||"secondary"}`}>{r.note}</span>, width: "75px" },
    ] : [
        { name: "#", selector: (_, i) => i + 1, width: "60px" },
        { name: "Lesson", selector: (r) => r.lessonName, sortable: true },
        { name: "Term", selector: (r) => r.educationTerm?.replace("_", " ") },
        { name: "Midterm", selector: (r) => r.midtermExam, width: "85px" },
        { name: "Final", selector: (r) => r.finalExam, width: "85px" },
        { name: "Avg", selector: (r) => r.average?.toFixed(1), width: "75px" },
        { name: "Grade", cell: (r) => <span className={`badge bg-${noteColor[r.note]||"secondary"}`}>{r.note}</span>, width: "75px" },
    ];

    const meetColumns = [
        { name: "#", selector: (_, i) => i + 1, width: "60px" },
        { name: "Description", selector: (r) => r.description, grow: 2, wrap: true },
        { name: "Date", selector: (r) => r.date, width: "110px" },
        { name: "Start", selector: (r) => r.startTime, width: "80px" },
        { name: "Stop", selector: (r) => r.stopTime, width: "80px" },
        ...(isTeacher ? [{ name: "Students", selector: (r) => r.students?.length || 0, width: "80px" }] : [{ name: "Teacher", selector: (r) => r.teacherName }]),
    ];

    return (
        <div>
            <div className="page-header"><h4>Grades & Meetings</h4></div>
            <Tabs defaultActiveKey="grades" className="mb-3">
                <Tab eventKey="grades" title="My Grades">
                    <div className="table-card">
                        <DataTable columns={gradeColumns} data={grades} progressPending={loadingG} pagination paginationServer paginationTotalRows={total} onChangePage={(p) => { setPage(p-1); loadGrades(p-1); }} dense highlightOnHover responsive noDataComponent={<div className="p-4 text-muted">No grade records found.</div>}/>
                    </div>
                </Tab>
                <Tab eventKey="meets" title="Meetings">
                    <div className="table-card">
                        <DataTable columns={meetColumns} data={meets} progressPending={loadingM} pagination dense highlightOnHover responsive noDataComponent={<div className="p-4 text-muted">No meetings found.</div>}/>
                    </div>
                </Tab>
            </Tabs>
        </div>
    );
};

export default GradesMeetsPage;

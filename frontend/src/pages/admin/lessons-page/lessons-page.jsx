import React, { useEffect, useState } from "react";
import DataTable from "react-data-table-component";
import { useFormik } from "formik";
import * as Yup from "yup";
import Swal from "sweetalert2";
import { Modal, Tabs, Tab } from "react-bootstrap";
import { FiPlus, FiTrash2, FiEdit2, FiSearch } from "react-icons/fi";
import { searchLessons, saveLesson, deleteLesson } from "../../../services/lessonService";
import { getAllEducationTerms, saveEducationTerm, updateEducationTerm, deleteEducationTerm } from "../../../services/educationTermService";
import { getAllLessonPrograms, saveLessonProgram, deleteLessonProgram } from "../../../services/lessonProgramService";

const days = ["MONDAY","TUESDAY","WEDNESDAY","THURSDAY","FRIDAY","SATURDAY","SUNDAY"];

const LessonsPage = () => {
    const [lessons, setLessons] = useState([]);
    const [terms, setTerms] = useState([]);
    const [programs, setPrograms] = useState([]);
    const [loadingL, setLoadingL] = useState(false);
    const [loadingT, setLoadingT] = useState(false);
    const [loadingP, setLoadingP] = useState(false);
    const [modal, setModal] = useState(null);
    const [editTerm, setEditTerm] = useState(null);
    const [filterL, setFilterL] = useState("");
    const [filterT, setFilterT] = useState("");

    const loadLessons = async () => { setLoadingL(true); try { const r = await searchLessons(0,100); setLessons(r.data?.content||r.data||[]); } catch { setLessons([]); } finally { setLoadingL(false); } };
    const loadTerms  = async () => { setLoadingT(true); try { const r = await getAllEducationTerms(); setTerms(r.data||[]); } catch { setTerms([]); } finally { setLoadingT(false); } };
    const loadProgs  = async () => { setLoadingP(true); try { const r = await getAllLessonPrograms(); setPrograms(r.data||[]); } catch { setPrograms([]); } finally { setLoadingP(false); } };

    useEffect(() => { loadLessons(); loadTerms(); loadProgs(); }, []);

    const lessonForm = useFormik({
        initialValues: { lessonName:"", creditScore:"", isCompulsory:false },
        validationSchema: Yup.object({ lessonName:Yup.string().min(2).max(16).required("Required"), creditScore:Yup.number().required("Required") }),
        onSubmit: async (v,{resetForm,setSubmitting}) => { try { await saveLesson(v); resetForm(); setModal(null); loadLessons(); Swal.fire({icon:"success",title:"Saved!",timer:1500,showConfirmButton:false}); } catch(e){Swal.fire({icon:"error",title:"Error",text:e.response?.data?.message||"Failed.",confirmButtonColor:"#1e40af"});} finally{setSubmitting(false);} },
    });

    const termForm = useFormik({
        initialValues: { term:"", startDate:"", endDate:"", lastRegistrationDate:"" },
        validationSchema: Yup.object({ term:Yup.string().required("Required"), startDate:Yup.string().required("Required"), endDate:Yup.string().required("Required"), lastRegistrationDate:Yup.string().required("Required") }),
        onSubmit: async (v,{resetForm,setSubmitting}) => { try { if(editTerm) await updateEducationTerm(editTerm,v); else await saveEducationTerm(v); resetForm(); setModal(null); setEditTerm(null); loadTerms(); Swal.fire({icon:"success",title:"Saved!",timer:1500,showConfirmButton:false}); } catch(e){Swal.fire({icon:"error",title:"Error",text:e.response?.data?.message||"Failed.",confirmButtonColor:"#1e40af"});} finally{setSubmitting(false);} },
    });

    const progForm = useFormik({
        initialValues: { day:"", startTime:"", stopTime:"", lessonIdList:[], educationTermId:"" },
        validationSchema: Yup.object({ day:Yup.string().required("Required"), startTime:Yup.string().required("Required"), stopTime:Yup.string().required("Required"), educationTermId:Yup.number().required("Required") }),
        onSubmit: async (v,{resetForm,setSubmitting}) => { try { await saveLessonProgram({...v,lessonIdList:v.lessonIdList.map(Number),educationTermId:Number(v.educationTermId)}); resetForm(); setModal(null); loadProgs(); Swal.fire({icon:"success",title:"Saved!",timer:1500,showConfirmButton:false}); } catch(e){Swal.fire({icon:"error",title:"Error",text:e.response?.data?.message||"Failed.",confirmButtonColor:"#1e40af"});} finally{setSubmitting(false);} },
    });

    const delLesson  = (id) => Swal.fire({title:"Delete lesson?",icon:"warning",showCancelButton:true,confirmButtonColor:"#dc2626"}).then(async r=>{if(r.isConfirmed){try{await deleteLesson(id);loadLessons();}catch(e){Swal.fire({icon:"error",title:"Error",text:e.response?.data?.message||"Failed."});}}});
    const delTerm    = (id) => Swal.fire({title:"Delete term?",icon:"warning",showCancelButton:true,confirmButtonColor:"#dc2626"}).then(async r=>{if(r.isConfirmed){try{await deleteEducationTerm(id);loadTerms();}catch(e){Swal.fire({icon:"error",title:"Error",text:e.response?.data?.message||"Failed."});}}});
    const delProgram = (id) => Swal.fire({title:"Delete program?",icon:"warning",showCancelButton:true,confirmButtonColor:"#dc2626"}).then(async r=>{if(r.isConfirmed){try{await deleteLessonProgram(id);loadProgs();}catch(e){Swal.fire({icon:"error",title:"Error",text:e.response?.data?.message||"Failed."});}}});

    const openEditTerm = (row) => { setEditTerm(row.id); termForm.setValues({term:row.term,startDate:row.startDate,endDate:row.endDate,lastRegistrationDate:row.lastRegistrationDate}); setModal("term"); };
    const toggleLesson = (id) => { const l=progForm.values.lessonIdList; progForm.setFieldValue("lessonIdList",l.includes(id)?l.filter(x=>x!==id):[...l,id]); };

    const tf=(n,t="text")=>({type:t,className:`form-control ${termForm.touched[n]&&termForm.errors[n]?"is-invalid":""}`, ...termForm.getFieldProps(n)});
    const pf=(n,t="text")=>({type:t,className:`form-control ${progForm.touched[n]&&progForm.errors[n]?"is-invalid":""}`, ...progForm.getFieldProps(n)});

    const lessonCols=[
        {name:"#",selector:(_,i)=>i+1,width:"60px"},
        {name:"Name",selector:r=>r.lessonName,sortable:true,grow:2},
        {name:"Credits",selector:r=>r.creditScore,width:"90px"},
        {name:"Required",cell:r=>r.isCompulsory?<span className="badge bg-warning text-dark">Yes</span>:<span className="badge bg-secondary">No</span>,width:"90px"},
        {name:"Actions",cell:r=><button className="btn btn-sm btn-outline-danger" onClick={()=>delLesson(r.lessonId)}><FiTrash2 size={12}/></button>,width:"80px"},
    ];
    const termCols=[
        {name:"#",selector:(_,i)=>i+1,width:"60px"},
        {name:"Term",selector:r=>r.term?.replace("_"," "),sortable:true},
        {name:"Start",selector:r=>r.startDate},
        {name:"End",selector:r=>r.endDate},
        {name:"Last Reg.",selector:r=>r.lastRegistrationDate},
        {name:"Actions",cell:r=><div className="d-flex gap-1"><button className="btn btn-sm btn-outline-primary" onClick={()=>openEditTerm(r)}><FiEdit2 size={12}/></button><button className="btn btn-sm btn-outline-danger" onClick={()=>delTerm(r.id)}><FiTrash2 size={12}/></button></div>,width:"100px"},
    ];
    const progCols=[
        {name:"#",selector:(_,i)=>i+1,width:"60px"},
        {name:"Day",selector:r=>r.day,sortable:true,width:"120px"},
        {name:"Start",selector:r=>r.startTime,width:"90px"},
        {name:"Stop",selector:r=>r.stopTime,width:"90px"},
        {name:"Lessons",selector:r=>r.lessonName?.map(l=>l.lessonName).join(", "),grow:2,wrap:true},
        {name:"Term",selector:r=>r.educationTerm?.term?.replace("_"," ")},
        {name:"Actions",cell:r=><button className="btn btn-sm btn-outline-danger" onClick={()=>delProgram(r.lessonProgramId)}><FiTrash2 size={12}/></button>,width:"80px"},
    ];

    const TableHeader = ({title,onAdd,filter,setFilter,btnLabel}) => (
        <div className="p-3 border-bottom d-flex justify-content-between align-items-center">
            <div className="input-group" style={{maxWidth:280}}><span className="input-group-text bg-white border-end-0"><FiSearch size={14}/></span><input className="form-control border-start-0" placeholder={`Search ${title}...`} value={filter} onChange={e=>setFilter(e.target.value)}/></div>
            <button className="btn btn-primary btn-sm d-flex align-items-center gap-1" onClick={onAdd}><FiPlus size={14}/> {btnLabel}</button>
        </div>
    );

    return (
        <div>
            <div className="page-header"><h4>Lesson Management</h4></div>
            <Tabs defaultActiveKey="lessons" className="mb-3">
                <Tab eventKey="lessons" title="Lessons">
                    <div className="table-card"><TableHeader title="lessons" onAdd={()=>{lessonForm.resetForm();setModal("lesson");}} filter={filterL} setFilter={setFilterL} btnLabel="Add Lesson"/><DataTable columns={lessonCols} data={lessons.filter(r=>r.lessonName?.toLowerCase().includes(filterL.toLowerCase()))} progressPending={loadingL} pagination dense highlightOnHover responsive noDataComponent={<div className="p-4 text-muted">No lessons.</div>}/></div>
                </Tab>
                <Tab eventKey="terms" title="Education Terms">
                    <div className="table-card"><TableHeader title="terms" onAdd={()=>{setEditTerm(null);termForm.resetForm();setModal("term");}} filter={filterT} setFilter={setFilterT} btnLabel="Add Term"/><DataTable columns={termCols} data={terms.filter(r=>r.term?.toLowerCase().includes(filterT.toLowerCase()))} progressPending={loadingT} pagination dense highlightOnHover responsive noDataComponent={<div className="p-4 text-muted">No terms.</div>}/></div>
                </Tab>
                <Tab eventKey="programs" title="Lesson Programs">
                    <div className="table-card"><div className="p-3 border-bottom d-flex justify-content-end"><button className="btn btn-primary btn-sm d-flex align-items-center gap-1" onClick={()=>{progForm.resetForm();setModal("program");}}><FiPlus size={14}/> Add Program</button></div><DataTable columns={progCols} data={programs} progressPending={loadingP} pagination dense highlightOnHover responsive noDataComponent={<div className="p-4 text-muted">No programs.</div>}/></div>
                </Tab>
            </Tabs>

            <Modal show={modal==="lesson"} onHide={()=>setModal(null)} centered>
                <Modal.Header closeButton><Modal.Title>Add Lesson</Modal.Title></Modal.Header>
                <Modal.Body>
                    <form id="lf" onSubmit={lessonForm.handleSubmit} noValidate>
                        <div className="mb-3"><label className="form-label">Lesson Name</label><input className={`form-control ${lessonForm.touched.lessonName&&lessonForm.errors.lessonName?"is-invalid":""}`} {...lessonForm.getFieldProps("lessonName")}/>{lessonForm.touched.lessonName&&lessonForm.errors.lessonName&&<div className="invalid-feedback">{lessonForm.errors.lessonName}</div>}</div>
                        <div className="mb-3"><label className="form-label">Credit Score</label><input type="number" className={`form-control ${lessonForm.touched.creditScore&&lessonForm.errors.creditScore?"is-invalid":""}`} {...lessonForm.getFieldProps("creditScore")}/>{lessonForm.touched.creditScore&&lessonForm.errors.creditScore&&<div className="invalid-feedback">{lessonForm.errors.creditScore}</div>}</div>
                        <div className="form-check"><input type="checkbox" className="form-check-input" id="comp" checked={lessonForm.values.isCompulsory} onChange={e=>lessonForm.setFieldValue("isCompulsory",e.target.checked)}/><label className="form-check-label" htmlFor="comp">Compulsory</label></div>
                    </form>
                </Modal.Body>
                <Modal.Footer><button className="btn btn-outline-secondary" onClick={()=>setModal(null)}>Cancel</button><button className="btn btn-primary" form="lf" type="submit" disabled={lessonForm.isSubmitting}>{lessonForm.isSubmitting?"Saving...":"Save"}</button></Modal.Footer>
            </Modal>

            <Modal show={modal==="term"} onHide={()=>{setModal(null);setEditTerm(null);}} centered>
                <Modal.Header closeButton><Modal.Title>{editTerm?"Edit Term":"Add Term"}</Modal.Title></Modal.Header>
                <Modal.Body>
                    <form id="tf" onSubmit={termForm.handleSubmit} noValidate>
                        <div className="mb-3"><label className="form-label">Term</label><select className={`form-select ${termForm.touched.term&&termForm.errors.term?"is-invalid":""}`} {...termForm.getFieldProps("term")}><option value="">Select</option><option value="FALL_SEMESTER">Fall Semester</option><option value="SPRING_SEMESTER">Spring Semester</option></select>{termForm.touched.term&&termForm.errors.term&&<div className="invalid-feedback">{termForm.errors.term}</div>}</div>
                        {[["startDate","Start Date"],["endDate","End Date"],["lastRegistrationDate","Last Registration Date"]].map(([n,l])=>(
                            <div className="mb-3" key={n}><label className="form-label">{l}</label><input {...tf(n,"date")}/>{termForm.touched[n]&&termForm.errors[n]&&<div className="invalid-feedback">{termForm.errors[n]}</div>}</div>
                        ))}
                    </form>
                </Modal.Body>
                <Modal.Footer><button className="btn btn-outline-secondary" onClick={()=>{setModal(null);setEditTerm(null);}}>Cancel</button><button className="btn btn-primary" form="tf" type="submit" disabled={termForm.isSubmitting}>{termForm.isSubmitting?"Saving...":editTerm?"Update":"Save"}</button></Modal.Footer>
            </Modal>

            <Modal show={modal==="program"} onHide={()=>setModal(null)} size="lg" centered>
                <Modal.Header closeButton><Modal.Title>Add Lesson Program</Modal.Title></Modal.Header>
                <Modal.Body>
                    <form id="pf" onSubmit={progForm.handleSubmit} noValidate>
                        <div className="row g-3">
                            <div className="col-md-6"><label className="form-label">Day</label><select className={`form-select ${progForm.touched.day&&progForm.errors.day?"is-invalid":""}`} {...progForm.getFieldProps("day")}><option value="">Select</option>{days.map(d=><option key={d} value={d}>{d}</option>)}</select>{progForm.touched.day&&progForm.errors.day&&<div className="invalid-feedback">{progForm.errors.day}</div>}</div>
                            <div className="col-md-3"><label className="form-label">Start</label><input {...pf("startTime","time")}/></div>
                            <div className="col-md-3"><label className="form-label">Stop</label><input {...pf("stopTime","time")}/></div>
                            <div className="col-md-6"><label className="form-label">Education Term</label><select className={`form-select ${progForm.touched.educationTermId&&progForm.errors.educationTermId?"is-invalid":""}`} {...progForm.getFieldProps("educationTermId")}><option value="">Select</option>{terms.map(t=><option key={t.id} value={t.id}>{t.term?.replace("_"," ")} ({t.startDate})</option>)}</select>{progForm.touched.educationTermId&&progForm.errors.educationTermId&&<div className="invalid-feedback">{progForm.errors.educationTermId}</div>}</div>
                            <div className="col-12"><label className="form-label">Lessons</label><div className="border rounded p-2 d-flex flex-wrap gap-2" style={{maxHeight:130,overflowY:"auto"}}>{lessons.map(l=><div key={l.lessonId} className="form-check form-check-inline m-0"><input type="checkbox" className="form-check-input" id={`pl${l.lessonId}`} checked={progForm.values.lessonIdList.includes(l.lessonId)} onChange={()=>toggleLesson(l.lessonId)}/><label className="form-check-label" htmlFor={`pl${l.lessonId}`} style={{fontSize:".82rem"}}>{l.lessonName}</label></div>)}</div></div>
                        </div>
                    </form>
                </Modal.Body>
                <Modal.Footer><button className="btn btn-outline-secondary" onClick={()=>setModal(null)}>Cancel</button><button className="btn btn-primary" form="pf" type="submit" disabled={progForm.isSubmitting}>{progForm.isSubmitting?"Saving...":"Save Program"}</button></Modal.Footer>
            </Modal>
        </div>
    );
};

export default LessonsPage;

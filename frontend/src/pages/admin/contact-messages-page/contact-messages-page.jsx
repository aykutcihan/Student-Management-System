import React, { useEffect, useState } from "react";
import DataTable from "react-data-table-component";
import { FiSearch, FiMail } from "react-icons/fi";
import { Modal } from "react-bootstrap";
import { getAllContactMessages } from "../../../services/contactMessageService";

const ContactMessagesPage = () => {
    const [data, setData] = useState([]);
    const [loading, setLoading] = useState(false);
    const [filter, setFilter] = useState("");
    const [selected, setSelected] = useState(null);
    const [page, setPage] = useState(0);
    const [totalRows, setTotalRows] = useState(0);

    const load = async (p = 0) => {
        setLoading(true);
        try { const res = await getAllContactMessages(p, 10); setData(res.data?.content || res.data || []); setTotalRows(res.data?.totalElements || 0); }
        catch { setData([]); }
        finally { setLoading(false); }
    };

    useEffect(() => { load(); }, []);

    const filtered = data.filter((r) => `${r.name} ${r.email} ${r.subject}`.toLowerCase().includes(filter.toLowerCase()));

    const columns = [
        { name: "#", selector: (_, i) => i + 1, width: "60px" },
        { name: "Name", selector: (r) => r.name, sortable: true },
        { name: "Email", selector: (r) => r.email, sortable: true },
        { name: "Subject", selector: (r) => r.subject, grow: 2 },
        { name: "Date", selector: (r) => r.date, width: "120px" },
        { name: "Actions", cell: (r) => <button className="btn btn-sm btn-outline-primary d-flex align-items-center gap-1" onClick={() => setSelected(r)}><FiMail size={12}/> View</button>, width: "90px" },
    ];

    return (
        <div>
            <div className="page-header"><h4>Contact Messages</h4></div>
            <div className="table-card">
                <div className="p-3 border-bottom">
                    <div className="input-group" style={{ maxWidth: 320 }}>
                        <span className="input-group-text bg-white border-end-0"><FiSearch size={14} color="#64748b" /></span>
                        <input className="form-control border-start-0" placeholder="Search messages..." value={filter} onChange={(e) => setFilter(e.target.value)} />
                    </div>
                </div>
                <DataTable columns={columns} data={filtered} progressPending={loading} pagination paginationServer paginationTotalRows={totalRows} onChangePage={(p) => { setPage(p - 1); load(p - 1); }} dense highlightOnHover responsive noDataComponent={<div className="p-4 text-muted">No messages found.</div>} />
            </div>
            <Modal show={!!selected} onHide={() => setSelected(null)} centered>
                <Modal.Header closeButton><Modal.Title>Message from {selected?.name}</Modal.Title></Modal.Header>
                <Modal.Body>
                    <dl className="row mb-0">
                        <dt className="col-4 text-muted">From</dt><dd className="col-8">{selected?.name}</dd>
                        <dt className="col-4 text-muted">Email</dt><dd className="col-8"><a href={`mailto:${selected?.email}`}>{selected?.email}</a></dd>
                        <dt className="col-4 text-muted">Subject</dt><dd className="col-8">{selected?.subject}</dd>
                        <dt className="col-4 text-muted">Date</dt><dd className="col-8">{selected?.date}</dd>
                        <dt className="col-4 text-muted">Message</dt><dd className="col-8">{selected?.message}</dd>
                    </dl>
                </Modal.Body>
                <Modal.Footer>
                    <button className="btn btn-outline-secondary" onClick={() => setSelected(null)}>Close</button>
                    <a href={`mailto:${selected?.email}`} className="btn btn-primary d-flex align-items-center gap-2"><FiMail size={14}/> Reply</a>
                </Modal.Footer>
            </Modal>
        </div>
    );
};

export default ContactMessagesPage;

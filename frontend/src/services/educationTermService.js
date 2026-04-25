import api from "../api/api";

const BASE = "/educationTerms";

export const getAllEducationTerms = () => api.get(`${BASE}/getAll`);

export const searchEducationTerms = (page = 0, size = 10, sort = "startDate", type = "desc") =>
    api.get(`${BASE}/search`, { params: { page, size, sort, type } });

export const saveEducationTerm = (data) => api.post(`${BASE}/save`, data);

export const updateEducationTerm = (id, data) => api.put(`${BASE}/update/${id}`, data);

export const deleteEducationTerm = (id) => api.delete(`${BASE}/delete/${id}`);

export const getEducationTermById = (id) => api.get(`${BASE}/${id}`);

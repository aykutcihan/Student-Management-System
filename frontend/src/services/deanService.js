import api from "../api/api";

const BASE = "/dean";

export const getAllDeans = () => api.get(`${BASE}/getAll`);

export const searchDeans = (page = 0, size = 10, sort = "name", type = "desc") =>
    api.get(`${BASE}/search`, { params: { page, size, sort, type } });

export const saveDean = (data) => api.post(`${BASE}/save`, data);

export const updateDean = (id, data) => api.put(`${BASE}/update/${id}`, data);

export const deleteDean = (id) => api.delete(`${BASE}/delete/${id}`);

export const getDeanById = (id) => api.get(`${BASE}/getManagerById/${id}`);

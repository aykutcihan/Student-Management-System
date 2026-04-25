import api from "../api/api";

const BASE = "/vicedean";

export const getAllViceDeans = () => api.get(`${BASE}/getAll`);

export const searchViceDeans = (page = 0, size = 10, sort = "name", type = "desc") =>
    api.get(`${BASE}/search`, { params: { page, size, sort, type } });

export const saveViceDean = (data) => api.post(`${BASE}/save`, data);

export const updateViceDean = (id, data) => api.put(`${BASE}/update/${id}`, data);

export const deleteViceDean = (id) => api.delete(`${BASE}/delete/${id}`);

export const getViceDeanById = (id) => api.get(`${BASE}/getViceDeanById/${id}`);

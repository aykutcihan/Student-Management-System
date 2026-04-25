import api from "../api/api";

const BASE = "/admin";

export const getAllAdmins = (page = 0, size = 10, sort = "name", type = "desc") =>
    api.get(`${BASE}/getAll`, { params: { page, size, sort, type } });

export const saveAdmin = (data) => api.post(`${BASE}/save`, data);

export const deleteAdmin = (id) => api.delete(`${BASE}/delete/${id}`);

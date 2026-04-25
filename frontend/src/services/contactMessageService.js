import api from "../api/api";

const BASE = "/contactMessages";

export const saveContactMessage = (data) => api.post(`${BASE}/save`, data);

export const getAllContactMessages = (page = 0, size = 10, sort = "date", type = "desc") =>
    api.get(`${BASE}/getAll`, { params: { page, size, sort, type } });

export const searchByEmail = (email, page = 0, size = 10) =>
    api.get(`${BASE}/searchByEmail`, { params: { email, page, size, sort: "date", type: "desc" } });

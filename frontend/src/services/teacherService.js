import api from "../api/api";

const BASE = "/teachers";

export const getAllTeachers = () => api.get(`${BASE}/getAll`);

export const searchTeachers = (page = 0, size = 10, sort = "name", type = "desc") =>
    api.get(`${BASE}/search`, { params: { page, size, sort, type } });

export const saveTeacher = (data) => api.post(`${BASE}/save`, data);

export const updateTeacher = (id, data) => api.put(`${BASE}/update/${id}`, data);

export const deleteTeacher = (id) => api.delete(`${BASE}/delete/${id}`);

export const getTeacherById = (id) => api.get(`${BASE}/getSavedTeacherById/${id}`);

export const getAdvisoryTeachers = () => api.get("/advisorTeacher/getAll");

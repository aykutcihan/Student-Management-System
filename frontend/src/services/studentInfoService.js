import api from "../api/api";

const BASE = "/studentInfo";

export const saveStudentInfo = (data) => api.post(`${BASE}/save`, data);

export const updateStudentInfo = (id, data) => api.put(`${BASE}/update/${id}`, data);

export const deleteStudentInfo = (id) => api.delete(`${BASE}/delete/${id}`);

export const getStudentInfoForTeacher = (username, page = 0, size = 10) =>
    api.get(`${BASE}/getAllForTeacher`, { headers: { username }, params: { page, size } });

export const getStudentInfoForStudent = (username, page = 0, size = 10) =>
    api.get(`${BASE}/getAllForStudent`, { headers: { username }, params: { page, size } });

export const searchStudentInfo = (page = 0, size = 10, sort = "id", type = "desc") =>
    api.get(`${BASE}/search`, { params: { page, size, sort, type } });

export const getStudentInfoByStudentId = (studentId) =>
    api.get(`${BASE}/getByStudentId/${studentId}`);

import api from "../api/api";

const BASE = "/students";

export const getAllStudents = () => api.get(`${BASE}/getAll`);

export const searchStudents = (page = 0, size = 10, sort = "name", type = "desc") =>
    api.get(`${BASE}/search`, { params: { page, size, sort, type } });

export const saveStudent = (data) => api.post(`${BASE}/save`, data);

export const updateStudent = (id, data) => api.put(`${BASE}/update/${id}`, data);

export const deleteStudent = (id) => api.delete(`${BASE}/delete/${id}`);

export const changeStudentStatus = (id, status) =>
    api.get(`${BASE}/changeStatus`, { params: { id, status } });

export const chooseLesson = (data, username) =>
    api.post(`${BASE}/chooseLesson`, data, { headers: { username } });

export const getStudentsByAdvisor = (username) =>
    api.get(`${BASE}/getAllByAdvisorId`, { headers: { username } });

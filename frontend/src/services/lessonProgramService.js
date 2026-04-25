import api from "../api/api";

const BASE = "/lessonPrograms";

export const getAllLessonPrograms = () => api.get(`${BASE}/getAll`);

export const getAllUnassigned = () => api.get(`${BASE}/getAllUnassigned`);

export const getAllAssigned = () => api.get(`${BASE}/getAllAssigned`);

export const saveLessonProgram = (data) => api.post(`${BASE}/save`, data);

export const deleteLessonProgram = (id) => api.delete(`${BASE}/delete/${id}`);

export const getLessonProgramsByTeacher = (username) =>
    api.get(`${BASE}/getAllLessonProgramByTeacher`, { headers: { username } });

export const searchLessonPrograms = (page = 0, size = 10, sort = "day", type = "asc") =>
    api.get(`${BASE}/search`, { params: { page, size, sort, type } });

import api from "../api/api";

const BASE = "/lessons";

export const searchLessons = (page = 0, size = 100, sort = "lessonName", type = "asc") =>
    api.get(`${BASE}/search`, { params: { page, size, sort, type } });

export const saveLesson = (data) => api.post(`${BASE}/save`, data);

export const deleteLesson = (id) => api.delete(`${BASE}/delete/${id}`);

export const getLessonsByIds = (lessonId) =>
    api.get(`${BASE}/getAllLessonByLessonId`, { params: { lessonId } });

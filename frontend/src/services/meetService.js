import api from "../api/api";

const BASE = "/meet";

export const getAllMeets = () => api.get(`${BASE}/getAll`);

export const saveMeet = (data) => api.post(`${BASE}/save`, data);

export const updateMeet = (id, data) => api.put(`${BASE}/update/${id}`, data);

export const deleteMeet = (id) => api.delete(`${BASE}/delete/${id}`);

export const getMeetsByTeacher = (username) =>
    api.get(`${BASE}/getAllMeetByAdvisorTeacherAsList`, { headers: { username } });

export const getMeetsByStudent = (username) =>
    api.get(`${BASE}/getAllMeetByStudent`, { headers: { username } });

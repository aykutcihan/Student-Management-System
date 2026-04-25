import axios from "axios";
import { EncryptStorage } from "encrypt-storage";

export const encryptStorage = new EncryptStorage("school-mgmt-secret-key");

const api = axios.create({
    baseURL: "http://localhost:8081",
});

api.interceptors.request.use((config) => {
    const token = encryptStorage.getItem("token");
    if (token) {
        config.headers.Authorization = `Bearer ${token}`;
    }
    return config;
});

api.interceptors.response.use(
    (response) => response,
    (error) => {
        if (error.response?.status === 401) {
            encryptStorage.removeItem("token");
            window.location.href = "/login";
        }
        return Promise.reject(error);
    }
);

export default api;

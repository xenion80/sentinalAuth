import axios from "axios";

const API = "http://localhost:8080/auth";

export const registerUser = (data) => {
    return axios.post(`${API}/register`, data);
};

export const loginUser = (data) => {
    return axios.post(`${API}/login`, data, {
        withCredentials: true
    });
};

export const forgotPassword = (email) => {
    return axios.post(`${API}/forgot-password`, {
        email
    });
};

export const resetPassword = (token, newPassword) => {
    return axios.post(`${API}/reset-password`, {
        token,
        newPassword
    });
};

export const verifyEmail = (token) => {
    return axios.get(`${API}/verify-email?token=${token}`);
};
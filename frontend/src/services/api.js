import axios from "axios";

const API_URL = 'http://localhost:8080/api';

const api = axios.create({
    baseURL: API_URL
});

api.interceptors.request.use((config) => {
    const userId = localStorage.getItem('userId');
    const token = localStorage.getItem('token');

    if (token) {
        config.headers['Authorization'] = `Bearer ${token}`;
    }

    if (userId) {
        config.headers['X-User-ID'] = userId;
    }
    return config;
});

// Todo API functions
export const getTodos = () => api.get('/todos');
export const addTodo = (todo) => api.post('/todos', todo);
export const getTodoDetail = (id) => api.get(`/todos/${id}`);
export const updateTodo = (id, todo) => api.put(`/todos/${id}`, todo);
export const deleteTodo = (id) => api.delete(`/todos/${id}`);
export const getTodosByStatus = (completed) => api.get(`/todos/status/${completed}`);
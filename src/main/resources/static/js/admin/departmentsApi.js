// API URL для работы с отделами
const API_URL = "http://localhost:8080/departments";

// Получение всех отделов
const getDepartments = async () => {
    try {
        const response = await axios.get(API_URL);
        return response.data;
    } catch (error) {
        throw new Error("Ошибка при получении списка отделов: " + error.response.data.message);
    }
};

// Добавление нового отдела
const addDepartment = async (departmentData) => {
    try {
        const formData = new URLSearchParams(departmentData).toString();
        await axios.post(API_URL, formData, {
            headers: { 'Content-Type': 'application/x-www-form-urlencoded' }
        });
    } catch (error) {
        throw new Error("Ошибка при добавлении отдела: " + error.response.data.message);
    }
};

// Обновление данных отдела
const updateDepartment = async (departmentData) => {
    try {
        const formData = new URLSearchParams(departmentData).toString();
        await axios.put(API_URL, formData, {
            headers: { 'Content-Type': 'application/x-www-form-urlencoded' }
        });
    } catch (error) {
        throw new Error("Ошибка при обновлении данных отдела: " + error.response.data.message);
    }
};

// Удаление отдела
const deleteDepartment = async (departmentId) => {
    try {
        const formData = new URLSearchParams({ id: departmentId }).toString();
        await axios.delete(`${API_URL}?${formData}`);
    } catch (error) {
        throw new Error("Ошибка при удалении отдела: " + error.response.data.message);
    }
};

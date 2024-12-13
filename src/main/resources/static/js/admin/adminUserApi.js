const API_URL = 'http://localhost:8080';

const showError = (message) => {
    const errorMessage = document.getElementById("error-message");
    const errorText = document.getElementById("error-text");
    
    errorText.textContent = message;
    errorMessage.classList.add("visible");
};
document.getElementById("error-close-btn").addEventListener("click", () => {
    const errorMessage = document.getElementById("error-message");
    errorMessage.classList.remove("visible");
});

const getUsers = async () => {
    try {
        const response = await axios.get(`${API_URL}/users`);
        return response.data;
    } catch (error) {
        console.error('Ошибка при получении пользователей:', error.response.data.message);
        showError('Ошибка при получении пользователей:', error.response.data.message);
    }
};

const deleteUser = async (userId) => {
    try {
        const response = await axios.delete(`${API_URL}/users`,{
            params: {id: userId},
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded',
            }
        });
        console.log(`Пользователь с ID ${userId} удален`);
    } catch (error) {
        console.error(`Ошибка при удалении пользователя ${userId}:`, error.response.data.message);
        showError('Ошибка при удалении пользователя:', error.response.data.message);
    }
};

const getRoles = async () => {
    try {
        const response = await axios.get(`${API_URL}/roles/allroles`);
        return response.data;
    } catch (error) {
        console.error('Ошибка при получении ролей:', error.response.data.message);
        showError('Ошибка при получении ролей:', error.response.data.message);
    }
};

const getDepartments = async () => {
    try {
        const response = await axios.get(`${API_URL}/departments`);
        return response.data;
    } catch (error) {
        showError('Ошибка при получении отделов:', error.response.data.message);
        console.error('Ошибка при получении отделов:', error.response.data.message);
    }
};

const addUser = async (userData) => {
    try {
        const formData = new URLSearchParams(userData).toString();
        const response = await axios.post(`${API_URL}/users`, formData,{
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded',
            }
        });
        return response.data;
    } catch (error) {
        showError('Ошибка при добавлении пользователя:'+ error.response.data.message);
        console.error('Ошибка при добавлении пользователя:', error.response.data.message);
    }
};

const getUserByName = async (name) => {
    try {
        const response = await axios.get(`${API_URL}/users/${name}`);
        return response.data;
    } catch (error) {
        showError('Ошибка при поиске пользователя:', error.response.data.message);
        console.error('Ошибка при поиске пользователя:', error.response.data.message);
    }
};

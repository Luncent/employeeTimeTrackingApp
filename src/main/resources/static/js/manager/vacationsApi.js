const VACATION_API_URL = "http://localhost:8080/vacations";
const STATISTICS_API_URL = "http://localhost:8080/statistics";

const errorCloseBtn = document.getElementById("error-close-btn");
const errorMessage = document.getElementById("error-message");

// Функция для отображения сообщений об ошибках
const showError = (message) => {
    const errorText = document.getElementById("error-text");
    console.log(message+"asd")
    errorText.textContent = message;
    errorMessage.classList.add("visible");
};

errorCloseBtn.addEventListener("click", ()=>{
    errorMessage.classList.remove("visible");
})

// Получить заявки на отпуск
const getVacations = async (departmentId) => {
    try {
        const params = new URLSearchParams({ departmentId }).toString();
        const response = await axios.get(`${VACATION_API_URL}?${params}`);
        return response.data;
    } catch (error) {
        console.error("Ошибка при загрузке заявок на отпуск:", error);
        showError(error.response?.data?.message ?? error.message);
        return [];
    }
};

// Обновить статус отпуска
const updateVacationStatus = async (vacationId, status) => {
    try {
        const params = new URLSearchParams({ vacationId, status }).toString();
        await axios.put(VACATION_API_URL, params, {
            headers: { 'Content-Type': 'application/x-www-form-urlencoded' }
        });
    } catch (error) {
        console.error("Ошибка при обновлении статуса:", error);
        showError(error.response?.data?.message ?? error.message);
    }
};

// Получить статистику сотрудника
const getUserStatistics = async (userName) => {
    try {
        const response = await axios.get(`${STATISTICS_API_URL}/${userName}`);
        return response.data;
    } catch (error) {
        console.error("Ошибка при получении статистики:", error);
        showError(error.response?.data?.message ?? error.message);
        return null;
    }
};

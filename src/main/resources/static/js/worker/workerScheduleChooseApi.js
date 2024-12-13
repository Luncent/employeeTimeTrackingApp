//URL API
const API_BASE_URL = "http://localhost:8080";

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

const getAvailableShifts = async (departmentId) => {
    try{
        const response = await axios.get(`${API_BASE_URL}/departmentDaysShifts`, {
            params: { departmentId }
        });
        return response.data.daySchedulesDTOList;
    }catch (error) {
        console.error("Ошибка при загрузке расписания:", error);
        showError(error.response?.data?.message ?? error.message);
        return [];
    }
};

const getChosenShifts = async (userName) => {
    try{
         const response = await axios.get(`${API_BASE_URL}/userSchedules/chosen`, {
            params: { userName }
        });
        return response.data;
    }catch (error) {
        console.error("Ошибка при загрузке выбанного расписания:", error);
        showError(error.response?.data?.message ?? error.message);
        return [];
    }
};

const addChosenShift = async (userName, departmentDayScheduleId) => {
    try{
        console.log(userName+" "+departmentDayScheduleId);
        const req_data = new URLSearchParams({
            userName : userName,
            departmentDayScheduleId : departmentDayScheduleId
        })
        await axios.post(`${API_BASE_URL}/userSchedules`, req_data,{
            headers: { "Content-Type": "application/x-www-form-urlencoded" }
        });
    }
    catch (error) {
        console.error("Ошибка при выборе смены:", error);
        showError(error.response?.data?.message ?? error.message);
        return [];
    }
};

const deleteChosenShift = async (userScheduleId) => {
    try{
        await axios.delete(`${API_BASE_URL}/userSchedules`, {
            params: { userScheduleId }
        });
    }catch (error) {
        console.error("Ошибка при удалении смены:", error);
        showError(error.response?.data?.message ?? error.message);
        return [];
    }
};

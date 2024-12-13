const API_URL = "http://localhost:8080/holidays";

const getHolidays = async () => {
    try {
        const response = await axios.get(API_URL);
        return response.data;
    } catch (error) {
        showError("Ошибка при получении списка праздников. " + error.response.data.message);
        console.error(error);
    }
};

const addHoliday = async (holidayData) => {
    try {
        const formData = new URLSearchParams(holidayData).toString();
        await axios.post(API_URL, formData, {
            headers: { 'Content-Type': 'application/x-www-form-urlencoded' }
        });
    } catch (error) {
        showError("Ошибка при добавлении праздника. " + error.response.data.message);
        console.error(error);
    }
};

const updateHoliday = async (holidayData) => {
    try {
        const formData = new URLSearchParams(holidayData).toString();
        await axios.put(API_URL, formData, {
            headers: { 'Content-Type': 'application/x-www-form-urlencoded' }
        });
    } catch (error) {
        showError("Ошибка при обновлении данных праздника. "+error.response.data.message);
        console.error(error);
    }
};

const deleteHoliday = async (holidayId) => {
    try {
        const formData = new URLSearchParams({ id: holidayId }).toString();
        await axios.delete(`${API_URL}?${formData}`);
    } catch (error) {
        showError("Ошибка при удалении праздника. " +error.response.data.message);
        console.error(error);
    }
};

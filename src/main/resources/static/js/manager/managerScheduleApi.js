const API_URL = "http://localhost:8080/departmentDaysShifts";

function getCookie(name) {
    const cookies = document.cookie.split('; ');

    for (let i = 0; i < cookies.length; i++) {
        const cookie = cookies[i];

        const [cookieName, cookieValue] = cookie.split('=');

        if (cookieName === name) {
            return decodeURIComponent(cookieValue);
        }
    }

    return null;
}

const departmentId = getCookie('departmentId');

async function getDepartmentSchedule() {
    try{
        const params = new URLSearchParams({ departmentId });
        const response = await axios.get(`${API_URL}?${params}`);
        return response.data;
    }
    catch (error) {
        console.error("Ошибка при загрузке расписания:", error);
        showError(error.response?.data?.message ?? error.message);
        return [];
    }
}

async function deleteSchedule(dayShiftId) {
    try{
        console.log("Удаление "+ dayShiftId);
        const params = new FormData(); 
        params.append('dayShiftId', dayShiftId); 
        await axios.delete(API_URL, { 
            data: params 
        });
    }catch (error) {
        console.error("Ошибка при удалении:", error);
        showError(error.response?.data?.message ?? error.message);
    }
}

async function addSchedule(dayName, start, end) {
    try{
        const params = new URLSearchParams({
            departmentId,
            dayName,
            start,
            end
        });
        await axios.post(API_URL, params, {
            headers: { "Content-Type": "application/x-www-form-urlencoded" }
        });
    } catch (error) {
        console.error("Ошибка при добавлении расписания:", error);
        showError(error.response?.data?.message ?? error.message);
        return null;
    }
}

const apiBaseUrl = 'http://localhost:8080/work';

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

const api = axios.create({
    baseURL: apiBaseUrl,
    headers: {
        'Content-Type': 'application/x-www-form-urlencoded',
    },
});

const getWorkStatus = async (userName) => {
    try {
        console.log(userName);

        const response = await axios.get(apiBaseUrl, {
            params:{userName : userName},
            headers: {
                'Content-Type': 'multipart/form-data'
            }
        });

        return response.data;
    } catch (error) {
        console.error("Ошибка при получении состояния работы:", error);
        showError("Ошибка при получении состояния работы:" + (error.response?.data?.message ?? error.message));
        return null;
    }
};

const getSchedule = async (userName) => {
    try {
        const response = await api.get(`/schedule/${userName}`);
        return response.data;
    } catch (error) {
        console.error("Ошибка при получении расписания:", error);
        showError("Ошибка при получении расписания:" + (error.response?.data?.message ?? error.message));
        return null;
    }
};

const startWork = async (userName) => {
    try {
        await api.post('/start', { userName });
    } catch (error) {
        console.error("Ошибка при запуске работы:", error);
        showError("Ошибка при запуске работы:" + (error.response?.data?.message ?? error.message));
    }
};

const pauseWork = async (userName) => {
    try {
        await api.post('/pause', { userName });
    } catch (error) {
        showError("Не удалось поствить работу на паузу." + (error.response?.data?.message ?? error.message));
        console.error("Ошибка при постановке работы на паузу:", error);
    }
};

const resumeWork = async (userName) => {
    try {
        await api.post('/resume', { userName });
    } catch (error) {
        showError("Не удалось возобновить." + (error.response?.data?.message ?? error.message));
        console.error("Ошибка при возобновлении работы:", error);
    }
};

const finishWork = async (userName) => {
    try {
        await api.post('/finish', { userName });
    } catch (error) {
        showError("Не удалось завершить работу." + (error.response?.data?.message ?? error.message));
        console.error("Ошибка при завершении работы:", error);
    }
};


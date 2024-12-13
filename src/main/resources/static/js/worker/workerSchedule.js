const scheduleRows = {
    "Понедельник": "monday",
    "Вторник": "tuesday",
    "Среда": "wednesday",
    "Четверг": "thursday",
    "Пятница": "friday",
    "Суббота": "saturday",
    "Воскресенье": "sunday"
};

let currentWeekOffset = 0;

function getCookie(name) {
    const cookies = document.cookie.split('; ');

    for (let i = 0; i < cookies.length; i++) {
        const cookie = cookies[i];

        const [cookieName, cookieValue] = cookie.split('=');

        if (cookieName === name) {
            return decodeURIComponent(cookieValue.replace(/\+/g, ' '));
        }
    }

    return null;
}

const userName = getCookie('username');

const loadSchedule = async () => {
    const scheduleData = await getScheduleData(userName, currentWeekOffset);
    displaySchedule(scheduleData);
};

const getScheduleData = async (userName, weeksToSkip) => {
    try {
        const params = new URLSearchParams();
        params.append("userName", userName);
        params.append("weeksToSkip", weeksToSkip);

        const response = await fetch(`http://localhost:8080/userSchedules?${params.toString()}`);
        return await response.json();
    } catch (error) {
        console.error("Ошибка загрузки данных:", error);
        return [];
    }
};

const displaySchedule = (data) => {
    const daysSchedules = data.daysSchedules || [];

    daysSchedules.forEach((schedule) => {
        const dayName = scheduleRows[schedule.dayName];
        const dayElement = document.getElementById(`${dayName}-schedule`);
        const dateElement = document.getElementById(`${dayName}-date`);

        if (dayElement && dateElement) {
            dateElement.textContent = new Date(schedule.date).toLocaleDateString('ru-RU');
            dayElement.textContent = schedule.holiday 
                ? "Выходной" 
                : `${schedule.start} - ${schedule.end}`;
        }
    });
};

document.getElementById("prev-week").addEventListener("click", () => {
    currentWeekOffset--;
    loadSchedule();
});

document.getElementById("next-week").addEventListener("click", () => {
    currentWeekOffset++;
    loadSchedule();
});

loadSchedule();

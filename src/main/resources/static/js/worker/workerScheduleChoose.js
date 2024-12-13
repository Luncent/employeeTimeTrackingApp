// Словарь для строк расписания
const scheduleRows = {
    "Понедельник": "monday",
    "Вторник": "tuesday",
    "Среда": "wednesday",
    "Четверг": "thursday",
    "Пятница": "friday",
    "Суббота": "saturday",
    "Воскресенье": "sunday"
};

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

const departmentId = getCookie('departmentId');
const userName = getCookie('username');

// Загрузка доступных смен
const loadAvailableShifts = async () => {
    const availableSchedule = await getAvailableShifts(departmentId);

    for (let day in scheduleRows) {
        document.getElementById(`available-${scheduleRows[day]}-row`).innerHTML = "";
    }

    availableSchedule.forEach(daySchedule => {
        const dayName = daySchedule.dayName;

        daySchedule.schedules.forEach(shift => {
            const shiftElement = document.createElement("div");
            shiftElement.className = "schedule-item";
            shiftElement.innerHTML = `
                <span>${shift.start} - ${shift.end}</span>
                <button class="select-btn" data-id="${shift.id}">Выбрать</button>
            `;
            document.getElementById(`available-${scheduleRows[dayName]}-row`).appendChild(shiftElement);
        });
    });

    attachSelectEvents();
};

// Загрузка выбранных смен
const loadChosenShifts = async () => {
    const chosenSchedule = await getChosenShifts(userName);

    // Очистить старые смены
    for (let day in scheduleRows) {
        document.getElementById(`chosen-${scheduleRows[day]}-row`).innerHTML = "";
    }

    chosenSchedule.forEach(item => {
        const dayName = item.dayShift.dayName;
        const shiftElement = document.createElement("div");
        shiftElement.className = "schedule-item";
        shiftElement.innerHTML = `
            <span>${item.dayShift.start} - ${item.dayShift.end}</span>
            <button class="delete-btn" data-id="${item.id}">Удалить</button>
        `;
        document.getElementById(`chosen-${scheduleRows[dayName]}-row`).appendChild(shiftElement);
    });

    attachDeleteEvents();
};

const attachSelectEvents = () => {
    document.querySelectorAll(".select-btn").forEach(button => {
        button.addEventListener("click", async () => {
            const id = button.dataset.id;
            await addChosenShift(userName, id);
            await loadAvailableShifts();
            await loadChosenShifts();
        });
    });
};

// Обработчики для удаления смен
const attachDeleteEvents = () => {
    document.querySelectorAll(".delete-btn").forEach(button => {
        button.addEventListener("click", async () => {
            const id = button.dataset.id;
            await deleteChosenShift(id);
            await loadChosenShifts();
        });
    });
};

loadAvailableShifts();
loadChosenShifts();

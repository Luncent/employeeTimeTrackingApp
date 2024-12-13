const scheduleRows = {
    "Понедельник": document.getElementById("monday-row"),
    "Вторник": document.getElementById("tuesday-row"),
    "Среда": document.getElementById("wednesday-row"),
    "Четверг": document.getElementById("thursday-row"),
    "Пятница": document.getElementById("friday-row"),
    "Суббота": document.getElementById("saturday-row"),
    "Воскресенье": document.getElementById("sunday-row")
};

const addScheduleForm = document.querySelector("#add-schedule-form");
const incompleteMessage = document.getElementById("incomplete-message");
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

const loadSchedule = async () => {
    const scheduleData = await getDepartmentSchedule();

    for (let row in scheduleRows) {
        scheduleRows[row].innerHTML = ""; 
    }

    if (!scheduleData.isComplete) {
        incompleteMessage.classList.remove("hidden");
    } else {
        incompleteMessage.classList.add("hidden");
    }

    scheduleData.daySchedulesDTOList.forEach(daySchedule => {
        daySchedule.schedules.forEach(schedule => {
            const scheduleItem = document.createElement("div");
            scheduleItem.className = "schedule-item";
            scheduleItem.innerHTML = `
                <span>${schedule.start} - ${schedule.end}</span>
                <button class="delete-btn" data-id="${schedule.id}">Удалить</button>
            `;
            scheduleRows[daySchedule.dayName].appendChild(scheduleItem);
            console.log('Adding '+daySchedule.dayName);
        });
    });

    attachDeleteEventListeners();
};

const attachDeleteEventListeners = () => {
    document.querySelectorAll(".delete-btn").forEach(button => {
        button.addEventListener("click", async (e) => {
            const dayShiftId = e.target.dataset.id;
            console.log(dayShiftId);
            await deleteSchedule(dayShiftId);
            loadSchedule();
        });
    });
};

addScheduleForm.addEventListener("submit", async (e) => {
    e.preventDefault();

    const dayName = document.getElementById("day-name").value;
    const start = document.getElementById("start-time").value;
    const end = document.getElementById("end-time").value;

    await addSchedule(dayName, start, end);
    loadSchedule();
    addScheduleForm.reset();
});

loadSchedule();

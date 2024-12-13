const holidaysTable = document.querySelector("#holidays-table tbody");
const addHolidayForm = document.querySelector("#add-holiday-form");
const holidayNameInput = document.querySelector("#holiday-name");
const holidayDateInput = document.querySelector("#holiday-date");
const editModal = document.getElementById("edit-modal");
const editHolidayForm = document.getElementById("edit-holiday-form");
const editHolidayName = document.getElementById("edit-holiday-name");
const editHolidayDate = document.getElementById("edit-holiday-date");
const editHolidayId = document.getElementById("edit-holiday-id");
const cancelBtn = document.getElementById("cancel-btn");

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

const openEditModal = (holidayId, holidayName, holidayDate) => {
    editHolidayName.value = holidayName;
    editHolidayDate.value = holidayDate;
    editHolidayId.value = holidayId;
    editModal.classList.remove("hidden");
};

const closeEditModal = () => {
    editModal.classList.add("hidden");
};

cancelBtn.addEventListener("click", closeEditModal);

// Обработчик редактирования формы
editHolidayForm.addEventListener("submit", async (e) => {
    e.preventDefault();

    const id = editHolidayId.value;
    const name = editHolidayName.value.trim();
    const date = editHolidayDate.value.trim();

    if (!name || !date) {
        showError("Все поля должны быть заполнены.");
        return;
    }

    try {
        await updateHoliday({ id, name, date });
        closeEditModal();
        loadHolidays();
    } catch (error) {
        console.error("Ошибка обновления праздника:", error);
        showError("Не удалось обновить праздник.");
    }
});

// Загрузка праздников
const loadHolidays = async () => {
    try {
        const holidays = await getHolidays();
        holidaysTable.innerHTML = '';

        holidays.forEach(holiday => {
            const row = document.createElement("tr");
            row.innerHTML = `
                <td>${holiday.name}</td>
                <td>${holiday.date}</td>
                <td>
                    <button class="edit-btn" data-id="${holiday.id}" data-name="${holiday.name}" data-date="${holiday.date}">Изменить</button>
                    <button class="delete-btn" data-id="${holiday.id}">Удалить</button>
                </td>
            `;
            holidaysTable.appendChild(row);
        });

        attachHolidayEventListeners();
    } catch (error) {
        console.error("Ошибка загрузки праздников:", error);
        showError("Не удалось загрузить список праздников.");
    }
};

const attachHolidayEventListeners = () => {
    const deleteButtons = document.querySelectorAll(".delete-btn");
    const editButtons = document.querySelectorAll(".edit-btn");

    deleteButtons.forEach(button => {
        button.addEventListener("click", async (e) => {
            const holidayId = e.target.dataset.id;
            try {
                await deleteHoliday(holidayId);
                loadHolidays();
            } catch (error) {
                console.error("Ошибка удаления праздника:", error);
                showError("Не удалось удалить праздник.");
            }
        });
    });

    editButtons.forEach((button) => {
        button.addEventListener("click", (e) => {
            const holidayId = e.target.dataset.id;
            const holidayName = e.target.dataset.name;
            const holidayDate = e.target.dataset.date;
            openEditModal(holidayId, holidayName, holidayDate);
        });
    });
};

// Обработчик добавления праздника
addHolidayForm.addEventListener("submit", async (e) => {
    e.preventDefault();

    const name = holidayNameInput.value.trim();
    const date = holidayDateInput.value.trim();

    if (!name || !date) {
        showError("Все поля должны быть заполнены.");
        return;
    }

    try {
        await addHoliday({ name, date });
        addHolidayForm.reset();
        loadHolidays();
    } catch (error) {
        console.error("Ошибка добавления праздника:", error);
        showError("Не удалось добавить праздник.");
    }
});

// Загрузка праздников при запуске
loadHolidays();

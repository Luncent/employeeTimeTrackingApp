// Получаем ссылки на DOM элементы
const departmentsTable = document.querySelector("#departments-table tbody");
const addDepartmentForm = document.querySelector("#add-department-form");
const departmentNameInput = document.querySelector("#department-name");
const editModal = document.getElementById("edit-modal");
const editDepartmentForm = document.getElementById("edit-department-form");
const editDepartmentName = document.getElementById("edit-department-name");
const editDepartmentId = document.getElementById("edit-department-id");
const cancelBtn = document.getElementById("cancel-btn");
const errorCloseBtn = document.getElementById("error-close-btn");
const errorMessage = document.getElementById("error-message");

// Функция для отображения сообщений об ошибках
const showError = (message) => {
    const errorText = document.getElementById("error-text");
    
    errorText.textContent = message;
    errorMessage.classList.add("visible");
};

errorCloseBtn.addEventListener("click", ()=>{
    errorMessage.classList.remove("visible");
})

// Функция для открытия модального окна для редактирования
const openEditModal = (departmentId, departmentName) => {
    editDepartmentName.value = departmentName;
    editDepartmentId.value = departmentId;
    editModal.classList.remove("hidden");
};

// Функция для закрытия модального окна
const closeEditModal = () => {
    editModal.classList.add("hidden");
};

// Загрузка списка отделов в таблицу
const loadDepartments = async () => {
    try {
        const departments = await getDepartments();
        departmentsTable.innerHTML = '';

        departments.forEach(department => {
            const row = document.createElement("tr");
            row.innerHTML = `
                <td>${department.name}</td>
                <td>
                    <button class="edit-btn" data-id="${department.id}" data-name="${department.name}">Изменить</button>
                    <button class="delete-btn" data-id="${department.id}">Удалить</button>
                </td>
            `;
            departmentsTable.appendChild(row);
        });

        // Привязываем обработчики событий к кнопкам редактирования и удаления
        attachDepartmentEventListeners();
    } catch (error) {
        console.error("Ошибка загрузки отделов:", error);
        showError(error.message);
    }
};

// Привязка обработчиков событий к кнопкам
const attachDepartmentEventListeners = () => {
    const deleteButtons = document.querySelectorAll(".delete-btn");
    const editButtons = document.querySelectorAll(".edit-btn");

    deleteButtons.forEach(button => {
        button.addEventListener("click", async (e) => {
            const departmentId = e.target.dataset.id;
            const userConfirmed = window.confirm("Вы уверены, что хотите удалить этот отдел?");

            if (userConfirmed) {
                try {
                    await deleteDepartment(departmentId);
                    loadDepartments();
                } catch (error) {
                    console.error("Ошибка удаления отдела:", error);
                    showError(error.message);
                }
            }
        });
    });

    editButtons.forEach((button) => {
        button.addEventListener("click", (e) => {
            const departmentId = e.target.dataset.id;
            const departmentName = e.target.dataset.name;
            openEditModal(departmentId, departmentName);
        });
    });
};

// Обработчик для добавления нового отдела
addDepartmentForm.addEventListener("submit", async (e) => {
    e.preventDefault();

    const name = departmentNameInput.value.trim();

    if (!name) {
        showError("Название отдела не может быть пустым.");
        return;
    }

    try {
        await addDepartment({ name });
        addDepartmentForm.reset();
        loadDepartments();
    } catch (error) {
        console.error("Ошибка добавления отдела:", error);
        showError(error.message);
    }
});

// Обработчик для редактирования отдела
editDepartmentForm.addEventListener("submit", async (e) => {
    e.preventDefault();

    const id = editDepartmentId.value;
    const name = editDepartmentName.value.trim();

    if (!name) {
        showError("Все поля должны быть заполнены.");
        return;
    }

    try {
        await updateDepartment({ id, name });
        closeEditModal();
        loadDepartments();
    } catch (error) {
        console.error("Ошибка обновления отдела:", error);
        showError(error.message);
    }
});

// Закрытие модального окна при нажатии на кнопку "×"
cancelBtn.addEventListener("click", closeEditModal);

// Загрузка отделов при старте страницы
loadDepartments();

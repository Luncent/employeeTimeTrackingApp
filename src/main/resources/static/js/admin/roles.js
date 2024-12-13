const rolesTable = document.querySelector("#roles-table tbody");
const addRoleForm = document.querySelector("#add-role-form");
const roleTitleInput = document.querySelector("#role-title");
const editModal = document.getElementById("edit-modal");
const editRoleForm = document.getElementById("edit-role-form");
const editRoleTitle = document.getElementById("edit-role-title");
const editRoleId = document.getElementById("edit-role-id");
const cancelBtn = document.getElementById("cancel-btn");

//errors
const showError = (message) => {
    const errorMessage = document.getElementById("error-message");
    const errorText = document.getElementById("error-text");
    
    errorText.textContent = message;
    errorMessage.classList.add("visible");
};

// Закрытие сообщения об ошибке
document.getElementById("error-close-btn").addEventListener("click", () => {
    const errorMessage = document.getElementById("error-message");
    errorMessage.classList.remove("visible");
});


const openEditModal = (roleId, roleTitle) => {
    editRoleTitle.value = roleTitle;
    editRoleId.value = roleId;
    editModal.classList.remove("hidden");
};

const closeEditModal = () => {
    editModal.classList.add("hidden");
};

cancelBtn.addEventListener("click", closeEditModal);

// Обработчик редактирования формы
editRoleForm.addEventListener("submit", async (e) => {
    e.preventDefault();

    const id = editRoleId.value;
    const title = editRoleTitle.value.trim();

    if (!title) {
        showError("Название роли не может быть пустым.");
        return;
    }

    try {
        await updateRole({ id, title });
        closeEditModal();
        loadRoles();
    } catch (error) {
        console.error("Ошибка обновления роли:", error);
        showError("Не удалось обновить роль.");
    }
});

// Загрузка ролей
const loadRoles = async () => {
    try {
        const roles = await getRoles();
        rolesTable.innerHTML = '';

        roles.forEach(role => {
            const row = document.createElement("tr");
            row.innerHTML = `
                <td>${role.title}</td>
                <td>
                    <button class="edit-btn" data-id="${role.id}" data-title="${role.title}">Изменить</button>
                    <button class="delete-btn" data-id="${role.id}">Удалить</button>
                </td>
            `;
            rolesTable.appendChild(row);
        });

        attachRoleEventListeners();
    } catch (error) {
        console.error("Ошибка загрузки ролей:", error);
        showError("Не удалось загрузить список ролей.");
    }
};

const attachRoleEventListeners = () => {
    const deleteButtons = document.querySelectorAll(".delete-btn");
    const editButtons = document.querySelectorAll(".edit-btn");

    deleteButtons.forEach(button => {
        button.addEventListener("click", async (e) => {
            const roleId = e.target.dataset.id;

            const userConfirmed = window.confirm("Вы уверены, что хотите удалить эту роль?");

            if (userConfirmed) {
                try {
                    await deleteRole(roleId);
                    loadRoles();
                } catch (error) {
                    console.error("Ошибка удаления роли:", error);
                    showError("Не удалось удалить роль.");
                }
            }
        });
    });

    editButtons.forEach((button) => {
        button.addEventListener("click", (e) => {
            const roleId = e.target.dataset.id;
            const roleTitle = e.target.dataset.title;
            openEditModal(roleId, roleTitle);
        });
    });
};

addRoleForm.addEventListener("submit", async (e) => {
    e.preventDefault();
    const title = roleTitleInput.value.trim();

    if (!title) {
        showError("Введите название роли.");
        return;
    }

    try {
        await addRole({ title });
        addRoleForm.reset();
        loadRoles();
    } catch (error) {
        console.error("Ошибка добавления роли:", error);
        showError("Не удалось добавить роль.");
    }
});

// Загрузка ролей при запуске
loadRoles();

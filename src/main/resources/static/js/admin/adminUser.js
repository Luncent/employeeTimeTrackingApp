document.addEventListener('DOMContentLoaded', () => {
    const usersTable = document.getElementById('users-table').getElementsByTagName('tbody')[0];
    const addUserForm = document.getElementById('add-user-form');
    const departmentSelect = document.getElementById('department-select');
    const roleSelect = document.getElementById('role-select');

    const loadRolesAndDepartments = async () => {
        const roles = await getRoles();
        const departments = await getDepartments();

        roles.forEach(role => {
            const option = document.createElement('option');
            option.value = role.id;
            option.textContent = role.title;
            roleSelect.appendChild(option);
        });

        departments.forEach(department => {
            const option = document.createElement('option');
            option.value = department.id;
            option.textContent = department.name;
            departmentSelect.appendChild(option);
        });
    };


    const loadUsers = async () => {
        const users = await getUsers();
        usersTable.innerHTML = '';

        users.forEach(user => {
            const row = document.createElement('tr');
            row.innerHTML = `
                <td>${user.name}</td>
                <td>${user.role.title}</td>
                <td>${user.department.name}</td>
                 <td>
                    <button class="delete-btn" data-id="${user.id}">Удалить</button>
                </td>
            `;
            usersTable.appendChild(row);
        });
        const deleteButtons = document.querySelectorAll('.delete-btn');
        deleteButtons.forEach(button => {   
            button.addEventListener('click', async (e) => {
                console.log("click");
                const userId = e.target.dataset.id;

                const userConfirmed = window.confirm("Вы уверены, что хотите удалить этого пользователя?");
                if (userConfirmed) {
                    await deleteUser(userId);
                    loadUsers();
                }
        });
    });
    };

    

    //форм добавления пользователя
    addUserForm.addEventListener('submit', async (e) => {
        e.preventDefault();

        const name = document.getElementById('name').value;
        const password = document.getElementById('password').value;
        const departmentId = departmentSelect.value;
        const roleId = roleSelect.value;

        const userData = {
            name: name,
            password: password,
            departmentId: departmentId,
            roleId: roleId 
        };
        await addUser(userData);

        loadUsers();
        addUserForm.reset(); 
    });

    // загрузка стр
    loadRolesAndDepartments();
    loadUsers();
});

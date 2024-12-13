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
const username = getCookie('username');
const userId = getCookie('uid');

const vacationsContainer = document.getElementById('vacations-container');
const addVacationForm = document.getElementById('vacation-form');

const fetchVacations = async () => {
    try {
        const response = await axios.get(`http://localhost:8080/vacations/${encodeURIComponent(username)}`);
        const vacations = response.data;
        renderVacations(vacations);
    } catch (error) {
        console.error('Ошибка при получении отпусков:', error);
        showError("Ошибка при получении отпусков:" + (error.response?.data?.message ?? error.message));
    }
};

const renderVacations = (vacations) => {
    vacationsContainer.innerHTML = '';

    vacations.forEach(vacation => {
        const vacationElement = document.createElement('div');
        vacationElement.classList.add('vacation');

        const statusText = {
            1: 'Ожидает',
            2: 'Принят',
            3: 'Отклонен',
            4: 'Прошел'
        };

        vacationElement.innerHTML = `
                <div>
                    <strong>Отпуск:</strong> ${vacation.start} - ${vacation.end}<br>
                    <strong>Статус:</strong> <span class="status">${statusText[vacation.status] || 'Неизвестен'}</span>
                </div>
                ${vacation.status === 3 || vacation.status === 1? `<button class="delete-btn" data-id="${vacation.id}">Удалить</button>` : ''}
            `;

        if (vacation.status === 3 || vacation.status === 1) {
            const deleteButton = vacationElement.querySelector('.delete-btn');
            deleteButton.addEventListener('click', () => deleteVacation(vacation.id));
        }

        vacationsContainer.appendChild(vacationElement);
    });
};

const addVacation = async (event) => {
    event.preventDefault();

    const startInput = document.getElementById('start');
    const endInput = document.getElementById('end');

    const req_data = new URLSearchParams({
        userId: userId,
        start: startInput.value,
        end: endInput.value
    })

    try {
        await axios.post('http://localhost:8080/vacations', req_data, {
            headers: {"Content-Type": "application/x-www-form-urlencoded"}
        });

        startInput.value = '';
        endInput.value = '';
        fetchVacations();
    } catch (error) {
        console.error('Ошибка при добавлении отпуска:', error);
        showError("Ошибка при добавлении отпуска:" + (error.response?.data?.message ?? error.message));
    }
};

const deleteVacation = async (vacationId) => {
    try {
        await axios.delete('http://localhost:8080/vacations', {
            params: {vacationId}
        });

        fetchVacations();
    } catch (error) {
        console.error('Ошибка при удалении отпуска:', error);
        showError("Ошибка при удалении отпуска:" + (error.response?.data?.message ?? error.message));
    }
};


addVacationForm.addEventListener('submit', addVacation);

fetchVacations();

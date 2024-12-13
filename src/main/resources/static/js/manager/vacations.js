const vacationsTable = document.querySelector("#vacations-table tbody");

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

// Загрузка заявок на отпуск
const loadVacations = async () => {
    const vacations = await getVacations(departmentId);

    vacationsTable.innerHTML = "";

    vacations.forEach((vacation) => {
        const row = document.createElement("tr");

        row.innerHTML = `
            <td>${vacation.user.name}</td>
            <td>${vacation.user.department.name}</td>
            <td>${vacation.user.role.title}</td>
            <td>${vacation.start} - ${vacation.end}</td>
            <td>
                <button class="approve" data-id="${vacation.id}">Разрешить</button>
                <button class="reject" data-id="${vacation.id}">Отклонить</button>
                <button class="stats" data-user="${vacation.user.name}">Успеваемость</button>
            </td>
        `;

        vacationsTable.appendChild(row);
    });

    attachEventListeners();
};

const attachEventListeners = () => {
    document.querySelectorAll(".approve").forEach((btn) =>
        btn.addEventListener("click", async (e) => {
            const vacationId = e.target.dataset.id;
            await updateVacationStatus(vacationId, 2);  // Разрешить
            loadVacations();
        })
    );

    document.querySelectorAll(".reject").forEach((btn) =>
        btn.addEventListener("click", async (e) => {
            const vacationId = e.target.dataset.id;
            await updateVacationStatus(vacationId, 3);  // Отклонить
            loadVacations();
        })
    );

    document.querySelectorAll(".stats").forEach((btn) =>
        btn.addEventListener("click", async (e) => {
            const userName = e.target.dataset.user;
            const stats = await getUserStatistics(userName);

            if (stats) {
                const row = e.target.closest("tr");
                let statsRow = row.nextElementSibling;

                if (statsRow && statsRow.classList.contains("stats-box")) {
                    statsRow.remove();  // Скрыть, если уже открыт
                } else {
                    const statsBox = document.createElement("tr");
                    statsBox.className = "stats-box";
                    statsBox.innerHTML = `
                        <td colspan="5">
                            <div class="stats-box">
                                <div><span class="stats-label">Опоздания:</span> ${stats.lateTimeInMinutes} мин. (${stats.lateNumber} раз)</div>
                                <div><span class="stats-label">Переработка:</span> ${stats.overworkTimeInMinutes} мин. (${stats.overworkDaysNumber} дн.)</div>
                                <div><span class="stats-label">Недоработка:</span> ${stats.underWorkTimeInMinutes} мин. (${stats.underWorkDaysNumber} дн.)</div>
                                <div><span class="stats-label">Отпуск:</span> ${stats.daysInVacation} дн.</div>
                                <div><span class="stats-label">Отработано (часы):</span> ${stats.overallWorkedTimeHours} / ${stats.overallWorkedTimeNormInHours}</div>
                                <div><span class="stats-label">Процент выполнения:</span> ${(stats.percentOfWorkNorm * 100).toFixed(2)}%</div>
                            </div>
                        </td>
                    `;
                    row.after(statsBox);
                }
            }
        })
    );
};

// Загрузка заявок при загрузке страницы
loadVacations();

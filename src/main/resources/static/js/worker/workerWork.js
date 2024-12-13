document.addEventListener("DOMContentLoaded", () => {
    const startBtn = document.getElementById("start-btn");
    const pauseBtn = document.getElementById("pause-btn");
    const resumeBtn = document.getElementById("resume-btn");
    const finishBtn = document.getElementById("finish-btn");
    const statusMessage = document.getElementById("status-message");
    const scheduleTimeContainer = document.getElementById("schedule-time");

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

    const updateUI = (state) => {
        startBtn.classList.add('hidden');
        pauseBtn.classList.add('hidden');
        resumeBtn.classList.add('hidden');
        finishBtn.classList.add('hidden');
        statusMessage.classList.add('hidden');

        getScheduleTime();

        //установка статуса
        switch (state) {
            case 0:
                startBtn.classList.remove('hidden');
                statusMessage.textContent = "Работа не начата.";
                break;
            case 1:
                pauseBtn.classList.remove('hidden');
                finishBtn.classList.remove('hidden');
                statusMessage.textContent = "Работа в процессе.";
                break;
            case -1:
                statusMessage.textContent = "Нет расписания на сегодня.";
                break;
            case 2:
                resumeBtn.classList.remove('hidden');
                statusMessage.textContent = "Перерыв";
                break;
            case 3:
                statusMessage.textContent = "Работа завершена.";
                break;
        }

        statusMessage.classList.remove('hidden');
    };

    const getScheduleTime = async () => {
        const schedule = await getSchedule(userName);

        if (schedule && schedule.dayShift) {
            const shift = schedule.dayShift;
            scheduleTimeContainer.textContent = `Время смены: ${shift.start} - ${shift.end}`;
        }
    };

    const getCurrentWorkStatus = async () => {
        const state = await getWorkStatus(userName);
        if (state !== null) {
            updateUI(state);
        }
    };

    startBtn.addEventListener("click", async () => {
        await startWork(userName);
        getCurrentWorkStatus();
    });

    pauseBtn.addEventListener("click", async () => {
        await pauseWork(userName);
        getCurrentWorkStatus();
    });

    resumeBtn.addEventListener("click", async () => {
        await resumeWork(userName);
        getCurrentWorkStatus();
    });

    finishBtn.addEventListener("click", async () => {
        await finishWork(userName);
        getCurrentWorkStatus();
    });

    getCurrentWorkStatus();
});

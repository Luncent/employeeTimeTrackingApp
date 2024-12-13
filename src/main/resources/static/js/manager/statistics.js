const API_URL = "http://localhost:8080/statistics";

// Элементы отображения статистики
const statisticsSection = document.getElementById("statistics");
const workersQuantity = document.getElementById("workersQuantity");
const lateTimeInMinutes = document.getElementById("lateTimeInMinutes");
const overworkTimeInMinutes = document.getElementById("overworkTimeInMinutes");
const underWorkTimeInMinutes = document.getElementById("underWorkTimeInMinutes");
const daysInVacation = document.getElementById("daysInVacation");
const percentOfWork = document.getElementById("percentOfWork");
const errorCloseBtn = document.getElementById("error-close-btn");
const errorMessage = document.getElementById("error-message");


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

const showError = (message) => {
    const errorText = document.getElementById("error-text");
    console.log(message+"asd")
    errorText.textContent = message;
    errorMessage.classList.add("visible");
};

errorCloseBtn.addEventListener("click", ()=>{
    errorMessage.classList.remove("visible");
})

const getStatistics = async () => {
    try {
        const response = await axios.get(API_URL, {
            params: { departmentId: departmentId },  // Передача form-data
            headers: { 'Content-Type': 'application/x-www-form-urlencoded' }
        });
        return response.data;
    } catch (error) {
        console.error("Ошибка загрузки статистики:", error);
        showError("Не удалось загрузить статистику отдела." + (error.response?.data?.message ?? error.message));
    }
};

// Функция для оценки значения по категориям
const getCategory = (value, goodRange, averageRange) => {
    if (value <= goodRange) return "Хорошо";
    if (value <= averageRange) return "Удовлетворительно";
    return "Плохо";
};

const getMoreCategory = (value, goodRange, averageRange) => {
    if (value >= goodRange) return "Хорошо";
    if (value >= averageRange) return "Удовлетворительно";
    return "Плохо";
};

// Функция для отображения статистики
const displayStatistics = (data) => {
    const workersQuantityText = document.getElementById("workers-quantity-text");
    const workPercentText = document.getElementById("work-percent-text");
    const latenessText = document.getElementById("lateness-text");
    const overworkText = document.getElementById("overwork-text");
    const underworkText = document.getElementById("underwork-text");
    const vacationText = document.getElementById("vacation-text");

    workersQuantityText.textContent = `Количество работников: ${data.workersQuantity}`;

    const workPercentCategory = getMoreCategory(data.percentOfWork*100, 90, 80);
    workPercentText.innerHTML = `
    <strong> Процент выполнения работы: ${(data.percentOfWork*100).toFixed(2)}%</strong> - <strong>${workPercentCategory}</strong><br>
    [90 и выше] - Хорошо, [80-90] - Удовлетворительно, [ниже 80] - Плохо`;

    const latenessCategory = getCategory(data.lateTimeInMinutes / data.workersQuantity, 200, 600);
    latenessText.innerHTML = `
    <strong> Опоздание на одного человека: ${(data.lateTimeInMinutes / data.workersQuantity).toFixed(2)} мин. - ${latenessCategory}</strong><br>
    [ниже 200] - Хорошо, [200-600] - Удовлетворительно, [выше 600] - Плохо`;

    const overworkCategory = getCategory(data.overworkTimeInMinutes / data.workersQuantity,  200, 600);
    //overworkText.textContent = `Переработка на одного человека: ${data.overworkTimeInMinutes} мин. - ${overworkCategory}`;
    overworkText.innerHTML = `
    <strong> Переработка на одного человека: ${(data.overworkTimeInMinutes / data.workersQuantity).toFixed(2)} мин. - ${overworkCategory}</strong><br>
    [ниже 200] - Хорошо, [200-600] - Удовлетворительно, [выше 600] - Плохо`;

    const underworkCategory = getCategory(data.underWorkTimeInMinutes / data.workersQuantity, 200, 600);
    //underworkText.textContent = `Недоработка на одного человека: ${data.underWorkTimeInMinutes} мин. - ${underworkCategory}`;
    underworkText.innerHTML = `
    <strong> Недоработка на одного человека: ${(data.underWorkTimeInMinutes / data.workersQuantity).toFixed(2)} мин. - ${underworkCategory}</strong><br>
    [ниже 200] - Хорошо, [200-600] - Удовлетворительно, [выше 600] - Плохо`;

    const vacationCategory = getCategory(data.daysInVacation / data.workersQuantity, 2, 4);
    //vacationText.textContent = `Дни в отпуске на одного человека: ${data.daysInVacation} дней - ${vacationCategory}`;
    vacationText.innerHTML = `
    <strong> Дни в отпуске на одного человека: ${(data.daysInVacation / data.workersQuantity).toFixed(2)} дней - ${vacationCategory}</strong><br>
    [2 и ниже] - Хорошо, [2-4] - Удовлетворительно, [выше 4] - Плохо`;

    createChart("lateness-chart", data.lateTimeInMinutes/data.workersQuantity,200,600, "Опоздание");
    createChart("overwork-chart", data.overworkTimeInMinutes/data.workersQuantity,200,600, "Переработка");
    createChart("underwork-chart", data.underWorkTimeInMinutes/data.workersQuantity,200,600, "Недоработка");
    createChart("vacation-chart", data.daysInVacation/data.workersQuantity,2,4, "Отпуск");
    createChartMore("work-percent-chart", data.percentOfWork*100, 90, 80, "Процент выполнения работы");
};

// Функция для создания диаграмм при правиле менее
const createChart = (chartId, value, good, average, label) => {
    const ctx = document.getElementById(chartId).getContext("2d");

    const color = value <= good ? 'green' :
        value <= average ? 'yellow' : 'red';

    new Chart(ctx, {
        type: 'bar',
        data: {
            labels: [label],
            datasets: [{
                label: label,
                data: [value],
                backgroundColor: color,
                borderColor: 'black',
                borderWidth: 1
            }]
        },
        options: {
            scales: {
                y: { beginAtZero: true }
            }
        }
    });
};

// Функция для создания диаграмм при правиле более
const createChartMore = (chartId, value, good, average, label) => {
    const ctx = document.getElementById(chartId).getContext("2d");

    const color = value >= good ? 'green' :
        value >= average ? 'yellow' : 'red';

    new Chart(ctx, {
        type: 'bar',
        data: {
            labels: [label],
            datasets: [{
                label: label,
                data: [value],
                backgroundColor: color,
                borderColor: 'black',
                borderWidth: 1
            }]
        },
        options: {
            scales: {
                y: { beginAtZero: true }
            }
        }
    });
};

getStatistics().then(data => {
    if (data) {
        displayStatistics(data);
    }
});

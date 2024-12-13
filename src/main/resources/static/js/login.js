// script.js

var HOST = "http://localhost:8080";

document.getElementById('loginForm').addEventListener('submit', function(event) {
    event.preventDefault(); // Предотвращаем стандартную отправку формы

    const username = document.getElementById('username').value;
    const password = document.getElementById('password').value;
    const errorMessage = document.getElementById('errorMessage');

    if (!username || !password) {
        errorMessage.textContent = 'Пожалуйста, заполните все поля.';
        errorMessage.style.display = 'block';
        return;
    }

    errorMessage.style.display = 'none';

    const data = new URLSearchParams();
    data.append('userName', username);
    data.append('password', password);

    axios.post(HOST+'/auth', data, {
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded'
        },
        withCredentials: true
    })
        .then(response => {
            console.log(response.headers['location']);
            console.log(JSON.stringify(response.headers));
            window.location.href = response.headers['location'];
        })
        .catch(error => {
            errorMessage.textContent = JSON.stringify(error.response.data) || 'Что-то пошло не так. Попробуйте снова.';
            errorMessage.style.display = 'block';
        });
});

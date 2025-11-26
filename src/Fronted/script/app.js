document.getElementById("btnShowLogin").onclick = () => switchTab("login");
document.getElementById("btnShowRegister").onclick = () => switchTab("register");
document.getElementById("loginBtn").onclick = login;
document.getElementById("registerBtn").onclick = register;

function switchTab(tab) {
    const loginCard = document.getElementById("loginCard");
    const registerCard = document.getElementById("registerCard");
    const btnLogin = document.getElementById("btnShowLogin");
    const btnRegister = document.getElementById("btnShowRegister");

    if (tab === "login") {
        loginCard.classList.remove("hidden");
        registerCard.classList.add("hidden");
        btnLogin.classList.add("active");
        btnRegister.classList.remove("active");
    } else {
        loginCard.classList.add("hidden");
        registerCard.classList.remove("hidden");
        btnLogin.classList.remove("active");
        btnRegister.classList.add("active");
    }
}

async function register() {
    const username = document.getElementById("regUser").value;
    const password = document.getElementById("regPass").value;
    const role = document.getElementById("regRole").value;

    try {
        const response = await fetch("http://localhost:8081/auth/register", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({ username, password, role })
        });

        document.getElementById("regMsg").innerText = response.ok ? 
            "Usuario registrado exitosamente" : 
            "Error al registrar el usuario";

    } catch (error) {
        document.getElementById("regMsg").innerText = "Error de conexión";
    }
}

async function login() {
    const username = document.getElementById("logUser").value;
    const password = document.getElementById("logPass").value;

    try {
        const res = await fetch("http://localhost:8081/auth/login", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({ username, password })
        });

        if (!res.ok) {
            document.getElementById("loginMsg").innerText = "Credenciales incorrectas";
            return;
        }

        const tokenText = await res.text();
        localStorage.setItem("jwt", tokenText);
        document.getElementById("loginMsg").innerText = "Inicio de sesión exitoso!!!";

        setTimeout(() => {
            window.location.href = "panel.html";
        }, 800);

    } catch (error) {
        document.getElementById("loginMsg").innerText = "Error de conexión";
    }
}
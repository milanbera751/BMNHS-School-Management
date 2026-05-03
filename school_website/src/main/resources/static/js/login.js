// Keep the function at the very top
function showToast(message, type = "success") {
    const container = document.getElementById("toast-container");

    // Safety check: if HTML is missing the div, create it!
    if (!container) {
        console.error("Missing toast-container in HTML");
        return;
    }

    const toast = document.createElement("div");
    toast.className = `toast ${type}`; // This allows for .toast.success or .toast.error

    // Manual styling in case CSS isn't loading
    toast.style.backgroundColor = type === "error" ? "#e74c3c" : "#2ecc71";

    const icon = type === "success" ? "fa-circle-check" : "fa-circle-exclamation";
    toast.innerHTML = `<i class="fa-solid ${icon}"></i> ${message}`;

    container.appendChild(toast);

    setTimeout(() => {
        toast.style.opacity = "0"; // Smooth fade out
        setTimeout(() => toast.remove(), 500);
    }, 3000);
}

document.getElementById("loginForm").addEventListener("submit", async (e) => {
    e.preventDefault();

    const loader = document.getElementById("loader-container");
    const params = new URLSearchParams(window.location.search);
    const currentMode = params.get('mode');

    const username = document.getElementById("username").value.trim();
    const password = document.getElementById("password").value.trim();

    if (!username || !password) {
        showToast("Please enter username and password", "error");
        return;
    }

    loader.style.display = "flex";

    try {
        const res = await fetch("https://bmnhs-school-management-1.onrender.com/api/auth/login", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({ username, password })
        });

        const data = await res.json();

        if (data.error) {
            showToast(data.error, "error");
            loader.style.display = "none";
            return;
        }

        const role = (data.role || "").toUpperCase();

        const handleSuccess = (redirectPath) => {
            localStorage.setItem("token", data.token);
            localStorage.setItem("role", role);
            showToast("Login Successful!");

            setTimeout(() => {
                window.location.href = redirectPath;
            }, 1000);
        };

        // Logic check
        if (currentMode === "admin") {
            if (role === "ADMIN") {
                handleSuccess("/Dashboard/admin.html");
            } else {
                showToast("Admins only!", "error");
            }
        } else {
            if (role === "STUDENT") {
                handleSuccess("/Dashboard/student.html");
            } else if (role === "TEACHER") {
                handleSuccess("/Dashboard/teacher.html");
            } else {
                showToast("Access Denied", "error");
            }
        }
    } catch (err) {
        showToast("Server Connection Failed", "error");
    } finally {
        loader.style.display = "none";
    }
});
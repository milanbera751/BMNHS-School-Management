window.onpageshow = function(event) {
    if (event.persisted || (window.performance && window.performance.navigation.type === 2)) {
        if (!localStorage.getItem("token")) {
            window.location.replace("../login.html");
        }
    }
};
function showToast(message, type = "success") {
    const container = document.getElementById("toast-container");
    if (!container) return;

    const toast = document.createElement("div");
    toast.className = `toast ${type === "error" ? "error" : ""}`;

    const icon = type === "success" ? "fa-circle-check" : "fa-circle-exclamation";

    // Check line 35 area: Ensure you are using backticks (`) for template literals
    toast.innerHTML = `<i class="fa-solid ${icon}"></i> <span>${message}</span>`;

    container.appendChild(toast);

    setTimeout(() => {
        toast.style.opacity = "0";
        setTimeout(() => toast.remove(), 500);
    }, 3000);
}

/**
 * 2. Security & Initialization
 */
function checkAuth(requiredRole = null) {
    const token = localStorage.getItem("token");
    const role = localStorage.getItem("role");

    if (!token) {
        window.location.href = "../login.html";
        return;
    }

    if (requiredRole && role !== requiredRole) {
        // Allow Admin to see Teacher dashboard if that's your logic
        if (!(requiredRole === "TEACHER" && role === "ADMIN")) {
            window.location.href = "../login.html";
        }
    }
}
function comings(){
    alert("Coming soon!")
}
/**
 * 3. Logout Function
 */
function logout() {
    if (confirm("Are you sure you want to logout?")) {
        localStorage.removeItem("token");
        window.location.href = "../index.html";
    }
}

/**
 * 4. Run on Page Load
 */
document.addEventListener("DOMContentLoaded", () => {
    const shouldShowWelcome = localStorage.getItem("showWelcome");
    const adminName = localStorage.getItem("userName");

    if (shouldShowWelcome === "true") {
        const msg = adminName ? `Welcome, ${adminName}!` : "Welcome to Dashboard!";
        showToast(msg, "success");
        localStorage.removeItem("showWelcome");
    }

    const nameLabel = document.getElementById("admin-display-name");
    if (nameLabel && adminName) {
        nameLabel.innerText = adminName;
    }
});
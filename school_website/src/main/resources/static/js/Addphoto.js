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
function upload() {
    const fileInput = document.getElementById("file");
    const file = fileInput.files[0];
    const token = localStorage.getItem("token");
    const loader = document.getElementById("loader-container");

    if (!file || !token) {
        alert(!file ? "Select file" : "Login first");
        return;
    }

    // 1. Show the loader first
    loader.style.display = "flex";

    // 2. Wrap everything else in a tiny timeout (50ms)
    setTimeout(() => {
        const formData = new FormData();
        formData.append("file", file);

        fetch("https://bmnhs-school-management-1.onrender.com/api/gallery/add", {
            method: "POST",
            headers: { "Authorization": "Bearer " + token },
            body: formData
        })
        .then(res => {
            if (!res.ok) throw new Error("Upload failed");
            return res.json();
        })
        .then(data => {
            showToast("Photo Uploaded successfully.");
            fileInput.value = "";
        })
        .catch(err => {
            console.error(err);
            showToast("Uploading Failed!","error");
            fileInput.value = "";
        })
        .finally(() => {
            // 3. Hide the loader when finished
            loader.style.display = "none";
        });
    }, 50);
}
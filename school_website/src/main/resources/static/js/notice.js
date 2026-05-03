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

document.getElementById("noticeForm").addEventListener("submit", async function(e) {
    e.preventDefault();
    console.log("from submitted");
    const token = localStorage.getItem("token");
    const formData = new FormData();
    formData.append("title", document.getElementById("title").value);
    formData.append("content", document.getElementById("content").value);

    const fileInput=document.getElementById("file_upload");
    if(fileInput.files.length>0){
        formData.append("file", fileInput.files[0]);
    }
    try{
        const res = await fetch("http://localhost:8080/api/admin/notice", {
                method: "POST",
                headers: {
                    "Authorization": "Bearer " + token
                },
                body: formData
            });
            if (res.ok) {
                showToast("Notice successfully added.");
                document.querySelectorAll("#title,#content")
                                                    .forEach(i => i.value = "");
                fileInput.value = "";

            } else {
                showToast("Notice Posting failed!","error")
            }
    }
    catch(err){
        console.error("Error",err);
    }
});

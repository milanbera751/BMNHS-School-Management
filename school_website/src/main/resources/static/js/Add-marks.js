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
function submitMarks() {
    const course = document.getElementById("courseInput").value;
    const roll = document.getElementById("studentRoll").value;
    const subject = document.getElementById("subjectName").value;
    const obtained = document.getElementById("marksObtained").value;
    const total = document.getElementById("totalMarks").value;

    const token = localStorage.getItem("token");
    const loader = document.getElementById("loader-container");

    if (!course || !roll || !subject || !obtained || !total) {
        alert("Please fill in all fields (Course, Roll, Subject, Marks, and Total)");
        return;
    }
    loader.style.display = "flex";

    setTimeout(() => {

        // Prepare the numeric data (convert strings to numbers)
        const marksData = {
            subjectName: subject,
            marksObtained: parseFloat(obtained),
            totalMarks: parseFloat(total)
        };

        fetch(`http://localhost:8080/api/teacher/add-marks/${course}/${roll}`, {
            method: "POST",
            headers: {
                "Authorization": "Bearer " + token,
                "Content-Type": "application/json"
            },
            body: JSON.stringify(marksData)
        })
        .then(async (res) => {
            if (res.ok) {
                showToast(`Success: Marks saved for Roll ${roll} in ${course}`);
                // Clear marks but keep roll/course for the next subject entry
                document.getElementById("marksObtained").value = "";
                document.getElementById("subjectName").value = "";
            } else {
                const errorMessage = await res.text();
                throw new Error(errorMessage || "Failed to save marks");
            }
        })
        .catch(err => {
            console.error(err);
            showToast(err.message,"error");
        })
        .finally(() => {
            // 5. Hide the loader regardless of success or failure
            loader.style.display = "none";
        });
    }, 100);
}

// Function to reset the entire form
function clearForm() {
    document.getElementById("courseInput").value = "";
    document.getElementById("studentRoll").value = "";
    document.getElementById("subjectName").value = "";
    document.getElementById("marksObtained").value = "";
    document.getElementById("totalMarks").value = "";
}

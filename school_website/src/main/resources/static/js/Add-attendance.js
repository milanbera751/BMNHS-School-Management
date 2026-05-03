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
// =====================================
//CLASS → SUBJECT DATA
// =====================================
const classSubjects = {
    "5": ["Math", "English", "Science"],
    "6": ["Math", "English", "Science", "History"],
    "7": ["Math", "English", "Science", "History"],
    "8": ["Math", "English", "Science", "Geography", "Computer"],
    "9": ["Math", "Physics", "Chemistry", "Biology"],
    "10": ["Math", "Physics", "Chemistry", "Biology"]
};
// =====================================
//DOM READY (IMPORTANT FIX)
// =====================================
document.addEventListener("DOMContentLoaded", () => {

    const classInput = document.getElementById("classInput");

    if (!classInput) {
        console.error("classInput not found");
        return;
    }

    // CLASS CHANGE EVENT
    classInput.addEventListener("change", function () {
        const selectedClass = this.value;

        loadSubjects(selectedClass);
        loadStudents(selectedClass);
    });
});


// =====================================
// 📘 LOAD SUBJECTS
// =====================================
function loadSubjects(className) {

    const subjectList = document.getElementById("subjectList");

    if (!subjectList) return;

    subjectList.innerHTML = "";

    if (classSubjects[className]) {
        classSubjects[className].forEach(sub => {

            subjectList.innerHTML += `<option value="${sub}">`;
        });
    }
}


// =====================================
// 👨‍🎓 LOAD STUDENTS (CHECKBOX LIST)
// =====================================
function loadStudents(className) {

    const container = document.getElementById("studentContainer");
    const token=localStorage.getItem("token");
    const loader = document.getElementById("loader-container");
    if (!container) return;

    loader.style.display = "flex";
    container.innerHTML = "Loading students...";

    fetch(`/api/students?class=${className}`,{
    method: "GET",
    headers: {
        "Authorization": "Bearer "+token,
        "Content-Type": "application/json"
        }
    })
        .then(res => {
            if (!res.ok) throw new Error("Failed to fetch students");
            return res.json();
        })
        .then(data => {

            container.innerHTML = "";

            if (data.length === 0) {
                container.innerHTML = "<p>No students found</p>";
                return;
            }

            data.forEach(s => {
                container.innerHTML += `
                    <div class="student-row">
                        <input type="checkbox" value="${s.username}">
                        <label>
                            <b>${s.name}</b>
                            <small>(${s.username})</small>
                        </label>
                    </div>
                `;
            });
        })
        .catch(err => {
            showToast("Student fetch error","error");
            container.innerHTML = "<p>Error loading students</p>";
        })
        .finally(() => {
            loader.style.display = "none";
        });
}


// =====================================
//SUBMIT BULK ATTENDANCE
// =====================================
function submitAttendance() {

    const subject = document.getElementById("subject").value.trim();
    const date = document.getElementById("date").value;
    const token = localStorage.getItem("token");
    const loader = document.getElementById("loader-container");

    if (!subject || !date) {
        alert("Please select subject and date");
        return;
    }

    if (!token) {
        alert("Please login first");
        return;
    }

    const checkboxes = document.querySelectorAll("#studentContainer input");

    if (checkboxes.length === 0) {
        alert("No students loaded");
        return;
    }

    let attendanceList = [];

    checkboxes.forEach(cb => {
        attendanceList.push({
            studentUsername: cb.value,
            status: cb.checked ? "PRESENT" : "ABSENT"
        });
    });
    loader.style.display = "flex";
    fetch("/api/teacher/attendance/bulk", {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
            "Authorization": "Bearer " + token
        },
        body: JSON.stringify({
            subject: subject,
            date: date,
            list: attendanceList
        })
    })
    .then(res => {
        if (!res.ok) {
            showToast("Failed to save attendance","error");
            loader.style.display = "none";
        }
        return res.text();
    })
    .then(() => {
        showToast("Attendance saved successfully");
        loader.style.display = "none";

        // RESET FORM
        document.getElementById("subject").value = "";
        document.getElementById("date").value = "";

        document.querySelectorAll("#studentContainer input")
            .forEach(cb => cb.checked = false);
    })
    .catch(err => {
        console.error("Error:", err);
        showToast("Error saving attendance","error")
    })
    .finally(() => {
        loader.style.display = "none";
    });
}


// =====================================
// MARK ALL PRESENT
// =====================================
function markAllPresent() {
    document.querySelectorAll("#studentContainer input")
        .forEach(cb => cb.checked = true);
}


// =====================================
// CLEAR ALL (ALL ABSENT)
// =====================================
function clearAll() {
    document.querySelectorAll("#studentContainer input")
        .forEach(cb => cb.checked = false);
}
document.addEventListener("DOMContentLoaded", function() {
    fetchMarks();
});

function fetchMarks() {
    const token = localStorage.getItem("token");
    const marksBody = document.getElementById("marksBody");

    fetch("https://bmnhs-school-management-1.onrender.com/api/students/my-marks", {
        method: "GET",
        headers: {
            "Authorization": "Bearer " + token,
            "Content-Type": "application/json"
        }
    })
    .then(res => {
        if (!res.ok) throw new Error("Could not fetch marks");
        return res.json();
    })
    .then(data => {
        marksBody.innerHTML = ""; // Clear table

        if (data.length === 0) {
            marksBody.innerHTML = "<tr><td colspan='4'>No marks uploaded yet.</td></tr>";
            return;
        }

        data.forEach(mark => {
            const row = `
                <tr>
                    <td>${mark.subjectName}</td>
                    <td>${mark.marksObtained}</td>
                    <td>${mark.totalMarks}</td>
                    <td><b>${mark.percentage.toFixed(2)}%</b></td>
                </tr>
            `;
            marksBody.innerHTML += row;
        });
    })
    .catch(err => {
        console.error(err);
        alert("Session expired. Please login again.");
    });
}

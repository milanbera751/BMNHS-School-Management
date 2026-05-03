const token = localStorage.getItem("token");

fetch("https://bmnhs-school-management-1.onrender.com/api/students/attendance", {
    headers: {
        "Authorization": "Bearer " + token
    }
})
.then(res => res.json())
.then(data => {

    const table = document.getElementById("attendanceTable");
    table.innerHTML = "";

    data.forEach(row => {

        const tr = document.createElement("tr");

        tr.innerHTML = `
            <td>${row.subject}</td>
            <td>${row.totalClass || '-'}</td>
            <td>${row.attended || '-'}</td>
            <td>${row.percentage ? row.percentage.toFixed(2) + '%' : '-'}</td>
        `;

        table.appendChild(tr);
    });

})
.catch(err => {
    console.log(err);
    alert("Error loading attendance");
});
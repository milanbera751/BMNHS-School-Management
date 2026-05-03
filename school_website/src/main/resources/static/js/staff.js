const loader= document.getElementById("loader-container");
console.log("JS load");
loader.style.display="flex";
fetch("http://localhost:8080/api/teacher/staff")
.then(res => {
    console.log("Status:", res.status);
    return res.json();
})
.then(data => {
    console.log("DATA:", data);

    let container = document.getElementById("staffContainer");

    data.forEach(t => {
        container.innerHTML += `
            <div class="card">
                <img src="${t.imageUrl}" alt="staff"/>
                <h3>Name: ${t.name}</h3>
                <p>Subject: ${t.subject}</p>
                <p>Phone: ${t.phone}</p>
                <p>Email: ${t.email}</p>
            </div>
        `;
    });
})
.catch(err => {
    console.log("FETCH ERROR:", err);
})
.finally(() => {
    loader.style.display = "none";
});
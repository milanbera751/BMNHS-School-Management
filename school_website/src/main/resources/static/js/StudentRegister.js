async function addStudent() {

    const token = localStorage.getItem("token");
    const loader= document.getElementById("loader-container");
    const msg= document.getElementById("msg");

    const student = {
        name: document.getElementById("name").value.trim(),
        username: document.getElementById("username").value.trim(),
        password: document.getElementById("password").value.trim(),
        course: document.getElementById("course").value.trim(),
        phone: document.getElementById("phone").value.trim(),
        roll: document.getElementById("roll").value.trim()
    };

    if (!student.name || !student.username || !student.password) {
        msg.innerText = "Name, Username & Password required";
        loader.style.display="none";
        return;
    }
        loader.style.display="flex";
        msg.innerText="Processing.....";

    try {
        const res = await fetch("https://bmnhs-school-management-1.onrender.com/api/admin/register-student", {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
                "Authorization": "Bearer " + token
            },
            body: JSON.stringify(student)
        });
        const data=await res.text();

         if (res.ok) {
            alert("Student Added successfully");
             document.querySelectorAll("#name,#course,#username,#password,#phone,#section,#roll")
                                    .forEach(i => i.value = "");
         } else {
            alert("Error posting notice");
         }
        msg.innerText=data;
    } catch (err) {
        msg.innerText ="Connection Error"+err.message;
    }finally{
        loader.style.display="none";
    }
}
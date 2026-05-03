//async function addTeacher() {
//
//    const token = localStorage.getItem("token");
//
//    const teacher = {
//        name: document.getElementById("t_name").value.trim(),
//        username: document.getElementById("t_username").value.trim(),
//        password: document.getElementById("t_password").value.trim(),
//        email: document.getElementById("t_email").value.trim(),
//        phone: document.getElementById("t_phone").value.trim(),
//        subject: document.getElementById("t_subject").value.trim(),
//    };
//
//    const msg = document.getElementById("msg");
//
//    if (!teacher.name || !teacher.username || !teacher.password) {
//        msg.innerText = "Name, Username & Password required";
//        return;
//    }
//
//    try {
//        const res = await fetch("http://localhost:8080/api/admin/register-teacher", {
//            method: "POST",
//            headers: {
//                "Content-Type": "application/json",
//                "Authorization": "Bearer " + token
//            },
//            body: JSON.stringify(teacher)
//        });
//        const data=await res.text();
//         if (res.ok) {
//            alert("Teacher Added successfully");
//             document.querySelectorAll("#t_name, #t_subject,#t_username, #t_password, #t_email, #t_phone")
//                        .forEach(i => i.value = "");
//         } else {
//            alert("Error posting notice");
//         }
//        msg.innerText=data;
//    } catch (err) {
//        msg.innerText = err.message;
//    }
//}
async function addTeacher() {

    const token = localStorage.getItem("token");
    const msg = document.getElementById("msg");
    const fileInput = document.getElementById("t_image");

    const formData = new FormData();

    formData.append("name", document.getElementById("t_name").value.trim());
    formData.append("username", document.getElementById("t_username").value.trim());
    formData.append("password", document.getElementById("t_password").value.trim());
    formData.append("email", document.getElementById("t_email").value.trim());
    formData.append("phone", document.getElementById("t_phone").value.trim());
    formData.append("subject", document.getElementById("t_subject").value.trim());

    // ✅ IMAGE CHECK SAFE
    if (fileInput.files.length > 0) {
        formData.append("image", fileInput.files[0]);
    } else {
        msg.innerText = "Please select image";
        return;
    }

    try {
        const res = await fetch("http://localhost:8080/api/admin/register-teacher", {
            method: "POST",
            headers: {
                "Authorization": "Bearer " + token
            },
            body: formData
        });

        const data = await res.text();

        if (res.ok) {
            alert("Teacher Added successfully");

            document.querySelectorAll("#t_name, #t_subject, #t_username, #t_password, #t_email, #t_phone")
                .forEach(i => i.value = "");

            fileInput.value = "";

        } else {
            alert("Error adding teacher");
        }

        msg.innerText = data;

    } catch (err) {
        msg.innerText = err.message;
    }
}
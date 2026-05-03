// ✅ LOAD NOTICES (NO AUTH)
async function loadNotices() {
    const loader= document.getElementById("loader-container");
    try {
        const res = await fetch("http://localhost:8080/api/notices");

        const data = await res.json();

        console.log("Notices:", data); // debug

        const container = document.getElementById("noticeList");
        container.innerHTML = "";

        if (!Array.isArray(data)) {
            container.innerHTML = "<p>No notices.</p>";
            return;
        }

        data.forEach(n => {

            const date = n.createAt
                ? new Date(n.createAt).toLocaleDateString("en-IN",{
                   day:"2-digit",
                   month:"short",
                   year:"numeric"
                })
                : "NO Date";

            container.innerHTML += `
                <div class="notice-row">
                    <div class="title">${n.title}</div>

                    <div class="content">
                        ${n.content}
                    </div>

                    <div class="file">
                        ${
                            n.fileName
                            ? `<a href="#" onclick="downloadFile('${n.fileName}')">
                                    ${n.fileName}
                               </a>`
                            : "-"
                        }
                    </div>

                    <div class="date">
                        ${date}
                    </div>
                </div>
            `;
        });

    } catch (err) {
        console.error("Error loading notices:", err);
    }
}

// ✅ DOWNLOAD FILE (NO AUTH)
function downloadFile(fileName) {
    fetch("http://localhost:8080/api/notices/file/" + fileName)
        .then(res => res.blob())
        .then(blob => {
            const url = window.URL.createObjectURL(blob);
            const a = document.createElement("a");
            a.href = url;
            a.download = fileName;
            a.click();
        });
}

// LOAD
loadNotices();
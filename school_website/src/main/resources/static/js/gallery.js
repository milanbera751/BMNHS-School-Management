const loader = document.getElementById("loader-container");
const container = document.getElementById("gallery");

// 1. Show loader immediately
loader.style.display = "flex";

fetch("https://bmnhs-school-management-1.onrender.com/api/gallery")
    .then(res => {
        if (!res.ok) throw new Error("Server error: " + res.status);
        return res.json();
    })
    .then(data => {
        // Use a string buffer for better performance
        let galleryHTML = "";

        data.forEach(g => {
            galleryHTML += `
                <div class="card">
                    <img src="${g.imageUrl}" alt="Gallery Image">
                </div>
            `;
        });

        container.innerHTML = galleryHTML;
    })
    .catch(err => {
        console.error("FETCH ERROR:", err);
        // You could show your toast error here
    })
    .finally(() => {
        // 2. Add a small delay (e.g., 800ms) so the loader is actually visible
        setTimeout(() => {
            loader.style.display = "none";
        }, 800);
    });

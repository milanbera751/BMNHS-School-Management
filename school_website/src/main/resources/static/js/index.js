let currentSlide = 0;
const slides = document.querySelectorAll('.slide');

function showSlide(index) {
    // 1. Remove 'active' from all slides
    slides.forEach(slide => {
        slide.classList.remove('active');
    });

    // 2. Reset index if it goes out of bounds
    if (index >= slides.length) currentSlide = 0;
    if (index < 0) currentSlide = slides.length - 1;

    // 3. Add 'active' to the current slide
    slides[currentSlide].classList.add('active');
}

function changeSlide(step) {
    currentSlide += step;
    showSlide(currentSlide);
}

// 4. Auto-initialize the first slide
showSlide(currentSlide);

// 5. Set auto-play timer
setInterval(() => {
    changeSlide(1);
}, 5000);

function goLogins() {
    window.location.href = "/login.html?mode=admin";
}
function goLogin() {
    window.location.href = "/login.html?mode=user";
}
function goStaff() {
    window.location.href = "/pages/staff.html";
}
function goNotice() {
    window.location.href = "/pages/Notice_view.html";
}
function gallery(){
    window.location.href="/pages/Gallery.html";
}

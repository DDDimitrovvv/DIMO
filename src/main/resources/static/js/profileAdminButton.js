const archivedPr = document.getElementById('btn1_1');
const archivedSection =  document.getElementById('sectionArchived');

const viewPr = document.getElementById('btn1_2');
const viewPrSection =  document.getElementById('sectionViewProducts');

const viewSt = document.getElementById('btn1_3');
const viewStSection =  document.getElementById('sectionViewStories');

archivedPr.addEventListener('click', function () {
    if (archivedSection.style.display === "none") {
        archivedSection.style.display = "block";
    } else {
        archivedSection.style.display = "none";
    }
});

viewPr.addEventListener('click', function () {
    if (viewPrSection.style.display === "none") {
        viewPrSection.style.display = "block";
    } else {
        viewPrSection.style.display = "none";
    }
});

viewSt.addEventListener('click', function () {
    if (viewStSection.style.display === "none") {
        viewStSection.style.display = "block";
    } else {
        viewStSection.style.display = "none";
    }
});
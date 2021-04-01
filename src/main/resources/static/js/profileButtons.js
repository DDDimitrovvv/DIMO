const viewPr = document.getElementById('btn1_2');
const viewPrSection =  document.getElementById('sectionViewProducts');

const viewSt = document.getElementById('btn1_3');
const viewStSection =  document.getElementById('sectionViewStories');

const purchasedPr = document.getElementById('btn2_1');
const purchasedSection =  document.getElementById('sectionPurchased');

const soldPr = document.getElementById('btn2_2');
const soldSection =  document.getElementById('sectionSold');


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


purchasedPr.addEventListener('click', function () {
    if (purchasedSection.style.display === "none") {
        purchasedSection.style.display = "block";
    } else {
        purchasedSection.style.display = "none";
    }
});


soldPr.addEventListener('click', function () {
    if (soldSection.style.display === "none") {
        soldSection.style.display = "block";
    } else {
        soldSection.style.display = "none";
    }
});
const archivedPr = document.getElementById('btn1_1');
const archivedSection =  document.getElementById('sectionArchived');

const viewPr = document.getElementById('btn1_2');
const viewPrSection =  document.getElementById('sectionViewProducts');

const viewSt = document.getElementById('btn1_3');
const viewStSection =  document.getElementById('sectionViewStories');

const viewMsg = document.getElementById('btn1_4');
const viewMsgSection =  document.getElementById('sectionMessages');

const purchasedPr = document.getElementById('btn2_1');
const purchasedSection =  document.getElementById('sectionPurchased');

const soldPr = document.getElementById('btn2_2');
const soldSection =  document.getElementById('sectionSold');

if(archivedPr){
    archivedPr.addEventListener('click', function () {
        if (archivedSection.style.display === "none") {
            archivedSection.style.display = "block";
        } else {
            archivedSection.style.display = "none";
        }
    });
}

if(viewPr){
    viewPr.addEventListener('click', function () {
        if (viewPrSection.style.display === "none") {
            viewPrSection.style.display = "block";
        } else {
            viewPrSection.style.display = "none";
        }
    });
}

if(viewSt){
    viewSt.addEventListener('click', function () {
        if (viewStSection.style.display === "none") {
            viewStSection.style.display = "block";
        } else {
            viewStSection.style.display = "none";
        }
    });
}

if(viewMsg){
    viewMsg.addEventListener('click', function () {
        if (viewMsgSection.style.display === "none") {
            viewMsgSection.style.display = "block";
        } else {
            viewMsgSection.style.display = "none";
        }
    });
}

if(purchasedPr){
    purchasedPr.addEventListener('click', function () {
        if (purchasedSection.style.display === "none") {
            purchasedSection.style.display = "block";
        } else {
            purchasedSection.style.display = "none";
        }
    });
}

if(soldPr){
    soldPr.addEventListener('click', function () {
        if (soldSection.style.display === "none") {
            soldSection.style.display = "block";
        } else {
            soldSection.style.display = "none";
        }
    });
}

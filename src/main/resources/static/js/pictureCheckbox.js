const checkbox = document.querySelector("input[name=notUpdateMyPicture]");

checkbox.addEventListener('change', function () {
    if (this.checked) {
        console.log(true);
        document.getElementById('testId').style.display = 'none';
    } else {
        console.log(false);
        document.getElementById('testId').style.display = 'block';
    }
});


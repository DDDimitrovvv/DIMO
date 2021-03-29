const checkbox = document.querySelector("input[name=notUpdateMyPicture]");

checkbox.addEventListener('change', function () {
    if (this.checked) {
        document.getElementById('testId').style.display = 'none';
    } else {
        document.getElementById('testId').style.display = 'block';
    }
});


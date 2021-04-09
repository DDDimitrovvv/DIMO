const check = function () {
    if (document.getElementById('password').value ===
        document.getElementById('confirmPassword').value) {
        document.getElementById('password').style.color = 'green';
        document.getElementById('password').innerHTML = 'matching';
        document.getElementById('confirmPassword').style.color = 'green';
        document.getElementById('confirmPassword').innerHTML = 'matching';
        document.getElementById('wrongconfirmpassword').style.display = "none";

    } else {
        document.getElementById('password').style.color = 'red';
        document.getElementById('confirmPassword').innerHTML = 'not matching';
        document.getElementById('wrongconfirmpassword').style.display = "block";
    }
};
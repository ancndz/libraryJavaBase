var password = document.getElementById("pass")
    , confirm_password = document.getElementById("passConf");

function validatePassword() {
    if (password.value !== confirm_password.value) {
        confirm_password.setCustomValidity("Passwords Don't Match");
        alert("Password doesnt match")
        return false;
    } else {
        confirm_password.setCustomValidity('');
        return true;
    }
}

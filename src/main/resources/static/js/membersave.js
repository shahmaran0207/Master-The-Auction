const emailCheck = () => {
    const email = document.getElementById("memberEmail").value;
    const checkResult = document.getElementById("check-result");
    const submitBtn = document.getElementById("submit-btn");

    checkResult.innerHTML = "Checking email...";
    checkResult.style.color = "blue";
    submitBtn.disabled = true;

    $.ajax({
        type: "post",
        url: "/Member/email-check",
        data: { "memberEmail": email },

        success: function (res) {
            if (res === "ok") {
                checkResult.style.color = "green";
                checkResult.innerHTML = "사용 가능한 이메일 입니다.";
                checkFormValidity();
            } else {
                checkResult.style.color = "red";
                checkResult.innerHTML = "이미 사용중인 이메일 입니다.";
                submitBtn.disabled = true;
            }
        },
        error: function (err) {
            console.log("Error:", err);
            submitBtn.disabled = true;
        }
    });
};

const resetEmailCheckResult = () => {
    const checkResult = document.getElementById("check-result");
    checkResult.innerHTML = "";
    checkResult.style.color = "black";
    document.getElementById("submit-btn").disabled = true;
};



const resetPasswordCheckResult = () => {
    const passwordCheckResult = document.getElementById("password-check-result");
    passwordCheckResult.innerHTML = "";
    passwordCheckResult.style.color = "black";
    document.getElementById("submit-btn").disabled = true;
};



function sendNumber() {
    $("#mail_number").show();
}

function confirmNumber() {
    let mail = $("#mail").val();
    let number = $("#number").val();

    $.ajax({
        url: "/api/v1/email/verify",
        type: "post",
        data: { "mail": mail, "verifyCode": number },
        success: function (data) {
            alert("인증 성공");
            checkFormValidity();
        }
    });
}

function passwordCheck() {
    let password = document.getElementById("memberPassword").value;
    let result = document.getElementById("password-check-result");

    if (password.length >= 7) {
        result.textContent = "사용 가능한 비밀번호 입니다.";
        result.style.color = "blue";
    } else {
        result.textContent = "비밀번호 길이가 7자 이하입니다.";
        result.style.color = "red";
    }
    checkFormValidity();
}

function checkFormValidity() {
    let emailValid = document.getElementById("check-result").style.color === "green";
    let passwordValid = document.getElementById("password-check-result").style.color === "blue";
    let nameValid = document.getElementById("memberName").value.trim() !== "";

    document.getElementById("submit-btn").disabled = !(emailValid && passwordValid && nameValid);
}

document.getElementById("memberEmail").addEventListener("blur", emailCheck);
document.getElementById("memberEmail").addEventListener("input", resetEmailCheckResult);
document.getElementById("memberPassword").addEventListener("blur", passwordCheck);
document.getElementById("memberPassword").addEventListener("input", resetPasswordCheckResult);
document.getElementById("memberName").addEventListener("input", checkFormValidity);
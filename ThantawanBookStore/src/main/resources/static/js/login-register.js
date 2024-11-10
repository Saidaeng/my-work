const body = document.querySelector(".body");
const wrapper = document.querySelector(".wrapper");
const loginLink = document.querySelector(".login-link");
const registerLink = document.querySelector(".register-link");
const btnPopup = document.querySelectorAll(".login-registerpopup");
const iconClose = document.querySelector(".icon-closeio");
let userAccountLink = document.querySelector(
  ".user-account.for-buy.login-registerpopup"
);

if (memberID !== null) {
  userAccountLink.setAttribute("href", "myprofile.html");
  userAccountLink.innerHTML =
    '<i class="icon icon-user"></i><span> Account</span>';
  document.addEventListener("DOMContentLoaded", (event) => {
    // Select the element with the class 'logout-btn'
    var element = document.querySelector(".logout-btn");

    // Change the display property to 'inline-block'
    element.style.display = "inline-block";
  });
} else {
  registerLink.addEventListener("click", () => {
    wrapper.classList.add("active");
  });

  loginLink.addEventListener("click", () => {
    wrapper.classList.remove("active");
  });

  const handleClick = () => {
    wrapper.classList.add("active-popup");
    body.classList.add("no-scroll"); // Add 'no-scroll' class to body
  };

  btnPopup.forEach((btn) => {
    btn.addEventListener("click", handleClick);
  });

  iconClose.addEventListener("click", () => {
    wrapper.classList.remove("active-popup");
    body.classList.remove("no-scroll"); // Remove 'no-scroll' class from body
  });

  document
    .getElementById("registerForm")
    .addEventListener("submit", function (event) {
      event.preventDefault();

      var formData = new FormData(event.target);
      var jsonData = {};

      for (var pair of formData.entries()) {
        jsonData[pair[0]] = pair[1];
      }

      fetch("/api/register", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify(jsonData),
      })
        .then(function (response) {
          return response.json();
        })
        .then(function (data) {
          if (data.success) {
            swal({
              title: "Congratulations!",
              text: "Registration successful!",
              icon: "success",
              className: "customSwal",
              button: "Done",
            }).then((value) => {
              if (value) {
                wrapper.classList.remove("active");
              }
            });
          } else {
            swal({
              title: "Oops!",
              text: data.message,
              icon: "error",
              className: "customSwal",
              button: "Try again",
            }).then((value) => {});
          }
        })
        .catch(function (error) {
          console.error("Error:", error);
          swal({
            title: "Oops!",
            text: "An error occurred while processing your request: " + error,
            icon: "error",
            className: "customSwal",
            button: "Try again",
          }).then((value) => {});
        });
    });

  document
    .getElementById("loginForm")
    .addEventListener("submit", function (event) {
      event.preventDefault();

      var formData = new FormData(event.target);
      var jsonData = {};

      for (var pair of formData.entries()) {
        if (pair[0] === "loginID") {
          jsonData["username"] = pair[1];
          jsonData["citizenID"] = pair[1];
        } else {
          jsonData[pair[0]] = pair[1];
        }
      }

      fetch("/api/login", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify(jsonData),
      })
        .then(function (response) {
          return response.json();
        })
        .then(function (data) {
          if (data.success) {
            swal({
              title: "Congratulations!",
              text: "Login successful!",
              icon: "success",
              className: "customSwal",
              button: "Done",
            }).then((value) => {
              wrapper.classList.remove("active-popup");
              body.classList.remove("no-scroll");
              btnPopup.forEach((btn) => {
                btn.classList.remove("login-registerpopup");
                btn.removeEventListener("click", handleClick);
              });
              userAccountLink.setAttribute("href", "myprofile.html");
              userAccountLink.innerHTML =
                '<i class="icon icon-user"></i><span> Account</span>';
              sessionStorage.setItem("memberID", data.memberID);
              var logoutButton = document.querySelector(".logout-btn");
              logoutButton.style.display = "inline-block";
            });
          } else {
            swal({
              title: "Oops!",
              text: "Invalid username/email or password. Please try again.",
              icon: "error",
              className: "customSwal",
              button: "Try again",
            }).then((value) => {});
          }
        })
        .catch(function (error) {
          console.error("Error:", error);
          swal({
            title: "Oops!",
            text: "An error occurred while processing your request: " + error,
            icon: "error",
            className: "customSwal",
            button: "Try again",
          }).then((value) => {});
        });
    });
}

function deleteSessionItem() {
  sessionStorage.removeItem("memberID");
  location.reload();
}

let inputs = document.querySelectorAll("#submitEdit input");
var formElement = document.getElementById("editform");

fetch("/api/getData", {
  method: "POST",
  headers: {
    "Content-Type": "application/json",
  },
  body: JSON.stringify({ memberID: memberID }),
})
  .then((response) => {
    if (!response.ok) {
      throw new Error("Network response was not ok");
    }
    return response.text();
  })
  .then((data) => {
    if (data) {
      var jsonData = JSON.parse(data);
      if (jsonData) {
        document.getElementById("MemberID").textContent =
          "MemberID: " + jsonData.memberID;
        document.getElementById("username").textContent =
          "Username: " + jsonData.username;
        document.getElementById("citizenID").textContent =
          "CitizenID: " + jsonData.citizenID;
        document.getElementById("fname").value = jsonData.fname;
        document.getElementById("lname").value = jsonData.lname;
        document.getElementById("email").value = jsonData.email;
        document.getElementById("about").value = jsonData.about;
        document.getElementById("street").value = jsonData.street;
        document.getElementById("district").value = jsonData.district;
        document.getElementById("province").value = jsonData.province;
        if (jsonData.zipcode != 0) {
          document.getElementById("zipcode").value = jsonData.zipcode;
        }
        document.getElementById("tel").value = jsonData.tel;

        // Disable input fields that have a value
        inputs.forEach((input) => {
          input.disabled = true;
        });
      }
    } else {
      console.log("No data returned from server");
    }
  })
  .catch((error) => {
    console.error("Error:", error);
  });

document.querySelector(".edit").addEventListener("click", function () {
  inputs.forEach((input) => {
    if (input.disabled) {
      input.disabled = false;
    } else {
      swal({
        title: "Oops!",
        text: "Please press submit button!",
        icon: "error",
        className: "customSwal",
        button: "Try again",
      }).then((value) => {});
    }
  });
});

document
  .getElementById("submitEdit")
  .addEventListener("submit", function (event) {
    event.preventDefault();

    var formData = new FormData(event.target);
    var jsonData = {};

    for (var pair of formData.entries()) {
      jsonData[pair[0]] = pair[1];
    }
    jsonData.memberID = memberID;

    // Check if all inputs are enabled
    var allEnabled = Array.from(inputs).every((input) => !input.disabled);

    if (allEnabled) {
      fetch("/api/editProfile", {
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
              text: "Edit your profile successful!",
              icon: "success",
              className: "customSwal",
              button: "Done",
            }).then((value) => {
              inputs.forEach((input) => {
                if (!input.disabled) {
                  input.disabled = true;
                }
              });
            });
          } else {
            swal({
              title: "Oops!",
              text: "There is an error to edit your profile. Please try again.",
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
    } else {
      swal({
        title: "Oops!",
        text: "Please press edit my profile button first!",
        icon: "error",
        className: "customSwal",
        button: "Try again",
      }).then((value) => {});
    }
  });

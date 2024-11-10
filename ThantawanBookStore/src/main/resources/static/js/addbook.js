document.getElementById("image1").addEventListener("change", function () {
  previewImage(this, "imagePreview1");
});
document.getElementById("image2").addEventListener("change", function () {
  previewImage(this, "imagePreview2");
});
document.getElementById("image3").addEventListener("change", function () {
  previewImage(this, "imagePreview3");
});

function previewImage(input, previewId) {
  var reader = new FileReader();
  reader.onload = function (e) {
    document.getElementById(previewId).setAttribute("src", e.target.result);
  };
  reader.readAsDataURL(input.files[0]);
}

document.getElementById("addAuthor").addEventListener("click", function () {
  var authorContainer = document.getElementById("authorContainer");
  var authorCount = authorContainer.getElementsByTagName("input").length;
  var firstAuthor = document.getElementById("author1");

  if (firstAuthor.value === "") {
    alert("Please fill in the first author field before adding more authors.");
    return;
  }

  var newLabel = document.createElement("label");
  newLabel.className = "labels";
  newLabel.textContent = "Author " + (authorCount + 1);

  var newInput = document.createElement("input");
  newInput.type = "text";
  newInput.className = "form-control";
  newInput.name = "author" + (authorCount + 1);
  newInput.id = "author" + (authorCount + 1);

  authorContainer.appendChild(newLabel);
  authorContainer.appendChild(newInput);
});

document.getElementById("removeAuthor").addEventListener("click", function () {
  var authorContainer = document.getElementById("authorContainer");
  var authorCount = authorContainer.getElementsByTagName("input").length;

  if (authorCount > 1) {
    var lastInput =
      authorContainer.getElementsByTagName("input")[authorCount - 1];
    var lastLabel =
      authorContainer.getElementsByTagName("label")[authorCount - 1];

    authorContainer.removeChild(lastInput);
    authorContainer.removeChild(lastLabel);
  }
});

document
  .getElementById("submitAddBook")
  .addEventListener("submit", function (event) {
    event.preventDefault();

    // Get the selected category
    var category = document.getElementById("category").value;

    // Check if the user has selected a category other than the default
    if (category === "00") {
      alert("Please select a category.");
      return;
    }

    var bookForm = new FormData();
    bookForm.append("bName", document.getElementById("bName").value);
    bookForm.append("isbn", document.getElementById("isbn").value);
    bookForm.append("fullPrice", document.getElementById("fullPrice").value);
    bookForm.append("images", document.getElementById("image1").files[0]);
    bookForm.append("images", document.getElementById("image2").files[0]);
    bookForm.append("images", document.getElementById("image3").files[0]);
    bookForm.append("lessorID", memberID);
    bookForm.append("tagline", document.getElementById("tagline").value);
    bookForm.append("pTime", document.getElementById("pTime").value);
    bookForm.append("pYear", document.getElementById("pYear").value);
    bookForm.append("publisher", document.getElementById("publisher").value);
    bookForm.append(
      "rentPricePerDay",
      document.getElementById("rentPricePerDay").value
    );
    bookForm.append(
      "SecurityDeposit",
      document.getElementById("SecurityDeposit").value
    );
    bookForm.append("category", document.getElementById("category").value);

    var authors = document
      .getElementById("authorContainer")
      .getElementsByTagName("input");
    for (var i = 0; i < authors.length; i++) {
      bookForm.append("authors", authors[i].value);
    }

    // Get the current date and time
    var datebook = new Date();
    // Append it to the form data
    bookForm.append("datebook", datebook);

    fetch("/book/addBook", {
      method: "POST",
      body: bookForm,
    })
      .then(function (response) {
        return response.json();
      })
      .then(function (data) {
        if (data.success) {
          swal({
            title: "Congratulations!",
            text: "Add your book successful!",
            icon: "success",
            className: "customSwal",
            button: "Done",
          }).then((value) => {});
        } else {
          swal({
            title: "Oops!",
            text: "There is an error to add your book. Please try again.",
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

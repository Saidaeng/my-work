fetch("/book/countBookByID", {
  method: "POST",
  headers: {
    "Content-Type": "application/json",
  },
  body: JSON.stringify({ lessorID: memberID }),
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
      if (jsonData > 0) {
        fetch("/book/getBookByID", {
          method: "POST",
          headers: {
            "Content-Type": "application/json",
          },
          body: JSON.stringify({ lessorID: memberID }),
        })
          .then((response) => {
            if (!response.ok) {
              throw new Error("Network response was not ok");
            }
            return response.text();
          })
          .then((data) => {
            if (data) {
              var jsonDataContent = JSON.parse(data);
              if (jsonDataContent) {
                console.log(jsonDataContent);
                // Assuming jsonData is the number of slider-items you want to create
                // สมมติว่า jsonData เป็นจำนวนของ slider-items ที่คุณต้องการสร้าง
                for (var i = 0; i < jsonData; i++) {
                  // สร้าง div ใหม่
                  var newDiv = document.createElement("div");
                  newDiv.className = "col-md-3";
                  newDiv.style.textAlign = "center";
                  newDiv.style.justifyContent = "center";
                  newDiv.style.alignContent = "center";

                  // สร้าง figure ใหม่
                  var newFigure = document.createElement("figure");
                  newFigure.className = "product-style";

                  // สร้าง img ใหม่
                  var newImg = document.createElement("img");
                  newImg.src =
                    "data:image/jpeg;base64," +
                    jsonDataContent[i].pictures[0].picture;
                  newImg.alt = "Book Cover";
                  newImg.className = "product-image";
                  newImg.style.maxWidth = "220px";
                  newImg.style.maxHeight = "318px";

                  // สร้าง figcaption ใหม่
                  var newFigcaption = document.createElement("figcaption");

                  // สร้าง h3 ใหม่
                  var newH3 = document.createElement("h3");
                  newH3.style.margin = "25px 0 0";
                  newH3.textContent = jsonDataContent[i].book.bname;

                  var newP1 = document.createElement("p");
                  newP1.style.margin = "0";

                  // Initialize an empty string for the authors
                  var authors = "";

                  for (var j = 0; j < jsonDataContent[i].authors.length; j++) {
                    // Add each author to the string, separated by a comma and a space
                    authors += jsonDataContent[i].authors[j].author;
                    if (j < jsonDataContent[i].authors.length - 1) {
                      authors += ", ";
                    }
                  }

                  // Set the textContent of the p element to the authors string
                  newP1.textContent = authors;

                  var newP2 = document.createElement("p");
                  newP2.style.margin = "0";
                  newP2.textContent =
                    jsonDataContent[i].book.rentPricePerDay + "฿ / 1 day";

                  if (!jsonDataContent[i].book.status) {
                    var newP3 = document.createElement("p");
                    newP3.style.color = "#079f56";
                    newP3.style.margin = "0";
                    newP3.textContent = "Available";
                  } else {
                    var newP3 = document.createElement("p");
                    newP3.style.color = "#D6A916";
                    newP3.style.margin = "0";
                    newP3.textContent = jsonDataContent[i].book.statusLo;
                    // สร้าง button ใหม่
                    var newButton = document.createElement("button");
                    newButton.type = "button";
                    newButton.style.background = "#079f56";
                    newButton.className = "confirm-button";
                    newButton.textContent = "Deliver";
                    newFigcaption.appendChild(newButton);
                  }

                  // เพิ่ม h3, p, และ button ลงใน figcaption
                  newFigcaption.appendChild(newH3);
                  newFigcaption.appendChild(newP1);
                  newFigcaption.appendChild(newP2);
                  newFigcaption.appendChild(newP3);

                  var newButton = document.createElement("button");
                  newButton.type = "button";
                  newButton.textContent = "Detail";

                  // Create new anchor element
                  var newAnchor = document.createElement("a");
                  newAnchor.href =
                    "BookDetail.html?BookID=" + jsonDataContent[i].book.bookID;

                  // Append the button to the anchor
                  newAnchor.appendChild(newButton);

                  // Append the anchor (which now contains the button) to the figcaption
                  newFigcaption.appendChild(newAnchor);

                  // เพิ่ม img และ figcaption ลงใน figure
                  newFigure.appendChild(newImg);
                  newFigure.appendChild(newFigcaption);

                  // เพิ่ม figure ลงใน div
                  newDiv.appendChild(newFigure);

                  // เพิ่ม div ลงใน main-slider
                  var mainDiv = document.querySelector(".row.mt-3");
                  mainDiv.appendChild(newDiv);
                }
            
              }
            } else {
              console.log("No data returned from server");
            }
          })
          .catch((error) => {
            console.error("Error:", error);
          });
      }
    } else {
      console.log("No data returned from server");
    }
  })
  .catch((error) => {
    console.error("Error:", error);
  });

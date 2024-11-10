fetch("/book/getBookByBookID", {
  method: "POST",
  headers: {
    "Content-Type": "application/json",
  },
  body: JSON.stringify({ bookID: bookId }),
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
        console.log(jsonData);
        document.getElementById("bookName").textContent = jsonData.book.bname;
        if (jsonData.book.tagline == "") {
          document.getElementById("bookTitle").textContent = "ไม่มีรายละเอียด";
        } else {
          document.getElementById("bookTitle").textContent =
            jsonData.book.tagline;
        }

        if (jsonData.book.ISBN == 0) {
          document.getElementById("bookISBN").textContent = "ISBN: - ";
        } else {
          document.getElementById("bookISBN").textContent =
            "ISBN: " + jsonData.book.ISBN;
        }

        if (jsonData.book.ptime == 0) {
          document.getElementById("bookTime").textContent = "ครั้งที่พิมพ์: - ";
        } else {
          document.getElementById("bookTime").textContent =
            "ครั้งที่พิมพ์: " + jsonData.book.ptime;
        }

        if (jsonData.book.pyear == 0) {
          document.getElementById("bookYear").textContent = "ปีที่พิมพ์: - ";
        } else {
          document.getElementById("bookYear").textContent =
            "ปีที่พิมพ์: " + jsonData.book.pyear;
        }

        if (jsonData.book.fullPrice == null) {
          document.getElementById("bookFP").textContent =
            "ราคาเต็มของหนังสือ: ไม่ได้ระบุ ";
        } else {
          document.getElementById("bookFP").textContent =
            "ราคาเต็มของหนังสือ: " + jsonData.book.fullPrice + "฿";
        }

        if (jsonData.book.publisher == "") {
          document.getElementById("bookPublisher").textContent =
            "สำนักพิมพ์: - ";
        } else {
          document.getElementById("bookPublisher").textContent =
            "สำนักพิมพ์: " + jsonData.book.publisher;
        }

        document.getElementById("bookDate").textContent =
          "วันที่ลงเช่า: " + jsonData.book.datebook;

        var authors = "";
        for (var j = 0; j < jsonData.authors.length; j++) {
          // Add each author to the string, separated by a comma and a space
          authors += jsonData.authors[j].author;
          if (j < jsonData.authors.length - 1) {
            authors += ", ";
          }
        }
        document.getElementById("bookAuthor").textContent =
          "ผู้เขียน: " + authors;

        document.getElementById("bookCategory").textContent =
          "ประเภท: " + jsonData.book.category.cname;

        var mainDiv = document.querySelector(".col-md-5");
        for (var i = 0; i < jsonData.pictures.length; i++) {
          // สร้าง img ใหม่
          var newImg = document.createElement("img");
          newImg.src = "data:image/jpeg;base64," + jsonData.pictures[i].picture;
          newImg.alt = "Book Cover";
          newImg.className = "product-image";
          newImg.style.maxWidth = "220px";
          newImg.style.maxHeight = "318px";
          mainDiv.appendChild(newImg);
        }

        document.getElementById("bookPrice").textContent =
          jsonData.book.rentPricePerDay +
          "฿ / 1 day (มัดจำ " +
          jsonData.book.securityDeposit +
          "฿)";
        if (!jsonData.book.status) {
          document.getElementById("bookStatus").textContent = "Available";
          document.getElementById("bookStatus").style.color = "#079f56";
        } else {
          document.getElementById("bookStatus").style.color = "#D6A916";
          document.getElementById("bookStatus").textContent =
            jsonData.book.statusLo;
        }
      }
    } else {
      console.log("No data returned from server");
    }
  })
  .catch((error) => {
    console.error("Error:", error);
  });

fetch("/book/countBook", {
  method: "POST",
  headers: {
    "Content-Type": "application/json",
  },
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
      fetch("/book/getRandomBook", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
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
              // Assuming jsonData is the number of slider-items you want to create
              for (var i = 0; i < jsonData; i++) {
                // Create new slider item elements
                var sliderItem = document.createElement("div");
                sliderItem.className = "slider-item";

                var bannerContent = document.createElement("div");
                bannerContent.className = "banner-content";
                sliderItem.appendChild(bannerContent);

                var title = document.createElement("h2");
                title.className = "banner-title";
                title.id = "sliderItem" + (i + 1);
                title.textContent = "Life of the Wild";
                bannerContent.appendChild(title);

                var text = document.createElement("p");
                text.id = "sliderInfo" + (i + 1);
                text.textContent =
                  "Lorem ipsum dolor sit amet, consectetur adipiscing elit...";
                bannerContent.appendChild(text);

                var btnWrap = document.createElement("div");
                btnWrap.className = "btn-wrap";
                bannerContent.appendChild(btnWrap);

                var link = document.createElement("a");
                link.href = "BookDetail.html?BookID=" + jsonDataContent[i].book.bookID;
                link.className = "btn btn-outline-accent btn-accent-arrow";
                link.innerHTML =
                  'Read More<i class="icon icon-ns-arrow-right"></i>';
                btnWrap.appendChild(link);

                var image = document.createElement("img");
                image.src = "images/main-banner1.jpg";
                image.alt = "banner";
                image.className = "banner-image";
                image.id = "sliderPics" + (i + 1);
                image.style.maxWidth = "500px";
                image.style.maxHeight = "700px";
                sliderItem.appendChild(image);

                // Append the new slider item to the main slider
                var mainSlider = document.querySelector(
                  ".main-slider.pattern-overlay"
                );
                mainSlider.appendChild(sliderItem);
                document.getElementById("sliderItem" + (i+1)).textContent =
                  jsonDataContent[i].book.bname;
                document.getElementById("sliderInfo" + (i+1)).textContent =
                  jsonDataContent[i].book.tagline;
                document.getElementById("sliderPics" + (i+1)).src =
                  "data:image/jpeg;base64," +
                  jsonDataContent[i].pictures[0].picture;
              }
            }
          } else {
            console.log("No data returned from server");
          }
        })
        .catch((error) => {
          console.error("Error:", error);
        });
    } else {
      console.log("No data returned from server");
    }
  })
  .catch((error) => {
    console.error("Error:", error);
  });

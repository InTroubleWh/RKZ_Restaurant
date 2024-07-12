let slideIndex = 0;

// Function to show slides
function showSlides(n) {
    let i;
    let slides = document.getElementsByClassName("slide");
    let dots = document.getElementsByClassName("dot");

    if (n >= slides.length) {
        slideIndex = 0;
    } else if (n < 0) {
        slideIndex = slides.length - 1;
    } else {
        slideIndex = n;
    }

    for (i = 0; i < slides.length; i++) {
        slides[i].style.display = "none";
    }
    for (i = 0; i < dots.length; i++) {
        dots[i].className = dots[i].className.replace(" active", "");
    }
    slides[slideIndex].style.display = "block";
    dots[slideIndex].className += " active";
}

// Functions to control the main slideshow manually
function plusSlides(n) {
    showSlides(slideIndex + n);
}

function currentSlide(n) {
    showSlides(n - 1);
}

function loadMenuItems(sliderId) {
    $.ajax({
        url: 'specialOffer',
        method: 'GET',
        success: function (data) {
            let slider = $(`#${sliderId}`);
            slider.empty(); // Clear existing items
            data.forEach(item => {
                slider.append(`
                    <div class="menu-cards">
                        <div class="menu-cards-image">
                            <img src="/Project_2_RKZ_Restaurant/image?id=${item.itemId}&source=menu">
                        </div>
                        <div class="menu-cards-name">
                            <p>${item.name}</p>
                        </div>
                        <div class="menu-cards-price">
                            <p class="original-price" style="text-decoration:line-through; color:grey;">Rp.${item.originalPrice}</p>
                            <p class="discounted-price">Rp.${item.price}</p>
                        </div>
                        <button class="order-button" data-item-id="${item.itemId}">Order</button>
                    </div>
                `);
            });

            // Add event listener for "Order" button
            slider.find('.order-button').click(function () {
                let itemId = $(this).data('item-id');
                addToCart(itemId);
            });
        },
        error: function (xhr, status, error) {
            console.error('Error loading menu items:', status, error);
            alert('Error loading menu items');
        }
    });
}

function addToCart(itemId) {
    $.ajax({
        url: 'toCart',
        type: 'POST',
        data: {itemId: itemId},
        success: function (data) {
            renderCart(); // Refresh the cart content
        },
        error: function (xhr, status, error) {
            console.error('Error adding item to cart:', status, error);
        }
    });
}

document.addEventListener("DOMContentLoaded", function () {
    // Slideshow setup
    showSlides(slideIndex);
    loadMenuItems('special-offer-slider')

    // Function to change slides automatically
    setInterval(function () {
        plusSlides(1);
    }, 7000); // Change slide every 7 seconds

    // Special offer slider setup
    let specialOfferIndex = 0;
    const specialOfferSlides = document.querySelector('#special-offer-slider');

    if (specialOfferSlides) {
        function showSlides1(n) {
            const slides = specialOfferSlides.querySelectorAll('.menu-cards');
            if (slides.length > 0) {
                if (n >= slides.length) {
                    specialOfferIndex = 0;
                }
                if (n < 0) {
                    specialOfferIndex = slides.length - 1;
                }
                specialOfferSlides.scrollLeft = slides[specialOfferIndex].offsetLeft - specialOfferSlides.offsetLeft;
            }
        }

        function plusSlides1(n) {
            showSlides1(specialOfferIndex += n);
        }

        function currentSlide1(n) {
            showSlides1(specialOfferIndex = n);
        }

        document.querySelector('.slider-prev-special').onclick = function () {
            plusSlides1(-1);
        };
        document.querySelector('.slider-next-special').onclick = function () {
            plusSlides1(1);
        };

        showSlides1(specialOfferIndex);
    }

    // Top picks slider setup
    let topPicksIndex = 0;
    const topPicksSlides = document.querySelector('.top-picks-slider');

    if (topPicksSlides) {
        function showSlides2(n) {
            const slides = topPicksSlides.querySelectorAll('.menu-cards');
            if (slides.length > 0) {
                if (n >= slides.length) {
                    topPicksIndex = 0;
                }
                if (n < 0) {
                    topPicksIndex = slides.length - 1;
                }
                topPicksSlides.scrollLeft = slides[topPicksIndex].offsetLeft - topPicksSlides.offsetLeft;
            }
        }

        function plusSlides2(n) {
            showSlides2(topPicksIndex += n);
        }

        function currentSlide2(n) {
            showSlides2(topPicksIndex = n);
        }

        document.querySelector('.slider-prev-top').onclick = function () {
            plusSlides2(-1);
        };
        document.querySelector('.slider-next-top').onclick = function () {
            plusSlides2(1);
        };

        showSlides2(topPicksIndex);
    }
});

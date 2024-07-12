function loadMenuItems(category, sliderId) {
    $.ajax({
        url: 'menu',
        method: 'GET',
        data: {category: category},
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
                            <p>Rp.${item.price}</p>
                        </div>
                        <button class="order-button" data-item-id="${item.itemId}">Order</button>
                    </div>
                `);
            });

            // Add event listener for "Order" button
            slider.find('.order-button').click(function () {
                let itemId = $(this).data('item-id');
                addToCart(itemId);
                renderCart();
            });
        },
        error: function (xhr, status, error) {
            console.error('Error loading menu items:', status, error);
            alert('Error loading menu items');
        }
    });
}

function renderCart() {
    $.ajax({
        url: 'getCart',
        type: 'GET',
        success: function (data) {
            $('#cart-content').empty();
            data.forEach(item => {
                $('#cart-content').append(`
                        <li class="cart-item">
                            <div class="cart-item-container">
                                <span class="item-name-wrap">${item.itemName}</span>
                                <span class="item-price-wrap">${item.price.toFixed(2)}</span>
                                <div class="quantity-controls">
                                    <button class="decrease-qty" data-item-id="${item.itemId}">-</button>
                                    <input type="number" class="item-quantity" value="${item.quantity}" min="1">
                                    <button class="increase-qty" data-item-id="${item.itemId}">+</button>
                                </div>
                                <button class="remove-item" data-item-id="${item.itemId}">X</button>
                            </div>
                        </li>
                    `);
            });
        },
        error: function (xhr, status, error) {
            if (xhr.status === 401) {
                $('#login-modal').css('display', 'flex');
            } else {
                console.error('Error loading cart items:', status, error);
                alert('Error loading cart items');
            }
        }
    });
}

// Function to update item quantity in the cart
function updateCartItem(itemId, quantity) {
    $.ajax({
        url: 'updateCart',
        type: 'POST',
        contentType: 'application/json',
        data: JSON.stringify({itemId: itemId, quantity: quantity}),
        success: function () {
            renderCart(); // Refresh cart display after update
        },
        error: function (xhr, status, error) {
            console.error('Error updating cart item:', status, error);
        }
    });
}

// Function to remove item from cart
function removeCartItem(itemId) {
    $.ajax({
        url: 'removeFromCart',
        type: 'POST',
        data: {itemId: itemId},
        success: function () {
            renderCart(); // Refresh cart display after removal
        },
        error: function (xhr, status, error) {
            console.error('Error removing cart item:', status, error);
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

let slidesIndex = [0, 0, 0, 0]; // slidesIndex[0] is main, [1] is side, [2] is desserts, [3] is drinks

function slidesControl(sliderId, index, n) {
    const slider = document.querySelector('#' + sliderId);
    const slides = slider.querySelectorAll('.menu-cards');
    if (n >= slides.length) {
        slidesIndex[index] = 0;
    }
    if (n < 0) {
        slidesIndex[index] = slides.length - 1;
    }
    slider.scrollLeft = slides[slidesIndex[index]].offsetLeft - slider.offsetLeft;
}
//goToSlide function di window biar scope global dan bisa dipanggil button di luar fungsi anonimus document.ready
window.goToSlide = function (index, n) {
    slidesIndex[index] += n;
    switch (index) {
        case 0:
            slidesControl('main-slider', index, slidesIndex[index]);
            break;
        case 1:
            slidesControl('side-slider', index, slidesIndex[index]);
            break;
        case 2:
            slidesControl('desserts-slider', index, slidesIndex[index]);
            break;
        case 3:
            slidesControl('drinks-slider', index, slidesIndex[index]);
            break;
    }
};

$(document).ready(function () {
    // Load initial menu items
    loadMenuItems('main', 'main-slider');
    loadMenuItems('side', 'side-slider');
    loadMenuItems('dessert', 'dessert-slider');
    loadMenuItems('drinks', 'drinks-slider');
    renderCart(); // Load cart contents initially

    $('#search-bar').on('input', function () {
        let query = $(this).val();
        if (query.length > 0) {
            $.ajax({
                url: 'searchMenuSuggestions',
                type: 'GET',
                data: {query: query},
                success: function (data) {
                    let dropdownContent = $('#dropdown-content');
                    dropdownContent.empty();
                    data.forEach(item => {
                        dropdownContent.append(`<div class="dropdown-item">${item.name}</div>`);
                    });
                    dropdownContent.show();
                },
                error: function (xhr, status, error) {
                    console.error('Error fetching suggestions:', status, error);
                }
            });
        } else {
            $('#dropdown-content').hide();
        }
    });

    function performSearch(query) {
        $.ajax({
            url: 'searchMenu',
            type: 'GET',
            data: {query: query},
            success: function (data) {
                let menuContainer = $('#menu-groups-container');
                menuContainer.hide();
                let resultsContainer = $('#search-results-container');
                resultsContainer.empty();
                resultsContainer.show();
                data.forEach(item => {
                    resultsContainer.append(`
                        <div class="menu-cards">
                            <div class="menu-cards-image">
                                <img src="/Project_2_RKZ_Restaurant/image?id=${item.itemId}&source=menu">
                            </div>
                            <div class="menu-cards-name">
                                <p>${item.name}</p>
                            </div>
                            <div class="menu-cards-price">
                                <p>Rp.${item.price}</p>
                            </div>
                            <button class="order-button" data-item-id="${item.itemId}">Order</button>
                        </div>
                    `);
                });
                // Clear the search bar and hide the dropdown content
                $('#search-bar').val('');
                $('#dropdown-content').hide();
                // Reattach order button event listeners
                $('.order-button').click(function () {
                    let itemId = $(this).data('item-id');
                    addToCart(itemId);
                    renderCart();
                });
            },
            error: function (xhr, status, error) {
                console.error('Error performing search:', status, error);
                alert('Error performing search');
            }
        });
    }

    $(document).on('click', '.dropdown-item', function () {
        let selectedItem = $(this).text();
        $('#search-bar').val(selectedItem);
        $('#dropdown-content').hide();
        performSearch(selectedItem);
    });

    // Event listener for search button
    $('#search-button').on('click', function () {
        let query = $('#search-bar').val();
        if (query) {
            performSearch(query);
        }
    });


    // Event listener for increasing quantity
    $('#cart-content').on('click', '.increase-qty', function () {
        let itemId = $(this).data('item-id');
        let quantityInput = $(this).siblings('.item-quantity');
        let newQuantity = parseInt(quantityInput.val()) + 1;
        quantityInput.val(newQuantity);
        updateCartItem(itemId, newQuantity);
    });

    // Event listener for decreasing quantity
    $('#cart-content').on('click', '.decrease-qty', function () {
        let itemId = $(this).data('item-id');
        let quantityInput = $(this).siblings('.item-quantity');
        let newQuantity = parseInt(quantityInput.val()) - 1;
        if (newQuantity < 1) {
            newQuantity = 1; // Prevent decreasing below 1
        }
        quantityInput.val(newQuantity);
        updateCartItem(itemId, newQuantity);
    });

    // Event listener for removing item from cart
    $('#cart-content').on('click', '.remove-item', function () {
        let itemId = $(this).data('item-id');
        removeCartItem(itemId);
    });
});
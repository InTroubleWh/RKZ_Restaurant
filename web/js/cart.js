$(document).ready(function () {
    function fetchCartItems() {
        $.ajax({
            url: 'getCart',
            method: 'GET',
            dataType: 'json',
            success: function (cartItems) {
                renderCartItems(cartItems);
            },
            error: function (xhr, status, error) {
            if (xhr.status === 401) {
                $('#loginRequiredOverlay').show();
            } else {
                console.error('Error loading cart items:', status, error);
            }
        }
        });
    }

    function renderCartItems(cartItems) {
        const cartItemsContainer = $('#cart-items');
        cartItemsContainer.empty();

        let totalAmount = 0;

        $.each(cartItems, function (index, item) {
            const cartItemDiv = $(`
                <div class="cart-item">
                    <span class="cart-item-name">${item.itemName} x ${item.quantity}</span>
                    <span>Rp. ${(item.price * item.quantity).toFixed(2)}</span>
                    <button class="remove-button" data-item-id="${item.itemId}">Hapus</button>
                    <div class="quantity-controls">
                        <button class="decrease-qty" data-item-id="${item.itemId}" data-change="-1">-</button>
                        <input type="number" class="item-quantity" value="${item.quantity}" min="1" data-item-id="${item.itemId}">
                        <button class="increase-qty" data-item-id="${item.itemId}" data-change="1">+</button>
                    </div>
                </div>
            `);

            cartItemsContainer.append(cartItemDiv);
            totalAmount += item.price * item.quantity;
        });

        $('#total-amount').text(totalAmount.toFixed(2));
    }

    $(document).on('click', '.remove-button', function () {
        const itemId = $(this).data('item-id');
        updateCart(itemId, 0);
    });

    $(document).on('click', '.quantity-controls button', function () {
        const itemId = $(this).data('item-id');
        const change = $(this).data('change');
        const quantityInput = $(`input[data-item-id='${itemId}']`);
        const newQuantity = parseInt(quantityInput.val()) + change;

        if (newQuantity > 0) {
            quantityInput.val(newQuantity);
            updateCart(itemId, newQuantity);
        }
    });

    function updateCart(itemId, newQuantity) {
        $.ajax({
            url: 'updateCart',
            type: 'POST',
            contentType: 'application/json',
            data: JSON.stringify({itemId: itemId, quantity: newQuantity}),
            success: function () {
                fetchCartItems();
            },
            error: function () {
                alert('An error occurred while updating the cart.');
            }
        });
    }

    // Handle city selection to update post code placeholder
    $('.city-select').on('change', function () {
        const city = $(this).val();
        const postCodeInput = $(this).closest('form').find('.post-code');

        switch (city) {
            case 'Jakarta':
                postCodeInput.attr('placeholder', '10xxx - 14xxx e.g 10110');
                break;
            case 'Bogor':
                postCodeInput.attr('placeholder', '161xx - 169xx e.g 16119');
                break;
            case 'Depok':
                postCodeInput.attr('placeholder', '164xx - 165xx e.g 16411');
                break;
            default:
                postCodeInput.attr('placeholder', 'Post Code');
        }
    });

    $('#cart-buy').on('click', function () {
        const outletId = $('#address-form .outlet').val();
        const city = $('#address-form .city-select').val();
        const postCode = $('#address-form .post-code').val();
        const streetAddress = $('#address-form .street-address').val();

        if (!outletId || !city || !postCode || !streetAddress) {
            alert("Please fill in all address fields.");
            return;
        }

        // Hide the current form and show the payment modal
        $('#chooseModal').show();

        // Set the hidden form values in both payment forms
        $('#gopay-form .outlet, #va-form .outlet').val(outletId);
        $('#gopay-form .city-select, #va-form .city-select').val(city);
        $('#gopay-form .post-code, #va-form .post-code').val(postCode);
        $('#gopay-form .street-address, #va-form .street-address').val(streetAddress);
    });

    $('#gopay').click(function (event) {
        event.preventDefault();
        $('.container').hide();
        $('#chooseModal').hide();
        $('#gopayForm').show();
        $('#vaForm').hide();
    });

    $('#va').click(function (event) {
        event.preventDefault();
        $('.container').hide();
        $('#chooseModal').hide();
        $('#vaForm').show();
        $('#gopayForm').hide();
    });

    // Close modal
    $('.close').click(function () {
        $('#chooseModal').hide();
        window.location.href = 'home.jsp';
    });

    $(window).click(function (event) {
        if (event.target.id === 'chooseModal') {
            $('#chooseModal').hide();
            window.location.href = 'home.jsp';
        }
    });

    // Validate input to only allow numbers
    $('input[type="number"]').on('keypress', function (e) {
        if (e.which < 48 || e.which > 57) {
            e.preventDefault();
        }
    });

    $('#chooseModal').hide();
    $('#vaForm').hide();
    $('#gopayForm').hide();
    fetchCartItems();
});

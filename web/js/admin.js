$(document).ready(function () {
    function loadMenuItems() {
        $.ajax({
            url: 'adminPageMenu',
            type: 'GET',
            data: {action: 'getAll'},
            dataType: 'json',
            success: function (data) {
                var tableBody = $('#menu-table tbody');
                tableBody.empty(); // Clear any existing rows
                data.forEach(function (item) {
                    var row = `<tr>
                        <td>${item.itemId}</td>
                        <td><img src="/Project_2_RKZ_Restaurant/image?id=${item.itemId}&source=menu" alt="${item.name}" width="50"></td>
                        <td>${item.name}</td>
                        <td>${item.price}</td>
                        <td>${item.category}</td>
                        <td>
                            <button class="edit-item" data-id="${item.itemId}" data-section="menu">Edit</button>
                            <button class="delete-item" data-id="${item.itemId}">Delete</button>
                        </td>
                    </tr>`;
                    tableBody.append(row);
                });
            },
            error: function (xhr, status, error) {
                console.error('Error loading menu items:', error);
            }
        });
    }

    // Load menu items on page load
    loadMenuItems();

    // Event listener for delete buttons (use event delegation)
    $('#menu-table').on('click', '.delete-item', function () {
        var itemId = $(this).data('id');
        $.ajax({
            url: 'adminPageMenu',
            type: 'POST',
            data: {action: 'delete', itemId: itemId},
            success: function (response) {
                alert(response);
                loadMenuItems(); // Reload menu items after deletion
            },
            error: function (xhr, status, error) {
                console.error('Error deleting menu item:', error);
            }
        });
    });

    // Function to show modal with dynamic data
    function showModal(section, id) {
        var modal = $('#edit-modal');
        var content = $('.modal-content');

        // Load form fields dynamically based on the section
        switch (section) {
            case 'menu':
                // Load menu item details
                $.ajax({
                    url: 'adminPageMenu',
                    type: 'GET',
                    data: {action: 'getData', id: id},
                    dataType: 'json',
                    success: function (data) {
                        content.empty();
                        content.append(`
                            <span class="close">&times;</span>
                            <h2>Edit Item</h2>
                            <form id="edit-form" action="adminPageMenu" method="POST">
                                <label for="item-name">Name:</label>
                                <input type="text" id="item-name" name="name" value="${data.name}">
                                <label for="item-price">Price:</label>
                                <input type="text" id="item-price" name="price" value="${data.price}">
                                <label for="item-category">Category:</label>
                                <input type="text" id="item-category" name="category" value="${data.category}">
                                <input type="hidden" name="id" value="${data.itemId}">
                                <input type="hidden" name="action" value="update">
                                <button type="submit">Submit</button>
                            </form>
                        `);
                        // Close modal on close button click
                        $('.close').on('click', function () {
                            $('#edit-modal').hide();
                        });

                        // Handle save changes button click
                        $('#edit-form').on('submit', function (event) {
                            event.preventDefault(); // Prevent default form submission
                            var formData = $(this).serialize();
                            $.ajax({
                                url: 'adminPageMenu',
                                type: 'POST',
                                data: formData,
                                success: function (response) {
                                    alert(response);
                                    $('#edit-modal').hide();
                                    loadMenuItems(); // Reload menu items after update
                                },
                                error: function (xhr, status, error) {
                                    console.error('Error saving changes:', error);
                                }
                            });
                        });
                    },
                    error: function (xhr, status, error) {
                        console.error('Error loading item details:', error);
                    }
                });
                break;
                // Handle other cases for reservation and outlet similarly
        }

        modal.show();
    }

    // Event listener for edit buttons (use event delegation)
    $('body').on('click', '.edit-item', function () {
        var section = $(this).data('section');
        var id = $(this).data('id');
        showModal(section, id);
    });
});

$(document).ready(function () {
    // Get elements
    const $dateInput = $('#dob');
    const $timeInput = $('#time');
    const $errorMessage = $('<p class="error-message"></p>').insertBefore('#confirmBtn');

    // Function to update the time input field to the current time
    const updateTime = () => {
        const now = new Date();
        const yyyy = now.getFullYear();
        const mm = String(now.getMonth() + 1).padStart(2, '0'); // Months are zero-based
        const dd = String(now.getDate()).padStart(2, '0');
        const hh = String(now.getHours()).padStart(2, '0');
        const min = String(now.getMinutes()).padStart(2, '0');

        // Set date to today
        const currentDate = `${yyyy}-${mm}-${dd}`;
        $dateInput.val(currentDate);

        // Set time to current time
        const currentTime = `${hh}:${min}`;
        $timeInput.val(currentTime);
    };

    // Initial update of the time and date
    updateTime();

    // Update time every minute
    setInterval(updateTime, 60000);

    // Set minimum date for reservation to today
    const today = new Date().toISOString().split('T')[0];
    $dateInput.attr('min', today);

     // Event listener for form submission
    $('form').on('submit', function(event) {
        event.preventDefault();

        // Serialize form data
        var formData = $(this).serialize();

        // AJAX request to servlet
        $.ajax({
            type: 'POST',
            url: 'reserve',
            data: formData,
            dataType: 'json',
            success: function(response) {
                if (response.success) {
                    // Reservation successful
                    alert('Reservation successful!');
                    // Optionally, redirect to a success page or refresh current page
                } else {
                    // Reservation failed or validation error
                    alert('Reservation failed: ' + response.message);
                    // Display error message on the page
                    $('.error-message').text(response.message);
                }
            },
            error: function() {
                alert('Error processing reservation');
            }
        });
    });
});



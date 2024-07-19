$(document).ready(function () {
    var $loginModal = $("#login-modal");
    var $loginButton = $("#loginButton");
    var $loginForm = $("#login-modal-content form");
    var $errorMessage = $("<p>").css("color", "red").hide().appendTo($loginForm);

    // Show the login modal when the login button is clicked
    $loginButton.on("click", function () {
        $loginModal.css("display", "flex");
    });

    // Close the modal when clicking outside of the modal content
    $(window).on("click", function (event) {
        if (event.target == $loginModal[0]) {
            $loginModal.css("display", "none");
            $errorMessage.hide();
        }
    });

    // Submit login form via AJAX
    $loginForm.on("submit", function (event) {
        event.preventDefault();
        var identifier = $("#identifier").val();
        var password = $("#password").val();

        $.ajax({
            type: "POST",
            url: $(this).attr("action"), // The action attribute of the form
            data: {identifier: identifier, password: password},
            success: function (response) {
                response = response.trim();
                if (response === "success") {
                    // Close the modal on successful login
                    $loginModal.css("display", "none");
                    // Reload some part of the page or perform other actions
                    refreshHomePage(); // Example function to reload specific content
                } else {
                    // Show error message if login was not successful
                    $errorMessage.text("Login failed. Please try again.").show();
                }
            },
            error: function (xhr) {
                // Show specific error messages based on server response
                var errorMsg;
                if (xhr.status === 400) {
                    errorMsg = "Invalid request.";
                } else if (xhr.status === 401) {
                    errorMsg = "Wrong username, email, or password.";
                } else {
                    errorMsg = "An unexpected error occurred. Please try again later.";
                }
                $errorMessage.text(errorMsg).show();
            }
        });
    });

    function refreshHomePage() {
        // Example: Reload the navigation bar or specific content
        $("#loginNavbar").load(" #loginNavbar > *");
        // You can load specific elements or trigger other updates as needed
    }

    $('#loginPopUpOK').click(function() {
        // Close the confirmation popup
        $('#loginRequiredOverlay').hide();
    });
});
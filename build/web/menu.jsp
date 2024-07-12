<%@include file="header.jsp" %>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>RKZ Restaurant - Menu</title>
    <link rel="stylesheet" href="css/menu_page.css">
    <script src="js/menu.js"></script>
</head>
<body>
    <div id="content-container"> <!--content container-->
        <div id="menu-container"> <!--main content (menu)-->
            <!-- Search bar -->
            <div id="search-bar-container">
                <input type="text" id="search-bar" placeholder="Search for menu items...">
                <button id="search-button">Search</button>
                <div id="dropdown-content" class="dropdown-content"></div>
            </div>
            
            <!-- Existing menu groups -->
            <div id="menu-groups-container">
                <div class="menu-groups">
                    <h1>Main</h1>
                    <hr>
                    <div class="slider-container">
                        <button class="slider-prev" onclick="goToSlide(0, -1)">&#10094;</button>
                        <div class="menu-cards-slider" id="main-slider"></div>
                        <button class="slider-next" onclick="goToSlide(0, 1)">&#10095;</button>
                    </div>
                </div>
                <div class="menu-groups">
                    <h1>Side</h1>
                    <hr>
                    <div class="slider-container">
                        <button class="slider-prev" onclick="goToSlide(1, -1)">&#10094;</button>
                        <div class="menu-cards-slider" id="side-slider"></div>
                        <button class="slider-next" onclick="goToSlide(1, 1)">&#10095;</button>
                    </div>
                </div>
                <div class="menu-groups">
                    <h1>Desserts</h1>
                    <hr>
                    <div class="slider-container">
                        <button class="slider-prev" onclick="goToSlide(2, -1)">&#10094;</button>
                        <div class="menu-cards-slider" id="dessert-slider"></div>
                        <button class="slider-next" onclick="goToSlide(2, 1)">&#10095;</button>
                    </div>
                </div>
                <div class="menu-groups">
                    <h1>Drinks</h1>
                    <hr>
                    <div class="slider-container">
                        <button class="slider-prev" onclick="goToSlide(3, -1)">&#10094;</button>
                        <div class="menu-cards-slider" id="drinks-slider"></div>
                        <button class="slider-next" onclick="goToSlide(3, 1)">&#10095;</button>
                    </div>
                </div>
            </div>
            
            <!-- Search results container -->
            <div id="search-results-container" style="display: none;"></div>
        </div>
        
        <div id="order-cart"> <!--side content (cart)-->
            <h1 style="font-family:'Segoe UI',sans-serif">Your Order</h1>
            <div id="cart-content-container">
                <ul id="cart-content"></ul>
            </div>
            <a href="cart.jsp"><button>Checkout</button></a> <!--to another page for checkout-->
        </div>
    </div>
    <%@include file="footer.jsp" %>
</body>
</html>

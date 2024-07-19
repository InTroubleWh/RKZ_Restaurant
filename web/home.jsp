<%@include file="header.jsp" %>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>RKZ Restaurant - Home</title>
    <script type="text/javascript" src="js/homeslides.js"></script>
</head>
<body>
    <!-- Carousel -->
    <div class="slideshow-container">
        <div class="slide fade">
            <img src="<%=request.getContextPath()%>/image?id=1&source=banner" style="width:100%">
        </div>
        <div class="slide fade">
            <img src="<%=request.getContextPath()%>/image?id=2&source=banner" style="width:100%">
        </div>
        <div class="slide fade">
            <img src="<%=request.getContextPath()%>/image?id=3&source=banner" style="width:100%">
        </div>
        <!-- Tombol panah -->
        <a class="prev" onclick="plusSlides(-1)">&#10094;</a>
        <a class="next" onclick="plusSlides(1)">&#10095;</a>
    </div>

    <br>    

    <!-- Titik navigasi -->
    <div style="text-align:center">
        <span class="dot" onclick="currentSlide(1)"></span> 
        <span class="dot" onclick="currentSlide(2)"></span> 
        <span class="dot" onclick="currentSlide(3)"></span> 
    </div>
    <!-- Carousel End -->
    <div id="content1">
        <div id="content1-main">
            <h1 style="margin:20px 10%;font-family:'Roboto', sans-serif;">SPECIAL OFFERS</h1>
            <div class="slider-container">
                <button class="slider-prev slider-prev-special">&#10094;</button>
                <div class="menu-cards-slider" id="special-offer-slider">
                </div>
                <button class="slider-next slider-next-special">&#10095;</button>
            </div>
            <!--<h1 style="margin:20px 10%;font-family:'Roboto', sans-serif;">POPULAR PICKS</h1>
            <div class="slider-container">
                <button class="slider-prev slider-prev-top">&#10094;</button>
                <div class="menu-cards-slider top-picks-slider">
                    
                </div>
                <button class="slider-next slider-next-top">&#10095;</button>
            </div>-->
        </div>
        <div id="content1-side">
            <h1>RESERVATION</h1>
            <p style="text-align: center;font-size:12px;">Secure your spot for an unforgettable dining experience at our restaurant.</p>
            <hr>
            <br>
            <center>
                <P>We're open everyday!</p>
                <p>8:00am - 10:00pm</p>
            </center>
            <a href="reservation.jsp">p<button>Reserve a Seat</button></a>
        </div>
    </div>
    <%@include file="footer.jsp" %>
</body>
</html>

<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="css/style.css">
    <script type="text/javascript" src="js/jquery.js"></script>
    <script type="text/javascript" src="js/script.js"></script>
</head>
<body>
    <!--navigation bar-->
    <nav id="loginNavbar">
        <ul id="navlogin">
            <li>
                <a href="cart.jsp">Cart</a>
            </li>
            <li>
                <a href="aboutUs.html">About</a>
            </li>
            <li>
                <%
                    session = request.getSession(false);
                    String username = (session != null) ? (String) session.getAttribute("username") : null;
                    if (username != null) {
                %>
                    <span id="greeting">Hello, <%= username %></span>
                    <button id="logoutButton" onclick="window.location.href='<%=request.getContextPath()%>/Logout'">Log Out</button>
                <% } else { %>
                    <button id="loginButton" onclick="document.getElementById('login-modal').style.display='flex'">Log In</button>
                <% } %>
            </li>
        </ul>
    </nav>
    <nav id="mainNavbar">
        <ul>
            <li><a href="home.jsp"><img src="static/img/logo1.png"></a></li>
            <li><a href="home.jsp"><button>Home</button></a></li>
            <li><a href="menu.jsp"><button>Menu</button></a></li>
            <li><a href="reservation.jsp"><button>Reservation</button></a></li>
        </ul>
    </nav>
    <!-- navigation bar end -->
    <!--modal login-->
    <div id="login-modal" style="display:none;">
        <div id="login-modal-content">
            <h1 style="text-align:center;font-family:'Segoe UI',Tahoma,sans-serif;font-size:36px;">LOGIN</h1>
            <hr style="width: 90%">
            <p style="text-align:center;">RKZ Restaurant</p>
            <center>
                    <form action="<%=request.getContextPath()%>/Login" method="post">
                        <input type="text" id="identifier" name="identifier" placeholder="Username or Email" required>
                        <input type="password" id="password" name="password" placeholder="Password" required>
                        <button type="submit">LOGIN</button>
                        <p>Don't have an account?</p>
                        <p>
                            <a href="register.jsp">Register now!</a>
                        </p>
                    </form>
            </center>
        </div>
    </div>
    <!--modal login end-->
</body>

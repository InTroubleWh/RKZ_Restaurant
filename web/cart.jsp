<%@include file="header.jsp" %>
<%@page import="controller.outletDAO" %>
<%@page import="database.MyConnection" %>
<%@page import="java.util.List" %>
<%@page import="model.outletBean" %>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>RKZ Restaurant - Cart</title>
    <link rel="stylesheet" href="css/cart_page.css">
    <script type="text/javascript" src="js/cart.js"></script>
    <style>
        .choose { display: flex; align-items: center; justify-content: center; position: fixed; z-index: 1; left: 0; top: 0; width: 100%; height: 100%; overflow: auto; background-color: rgba(0,0,0,0.4); }
        .choose-content { background-color: #fefefe; padding: 20px; border: 1px solid #888; width: 80%; max-width: 500px; text-align: center; }
        .close { color: #aaa; float: right; font-size: 28px; font-weight: bold; cursor: pointer; }
        .close:hover, .close:focus { color: black; text-decoration: none; cursor: pointer; }
        .container { margin: 20px auto; }
        .payment-form { margin: 20px; }
    </style>
</head>

<body>
    <div class="container">
        <h1 style="font-weight:800;">CART</h1>
        <hr>
        <div class="cart">
            <div id="cart-items"></div>
            <div class="total">Total: Rp. <span id="total-amount">0</span></div>
        </div>
        <form style="margin: 20px" id="address-form">
            <label for="outlet">Select Outlet:</label>
            <select name="outlet" class="outlet" required>
                <option>Select an Outlet</option>
                <%
                    outletDAO dao = new outletDAO(MyConnection.getConnection());
                    List<outletBean> outlets = dao.getOutlets();
                    for (outletBean outlet : outlets) {
                %>
                <option value="<%=outlet.getOutletId()%>"><%=outlet.getOutletName()%> - <%=outlet.getAddress()%></option>
                <% }%>
            </select>
            <p><label for="city-select">City:</label></p>
            <select class="city-select" name="city-select" required>
                <option>Select a city</option>
                <option value="Jakarta">Jakarta</option>
                <option value="Bogor">Bogor</option>
                <option value="Depok">Depok</option>
            </select>
            <p><label for="post-code">Post Code:</label></p>
            <input type="text" class="post-code" name="post-code" placeholder="Post Code" required>
            <p><label for="street-address">Street Address:</label></p>
            <textarea class="street-address" name="street-address" rows="4" cols="50" required></textarea>
            <center>
                <button type="button" id="cart-buy">ORDER</button>
            </center>
        </form>
    </div>
    <div class="FormContainer" id="gopayForm" style="display:none;">
        <div class="payment-form">
            <h2>Gopay Payment</h2>
            <form id="gopay-form" action="<%=request.getContextPath()%>/transaction" method="POST">
                <input type="hidden" class="outlet" name="outlet">
                <input type="hidden" class="city-select" name="city-select">
                <input type="hidden" class="post-code" name="post-code">
                <input type="hidden" class="street-address" name="street-address">
                <input type="hidden" name="payment" value="goPay">
                <label for="gopayPhone">Phone Number:</label>
                <input type="number" id="gopayPhone" name="gopayPhone" required>
                <label for="gopayPin">PIN:</label>
                <input type="number" id="gopayPin" name="gopayPin" required>
                <button type="submit">Submit</button>
            </form>
        </div>
    </div>

    <div class="FormContainer" id="vaForm" style="display:none;">
        <div class="payment-form">
            <h2>BCA Virtual Account Payment</h2>
            <form id="va-form" action="<%=request.getContextPath()%>/transaction" method="POST">
                <input type="hidden" class="outlet" name="outletId">
                <input type="hidden" class="city-select" name="city-select">
                <input type="hidden" class="post-code" name="post-code">
                <input type="hidden" class="street-address" name="street-address">
                <input type="hidden" name="payment" value="BCAVirtualAccount">
                <label for="bank">Bank:</label>
                <input type="text" id="bank" name="bank" value="BCA" readonly>
                <label for="vaNumber">Virtual Account Number:</label>
                <input type="number" id="vaNumber" name="vaNumber" required>
                <button type="submit">Submit</button>
            </form>
        </div>
    </div>
    <div id="chooseModal" class="choose">
        <div class="choose-content">
            <span class="close">&times;</span>
            <h2>Choose your payment</h2>
            <form id="paymentForm" action="transactionServlet" method="POST">
                <input type="hidden" class="outlet" name="outletId">
                <input type="hidden" id="paymentMethod" name="paymentMethod">
                <input type="hidden" id="city" name="city">
                <input type="hidden" id="postCode" name="postCode">
                <input type="hidden" id="streetAddress" name="streetAddress">
                <button type="button" id="gopay">Gopay</button>
                <button type="button" id="va">Virtual Account</button>
            </form>
        </div>
    </div>
    <%@include file="footer.jsp" %>
</body>
</html>

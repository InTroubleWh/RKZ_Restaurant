<%@include file="header.jsp" %>
<%@page import="controller.outletDAO" %>
<%@page import="database.MyConnection" %>
<%@page import="java.util.List" %>
<%@page import="model.outletBean" %>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>RKZ Restaurant - Reservation</title>
    <link rel="stylesheet" href="css/reservation_page.css">
    <script type="text/javascript" src="js/reservation.js"></script>
</head>
<body>
    <main>
        <div class="container">
            <div class="reservation-form">
                <form id="reservation-form" action="reserve" method="post">
                    <h1 align="center">Reservation</h1>
                    <hr>
                    <label for="username">Customer Name: </label>
                    <input type="text" id="username" name="username" placeholder="Insert Your Name" required>

                    <label for="hp">Phone Number: </label>
                    <div class="phone-input-container">
                        <span class="country-code">+62</span>
                        <input type="tel" id="hp" name="No_HP" class="phone-number-input" placeholder="Insert Your Phone Number" style="margin-top: 0px;" required>
                    </div>

                    <label for="outlet">Select Outlet:</label>
                    <select name="outlet" id="outlet">
                        <option>Select an Outlet</option>
                        <%
                            outletDAO dao = new outletDAO(MyConnection.getConnection());
                            List<outletBean> outlets = dao.getOutlets();
                            for (outletBean outlet : outlets) {
                        %>
                        <option value="<%=outlet.getOutletId()%>"><%=outlet.getOutletName()%> - <%=outlet.getAddress()%></option>
                        <% } %>
                    </select>

                    <label for="reserve_date">Choose Reservation Date:</label>
                    <input type="date" id="reserve_date" name="reserve_date" required>

                    <label for="time">Choose Time Reservation:</label>
                    <input type="time" id="time" name="time" required>

                    <label for="duration">Duration (hours):</label>
                    <input type="number" id="duration" name="duration" min="1" value="1" required>

                    <button type="submit" id="confirmBtn" align="center">Confirmation</button>
                </form>
            </div>
        </div>
    </main>
    <%@include file="footer.jsp" %>
</body>
</html>

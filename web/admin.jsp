<%@include file="header.jsp" %>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>RKZ Restaurant - Admin Page</title>
    <link rel="stylesheet" href="css/admin_page.css">
    <script type="text/javascript" src="js/admin.js"></script>
</head>
<body>
    <h1>Admin Dashboard</h1>
    
    <div class="admin-section" id="menu-section">
        <h2>Menu</h2>
        <div class="table-container">
            <table id="menu-table">
                <thead>
                    <tr>
                        <th>Item ID</th>
                        <th>Image</th>
                        <th>Name</th>
                        <th>Price</th>
                        <th>Category</th>
                        <th>Action</th>
                    </tr>
                </thead>
                <tbody>
                    <!-- Rows will be loaded dynamically -->
                </tbody>
            </table>
        </div>
        <div id="menu-section-buttons" class="admin-section-buttons">
            <button id="add-menu-item">Add Item</button>
            <button id="add-special-offer">Add Special Offer</button>
        </div>
    </div>

    <div class="admin-section" id="reservation-section">
        <h2>Reservations</h2>
        <div class="table-container">
            <table id="reservation-table">
                <thead>
                    <tr>
                        <th>Reservation ID</th>
                        <th>Customer Name</th>
                        <th>Contact Number</th>
                        <th>Outlet</th>
                        <th>Reserved Date</th>
                        <th>Reserved Time</th>
                        <th>Duration</th>
                        <th>Date of Reservation</th>
                        <th>Status</th>
                        <th>Action</th>
                    </tr>
                </thead>
                <tbody>
                    <!-- Rows will be loaded dynamically -->
                </tbody>
            </table>
        </div>
        <div id="reservation-section-buttons" class="admin-section-buttons">
            <button id="add-reservation">Add Reservation</button>
        </div>
    </div>

    <div class="admin-section" id="outlet-section">
        <h2>Outlets</h2>
        <div class="table-container">
            <table id="outlet-table">
                <thead>
                    <tr>
                        <th>OutletID</th>
                        <th>Name</th>
                        <th>Location</th>
                        <th>Action</th>
                    </tr>
                </thead>
                <tbody>
                    <!-- Rows will be loaded dynamically -->
                </tbody>
            </table>
        </div>
        <div id="outlet-section-buttons" class="admin-section-buttons">
            <button id="add-outlet">Add Outlet</button>
        </div>
    </div>
    
    <!-- Dynamic Modal -->
    <div id="edit-modal" class="modal">
        <div class="modal-content">
        </div>
    </div>

    <%@include file="footer.jsp" %>
</body>
</html>

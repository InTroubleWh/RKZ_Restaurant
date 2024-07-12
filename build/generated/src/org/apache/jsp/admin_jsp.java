package org.apache.jsp;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;

public final class admin_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static final JspFactory _jspxFactory = JspFactory.getDefaultFactory();

  private static java.util.List<String> _jspx_dependants;

  static {
    _jspx_dependants = new java.util.ArrayList<String>(2);
    _jspx_dependants.add("/header.jsp");
    _jspx_dependants.add("/footer.jsp");
  }

  private org.glassfish.jsp.api.ResourceInjector _jspx_resourceInjector;

  public java.util.List<String> getDependants() {
    return _jspx_dependants;
  }

  public void _jspService(HttpServletRequest request, HttpServletResponse response)
        throws java.io.IOException, ServletException {

    PageContext pageContext = null;
    HttpSession session = null;
    ServletContext application = null;
    ServletConfig config = null;
    JspWriter out = null;
    Object page = this;
    JspWriter _jspx_out = null;
    PageContext _jspx_page_context = null;

    try {
      response.setContentType("text/html");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			null, true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;
      _jspx_resourceInjector = (org.glassfish.jsp.api.ResourceInjector) application.getAttribute("com.sun.appserv.jsp.resource.injector");

      out.write("\n");
      out.write("<!DOCTYPE html>\n");
      out.write("<html>\n");
      out.write("<head>\n");
      out.write("    <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">\n");
      out.write("    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n");
      out.write("    <link rel=\"stylesheet\" href=\"css/style.css\">\n");
      out.write("    <script type=\"text/javascript\" src=\"js/jquery.js\"></script>\n");
      out.write("    <script type=\"text/javascript\" src=\"js/script.js\"></script>\n");
      out.write("</head>\n");
      out.write("<body>\n");
      out.write("    <!--navigation bar-->\n");
      out.write("    <nav id=\"loginNavbar\">\n");
      out.write("        <ul id=\"navlogin\">\n");
      out.write("            <li>\n");
      out.write("                <a href=\"cart.jsp\">Cart</a>\n");
      out.write("            </li>\n");
      out.write("            <li>\n");
      out.write("                <a href=\"aboutUs.html\">About</a>\n");
      out.write("            </li>\n");
      out.write("            <li>\n");
      out.write("                ");

                    session = request.getSession(false);
                    String username = (session != null) ? (String) session.getAttribute("username") : null;
                    if (username != null) {
                
      out.write("\n");
      out.write("                    <span id=\"greeting\">Hello, ");
      out.print( username );
      out.write("</span>\n");
      out.write("                    <button id=\"logoutButton\" onclick=\"window.location.href='");
      out.print(request.getContextPath());
      out.write("/Logout'\">Log Out</button>\n");
      out.write("                ");
 } else { 
      out.write("\n");
      out.write("                    <button id=\"loginButton\" onclick=\"document.getElementById('login-modal').style.display='flex'\">Log In</button>\n");
      out.write("                ");
 } 
      out.write("\n");
      out.write("            </li>\n");
      out.write("        </ul>\n");
      out.write("    </nav>\n");
      out.write("    <nav id=\"mainNavbar\">\n");
      out.write("        <ul>\n");
      out.write("            <li><a href=\"home.jsp\"><img src=\"static/img/logo1.png\"></a></li>\n");
      out.write("            <li><a href=\"home.jsp\"><button>Home</button></a></li>\n");
      out.write("            <li><a href=\"menu.jsp\"><button>Menu</button></a></li>\n");
      out.write("            <li><a href=\"reservation.jsp\"><button>Reservation</button></a></li>\n");
      out.write("        </ul>\n");
      out.write("    </nav>\n");
      out.write("    <!-- navigation bar end -->\n");
      out.write("    <!--modal login-->\n");
      out.write("    <div id=\"login-modal\" style=\"display:none;\">\n");
      out.write("        <div id=\"login-modal-content\">\n");
      out.write("            <h1 style=\"text-align:center;font-family:'Segoe UI',Tahoma,sans-serif;font-size:36px;\">LOGIN</h1>\n");
      out.write("            <hr style=\"width: 90%\">\n");
      out.write("            <p style=\"text-align:center;\">RKZ Restaurant</p>\n");
      out.write("            <center>\n");
      out.write("                    <form action=\"");
      out.print(request.getContextPath());
      out.write("/Login\" method=\"post\">\n");
      out.write("                        <input type=\"text\" id=\"identifier\" name=\"identifier\" placeholder=\"Username or Email\" required>\n");
      out.write("                        <input type=\"password\" id=\"password\" name=\"password\" placeholder=\"Password\" required>\n");
      out.write("                        <button type=\"submit\">LOGIN</button>\n");
      out.write("                        <p>Don't have an account?</p>\n");
      out.write("                        <p>\n");
      out.write("                            <a href=\"register.jsp\">Register now!</a>\n");
      out.write("                        </p>\n");
      out.write("                    </form>\n");
      out.write("            </center>\n");
      out.write("        </div>\n");
      out.write("    </div>\n");
      out.write("    <!--modal login end-->\n");
      out.write("</body>\n");
      out.write("\n");
      out.write("<head>\n");
      out.write("    <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">\n");
      out.write("    <title>RKZ Restaurant - Admin Page</title>\n");
      out.write("    <link rel=\"stylesheet\" href=\"css/admin_page.css\">\n");
      out.write("</head>\n");
      out.write("<body>\n");
      out.write("    <h1>Admin Dashboard</h1>\n");
      out.write("    <div class=\"admin-section\" id=\"menu-section\">\n");
      out.write("        <h2>Menu</h2>\n");
      out.write("        <div class=\"table-container\">\n");
      out.write("            <table id=\"menu-table\">\n");
      out.write("                <thead>\n");
      out.write("                    <tr>\n");
      out.write("                        <th>Item ID</th>\n");
      out.write("                        <th>Image</th>\n");
      out.write("                        <th>Name</th>\n");
      out.write("                        <th>Price</th>\n");
      out.write("                        <th>Category</th>\n");
      out.write("                        <th>Action</th>\n");
      out.write("                    </tr>\n");
      out.write("                </thead>\n");
      out.write("                <tbody>\n");
      out.write("                    <!-- Rows will be loaded dynamically -->\n");
      out.write("                </tbody>\n");
      out.write("            </table>\n");
      out.write("        </div>\n");
      out.write("        <button id=\"add-menu-item\">Add Item</button>\n");
      out.write("        <button id=\"add-special-offer\">Add Special Offer</button>\n");
      out.write("    </div>\n");
      out.write("\n");
      out.write("    <div class=\"admin-section\" id=\"reservation-section\">\n");
      out.write("        <h2>Reservations</h2>\n");
      out.write("        <div class=\"table-container\">\n");
      out.write("            <table id=\"reservation-table\">\n");
      out.write("                <thead>\n");
      out.write("                    <tr>\n");
      out.write("                        <th>Reservation ID</th>\n");
      out.write("                        <th>Customer Name</th>\n");
      out.write("                        <th>Contact Number</th>\n");
      out.write("                        <th>Outlet</th> <!--outletName-->\n");
      out.write("                        <th>Reserved Date</th>\n");
      out.write("                        <th>Reserved Time</th>\n");
      out.write("                        <th>Duration</th>\n");
      out.write("                        <th>Date of Reservation</th>\n");
      out.write("                        <th>Status</th>\n");
      out.write("                        <th>Action</th>\n");
      out.write("                    </tr>\n");
      out.write("                </thead>\n");
      out.write("                <tbody>\n");
      out.write("                    <!-- Rows will be loaded dynamically -->\n");
      out.write("                </tbody>\n");
      out.write("            </table>\n");
      out.write("        </div>\n");
      out.write("        <button id=\"add-reservation\">Add Reservation</button>\n");
      out.write("    </div>\n");
      out.write("\n");
      out.write("    <div class=\"admin-section\" id=\"outlet-section\">\n");
      out.write("        <h2>Outlets</h2>\n");
      out.write("        <div class=\"table-container\">\n");
      out.write("            <table id=\"outlet-table\">\n");
      out.write("                <thead>\n");
      out.write("                    <tr>\n");
      out.write("                        <th>OutletID</th>\n");
      out.write("                        <th>Name</th>\n");
      out.write("                        <th>Location</th>\n");
      out.write("                        <th>Action</th>\n");
      out.write("                    </tr>\n");
      out.write("                </thead>\n");
      out.write("                <tbody>\n");
      out.write("                    <!-- Rows will be loaded dynamically -->\n");
      out.write("                </tbody>\n");
      out.write("            </table>\n");
      out.write("        </div>\n");
      out.write("        <button id=\"add-outlet\">Add Outlet</button>\n");
      out.write("    </div>\n");
      out.write("    ");
      out.write("<footer class=\"footer\">\n");
      out.write("    <div class=\"links\">\n");
      out.write("        <div class=\"social\">\n");
      out.write("        <h3>LINKS</h3>\n");
      out.write("        <ul>\n");
      out.write("            <li><a href=\"homePage.html\">Home</a></li>\n");
      out.write("            <li><a href=\"menuPage.html\">Menu</a></li>\n");
      out.write("            <li><a href=\"contactUsPage.html\">Contact Us</a></li>\n");
      out.write("            <li><a href=\"aboutUs.html\">About Us</a></li>\n");
      out.write("        </ul>\n");
      out.write("        </div>\n");
      out.write("    </div>\n");
      out.write("    <div class=\"social\">\n");
      out.write("        <h3>FOLLOW US</h3>\n");
      out.write("        <div class=\"social-icons\">\n");
      out.write("            <a href=\"https://www.twitter.com\" target=\"_blank\"><i class=\"fab fa-twitter\"></i></a>\n");
      out.write("            <a href=\"https://www.facebook.com\" target=\"_blank\"><i class=\"fab fa-facebook\"></i></a>\n");
      out.write("            <a href=\"https://www.instagram.com\" target=\"_blank\"><i class=\"fab fa-instagram\"></i></a>\n");
      out.write("        </div>\n");
      out.write("    </div>\n");
      out.write("    <div class=\"contact\">\n");
      out.write("        <h3>CONTACT</h3>\n");
      out.write("        <ul>\n");
      out.write("            <li>+62 821-5500-5925</li>\n");
      out.write("            <li><a href=\"contactUsPage.html\">info@RKZrestaurant.com</a></li>\n");
      out.write("        </ul>\n");
      out.write("    </div>\n");
      out.write("    <br><br>\n");
      out.write("    <div class=\"copyright\" align=\"center\">\n");
      out.write("        <h3>© 2023 by RKZ Restaurant. All rights reserved.</h3>\n");
      out.write("    </div>\n");
      out.write("</footer>\n");
      out.write("\n");
      out.write("\n");
      out.write("</body>\n");
      out.write("</html>\n");
    } catch (Throwable t) {
      if (!(t instanceof SkipPageException)){
        out = _jspx_out;
        if (out != null && out.getBufferSize() != 0)
          out.clearBuffer();
        if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
        else throw new ServletException(t);
      }
    } finally {
      _jspxFactory.releasePageContext(_jspx_page_context);
    }
  }
}

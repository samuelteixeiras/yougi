/* Yougi is a web application conceived to manage user groups or
 * communities focused on a certain domain of knowledge, whose members are
 * constantly sharing information and participating in social and educational
 * events. Copyright (C) 2011 Ceara Java User Group - CEJUG.
 *
 * This application is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by the
 * Free Software Foundation; either version 2.1 of the License, or (at your
 * option) any later version.
 *
 * This application is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY
 * or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public
 * License for more details.
 *
 * There is a full copy of the GNU Lesser General Public License along with
 * this library. Look for the file license.txt at the root level. If you do not
 * find it, write to the Free Software Foundation, Inc., 59 Temple Place,
 * Suite 330, Boston, MA 02111-1307 USA.
 * */
package org.cejug.yougi.web.controller;

import java.io.IOException;
import java.io.PrintWriter;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.cejug.yougi.business.UserAccountBsn;
import org.cejug.yougi.entity.UserAccount;

/**
 *
 * @author Hildeberto Mendonca  - http://www.hildeberto.com
 */
public class EmailConfirmationServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @EJB
    private UserAccountBsn userAccountBsn;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");

        String confirmationCode = request.getParameter("code");

        if (confirmationCode == null || confirmationCode.equals("")) {
            PrintWriter out = null;
            try {
                out = response.getWriter();
                out.println("<html>");
                out.println("<head>");
                out.println("<title>UG - Email Confirmation Failure</title>");
                out.println("<link href=\"/ug/resources/theme/css/default_theme.css\" rel=\"stylesheet\" type=\"text/css\"/>");
                out.println("</head>");
                out.println("<body>");
                out.println("<h1>Email Confirmation Failure</h1>");
                out.println("<p>This email confirmation is invalid. Check if the address on the browser coincides with the address sent by email.</p>");
                String scheme = request.getScheme();
                String serverName = request.getServerName();
                int serverPort = request.getServerPort();
                String contextPath = request.getContextPath();
                out.println("<p><a href=\"" + scheme + "://" + serverName + (serverPort != 80 ? ":" + serverPort : "") + (contextPath.equals("") ? "" : contextPath) + "\">Go to Homepage</a>.");
                out.println("</body>");
                out.println("</html>");
            }
            finally {
                if(out != null) {
                    out.close();
                }
            }
        }
        try {
            UserAccount userAccount = userAccountBsn.confirmUser(confirmationCode);
            if(userAccount != null) {
                response.sendRedirect("login.xhtml");
            }
            else {
                throw new Exception();
            }
        } catch (Exception e) {
            PrintWriter out = null;
            try {
                out = response.getWriter();
                out.println("<html>");
                out.println("<head>");
                out.println("<title>Yougi - Email Confirmation Failure</title>");
                out.println("<link href=\"/ug/resources/theme/css/default_theme.css\" rel=\"stylesheet\" type=\"text/css\"/>");
                out.println("</head>");
                out.println("<body>");
                out.println("<h1>Email Confirmation Failure</h1>");
                out.println("<p>This email confirmation '"+ confirmationCode +"' is invalid.</p>");
                out.println("<p class=\"alertMessage\">Cause: " + e.getCause().getMessage() + "</p>");
                out.println("<p>Check if the address on the browser coincides with the address sent my email.</p>");
                String scheme = request.getScheme();
                String serverName = request.getServerName();
                int serverPort = request.getServerPort();
                String contextPath = request.getContextPath();
                out.println("<p><a href=\"" + scheme + "://" + serverName + (serverPort != 80 ? ":" + serverPort : "") + (contextPath.equals("") ? "" : contextPath) + "\">Go to website</a>");
                out.println("</body>");
                out.println("</html>");
            }
            finally {
                if(out != null) {
                    out.close();
                }
            }
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }
}
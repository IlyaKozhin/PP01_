package servlet;

import model.User;
import service.UserService;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(value = "/users",name = "PP01Servlet")
public class PP01Servlet extends javax.servlet.http.HttpServlet {

    private UserService userService = UserService.getInstance();

    protected void doPost(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        String string = request.getParameter("update");
        if(string!=null) {

            if (string.startsWith("del")) {
                Long id = Long.parseLong(string.substring(3));
                try {
                    userService.deleteUser(id);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            } else if (string.startsWith("upd")) {
                Long id = Long.parseLong(string.substring(3));
                String name = request.getParameter("name"+id);
                String password = request.getParameter("password"+id);
                try {
                    userService.updateUser(new User(id,name,password));
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            } else if(string.equals("addUser")) {
                String name = request.getParameter("nameToAdd");
                String password = request.getParameter("passwordToAdd");
                try {
                    userService.addUser(new User(name,password));
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        response.setContentType("text/html");
        response.setStatus(HttpServletResponse.SC_OK);
        response.sendRedirect("users.jsp");
    }
}

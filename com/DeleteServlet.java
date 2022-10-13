package com;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@WebServlet("/delete")
public class DeleteServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        resp.setContentType("text/html");
        PrintWriter printWriter = resp.getWriter();
        String firstname=req.getParameter("firstname");
        String lastname=req.getParameter("lastname");
        String emailnew=req.getParameter("email");
        String city=req.getParameter("city");
        int age= Integer.parseInt(req.getParameter("age"));
        long number= Long.parseLong(req.getParameter("number"));

        HttpSession session = req.getSession(false);
        String email = (String) session.getAttribute("email");

        ServletContext servletContext = getServletContext();

        try {
            Class.forName(servletContext.getInitParameter("Driver"));

            Connection connection = DriverManager.getConnection(servletContext.getInitParameter("Url"),
                    servletContext.getInitParameter("Username"), servletContext.getInitParameter("Password"));

            printWriter.println("<center><h2>Welcome " + email + "as admin</h2></center>");

            PreparedStatement preparedStatement= connection.prepareStatement("delete from person where email='"+emailnew+"'");
//            preparedStatement.setString(1,firstname);
//            preparedStatement.setString(2,lastname);
//            preparedStatement.setLong(3,number);
//            preparedStatement.setInt(4,age);
//            preparedStatement.setString(5,emailnew);
//            preparedStatement.setString(6,city);

//            Statement statement = connection.createStatement();
//            ResultSet resultSet = statement.executeQuery("select * from person where email='"+emailnew+"'");

            preparedStatement.executeUpdate();
            printWriter.println("user deleted  successfully");
            printWriter.println("<a href='adminwelcome'>Click for admin Page</a>");

//            else {
//                printWriter.println("user already registered ");
//                printWriter.println("<a href='insert.html'>Click for another user</a>");
//            }


//
//            printWriter.println("<center><table border='3'>");
//            printWriter.println("<tr><th>Firstname</th><th>Lastname</th><th>Number</th><th>Age</th><th>Email</th><th>City</th></tr>");
//
//            while (resultSet.next()) {
//                printWriter.println("<tr><td>" + resultSet.getString(1) + "</td><td>" +
//                        resultSet.getString(2) + "</td><td>" + resultSet.getLong(3) +
//                        "</td><td>" + resultSet.getInt(4)+ "</td><td>"+ resultSet.getString(5)+
//                        "</td><td>"+resultSet.getString(6)+"</td></tr>");
//            }
//
//            printWriter.println("</table></center>");
//            printWriter.println("<a href='insert'>Click to insert</a>");
//            printWriter.println("<a href='update'>Click to update</a>");
//            printWriter.println("<a href='delete'>Click to delete</a>");
//            printWriter.println("<a href='login.html'>Click to logout</a>");
//

        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

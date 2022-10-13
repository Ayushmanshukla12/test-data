package com;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

@WebServlet("/welcome")
public class welcomeServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        PrintWriter printWriter = resp.getWriter();

//        String firstname=req.getParameter("fisrtname");
//        String lastname=req.getParameter("lastname");
        //String email=req.getParameter("email");
        //String city=req.getParameter("city");
        //int age= Integer.parseInt(req.getParameter("age"));
        //long number= Long.parseLong(req.getParameter("number"));
        HttpSession session=req.getSession(false);
        String email=(String)session.getAttribute("email");
        String firstname=(String)session.getAttribute("firstname");
        String lastname=(String)session.getAttribute("lastname");
        String city=(String)session.getAttribute("city");
        Long number= (Long) session.getAttribute("number");
        int age= (Integer)(session.getAttribute("age"));

        ServletContext servletContext=getServletContext();

        try {
            Class.forName(servletContext.getInitParameter("Driver"));

            Connection connection= DriverManager.getConnection(servletContext.getInitParameter("Url"),
                    servletContext.getInitParameter("Username"),servletContext.getInitParameter("Password"));

            printWriter.println("<center><h2>Welcome "+email+"</h2></center>");
            Statement statement= connection.createStatement();
            ResultSet resultSet= statement.executeQuery("select * from person where email='"+email+"'");
             printWriter.println("<center><table border='3'>");

            printWriter.println("<tr><th>Firstname</th><th>Lastname</th><th>Number</th><th>Age</th><th>Email</th><th>City</th></tr>");

            while(resultSet.next()){
                printWriter.println("<tr><td>"+firstname+"</td><td>"+
                        lastname+"</td><td>"+number+
                        "</td><td>"+age+ "</td><td>"+ email +"</td><td>"+city+"</td></tr>");
            }

            printWriter.println("</table></center>");
            printWriter.println("<a href='login.html'>Click to logout</a>");


        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

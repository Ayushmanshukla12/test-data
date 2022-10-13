package com;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        resp.setContentType("text/html");
        PrintWriter printWriter = resp.getWriter();

        String firstname=req.getParameter("fisrtname");
        String lastname=req.getParameter("lastname");
        String username=req.getParameter("email");
        //String city=req.getParameter("city");
        //int age= Integer.parseInt(req.getParameter("age"));
        long password= Long.parseLong(req.getParameter("number"));

        ServletContext servletContext=getServletContext();

        try {
            Class.forName(servletContext.getInitParameter("Driver"));

            Connection connection= DriverManager.getConnection(servletContext.getInitParameter("Url"),
                    servletContext.getInitParameter("Username"),servletContext.getInitParameter("Password"));

//            PreparedStatement preparedStatement= connection.prepareStatement("");

            Statement statement= connection.createStatement();

            ResultSet resultSet= statement.executeQuery("select email,number from person where email='"+username+"' and number='"+password+"'");

            if(resultSet.next()){
                System.out.printf("out");
                    printWriter.println("login succesful");
                    printWriter.println("<a href='welcome'>Click if user</a>");
                printWriter.println("<a href='adminwelcome'>Click if admin</a>");
                 }
            else {
                printWriter.println("incorrect username and password");
                RequestDispatcher requestDispatcher= req.getRequestDispatcher("login.html");
                requestDispatcher.include(req,resp);
            }


        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}

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

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        resp.setContentType("text/html");
        PrintWriter printWriter = resp.getWriter();

        String firstname=req.getParameter("firstname");
        String lastname=req.getParameter("lastname");
        String email=req.getParameter("email");
        String city=req.getParameter("city");
        int age= Integer.parseInt(req.getParameter("age"));
        long number= Long.parseLong(req.getParameter("number"));

        ServletContext servletContext=getServletContext();

        try {
            Class.forName(servletContext.getInitParameter("Driver"));

            Connection connection= DriverManager.getConnection(servletContext.getInitParameter("Url"),
                    servletContext.getInitParameter("Username"),servletContext.getInitParameter("Password"));

            PreparedStatement preparedStatement= connection.prepareStatement("insert into person values(?,?,?,?,?,?)");
            preparedStatement.setString(1,firstname);
            preparedStatement.setString(2,lastname);
            preparedStatement.setLong(3,number);
            preparedStatement.setInt(4,age);
            preparedStatement.setString(5,email);
            preparedStatement.setString(6,city);

            HttpSession httpSession=req.getSession();
            httpSession.setAttribute("firstname",firstname);
            httpSession.setAttribute("lastname",lastname);
            httpSession.setAttribute("email",email);
            httpSession.setAttribute("age",age);
            httpSession.setAttribute("number",number);
            httpSession.setAttribute("city",city);

            Statement statement= connection.createStatement();
            ResultSet resultSet= statement.executeQuery("select * from person where firstname='"+firstname+"'");

            if(!resultSet.next()){
                preparedStatement.executeUpdate();
                printWriter.println(firstname+" registered successfully");
                printWriter.println("<a href='login.html?email='"+email+"' & number='"+number+"'>Click to login</a>");
            }

            else{
                printWriter.println("user already exists");
                RequestDispatcher requestDispatcher= req.getRequestDispatcher("register.html");
                requestDispatcher.include(req,resp);
            }
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }
}

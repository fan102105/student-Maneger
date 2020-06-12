package com.itheima.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.itheima.domain.User;
import com.itheima.service.IUserService;
import com.itheima.service.impl.UserServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

//http://localhost:8080/userServlet
@WebServlet("/userServlet")
public class UserServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);

        IUserService userService = new UserServiceImpl();
        User login = userService.login(user);

        response.setContentType("text/html;charset=utf-8");
        PrintWriter out = response.getWriter();
        if (login != null) {
            HttpSession session = request.getSession();
            session.setAttribute("username", username);
            //登录成功,响应数据
            //ObjectMapper mapper = new ObjectMapper();
            //String s = mapper.writeValueAsString(login);
            out.print("true");
        } else {
            out.write("false");
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}

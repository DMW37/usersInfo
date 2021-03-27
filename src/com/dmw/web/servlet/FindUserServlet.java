package com.dmw.web.servlet;

import com.dmw.entity.User;
import com.dmw.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/findUserServlet")
public class FindUserServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1.获取id
        String id = request.getParameter("id");
        //1.1获取页码
        String currentPage = request.getParameter("currentPage");
        request.setAttribute("currentPage",currentPage);
        //2.调用service查询
        UserService service = new UserService();
        User user = service.findUserById(id);
        //3.存入request
        request.setAttribute("user", user);
        //4.转发到update.jsp
        request.getRequestDispatcher("/update.jsp").forward(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}

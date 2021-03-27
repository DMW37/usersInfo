package com.dmw.web.servlet;

import com.dmw.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/deleteUserServlet")
public class DeleteUserServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1.获取id
        String id = request.getParameter("id");
        //调用service删除方法
        UserService service = new UserService();
        service.deleteUser(id);
        //3.跳转页面/findByPageServlet
        response.sendRedirect(request.getContextPath()+"/findByPageServlet?currentPage="+request.getParameter("currentPage"));
        //转发自带虚拟路径么？
       // request.getRequestDispatcher("/findByPageServlet").forward(request,response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}

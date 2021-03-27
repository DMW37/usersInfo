package com.dmw.web.servlet;

import com.dmw.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/deleteUsersServlet")
public class DeleteUsersServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1.获取表单提交复选框提交的值
        String[] uids = request.getParameterValues("uid");
        //2.在service层中创建一个可以根据数组删除的方法
        UserService service = new UserService();
        if (uids.length!=0&&uids!=null){service.deleteUsers(uids);}

        //3.重定向/findByPageServlet
        response.sendRedirect(request.getContextPath()+"/findByPageServlet?currentPage="+request.getParameter("currentPage"));
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}

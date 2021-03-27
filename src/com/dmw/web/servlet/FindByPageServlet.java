package com.dmw.web.servlet;

import com.dmw.entity.PageBean;
import com.dmw.entity.User;
import com.dmw.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

@WebServlet("/findByPageServlet")
public class FindByPageServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        //1.获取当前页码
        Integer currentPage = 1;
        String reCurrentPage = request.getParameter("currentPage");

        if (reCurrentPage != null) {
            currentPage = Integer.parseInt(reCurrentPage);
        }
        //2.设置每页显示条数
        Integer pageSize = 4;
        //3.获取查询表单的参数
        Map<String, String[]> condition = request.getParameterMap();
        Set<String> keySet = condition.keySet();
        for (String key : keySet) {
            System.out.print("键"+key+" ");
            System.out.print("值"+condition.get(key));
            System.out.println();
        }

        //调用UserService完成分页查询
        UserService service = new UserService();
        PageBean<User> pageBean;
        pageBean = service.findByPage(currentPage, pageSize,condition);
        //将pageBean对象存入域对象
        //当我们使用全选删除最后一页时，就查询前面一页
        if (pageBean.getList() == null || pageBean.getList().size() == 0) {
            currentPage--;
            pageBean = service.findByPage(currentPage, pageSize, condition);
        }
        request.setAttribute("users", pageBean);
        request.setAttribute("condition",condition);
        request.getRequestDispatcher("/page.jsp").forward(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}

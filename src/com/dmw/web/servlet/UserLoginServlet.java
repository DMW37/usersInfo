package com.dmw.web.servlet;

import com.dmw.entity.User;
import com.dmw.entity.UserLogin;
import com.dmw.service.UserService;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

@WebServlet("/userLoginServlet")
public class UserLoginServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1.设置编码格式
        request.setCharacterEncoding("UTF-8");
        //2.获取表单信息
        Map<String, String[]> map = request.getParameterMap();
        UserLogin userLogin = new UserLogin();
        try {
            BeanUtils.populate(userLogin, map);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        //3.判断验证码
        //3.1获取图片的验证码
        HttpSession session = request.getSession();
        String checkcode_server = (String) session.getAttribute("CHECKCODE_SERVER");
        //3.2获取用户输入的密码
        String verifycode = request.getParameter("verifycode");
        //3.3保证生成验证码的一次性，获得图片中的验证码后移除
        session.removeAttribute("CHECKCODE_SERVER");
        if (checkcode_server!=null){
            if (checkcode_server.equalsIgnoreCase(verifycode)) {
                //输入验证码正确
                //4.访问数据库
                UserService service = new UserService();
                UserLogin userL = service.userLogin(userLogin.getAccount(), userLogin.getPassword());
                //5.判断账号/密码
                if (userL != null){
                    //登入成功
                    //6.重定向index.jsp
                    request.setAttribute("loginSuccess",userLogin.getAccount()+"欢迎您!");
                    request.getRequestDispatcher("index.jsp").forward(request, response);
                }else {
                    //登入失败
                    //提示错误信息
                    request.setAttribute("errorMSG","账号或者密码有误！");
                    //重新回到登入页面
                    request.getRequestDispatcher("/login.jsp").forward(request,response);
                }
            } else {
                //输入验证码有误
                //提示错误信息
                request.setAttribute("errorMSG","验证码有误！");
                //重新回到登入页面
                request.getRequestDispatcher("/login.jsp").forward(request,response);
            }
        }



    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}

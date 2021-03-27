package com.dmw.service;


import com.dmw.dao.UserDao;
import com.dmw.entity.PageBean;
import com.dmw.entity.User;
import com.dmw.entity.UserLogin;

import java.util.List;
import java.util.Map;

public class UserService {
    //创建UserDao对象
    UserDao userDao = new UserDao();

    public List<User> findAll() {
        return userDao.findAll();
    }

    public void addUser(User user) {
        userDao.addUser(user);
    }

    /**
     * 根据id删除
     *
     * @param id
     */
    public void deleteUser(String id) {
        userDao.deleteUser(Integer.parseInt(id));
    }

    /**
     * 根据id修理
     *
     * @param id
     * @return
     */
    public User findUserById(String id) {

        return userDao.findUserById(Integer.parseInt(id));
    }

    /**
     * 修改用户信息
     *
     * @param user
     */
    public void updateUser(User user) {
        userDao.updateUser(user);
    }

    public UserLogin userLogin(String account, String password) {
        return userDao.userLogin(account, password);
    }

    public void deleteUsers(String[] uids) {
        //遍历数组
        if (uids.length != 0 && uids != null) {
            for (String uid : uids) {
                //调用dao层中的单个删除方法
                userDao.deleteUser(Integer.parseInt(uid));
            }
        }

    }

    public PageBean<User> findByPage(Integer currentPage, Integer pageSize, Map<String, String[]> condition) {
        PageBean<User> pageBean = new PageBean<>();
        //1.设置当前页码参数
        pageBean.setCurrentPage(currentPage);

        //2.设置每页显示多少
        pageBean.setPageSize(pageSize);

        //3.设置总记录数
        Integer totalCount = userDao.findCount(condition);
        pageBean.setTotalCount(totalCount);

        //4.设置总页码
        Integer totalPage;
      if (totalCount % pageSize==0){
           totalPage =totalCount / pageSize;
       }else {
           totalPage = (totalCount / pageSize)+1;
       }
        pageBean.setTotalPage(totalPage);

        //5.设置每页显示的数据
        Integer start = (currentPage - 1) * pageSize;
        List<User> users = userDao.findPageList(start, pageSize,condition);
        pageBean.setList(users);
        return pageBean;
    }
}

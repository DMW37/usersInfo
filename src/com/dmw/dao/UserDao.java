package com.dmw.dao;

import com.dmw.entity.User;
import com.dmw.entity.UserLogin;
import com.dmw.utils.JDBCUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class UserDao {
    //创建模板
    JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());

    /**
     * 查询所有
     *
     * @return
     */
    public List<User> findAll() {
        //1，编写sql语句
        String sql = "select * from user";
        //2,查询结果
        List<User> users = template.query(sql, new BeanPropertyRowMapper<User>(User.class));
        return users;
    }

    /**
     * 添加一个用户
     *
     * @param user
     */
    public void addUser(User user) {
        String sql = "insert into user values(?,?,?,?,?,?,?)";
        template.update(sql, null, user.getName(),
                user.getGender(),
                user.getAge(),
                user.getAddress(), user.getQq(), user.getEmail());
    }

    /**
     * 删除一个User
     *
     * @param userId
     */
    public void deleteUser(int userId) {
        String sql = "delete from user where id =?";
        template.update(sql, userId);
    }

    public User findUserById(int id) {
        try {
            String sql = "select * from user where id =?";
            return template.queryForObject(sql, new BeanPropertyRowMapper<User>(User.class), id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void updateUser(User user) {
        String sql = "update user set name=?,gender = ?,age=?,address=?,qq=?,email=? where id=?";
        template.update(sql, user.getName(), user.getGender(), user.getAge(), user.getAddress(), user.getQq(), user.getEmail(), user.getId());
    }

    public UserLogin userLogin(String account, String password) {
        try {
            //1.准备sql语句
            String sql = "select * from userlogin where account = ? and password = ?";
            //2.访问数据库
            UserLogin userLogin = template.queryForObject(sql, new BeanPropertyRowMapper<UserLogin>(UserLogin.class), account, password);
            return userLogin;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 统计数据中数据的总数量
     *
     * @param condition
     * @return
     */
    public Integer findCount(Map<String, String[]> condition) {
        //1.定义一个模板sql
        String sql = "select count(*) from user where 1 =1 ";
        StringBuffer sb = new StringBuffer(sql);
        //2.遍历map,酌情拼接
        //2.定义一个存放value的集合
        List<Object> params = new ArrayList<Object>();
        Set<String> keySet = condition.keySet();
        for (String key : keySet) {
            if ("currentPage".equals(key) || "pageSize".equals(key)) {
                continue;
            }
            //获取value值
            String value = condition.get(key)[0];
            //判断value不为null或者""
            if (value != null && !"".equals(value)&&("name".equals(key)||"address".equals(key)||"email".equals(key))) {
                //有值
                sb.append(" and " + key + " like ? ");
                //添加填充参数的值
                params.add("%"+value+"%");
            }
        }
        System.out.println("数据库中的查询语句：" + sb.toString());

        return template.queryForObject(sb.toString(), Integer.class, params.toArray());

    }

    /**
     * 分页查询所有用户信息
     *
     * @param start
     * @param pageSize
     * @param condition
     * @return
     */
    public List<User> findPageList(Integer start, Integer pageSize, Map<String, String[]> condition) {
       try {
           //1.模板化sql语句
           String sql = "select * from user where 1=1 ";
           StringBuffer sb = new StringBuffer(sql);
           //2.遍历map集合
           Set<String> keySet = condition.keySet();
           //2.1定义一个集合存储map集合中的value值
           List<Object> params = new ArrayList<Object>();
           for (String key : keySet) {
               String value = condition.get(key)[0];
               if ("currentPage".equals(key)||"pageSize".equals(key)){
                   continue;
               }
               //如果携带了name address email,并且不是空 就进行拼接
               if (value!=null&&!"".equals(value)&&("name".equals(key)||"address".equals(key)||"email".equals(key))){
                   sb.append(" and "+key+" like ? ");
                   params.add("%"+value+"%");
               }
           }
           //3.当map集合遍历结束后，拼接limit
           sb.append(" limit ?,? ");
           params.add(start);
           params.add(pageSize);
           //4.将StringBuffer 转为字符串
           sql = sb.toString();
           System.out.println("分页查询的sql:"+sql);
           List<User> list = template.query(sql, new BeanPropertyRowMapper<User>(User.class), params.toArray());
           return list;
       }catch (Exception e) {
           e.printStackTrace();
       }
       return null;
    }
}

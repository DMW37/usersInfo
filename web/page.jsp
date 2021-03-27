<%--
  Created by IntelliJ IDEA.
  User: 35612
  Date: 2021/3/24
  Time: 14:42
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<!-- 网页使用的语言 -->
<html lang="zh-CN">
<head>
    <!-- 指定字符集 -->
    <meta charset="utf-8">
    <!-- 使用Edge最新的浏览器的渲染方式 -->
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <!-- viewport视口：网页可以根据设置的宽度自动进行适配，在浏览器的内部虚拟一个容器，容器的宽度与设备的宽度相同。
    width: 默认宽度与设备的宽度相同
    initial-scale: 初始的缩放比，为1:1 -->
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
    <title>用户信息管理系统</title>

    <!-- 1. 导入CSS的全局样式 -->
    <link href="css/bootstrap.min.css" rel="stylesheet">
    <!-- 2. jQuery导入，建议使用1.9以上的版本 -->
    <script src="js/jquery-2.1.0.min.js"></script>
    <!-- 3. 导入bootstrap的js文件 -->
    <script src="js/bootstrap.min.js"></script>
    <style type="text/css">
        td, th {
            text-align: center;
        }
    </style>
    <script>
        function deleteUser(id) {
            //提示框
            if (confirm("Are you sure you want to delete it?")) {
                //删除一个时，传递user 的id 和当前页码
                //如果最后删除的是最后一页，导致最后一页已无数据，就显示最后一页
                //如果当前所在页数等于总页数，同时最后一页数量为1
                <%--alert(${(requestScope.users.totalCount % requestScope.users.pageSize)});--%>
                if (${(requestScope.users.totalCount mod requestScope.users.pageSize)>1 or (requestScope.users.totalCount % requestScope.users.pageSize)==0}) {
                    // alert("当前页大于1");
                    location.href = "${pageContext.request.contextPath}/deleteUserServlet?id=" + id + "&currentPage=${requestScope.users.currentPage}";
                }
                if (${(requestScope.users.totalCount mod requestScope.users.pageSize)==1 and requestScope.users.currentPage==requestScope.users.totalPage}) {
                    //  alert("这是最后一页，条数仅为1");
                    location.href = "${pageContext.request.contextPath}/deleteUserServlet?id=" + id + "&currentPage=${requestScope.users.currentPage-1}";

                }

            }
        }

        window.onload = function () {
            //给删除选中添加提交事件
            let delu = document.getElementById("deleteServlet");
            delu.onclick = function () {
                if (confirm("Are you sure you want to delete the selected ones?")) {
                    //定义一个标记判断是否有选中的数据
                    let flag = false;
                    //定义一个计数器，统计每一页选中的总数
                    let count = 0;
                    let cbs = document.getElementsByName("uid");
                    for (let i = 0; i < cbs.length; i++) {
                        if (cbs[i].checked) {
                            flag = true;
                            count++;
                        }
                    }
                    <%-- alert("选中数是否相等" + (count ==${requestScope.users.totalCount%requestScope.users.pageSize}));
                       alert("是否最后一页数：" +${(requestScope.users.currentPage==requestScope.users.totalPage)});
                    --%>
                    if (flag) {
                        //给其它的按键添加提交表单的事件
                        document.getElementById("form").submit();
                    } else {
                        alert("您尚未选中删除对象！！！")
                    }

                }
            }
            //完成全选和全部选功能
            document.getElementById("firstCB").onclick = function () {
                let cbs = document.getElementsByName("uid");
                for (let i = 0; i < cbs.length; i++) {
                    cbs[i].checked = this.checked;
                }
            }

        }
    </script>
</head>
<body>
<div class="container">
    <h3 style="text-align: center">用户信息列表</h3>
    <div style="float: left;margin: 10px">
        <form class="form-inline" action="${pageContext.request.contextPath}/findByPageServlet">
            <div class="form-group">
                <label for="exampleInputName">姓名</label>
                <input type="text" class="form-control" name="name" id="exampleInputName"
                       value="${requestScope.condition.name[0]}">
            </div>
            <div class="form-group">
                <label for="exampleInputAddress">籍贯</label>
                <input type="text" class="form-control" name="address" id="exampleInputAddress"
                       value="${requestScope.condition.address[0]}">
            </div>
            <div class="form-group">
                <label for="exampleInputEmail">邮箱</label>
                <input type="email" class="form-control" name="email" id="exampleInputEmail"
                       value="${requestScope.condition.email[0]}">
            </div>
            <button type="submit" class="btn btn-default">查询</button>
        </form>
    </div>
    <div style="float: right;margin: 10px">
        <a class="btn btn-primary"
           href="${pageContext.request.contextPath}/add.jsp?currentPage=${requestScope.users.currentPage}">添加联系人</a>
        <a class="btn btn-primary" href="javascript:void(0);" id="deleteServlet">删除选中</a>

    </div>
    <form id="form"
          action="${pageContext.request.contextPath}/deleteUsersServlet?currentPage=${requestScope.users.currentPage}"
          method="post">
        <table border="1" class="table table-bordered table-hover">
            <tr class="success">
                <th><input type="checkbox" id="firstCB"></th>
                <th>编号</th>
                <th>姓名</th>
                <th>性别</th>
                <th>年龄</th>
                <th>籍贯</th>
                <th>QQ</th>
                <th>邮箱</th>
                <th>操作</th>
            </tr>

            <c:forEach items="${requestScope.users.list}" var="user" varStatus="i">
                <tr>
                    <td><input type="checkbox" name="uid" value="${user.id}"></td>
                    <td>${i.count}</td>
                    <td>${user.name}</td>
                    <td>${user.gender}</td>
                    <td>${user.age}</td>
                    <td>${user.address}</td>
                    <td>${user.qq}</td>
                    <td>${user.email}</td>
                    <td>
                        <a class="btn btn-default btn-sm"
                           href="${pageContext.request.contextPath}/findUserServlet?id=${user.id}&currentPage=${requestScope.users.currentPage}">修改</a>&nbsp;
                        <a class="btn btn-default btn-sm" href="javascript:deleteUser(${user.id})">删除</a></td>
                </tr>
            </c:forEach>
            </tr>
        </table>
    </form>
    <div>
        <nav aria-label="Page navigation">
            <ul class="pagination">
                <c:if test="${requestScope.users.currentPage==1 or requestScope.users.totalPage==0}">
                    <li class="disabled">
                        <a href="#" aria-label="Previous" title="不能再点了">
                            <span aria-hidden="true" style="color: red">!</span>
                        </a>
                    </li>
                </c:if>
                <c:if test="${requestScope.users.currentPage>1}">
                    <li>
                        <a href="${pageContext.request.contextPath}/findByPageServlet?currentPage=${requestScope.users.currentPage-1}&name=${requestScope.condition.name[0]}&address=${requestScope.condition.address[0]}&email=${requestScope.condition.email[0]}"
                           aria-label="Previous">
                            <span aria-hidden="true">&laquo;</span>
                        </a>
                    </li>
                </c:if>

                <c:forEach begin="1" end="${requestScope.users.totalPage}" var="i">
                    <c:if test="${requestScope.users.currentPage==i}">
                        <li class="active">
                            <a href="${pageContext.request.contextPath}/findByPageServlet?currentPage=${i}&name=${requestScope.condition.name[0]}&address=${requestScope.condition.address[0]}&email=${requestScope.condition.email[0]}">${i}</a>
                        </li>
                    </c:if>
                    <c:if test="${requestScope.users.currentPage!=i}">
                        <li>
                            <a href="${pageContext.request.contextPath}/findByPageServlet?currentPage=${i}&name=${requestScope.condition.name[0]}&address=${requestScope.condition.address[0]}&email=${requestScope.condition.email[0]}">${i}</a>
                        </li>
                    </c:if>
                </c:forEach>


                <c:if test="${requestScope.users.currentPage==requestScope.users.totalPage}">
                    <li class="disabled">
                        <a href="#" aria-label="Previous" title="不能再点了">
                            <span aria-hidden="true" style="color: red">!</span>
                        </a>
                    </li>
                </c:if>

                <c:if test="${requestScope.users.currentPage<requestScope.users.totalPage}">
                    <li>
                        <a href="${pageContext.request.contextPath}/findByPageServlet?currentPage=${requestScope.users.currentPage+1}&name=${requestScope.condition.name[0]}&address=${requestScope.condition.address[0]}&email=${requestScope.condition.email[0]}"
                           aria-label="Previous">
                            <span aria-hidden="true">&raquo;</span>
                        </a>
                    </li>
                </c:if>


                <span style="font-size: 25px;margin: 5px">总共${requestScope.users.totalPage}页，共${requestScope.users.totalCount}条</span>
            </ul>
        </nav>
    </div>
</div>
</body>
</html>


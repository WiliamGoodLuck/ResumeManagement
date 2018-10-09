<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
    <title>人才库信息管理系统</title>
    <c:set var="ctx" value="${pageContext.request.contextPath}"/>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <meta name="keywords" content=""/>
    <script type="application/x-javascript">
        addEventListener("load", function () {
        setTimeout(hideURLbar, 0);
    }, false);

    function hideURLbar() {
        window.scrollTo(0, 1);
    }

    </script>
    <!-- Bootstrap Core CSS -->
    <link href="${ctx}/staticfile/css/bootstrap.css" rel='stylesheet' type='text/css'/>
    <!-- Custom CSS -->
    <link href="${ctx}/staticfile/css/style.css" rel='stylesheet' type='text/css'/>
    <!-- font CSS -->
    <!-- font-awesome icons -->
    <link href="${ctx}/staticfile/css/font-awesome.css" rel="stylesheet">
    <!-- //font-awesome icons -->
    <!-- js-->
    <script src="${ctx}/staticfile/js/jquery-1.11.1.min.js"></script>
    <script src="${ctx}/staticfile/js/modernizr.custom.js"></script>
    <!--webfonts-->
    <link href='https://fonts.googleapis.com/css?family=Roboto+Condensed:400,300,300italic,400italic,700,700italic'
          rel='stylesheet' type='text/css'>
    <!--//webfonts-->
    <!--animate-->
    <link href="${ctx}/staticfile/css/animate.css" rel="stylesheet" type="text/css" media="all">
    <script src="${ctx}/staticfile/js/wow.min.js"></script>
    <script>
        new WOW().init();
    </script>
    <!--//end-animate-->
    <!-- Metis Menu -->
    <script src="${ctx}/staticfile/js/metisMenu.min.js"></script>
    <script src="${ctx}/staticfile/js/custom.js"></script>
    <link href="${ctx}/staticfile/css/custom.css" rel="stylesheet">
    <!--//Metis Menu -->
</head>
<body class="cbp-spmenu-push">
<%
    HttpSession ses = request.getSession();
    String message = (String) ses.getAttribute("mes");
    if (message == "") {
%>
<%
} else {
%>
<script type="text/javascript">
    alert("<%=message %>");
</script>
<%
        ses.setAttribute("mes", "");
    }
%>
<div class="main-content">
    <!--left-fixed -navigation-->
    <%@include file="_leftNavigation.jsp" %>
    <!--left-fixed -navigation-->
    <!-- header-starts -->
    <%@include file="_header.jsp" %>
    <!-- //header-ends -->
    <!-- main content start-->
    <div id="page-wrapper">
        <div class="main-page">

            <%--	<h3 class="title1">Tables</h3>--%>
            <div class="panel-body widget-shadow">
                <form action="${pageContext.request.contextPath }/personnel/manager/page?userName=${pageInfo.userName}" method="get" id="form1">

                    <span>姓名:</span><input type="text" name="userName" maxlength="20" value="${pageInfo.userName}"/>
                   <%-- <span>     在职状态:</span><select name="status">
                    <option value="1" selected> 在职</option>
                    <option value="2"> 离职</option>
                </select>--%>
                    <button type="submit" class="btn btn-default">搜索</button>

                </form>
            </div>


            <div class="panel-body widget-shadow">


                <%--<h4>查询简历</h4>--%>
                <table class="table">
                    <thead>
                    <tr>

                        <td width="65px">姓名</td>
                        <td width="45px">性別</td>
                        <td width="45px">年龄</td>
                        <td width="50px">学历</td>
                        <td width="45px">年限</td>
                        <td width="70px">分类</td>
                        <td width="100px">联系电话</td>
                        <td width="130px">邮箱</td>
                       <%-- <td width="70px">状态</td>--%>
                        <td width="130px">操作</td>

                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="user" items="${pageInfo.list}" varStatus="status">
                        <tr>

                            <td>${user.userName}</td>
                                <%-- <td>${user.gender}</td>--%>
                            <td>
                                <c:if test="${user.gender==1}"> 男</c:if>
                                <c:if test="${user.gender==2}"> 女</c:if>
                            </td>
                            <td>${user.age}</td>
                            <td>${user.education}</td>
                            <td>${user.serviceYear}</td>
                            <td>${user.workType}</td>
                            <td>${user.phoneNumber}</td>

                            <td>${user.email}</td>
                            <%--<td>
                                <c:if test="${user.status==1}"> 在职</c:if>
                                <c:if test="${user.status==2}"> 离职</c:if>
                            </td>--%>
                            <td>

                                <a href="${pageContext.request.contextPath}/home/manager/toUpdate.do?id=${user.id}">修改</a>|
                                <a href="${pageContext.request.contextPath}/personnel/manager/downloadFile.do?id=${user.id}">下载</a>|
                                <a href="${pageContext.request.contextPath}/personnel/manager/deleteFile.do?id=${user.id}">删除</a>
                            </td>
                                <%-- <td>${user.age}</td>--%>
                        </tr>
                    </c:forEach>
                    <%--   <tr class="prevnext">
                           <td>
                               <input id="prev" type="button" value="上一页">
                           </td>
                           <td   colspan="4">当前<label id="currentPage"></label>页/共<label id="sumPages"></label>页</td>
                           <td>
                               <input id="next" type="button" value="下一页">
                           </td>
                       </tr>--%>

                    </tbody>

                </table>
                    <div  id="pagin-btm" >
                        <p id="common_page">
                            共${pageInfo.countRow }条记录,共${pageInfo.countPage }页
                            <a href="${pageContext.request.contextPath }/personnel/manager/page?userName=${pageInfo.userName}&thisPage=${pageInfo.firstPage}&pageSize=${pageInfo.pageSize}">首页</a>
                            <a href="${pageContext.request.contextPath }/personnel/manager/page?userName=${pageInfo.userName}&thisPage=${pageInfo.prePage}&pageSize=${pageInfo.pageSize}">上一页</a>

                            <c:set var="begin" value="0"></c:set>
                            <c:set var="end" value="0"></c:set>
                            <c:if test="${pageInfo.countPage <= 5 }">
                                <c:set var="begin" value="1"></c:set>
                                <c:set var="end" value="${pageInfo.countPage }"></c:set>
                            </c:if>
                            <c:if test="${pageInfo.countPage > 5 }">
                                <c:choose>
                                    <c:when test="${pageInfo.thisPage <= 3 }">
                                        <c:set var="begin" value="1"></c:set>
                                        <c:set var="end" value="5"></c:set>
                                    </c:when>
                                    <c:when test="${pageInfo.thisPage >=  pageInfo.countPage - 2 }">
                                        <c:set var="begin" value="${pageInfo.countPage - 4 }"></c:set>
                                        <c:set var="end" value="${pageInfo.countPage }"></c:set>
                                    </c:when>
                                    <c:otherwise>
                                        <c:set var="begin" value="${pageInfo.thisPage - 2 }"></c:set>
                                        <c:set var="end" value="${pageInfo.thisPage + 2 }"></c:set>
                                    </c:otherwise>
                                </c:choose>
                            </c:if>
                            <c:forEach begin="${begin }" end="${end }" step="1" var="i">
                                <c:if test="${pageInfo.thisPage == i }">
                                    ${i }
                                </c:if>
                                <c:if test="${pageInfo.thisPage != i }">
                                    <a href="${pageContext.request.contextPath }/personnel/manager/page?userName=${pageInfo.userName}&thisPage=${i}&pageSize=${pageInfo.pageSize}">${i }</a>
                                </c:if>
                            </c:forEach>

                            <a href="${pageContext.request.contextPath }/personnel/manager/page?userName=${pageInfo.userName}&thisPage=${pageInfo.nextPage}&pageSize=${pageInfo.pageSize}">下一页</a>
                            <a href="${pageContext.request.contextPath }/personnel/manager/page?userName=${pageInfo.userName}&thisPage=${pageInfo.lastPage}&pageSize=${pageInfo.pageSize}">尾页</a>

                            跳转到<input type="text" style="width:20px" value="${pageInfo.thisPage }" onchange="changePage(this)"/>页
                            <!-- 分页条结束 -->
                        </p> </div>
            </div>

        </div>
    </div>
</div>
<!--footer-->
<div class="footer">
    <p>Copyright &copy; 2016 code.com,All Rights Reserved. 上海信息服务有限公司
    </p>
</div>
<!--//footer-->

<!-- Classie -->
<script src="${ctx}/staticfile/js/classie.js"></script>
<script>
    var menuLeft = document.getElementById('cbp-spmenu-s1'),
        showLeftPush = document.getElementById('showLeftPush'),
        body = document.body;

    showLeftPush.onclick = function () {
        classie.toggle(this, 'active');
        classie.toggle(body, 'cbp-spmenu-push-toright');
        classie.toggle(menuLeft, 'cbp-spmenu-open');
        disableOther('showLeftPush');
    };

    function disableOther(button) {
        if (button !== 'showLeftPush') {
            classie.toggle(showLeftPush, 'disabled');
        }
    }
</script>
<!--scrolling js-->
<script src="${ctx}/staticfile/js/jquery.nicescroll.js"></script>
<script src="${ctx}/staticfile/js/scripts.js"></script>
<!--//scrolling js-->
<!-- Bootstrap Core JavaScript -->
<script src="${ctx}/staticfile/js/bootstrap.js"></script>

</body>
</html>
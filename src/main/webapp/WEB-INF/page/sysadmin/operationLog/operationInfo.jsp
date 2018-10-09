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
    <%@include file="../../_leftNavigation.jsp" %>
    <!--left-fixed -navigation-->
    <!-- header-starts -->
    <%@include file="../../_header.jsp" %>
    <!-- //header-ends -->
    <!-- main content start-->
    <div id="page-wrapper">
        <div class="main-page">

            <%--	<h3 class="title1">Tables</h3>--%>
            <div class="panel-body widget-shadow">
                <form action="${pageContext.request.contextPath }/operation/system/page?personnelName=${pageInfo.personnelName}&operationType=${pageInfo.operationType}" method="get" id="form1">

                    <span>被推荐人姓名:</span><input type="text" name="personnelName" maxlength="20" value="${pageInfo.personnelName}"/>
                  <span>  操作:</span><select name="operationType">
                    <option value="1" selected> 上传</option>
                    <option value="2"> 下载</option>
                    <option value="3"> 删除</option>
                    <option value="4"> 全部</option>
                </select>
                    <button type="submit" class="btn btn-default">搜索</button>

                </form>
            </div>


            <div class="panel-body widget-shadow">


                <%--<h4>查询简历</h4>--%>
                <table class="table">
                    <thead>
                    <tr>

                        <td width="70px">被推荐人姓名</td>
                        <td width="90px">工作分类</td>
                        <td width="45px">操作</td>
                        <td width="60px">操作员</td>
                        <td width="120px">操作员邮箱</td>
                        <td width="100px">操作时间</td>


                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="operation" items="${pageInfo.operationLogList}" varStatus="status">
                        <tr>

                            <td>${operation.personnelName}</td>
                            <td>${operation.workType}</td>

                            <td>
                                <c:if test="${operation.operationType==1}"> 上传</c:if>
                                <c:if test="${operation.operationType==2}"> 下载</c:if>
                                <c:if test="${operation.operationType==3}"> <p style="color: red" >删除</p></c:if>
                            </td>
                            <td>${operation.createName}</td>
                            <td>${operation.userName}</td>

                            <td>${operation.createDate}</td>

                        </tr>
                    </c:forEach>


                    </tbody>

                </table>
                    <div  id="pagin-btm" >
                        <p id="common_page">
                            共${pageInfo.countRow }条记录,共${pageInfo.countPage }页
                            <a href="${pageContext.request.contextPath }/operation/system/page?personnelName=${pageInfo.personnelName}&thisPage=${pageInfo.firstPage}&pageSize=${pageInfo.pageSize}">首页</a>
                            <a href="${pageContext.request.contextPath }/operation/system/page?personnelName=${pageInfo.personnelName}&thisPage=${pageInfo.prePage}&pageSize=${pageInfo.pageSize}">上一页</a>

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
                                    <a href="${pageContext.request.contextPath }/user/system/page?personnelName=${pageInfo.personnelName}&thisPage=${i}&pageSize=${pageInfo.pageSize}">${i }</a>
                                </c:if>
                            </c:forEach>

                            <a href="${pageContext.request.contextPath }/operation/system/page?personnelName=${pageInfo.personnelName}&thisPage=${pageInfo.nextPage}&pageSize=${pageInfo.pageSize}">下一页</a>
                            <a href="${pageContext.request.contextPath }/operation/system/page?personnelName=${pageInfo.personnelName}&thisPage=${pageInfo.lastPage}&pageSize=${pageInfo.pageSize}">尾页</a>

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
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

        function addFile() {
            var div1 = document.getElementById("div1");
            div1.innerHTML += "<div> <input type='file' name='file'><input type='button' value='删除' onclick='deleteFile(this)''><br></div>";
        };

        function deleteFile(input) {
            input.parentNode.parentNode.removeChild(input.parentNode);
            ;
        };

        function check() {
            var name = document.getElementById("inputfile").value;
            if (name == null || name == '') {
                alert("文件不能为空");
                return false;
            }
            return true;
        };

        function call() {

            //选择文件框的value属性赋值为空串
            document.getElementById("inputfile").value = "";
        };

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
    String message = (String)ses.getAttribute("mes");
    if(message == ""){
%>
<%
}else{
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
            <div class="forms">
                <!--  <h3 class="title1">Forms</h3>-->
                <div class="panel-body widget-shadow" data-example-id="basic-forms">
                    <div class="form-body">
                        <%--<form action="${pageContext.request.contextPath}/file/batchUpload.do" method="post" enctype="multipart/form-data">
                            <input type="text" name="name"><br>
                            <div id="div1">
                                <div> <input type="file" name="file">
                                    <input type="button" value="添加" onclick="addFile();"><br></div>
                            </div>
                            <input type="submit" >
                        </form>--%>
                        <form action="${pageContext.request.contextPath}/file/batchUpload.do" method="post" onsubmit="return check();"
                              name="file_upload" enctype="multipart/form-data">

                            <input type="file" name="file" multiple="multiple" id="inputfile"/><br>

                            <button type="submit" class="btn btn-default" >上传</button>
                            &nbsp&nbsp&nbsp
                            <button type="button" class="btn btn-default" onclick="call();">取消</button>
                            <br/>
                            <p style="color: red">上传文件说明：<br/>
                                1.支持上传doc、docx、pdf格式文件。<br/>
                                2.固定文件格式：[姓名]-[工作分类].[文件类型]<br/>e.g.:张三-测试.doc</p>

                        </form>
                    </div>
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
</div>
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
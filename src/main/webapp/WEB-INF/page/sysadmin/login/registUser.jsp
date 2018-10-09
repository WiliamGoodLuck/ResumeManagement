<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
	<title>人才库信息管理系统</title>
	<head>
		<c:set var="ctx" value="${pageContext.request.contextPath}"/>
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<%--<link rel="shortcut icon" href="static/favicon.ico"/>--%>
		<link href="${ctx}/staticfile/css/loginbootstrap.min.css" rel="stylesheet"/>
		<link href="${ctx}/staticfile/css/font-awesome.css?v=4.4.0" rel="stylesheet"/>
		<link href="${ctx}/staticfile/css/loginanimate.css" rel="stylesheet"/>
		<link href="${ctx}/staticfile/css/loginstyle.css" rel="stylesheet"/>
		<link href="${ctx}/staticfile/css/login.css" rel="stylesheet"/>
		<!--[if lt IE 9]>
		<meta http-equiv="refresh" content="0;ie.html" />
		<![endif]-->
		<script>
            if (window.top !== window.self) {
                window.top.location = window.location;
            }
		</script>
		<script language="javascript" type="text/javascript">
            function check()
            {
                if (form1.nickName.value==""){
                    alert("请输入登录账号!");
                    return false;
                }
                if (form1.username.value==""){
                    alert("请输入登录邮箱!");
                    return false;
                }
                if (form1.password.value==""){
                    alert("请输入登录密码!");
                    return false;
                }
                if (form1.repassword.value==""){
                    alert("请输入确认密码!");
                    return false;
                }
                if (form1.password.value!=form1.repassword.value){
                    alert("两次密码不一致");
                    return false;
                }
                return true;
            }
		</script>
	</head>

<body class="signin">
<div class="signinpanel">
	<div class="row">
		<div class="col-sm-7">
			<div class="signin-info">
				<div class="logopanel m-b">
					<h1>人才库信息管理系统</h1>
				</div>
				<div class="m-b"></div>
				<h2>code Resume Management System</h2>


			</div>
		</div>
		<div class="col-sm-5">
			<form id="form1" method="post" action="${ctx}/loginRegist">
				<h4 class="no-margins" style="color: #8B8378" >注册：</h4>
				<%--<p class="m-t-md"  style="color: #8B8378" >登录到后台</p>--%>
				<input type="text" class="form-control uname" name="nickName"  placeholder="姓名" />
				<input type="text" class="form-control uname"  id="username" name="username"  placeholder="登录邮箱" />
				<input type="password" class="form-control pword m-b" name="password"  placeholder="登录密码" />
				<input type="password" class="form-control pword m-b" name="repassword"  placeholder="确认密码" />
				<%--<input  th:if="${kaptcha} == true"  type="text" class="form-control" style="color: #000000"  name="kaptcha"  placeholder="验证码" />

				<img th:if="${kaptcha} == true" src="/static/kaptcha.jpg" width="200" id="kaptchaImage" alt="验证码加载中" title="看不清，点击换一张" />--%>
				<%--   <a href="">忘记密码了？</a>--%>
				<a href="${ctx}/login">返回登录页</a>
				<button class="btn btn-success btn-block" onClick="return check()">注册</button>

				<div class="msgtip">
					<c:if test="${!empty ERR_INFO}">
					<p style="color: red">	${ERR_INFO}<p>
					</c:if>
				</div>
			</form>

		</div>
	</div>
</div>
<div class="signup-footer">
	<div class="signup-footer p">
	<p>Copyright &copy; 2016 code.com,All Rights Reserved. 上海信息服务有限公司
	</p>
	</div>
</div>
</body>
<%--<script src="js/jquery.min.js?v=2.1.4"></script>--%>
<%--<script type="text/javascript">
    $(function() {
        $('#kaptchaImage').click(function() {$(this).attr('src','/static/kaptcha.jpg?' + Math.floor(Math.random() * 100));});
    });
</script>--%>

</html>
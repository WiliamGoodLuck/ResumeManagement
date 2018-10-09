<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<div class="sticky-header header-section ">
    <div class="header-left">
        <!--toggle button start-->
        <button id="showLeftPush"><i class="fa fa-bars"></i></button>
        <!--toggle button end-->
        <!--logo -->
        <div class="logo">
            <a href="${ctx}/home/home.do">
                <h1>简历管理系统</h1>
                <span>code</span>
            </a>
        </div>
        <!--//logo-->
        <!--search-box-->
        <%-- <div class="search-box">
                <form class="input">
                    <input class="sb-search-input input__field--madoka" placeholder="Search..." type="search"
                           id="input-31"/>
                    <label class="input__label" for="input-31">
                        <svg class="graphic" width="100%" height="100%" viewBox="0 0 404 77" preserveAspectRatio="none">
                            <path d="m0,0l404,0l0,77l-404,0l0,-77z"/>
                        </svg>
                    </label>
                </form>
            </div>--%><!--//end-search-box-->

    </div>
    <div class="header-right">
        <!--notification menu end -->
        <div class="profile_details">
            <ul>
                <li class="dropdown profile_details_drop">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown" aria-expanded="false">
                        <div class="profile_img">
                            <%-- 可加入头像
                                  <span class="prfil-img"><img src="${ctx}/staticfile/images/a.png" alt=""> </span>--%>
                            <div class="user-name">
                                <p>${USER_INFO.nickName}</p>
                                <span>${USER_INFO.roleName}</span>
                            </div>
                            <i class="fa fa-angle-down lnr"></i>
                            <i class="fa fa-angle-up lnr"></i>
                            <div class="clearfix"></div>
                        </div>
                    </a>
                    <ul class="dropdown-menu drp-mnu">
                        <li><a href="${ctx}/user/toUpdateSys.do"><i class="fa fa-cog"></i> 系统设置</a></li>
                        <li><a href="${ctx}/user/toUpdate.do"><i class="fa fa-user"></i> 个人信息</a></li>
                        <li><a href="${ctx}/logout.do"><i class="fa fa-sign-out"></i> 退出登录</a></li>
                    </ul>
                </li>
            </ul>
        </div>

    </div>

</div>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<!--left-fixed -navigation-->
<div class=" sidebar" role="navigation">
    <div class="navbar-collapse">
        <nav class="cbp-spmenu cbp-spmenu-vertical cbp-spmenu-left" id="cbp-spmenu-s1">
            <ul class="nav" id="side-menu">
                <li>
                    <a href="${ctx}/home/batchAdd" class="chart-nav"><i class="fa fa-bar-chart nav_icon"></i>简历上传
                        <span class="nav-badge-btm pull-right"></span></a>
                </li>
             <%--   <shiro:hasPermission name="manager:file">--%>
                    <li>
                        <a href="${ctx}/home/manager/query" class="chart-nav"><i
                                class="fa fa-bar-chart nav_icon"></i>简历查询
                            <span class="nav-badge-btm pull-right"></span></a>
                    </li>
    <%--            </shiro:hasPermission>--%>欢迎访问简历管理系统！
                <shiro:hasPermission name="admin:count">
                    <li>
                        <a href="${ctx}/home/system/index.do" class="chart-nav"><i class="fa fa-bar-chart nav_icon"></i>简历统计</a>
                    </li>
                </shiro:hasPermission>
                <shiro:hasPermission name="admin:editUser">
                    <li>
                        <a href="${ctx}/home/system/queryUser.do" class="chart-nav"><i
                                class="fa fa-bar-chart nav_icon"></i>用户查询 <span
                                class="nav-badge-btm pull-right"></span></a>
                    </li>
                </shiro:hasPermission>
                <shiro:hasPermission name="admin:editUser">
                    <li>
                        <a href="${ctx}/operation/system/page.do" class="chart-nav"><i
                                class="fa fa-bar-chart nav_icon"></i>操作查询 <span
                                class="nav-badge-btm pull-right"></span></a>
                    </li>
                </shiro:hasPermission>
            </ul>
            <!-- //sidebar-collapse -->
        </nav>
    </div>
</div>
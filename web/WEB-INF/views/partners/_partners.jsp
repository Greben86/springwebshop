<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<h1 class="page-header">Партнеры</h1>

<a href="${pageContext.request.contextPath}/admin/partners/add" class="btn btn-default">
    <span class="glyphicon glyphicon-plus"></span>
    Новый партнер
</a>

<table class="table table-striped">
    <col width="50%">
    <col width="20%">
    <col width="15%">
    <col width="15%">
    <thead>
        <tr>
            <th>Описание</th>
            <th>Ссылка</th>
            <th>#</th>
            <th>#</th>
        </tr>
    </thead>
    <tbody>
        <c:forEach items="${partners}" var="item">
            <tr>     
                <td>${item.note}</td>
                <td>${item.link}</td>
                <td>
                    <a href="${pageContext.request.contextPath}/admin/partners/edit/${item.id}" class="btn btn-primary">
                        <span class="glyphicon glyphicon-pencil"></span>
                        Редактировать
                    </a>
                </td>
                <td>
                    <a href="${pageContext.request.contextPath}/admin/partners/delete/${item.id}" class="btn btn-danger">
                        <span class="glyphicon glyphicon-remove"></span>
                        Удалить
                    </a>
                </td>
            </tr>
        </c:forEach>
    </tbody>
</table>
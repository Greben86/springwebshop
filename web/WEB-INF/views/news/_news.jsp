<%@ page pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<h1 class="page-header">Новости</h1>

<a href="${pageContext.request.contextPath}/admin/news/add" class="btn btn-default">
    <span class="glyphicon glyphicon-plus"></span>
    Добавить новость
</a>

<table class="table table-striped">
    <col width="15%">
    <col width="55%">
    <col width="15%">
    <col width="15%">
    <caption></caption>
    <thead>
        <tr>
            <th>Дата</th>
            <th>Заголовок</th>
            <th>#</th>
            <th>#</th>
        </tr>
    </thead>
    <tbody>
        <c:forEach items="${news}" var="item">
            <tr>
                <td>${item.date}</td>
                <td>${item.title}</td>
                <td>
                    <a href="${pageContext.request.contextPath}/admin/news/edit/${item.id}" class="btn btn-primary">
                        <span class="glyphicon glyphicon-pencil"></span>
                        Редактировать
                    </a>
                </td>
                <td>
                    <a href="${pageContext.request.contextPath}/admin/news/delete/${item.id}" class="btn btn-danger">
                        <span class="glyphicon glyphicon-remove"></span>
                        Удалить
                    </a>
                </td>
            </tr>
        </c:forEach>
    </tbody>
</table>

<%@ page pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<h1 class="page-header">
    Истории успеха
    <a href="${pageContext.request.contextPath}/admin/tales/add" class="btn btn-default">
        <span class="glyphicon glyphicon-plus"></span>
        Добавить историю
    </a>
</h1>

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
        <c:forEach items="${tales}" var="item">
            <tr>
                <td>${item.date}</td>
                <td>${item.title}</td>
                <td>
                    <a href="${pageContext.request.contextPath}/admin/tales/edit/${item.id}" class="btn btn-primary">
                        <span class="glyphicon glyphicon-pencil"></span>
                        Редактировать
                    </a>
                </td>
                <td>
                    <a href="${pageContext.request.contextPath}/admin/tales/delete/${item.id}" class="btn btn-danger">
                        <span class="glyphicon glyphicon-remove"></span>
                        Удалить
                    </a>
                </td>
            </tr>
        </c:forEach>
    </tbody>
</table>

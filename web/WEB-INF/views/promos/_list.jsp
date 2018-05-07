<%@ page pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<h1 class="page-header">Промо-акции</h1>

<a href="${pageContext.request.contextPath}/admin/promos/add" class="btn btn-default">
    <span class="glyphicon glyphicon-plus"></span>
    Новая промо-акция
</a>

<table class="table table-striped">
    <col width="70%">
    <col width="15%">
    <col width="15%">
    <caption></caption>
    <thead>
        <tr>
            <th>Название</th>
            <th>#</th>
            <th>#</th>
        </tr>
    </thead>
    <tbody>
        <c:forEach items="${promos}" var="item">
            <tr>
                <td>${item.name}</td>
                <td>
                    <a href="${pageContext.request.contextPath}/admin/promos/edit/${item.id}" class="btn btn-primary">
                        <span class="glyphicon glyphicon-pencil"></span>
                        Редактировать
                    </a>
                </td>
                <td>
                    <a href="${pageContext.request.contextPath}/admin/promos/delete/${item.id}" class="btn btn-danger">
                        <span class="glyphicon glyphicon-remove"></span>
                        Удалить
                    </a>
                </td>
            </tr>
        </c:forEach>
    </tbody>
</table>
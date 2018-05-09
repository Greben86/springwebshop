<%@ page pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<h1 class="page-header">Заявки на вступление в кооператив</h1>

<table class="table table-striped">
    <caption></caption>
    <thead>
        <tr>
            <th>Дата</th>
            <th>Организация</th>
            <th>ФИО</th>
            <th>Телефон</th>
            <th>Email</th>
            <th>Описание</th>
        </tr>
    </thead>
    <tbody>
        <c:forEach items="${requests}" var="item">
            <tr>
                <td>${item.date}</td>
                <td>${item.fullname}</td>
                <td>${item.family} ${item.name} ${item.name2}</td>
                <td>${item.phone}</td>
                <td>${item.email}</td>
                <td>${item.note}</td>
            </tr>
        </c:forEach>
    </tbody>
</table>

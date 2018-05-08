<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<sf:form method="POST" action="${pageContext.request.contextPath}/login" cssClass="form-signin">
    <center>
        <h1 class="h3 mb-3 font-weight-normal">Авторизация</h1>
    </center>            

    <label for="username" class="sr-only">Электронная почта</label>
    <input type="text" name="username" class="form-control" placeholder="Email" required autofocus>

    <label for="password" class="sr-only">Пароль</label>
    <input type="password" name="password" class="form-control" placeholder="Пароль" required>

    <c:if test="${param.error != null}">
        <div class="alert alert-danger" role="alert">
            Ошибочный логин или пароль
        </div>
    </c:if>
    <c:if test="${param.logout != null}">
        <div class="alert alert-success" role="alert">
            Вы вышли из системы
        </div>
    </c:if>

    <button class="btn btn-lg btn-primary btn-block" type="submit">Войти</button>
</sf:form>

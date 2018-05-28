<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<sf:form method="POST" modelAttribute="news">
    <fieldset>
        <sf:hidden path="id" id="id"/>

        <div class="form-group">
            <label for="title" class="required">Заголовок</label>
            <sf:input path="title" id="title" class="form-control input-sm" placeholder="Укажите заголовок" size="100"/>
            <sf:errors path="title" cssStyle="color: red; font-size: 10pt;"/>
        </div>
    </fieldset>
    <input type="submit" name="submit" id="submit" value="Сохранить" class="btn btn-success"/>
</sf:form>

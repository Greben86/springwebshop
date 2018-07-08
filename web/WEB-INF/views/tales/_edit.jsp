<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<h1>${title}</h1>

<sf:form method="POST" modelAttribute="tale" 
         action="${pageContext.request.contextPath}/admin/tales/${callback}" 
         enctype="multipart/form-data">
    <fieldset>
        <sf:hidden path="id" id="id"/>
        <sf:hidden path="filename" id="filename"/>

        <div class="form-group">
            <label for="title" class="required">Заголовок</label>
            <sf:input path="title" id="title" class="form-control input-sm" placeholder="Укажите заголовок" size="100"/>
            <sf:errors path="title" cssStyle="color: red; font-size: 10pt;"/>
        </div>

        <div class="form-group">
            <label for="file" class="required">Файл</label>
            <input type="file" name="file" id="file" multiple="">
        </div>

        <div class="form-group">
            <label for="body" class="required">Текст</label>
            <sf:textarea path="body" id="body" class="form-control" rows="15"/>
            <sf:errors path="body" cssStyle="color: red; font-size: 10pt;"/>
        </div>

        <div class="checkbox">
            <label>
                <sf:checkbox path="enabled" id="enabled" class="checkbox"/>Активна
            </label>
            <sf:errors path="enabled" cssStyle="color: red; font-size: 10pt;"/>
        </div>
    </fieldset>
    <a href="${pageContext.request.contextPath}/admin/tales" class="btn btn-default">
        Отмена
    </a>
    <input type="submit" name="submit" id="submit" value="Сохранить" class="btn btn-success"/>
</sf:form>

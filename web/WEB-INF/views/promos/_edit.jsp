<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<sf:form method="POST" modelAttribute="promo" 
         action="${pageContext.request.contextPath}/admin/promos/${callback}" 
         enctype="multipart/form-data">
    <fieldset>
        <sf:hidden path="id" id="id"/>
        <sf:hidden path="filename" id="filename"/>

        <div class="form-group">
            <label for="name" class="required">Название</label>
            <sf:input path="name" id="name" class="form-control input-sm" placeholder="Укажите название" size="100"/>
            <sf:errors path="name" cssStyle="color: red; font-size: 10pt;"/>
        </div>

        <div class="form-group">
            <label for="file" class="required">Файл</label>
            <input type="file" name="file" id="file" multiple="">
        </div>
    </fieldset>
    <input type="submit" name="submit" id="submit" value="Сохранить" class="btn btn-success"/>
</sf:form>

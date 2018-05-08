<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<h1>${title}</h1>

<sf:form method="POST" modelAttribute="partner" 
         action="${pageContext.request.contextPath}/admin/partners/${callback}" 
         enctype="multipart/form-data">
    <fieldset>
        <sf:hidden path="id" id="id"/>
        <sf:hidden path="filename" id="filename"/>

        <div class="form-group">
            <label for="note" class="required">Краткое описание</label>
            <sf:textarea path="note" id="note" class="form-control" rows="15"/>
            <sf:errors path="note" cssStyle="color: red; font-size: 10pt;"/>
        </div>

        <div class="form-group">
            <label for="link" class="required">Ссылка</label>
            <sf:input path="link" id="link" class="form-control input-sm" placeholder="Укажите ссылку" size="100"/>
            <sf:errors path="link" cssStyle="color: red; font-size: 10pt;"/>
        </div>

        <div class="form-group">
            <label for="link" class="required">Скидка</label>
            <sf:input path="discount" id="discount" class="form-control input-sm" placeholder="Укажите скидку" size="100"/>
            <sf:errors path="discount" cssStyle="color: red; font-size: 10pt;"/>
        </div>

        <div class="form-group">
            <label for="file" class="required">Файл</label>
            <input type="file" name="file" id="file" multiple="">
        </div>
    </fieldset>
    <a href="${pageContext.request.contextPath}/admin/partners" class="btn btn-default">
        Отмена
    </a>
    <input type="submit" name="submit" id="submit" value="Сохранить" class="btn btn-success"/>
</sf:form>
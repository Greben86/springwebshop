<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<center>
    <img class="mb-4" src="${pageContext.request.contextPath}/images/error.jpg">
    <div class="container">
        <div class="row">
            <div class="col-md-12">
                <div class="error-template">
                    <h1>Ой! Кажется что-то сломалось</h2>
                        <div class="error-details">
                            ${message}
                        </div>
                        <div class="error-actions">
                            <a href="${pageContext.request.contextPath}/admin" class="btn btn-default btn-lg">
                                На главную
                            </a>
                        </div>
                </div>
            </div>
        </div>
    </div>
</center>

<!--
URL: ${url}
Exception:  ${exc.message}
<c:forEach items="${exc.stackTrace}" var="ste">    
    ${ste}
</c:forEach>
-->
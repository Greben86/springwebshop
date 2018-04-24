<%@ page pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<html>
    <head>
        <meta http-equiv="Content-Type"	content="text/html" charset="utf-8"/>
        <base href="/"/>
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <meta name="author" content="Greben V">
        <link rel="shortcut icon" href="/img/favicon.ico">
        <title><tiles:getAsString name="title"/></title>

        <!-- Latest compiled and minified CSS -->
        <link rel="stylesheet" href="//netdna.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css">

        <!-- Optional theme -->
        <link rel="stylesheet" href="//netdna.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap-theme.min.css">

        <!-- Latest compiled and minified JavaScript -->
        <script src="//netdna.bootstrapcdn.com/bootstrap/3.3.2/js/bootstrap.min.js"></script>

        <style type="text/css">
            /* Sticky footer styles
            -------------------------------------------------- */
            html {
                position: relative;
                min-height: 100%;
            }
            body {
                /* Margin bottom by footer height */
                margin-bottom: 60px;
            }
            /* Custom page CSS
            -------------------------------------------------- */
            /* Not required for template or sticky footer method. */

            .container {
                width: auto;
                max-width: 970px;
                padding: 0 15px;
            }
            .container .text-muted {
                margin: 20px 0;
            }

            #footer {
                position: absolute;
                bottom: 0;
                width: 100%;
                /* Set the fixed height of the footer here */
                height: 60px;
                background-color: #f5f5f5;
            }
        </style>
    </head>
    <body>
        <div class="navbar navbar-inverse navbar-fixed-top" role="navigation">
            <div class="container-fluid">
                <div class="navbar-header">
                    <a class="navbar-brand" href="${pageContext.request.contextPath}/">
                        Панель управления
                    </a>
                </div>
                <div class="navbar-collapse collapse">
                    <ul class="nav navbar-nav navbar-right">
                        <li><a href="${pageContext.request.contextPath}/admin/requests">Заявки</a></li>
                        <li><a href="${pageContext.request.contextPath}/admin/programs">Программы</a></li>
                        <li><a href="${pageContext.request.contextPath}/admin/partners">Партнеры</a></li>
                        <li><a href="${pageContext.request.contextPath}/admin/promos">Промо-акции</a></li>
                        <li><a href="${pageContext.request.contextPath}/logout">Выйти</a></li>
                    </ul>
                </div>
            </div>
        </div>
        
        <div class="container-fluid">
            <div class="row-fluid">
                <br>
                <br>
                <br>
                <tiles:insertAttribute name="body" />             
            </div>
        </div>        
        
        <div id="footer">
            <div class="container">
                <p class="text-muted">ПК ВО Содействие</p>
            </div>
        </div>
    </body>
</html>
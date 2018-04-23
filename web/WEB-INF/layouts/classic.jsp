<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<html>
    <head>
        <meta http-equiv="Content-Type"	content="text/html" charset="utf-8"/>
        <base href="/"/>
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <meta name="description" content="??? ??????????">
        <meta name="author" content="Greben V">
        <link rel="shortcut icon" href="/img/favicon.ico">

        <link rel="stylesheet" type="text/css" href="/css/bootstrap.css"/>
        <script src="/js/bootstrap.js"></script>

        <title><tiles:getAsString name="title"/></title>

        <script type="text/javascript" src="/js/jquery.js"></script>
        <script type="text/javascript" src="/js/bootstrap.min.js"></script>

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
        </style>
    </head>
    <body>
        <tiles:insertAttribute name="header" />
        <tiles:insertAttribute name="menu" />
        <tiles:insertAttribute name="body" />
        <tiles:insertAttribute name="footer" />
    </body>
</html>
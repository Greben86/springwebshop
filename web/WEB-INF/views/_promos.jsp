<%@ page pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <META http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <title>Customer controll</title>
</head>
<body>
    <form method="POST" enctype="multipart/form-data"
        action="/shop/goods/upload">
        File to upload: <input type="file" name="file"><br /> Name: <input
            type="text" name="name"><br /> <br /> <input type="submit"
            value="Upload"> Press here to upload the file!
    </form>
</body>
</html>
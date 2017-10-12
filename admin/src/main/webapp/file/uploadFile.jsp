<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title></title>
</head>
<body>
<form action="<c:url value="/file/uploadFile"/>" method="post" enctype="multipart/form-data">
    <input type="file" name="filedata">
    <input type="submit">
</form>
</body>
</html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title></title>
</head>
<body>
<form action="<c:url value="/file/uploadLog"/>" method="post" enctype="multipart/form-data">
    <input type="file" name="filedata">
    Information<textarea  name="Information" rows="5" cols="80"></textarea>
    <input type="submit">
</form>
</body>
</html>
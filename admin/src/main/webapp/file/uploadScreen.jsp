<%--
  Created by IntelliJ IDEA.
 * User: warriorr
 * Mail: warriorr@163.com
  Date: 2015/5/7
  Time: 19:30
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title></title>
</head>
<body>
<form action="<c:url value="/file/uploadScreen"/>" method="post" enctype="multipart/form-data">
    <input type="file" name="filedata">
    Information<textarea  name="Information" rows="5" cols="80"></textarea>
    <input type="submit">
</form>
</body>
</html>
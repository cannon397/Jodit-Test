<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<html>
<head>
    <title>Post List</title>
</head>
<body>
<h1>Saved Posts</h1>

<table border="1">
    <tr>
        <th>ID</th>
        <th>Content</th>
    </tr>
    <c:forEach var="post" items="${posts}">
        <tr>
            <td>${post.id}</td>
            <td>${post.content}</td>
        </tr>
    </c:forEach>
</table>

<a href="/">Go Back</a>
</body>
</html>

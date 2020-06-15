<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<head>
    <meta charset="UTF-8">
    <title>Title</title>
    <ol>
        <li><a href="/register">Register</a></li>
        <li><a href="/login">Login</a></li>

        <c:if test="${user!=null}">
            <li><a href="/logout">Logout</a></li>
            <li><a href="/addAuto">Add auto</a> </li>
            <li><a href="/catalog">Catalog</a> </li>
        </c:if>
    </ol>
    <c:if test="${alertMessage!=null}">
        <script type="text/javascript">
            window.onload = function () {
                alert(${alertMessage.message});
            };
        </script>
    </c:if>
</head>
<body>

</body>
</html>
<!DOCTYPE html>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<head>
    <meta charset="UTF-8">
    <title>Update auto</title>
</head>
<body>
<h2>Update auto</h2>
<ol>
    <li><a href="/index">Main page</a></li>
</ol>
<form:form method="POST"
           action="/update" modelAttribute="autoForm">
    <table>
        <tr>
            <td><form:label path="uuid">uuid</form:label></td>
            <td><form:input path="uuid"/></td>
        </tr>
        <tr>
            <td><form:label path="model">Model</form:label></td>
            <td><form:input path="model"/></td>
        </tr>
        <tr>
            <td><form:label path="seats">Seats</form:label></td>
            <td><form:input path="seats"/></td>
        </tr>
        <tr>
            <td><form:label path="price">Price</form:label></td>
            <td><form:input path="price"/></td>
        </tr>
        <tr>
            <td><form:label path="hasBabySeat">Baby seat</form:label></td>
            <td><form:checkbox path="hasBabySeat" /></td>
        </tr>
        <tr>
            <td><form:label path="hasConditioner">Conditioner</form:label></td>
            <td><form:checkbox path="hasConditioner" value="a1"/></td>
        </tr>
        <tr>
            <td><form:label path="hasBar">Bar</form:label></td>
            <td><form:checkbox path="hasBar" value="a2"/></td>
        </tr>
        <tr>
            <td><form:label path="manufacturer">Manufaturer</form:label></td>
            <td><form:input path="manufacturer"/></td>
        </tr>

        <tr>
            <td><input type="submit" value="Submit"/></td>
        </tr>
    </table>
</form:form>
</body>
</html>
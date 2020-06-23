<!DOCTYPE html>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<head>
    <meta charset="UTF-8">
    <title>Update auto</title>
</head>
<body>
<h2>Mementos</h2>
<ol>
    <li><a href="/index">Main page</a></li>
</ol>

    <c:forEach var="item" items="${mementoList}">
        <table border="1" style="margin-top: 10px;">
            <tr>
                <td>MEMENTO ID</td>
                <td><c:out value="${item['id']}"/></td>
            </tr>
            <tr>
                <td>Date</td>
                <td><c:out value="${item['date']}"/></td>
            </tr>
            <tr>
                <td>Model</td>
                <td><c:out value="${item['auto'].model}"/></td>
            </tr>
            <tr>
                <td>Price</td>
                <td><c:out value="${item['auto'].price}"/></td>
            </tr>
            <tr>
                <td>Seats</td>
                <td><c:out value="${item['auto'].seats}"/></td>
            </tr>
            <tr>
                <td>Baby seat</td>
                <td><c:out value="${item['auto'].hasBabySeat}"/></td>
            </tr>
            <tr>
                <td>Conditioner</td>
                <td><c:out value="${item['auto'].hasConditioner}"/></td>
            </tr>
            <tr>
                <td>Bar</td>
                <td><c:out value="${item['auto'].hasBar}"/></td>
            </tr>
            <tr>
                <td>
                    <form action="/memento" method="POST">
                        <input name="id" value="${item['id']}" type="hidden">
                        <button type="submit" href="/memento?id=${item['id']}">To this version
                        </button>
                    </form>
                </td>
            </tr>
        </table>
    </c:forEach>

</body>
</html>
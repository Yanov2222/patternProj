<!DOCTYPE html>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<head>
    <meta charset="UTF-8">
    <title>Catalog</title>
</head>
<body>
<h2>Catalog</h2>
<ol>
    <li><a href="/index">Main page</a></li>
</ol>
<form method="GET" action="/search" style="margin-left: 30%; margin-right:30%; margin-top: 3%;">
    <div>
        <label for="minPrice">Min price</label>
        <input id="minPrice" name="minPrice">
    </div>
    <div>
        <label for="maxPrice">Max price</label>
        <input id="maxPrice" name="maxPrice">
    </div>
    <div>
        <label for="hasBabySeat">Has conditioner</label>
        <input id="hasBabySeat" type="checkbox" name="hasBabySeat"/>
    </div>
    <div>
        <label for="hasConditioner">Has conditioner</label>
        <input id="hasConditioner" type="checkbox" name="hasConditioner"/>
    </div>
    <div>
        <label for="hasBar">Has bar</label>
        <input id="hasBar" type="checkbox" name="hasBar"/>
    </div>
    <button type="submit">Search</button>
</form>

<c:forEach var="item" items="${catalog}">
<table border="1" style="margin-top: 10px;">
    <tr>
        <td>Id</td>
        <td><c:out value="${item['id']}"/></td>
    </tr>
    <tr>
        <td>Model</td>
        <td><c:out value="${item['model']}"/></td>
    </tr>
    <tr>
        <td>Price</td>
        <td><c:out value="${item['price']}"/></td>
    </tr>
    <tr>
        <td>Seats</td>
        <td><c:out value="${item['seats']}"/></td>
    </tr>
    <tr>
        <td>Baby seat</td>
        <td><c:out value="${item['hasBabySeat']}"/></td>
    </tr>
    <tr>
        <td>Conditioner</td>
        <td><c:out value="${item['hasConditioner']}"/></td>
    </tr>
    <tr>
        <td>Bar</td>
        <td><c:out value="${item['hasBar']}"/></td>
    </tr>
    <tr>
        <td>
            <form action="update" method="GET">
                <input name="id" value="${item['id']}" type="hidden">
                <button type="submit" href="/update?id=${item['id']}">Update
                </button>
            </form>
        </td>
        <td>
            <form action="delete" method="POST">
                <input name="id" value="${item['id']}" type="hidden">
                <button type="submit" href="/delete?id=${item['id']}">Delete
                </button>
            </form>
        </td>
    </tr>
</table>
</c:forEach>
</body>
</html>
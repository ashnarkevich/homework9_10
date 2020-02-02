<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<html>
<head>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/style.css">
    <title>Add Users pages</title>
</head>
<body>

    <jsp:include page="header.jsp" />

    <h2>The list of users:</h2>

    <c:choose>
        <c:when test="${!empty users}">

            <table class="table">
                <thead class="table-title">
                    <tr class="table-row">
                        <td class="table-col">Name</td>
                        <td class="table-col">password</td>
                        <td class="table-col">active</td>
                        <td class="table-col">age</td>
                        <td class="table-col">address</td>
                        <td class="table-col">telephone</td>
                        <td class="table-col">delete</td>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="user" items="${users}">
                        <tr class="table-row">
                            <td class="table-col"><c:out value="${user.userName}"/></td>
                            <td class="table-col"><c:out value="${user.password}"/></td>
                            <td class="table-col">
                                <c:choose>
                                    <c:when test="${user.active}">
                                        I am a superman
                                    </c:when>
                                    <c:otherwise>
                                        Staying at shadow
                                    </c:otherwise>
                                </c:choose>
                            </td>
                            <td class="table-col"><c:out value="${user.age}"/></td>
                            <td class="table-col"><c:out value="${user.address}"/></td>
                            <td class="table-col"><c:out value="${user.telephone}"/></td>
                            <td class="table-col">
                                <a href="${pageContext.request.contextPath}/users/userDelete?id=${user.id}" name="delete">delete</a>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>

        </c:when>
        <c:otherwise>
            Users are not found
        </c:otherwise>
    </c:choose>

</body>
</html>
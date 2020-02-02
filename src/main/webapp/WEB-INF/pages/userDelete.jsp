<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/style.css">
    <title>Delete user</title>
</head>
<body>

    <jsp:include page="header.jsp" />

    <div>
        <c:choose>
            <c:when test="${addedUser != null or addedUser != 0}">
                <h3>User deleted.</h3>
            </c:when>
            <c:otherwise>
                <h3>User didn`t delete.</h3>
            </c:otherwise>
        </c:choose>

        <c:if test="${exception != null}" >
            <p>
                <c:out value="No correct parameter: " />
                <c:out value="${exception}" />
            </p>
        </c:if>
    </div>

</body>
</html>
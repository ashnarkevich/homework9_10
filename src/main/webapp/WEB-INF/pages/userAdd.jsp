<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/style.css">
    <title>Add Users pages</title>
</head>
<body>

    <jsp:include page="header.jsp" />

    <div>
    <h2>Add user:</h2>
    <form method="post" action="${pageContext.request.contextPath}/users/userAdd">
        <input name="username" type="text" placeholder="Your name" value="Name1" required minlength="1" maxlength="40"><br/>
        <input name="password" type="password" placeholder="Password" value="password" required minlength="1" maxlength="40"><br/>
        <label> is active:
            <select name="active" required>
                <option value="true" selected>true</option>
                <option value="false">false</option>
            </select>
        </label><br/>
        <input name="age" type="text" placeholder="Age" value="23" required pattern="[^0][0-9]{0,2}"><br/>
        <input name="address" type="text" placeholder="Address" value="Address1" required minlength="1" maxlength="100"><br/>
        <input name="telephone" type="text" placeholder="Telephone" value="telephone1" required minlength="1" maxlength="40"><br/>
        <input class="form-btn" value="add" type="submit"><br/>
    </form>
    </div>

    <div>
    <c:if test="${exception != null}" >
        <p>
            <c:out value="No correct parameter: " />
            <c:out value="${exception}" />
        </p>
    </c:if>

    <c:if test="${addedUser != null}">
        <h3>User added:</h3>
            <p>
                <c:out value="${addedUser.id}"/>
                <c:out value="${addedUser.userName}"/>
                <c:out value="${addedUser.password}"/>
                <c:out value="${addedUser.active}"/>
                <c:out value="${addedUser.age}"/>
                <c:out value="${addedUser.address}"/>
                <c:out value="${addedUser.telephone}"/>
            </p>
    </c:if>
    </div>

</body>
</html>
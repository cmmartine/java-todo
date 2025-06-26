<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html lang="en">
    <head>
        <title>Todo List JSP</title>
    </head>
    <body>
        <h1>Todo List (JSP)</h1>
        <ul>
            <c:forEach var="todo" items="${todos}">
                <li>${todo.text}</li>
            </c:forEach>
        </ul>
        <form method="post" action="/todos">
            <input type="text" name="text" placeholder="Enter a new todo" required/>
            <button type="submit">Add</button>
        </form>
    </body>
</html>
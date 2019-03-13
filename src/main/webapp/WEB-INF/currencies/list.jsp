<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<t:template title="Currencies">
    <jsp:body>
        <h1>Currencies</h1>
        <a href="./currencies?action=create">Create</a>
        <table>
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Name</th>
                    <th>Actions</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="item" items="${requestScope.data}">
                    <tr>
                        <td>${item.getId()}</td>
                        <td>${item.getName()}</td>
                        <td>
                            <a href="./currencies?action=edit&id=${item.getId()}">Edit</a>
                            <a href="./currencies?action=delete&id=${item.getId()}">Delete</a>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </jsp:body>
</t:template>

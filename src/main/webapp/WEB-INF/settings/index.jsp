<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<t:template title="Settings">
    <jsp:body>
        <h1>Settings</h1>
        <p>
            <a class="btn btn-warning" href="./settings?action=create">Create</a>
        </p>
        <div class="table-responsive">
            <table class="table table-bordered table-striped table-hover">
                <thead>
                    <tr>
                        <th class="text-center">Setting Name</th>
                        <th class="text-center">Text Value</th>
                        <th class="text-center">Number Value</th>
                        <th class="text-center">Actions</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="item" items="${listData}">
                        <tr>
                            <td class="text-center">
                                <a href="./settings?action=show-details&id=${item.getId()}">
                                    ${item.getName()}
                                </a>
                            </td>
                            <td>
                                ${item.getTextValue()}
                            </td>
                            <td>
                                ${item.getNumberValue()}
                            </td>
                            <td class="text-center">
                                <div class="btn-group-sm">
                                    <a class="btn btn-primary" href="./settings?action=edit&id=${item.getId()}">Edit</a>
                                    <a class="btn btn-danger" href="./settings?action=confirm-delete&id=${item.getId()}">Delete</a>
                                </div>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
    </jsp:body>
</t:template>

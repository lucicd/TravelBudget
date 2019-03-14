<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<t:template title="An error has occured">
    <jsp:body>
        Error message: ${requestScope.error}
    </jsp:body>
</t:template>
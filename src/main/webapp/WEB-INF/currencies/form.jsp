<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<t:template title="Create Currency">
    <jsp:body>
        <h1>${param.id == null ? "Create" : "Edit"} Currency</h1>
        <form>
            <input type="text" name="id" value="${param.id}">
            <div class="form-group">
                <label for="name">Currency</label>
                <input type="text" 
                       class="form-control" 
                       id="name"
                       name="name"
                       value="${requestScope.rec.name}">
            </div>
            <button type="submit" class="btn btn-primary">Submit</button>
        </form>
    </jsp:body>
</t:template>
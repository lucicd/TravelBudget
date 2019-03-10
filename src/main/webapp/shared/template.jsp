<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>${param.title}</title>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link rel="stylesheet" href="../css/uikit.min.css" />
        <script src="../js/uikit.min.js"></script>
        <script src="../js/uikit-icons.min.js"></script>
    </head>
    <body>
        <div class="uk-container uk-container-center uk-margin-top uk-margin-large-bottom">
            <jsp:include page="header.jsp"/>
            <h1>${param.title}</h1>
            <jsp:include page="${param.content}.jsp"/>
            <jsp:include page="footer.jsp"/>
        </div>
    </body>
</html>
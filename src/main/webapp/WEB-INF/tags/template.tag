<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ tag description="Overall Page Template" pageEncoding="UTF-8" %>
<%@ attribute name="title" required="true" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <title>${title}</title>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <meta name="author" content="Drazen Lucic">
        <link rel="stylesheet" href="css/bootstrap.min.css" type="text/css" />
        <link rel="stylesheet" href="css/main.css" type="text/css" />
    </head>
    <body>
        <nav class="navbar navbar-expand-md navbar-dark bg-dark fixed-top">
            <a class="navbar-brand" href="budget-plans">Travel Budget</a>
            <button class="navbar-toggler" 
                    type="button" 
                    data-toggle="collapse" 
                    data-target="#navbarsExampleDefault" 
                    aria-controls="navbarsExampleDefault" 
                    aria-expanded="false" 
                    aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
            </button>
            
            <div class="collapse navbar-collapse" id="navbarsExampleDefault">
                <ul class="navbar-nav mr-auto">
                    <li class="nav-item active">
                        <a class="nav-link" href="./">
                            Home <span class="sr-only">(current)</span>
                        </a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="budget-plans">Budget Plans</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="currencies">Currencies</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="exchange-rates">Exchange Rates</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="categories">Categories</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="settings">Settings</a>
                    </li>
                </ul>
            </div>
        </nav>
        
        <main role="main" class="container">
            <div class="starter-template">
                <jsp:doBody />
            </div>
        </main>
            
        <script src="js/jquery-3.3.1.slim.min.js"></script>
        <script src="js/bootstrap.min.js"></script>
    </body>
</html>
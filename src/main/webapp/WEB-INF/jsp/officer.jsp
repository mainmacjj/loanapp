<%-- 
    Document   : officer
    Created on : Aug 11, 2015, 6:22:53 PM
    Author     : macj7
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html data-ng-app="displayListApp" data-ng-controller="displayListCtrl2">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Customers</title>
        <link href="css/style2.css" rel="stylesheet" type="text/css">
    </head>
    <body>
        <header>
            <h1>Simple Loan Application</h1>
        </header>
        
        <nav>
            <a href="logout.php">Logout</a><br/>
        </nav>
        
        <h2>Welcome, {{ name }}!</h2>
        
        <p data-ng-show="notify">{{ msg }}</p>
        
        <section>
            <table>
                <tr><th>ID</th><th>Customer Name</th>
                    <th>Amount</th><th></th>
                </tr>
                <tr data-ng-repeat="user in users"> 
                    <td>{{ user.id }} </td>
                    <td>{{ user.name }} </td>
                    <td><input type="number" name="amt" step="0.01" min="0" data-ng-model="user.amt_owed"/></td>
                    <td><input type="button" value="create loan" data-ng-click="createLoan({{ $index }} )" /></td>
                </tr>
            </table>
        </section>
        
        <footer>
            Copyright © macj7
        </footer>
        
        <script src="js/libs/angular.js/angular.js"></script>
        <script src="js/displayListApp.js"></script>
        <script src="js/displayListCtrl2.js"></script>
    </body>
</html>
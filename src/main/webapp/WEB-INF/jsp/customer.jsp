<%-- 
    Document   : customer
    Created on : Aug 11, 2015, 6:22:53 PM
    Author     : macj7
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html data-ng-app="displayListApp" data-ng-controller="displayListCtrl">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Loans</title>
        <link href="resources/css/style2.css" rel="stylesheet" type="text/css">
    </head>
    <body>
        <header>
            <h1>Simple Loan Application</h1>
        </header>
        
        <nav>
            <a href="logout">Logout</a><br/>
        </nav>
        
        <h2>Welcome, {{ name }}!</h2>
        
        <p data-ng-show="notify">{{ msg }}</p>
        
        <section>
            <table>
                <tr><th>ID</th><th>Status</th><th>Officer</th><th>Customer</th><th>Balance</th>
                    <th>Amount</th><th></th><th></th><th></th>
                </tr>
                <tr data-ng-repeat="loan in loans"> 
                    <td>{{ loan.loan_no }} </td>
                    <td>{{ loan.loan_status }} </td>
                    <td>{{ loan.officer }} </td>
                    <td>{{ loan.customer }} </td>
                    <td>{{ loan.loan_amt }} </td>
                    <td><input type="number" name="amt" step="0.01" min="0" data-ng-model="loan.amt_repaid"/></td>
                    <td><input type="button" value="create repayment" data-ng-click="createRepayment({{ $index }} )" data-ng-hide="loan.flag1"/></td>
                    <td><input type="button" value="update repayment" data-ng-click="updateRepayment({{ $index }})" data-ng-hide="loan.flag2" /></td>
                    <td><input type="button" value="delete repayment" data-ng-click="deleteRepayment({{ $index }})" data-ng-hide="loan.flag2"/></td>
                </tr>
            </table>
        </section>
        
        <footer>
            Copyright © macj7
        </footer>
        
        <script src="resources/js/libs/angular.js/angular.js"></script>
        <script src="resources/js/displayListApp.js"></script>
        <script src="resources/js/displayListCtrl.js"></script>
    </body>
</html>
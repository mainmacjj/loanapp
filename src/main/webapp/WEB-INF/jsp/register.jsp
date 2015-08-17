<%-- 
    Document   : registered
    Created on : Aug 16, 2015, 4:22:21 PM
    Author     : macj7
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Registered!</title>
        <link href="css/style2.css" rel="stylesheet" type="text/css">
    </head>
    <body>
        <header>
            Simple Loan Application
        </header>
        
        <nav>
            <a href="index.php">Home</a><br/>
        </nav>
        
        <section style="float:none;">
            <div class="notice center">
                ${pcode}            
            </div>
            <p><Strong>Above is your pass code. Please write it down or memorize it quickly and then click 'Home' 
                    where you can log in.<Strong>
            </p>
        </section>
        
        <footer>
            Copyright © macj7
        </footer>
    </body>
</html>

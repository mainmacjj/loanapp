<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Loan App:: Login </title>
        <link href="resources/css/style2.css" rel="stylesheet" type="text/css">
    </head>

    <body>
        <header>
            Simple Loan Application
        </header>
        
        <section style="float:none;">
            <form class="center" action="login" method="post">
                <label>Pass Code:</label>
                <input type="text" name="pcode"/><br/><br/>
                <input type="submit" value="Login" />
                <input type="button" onclick="location.href='signUp';" value="Sign Up" />
            </form>
        </section>
        
        <footer>
            Copyright © macj7
        </footer>
        
    </body>

</html>

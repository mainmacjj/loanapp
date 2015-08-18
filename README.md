# Simple Loan Application

About Project
-------------
loanapp is a basic loan management web application created using Java EE, the Spring Framework,
AngularJS,and Hibernate. The project was a means of getting acquainted with these
technologies and demonstrating what I had learnt by implementing them together.

Specification
-------------
It Should Support:
1. Java and J2EE - http://www.oracle.com/technetwork/java/javaee/overview/index.html
2. Run on Jetty - https://en.wikipedia.org/wiki/Jetty_%28web_server%29
3. Mysql
4. Hibernate for data management - http://hibernate.org/orm/
5. Spring Framework - http://projects.spring.io/spring-framework/
6. AngularJS

The application should have a simple AngularJS web app that reads and writes data to an API. It should support the following objectives:
1. Manage loans records
2. Manage customer records
 



It should support the following functions:
1. Close a loan once the user has repaid all the loan amt
2. Allow the user to make repayments
3. UI Requirements - Customer
 
- Allow customer to register with name and automatically get a code
- Allow customer to login with code
 
- Allow a logged in customer to see all loans with status active
- Allow a logged in customer to select a loan and create a repayment
- Allow a logged in customer to select a loan and update a repayment
- Allow a logged in customer to select a loan and delete a repayment
 
4. UI Requirements - Loan Officer
 
- Allow loan officer to login with code
- Allow a logged in loan officer to see all customers
- Allow a logged in loan officer to select a customer and create a loan, the loan number should be automatic
 
5. Data structure should be 
   loan_officer - {officer_name, officer_id_no}
   customer - {cust_name, cust_id_no}
   loan - {loan_no,loan_status_id,officer_id_no,cust_id_no, loan_amt}
   repayments - {loan_no, amt_repaid,date_repaid}
6. Assume data for loan officer does not change - {"officer_name":"John Doe","officer_id_no":"1234"}
7. Assume loan status of [{"loan_status_id":"1","loan_status_name":"Active"},{"loan_status_id":"2","loan_status_name""Closed"}]   
8. No graphics or animations


It is important that you familiar yourself with these technologies and be able to demonstrate a knowledge of Java and J2EE. Please complete this so as to:
1. Demonstrate your ability to learn the technologies and execute on requirements
2. Demonstrate your ability to create an API
3. Demonstrate your ability to consume an API


Live demo of the project can be found online
---------------------------------------------
At:
http://loanapp-macj7.rhcloud.com/

There is only one loan officer in the database with pass code: 01010101

Also, there are two customers in the sample database, and the pass code for one of these is: 12345678

The details of the above users or the second customer can be found in the sql file in the
database-sample folder.


Instructions to build from source and run in Netbeans
-----------------------------------------------------
1. Create database with sample data by running database-sample/loan_db.sql or an sql file
of your choice.

2. To connect to your MySQL database go to src/main/resources/hibernate.cfg.xml and replace
the hibernate.connection.password property with your root password.

3. The necessary libraries if not available should be downloaded via maven. This can be done by opening loanapp as a Netbeans project and right click dependencies then click download declared dependencies (using netbeans 8.0.2 UI as reference).

4. Then right click and select build with dependencies.

5. After the downloads have finished Run with the server of your choice (my default was Glasfish 4.1). 

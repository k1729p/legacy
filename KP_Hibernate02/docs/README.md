<!DOCTYPE html>
<HTML>
<HEAD>
	<META charset="UTF-8">
</HEAD>
<BODY>
<IMG src="images/ColorScheme.jpg"/>
<H2>KP_Hibernate02 README</H2>
<P>This is a <B>JavaServer Faces</B> web application example.</P>
<HR/>

<H2>Use Cases</H2>
<UL>
  <LI/><B>Company</B>: welcome page
  <LI/><B>Show departments</B>: table view of the company's departments and links to its employees
  <LI/><B>Edit the existing department</B>: edit and update the information about a department
  <LI/><B>Add a new department</B>: add a new department to the company
  <LI/><B>Delete the department</B>: delete the existing department (with its employees) from the company
  <LI/><B>Show employees</B>: table view of selected department employees
  <LI/><B>Edit the existing employee</B>: update and edit the information about an employee
  <LI/><B>Add a new employee</B>: add a new employee to the department
  <LI/><B>Delete the employee</B>: delete an employee from the department
</UL>
<HR/>

<p>
<img src="images/yellowHR-500.png"><br>
<img src="images/yellowSquare.png"> 
<a href="http://htmlpreview.github.io/?https://github.com/k1729p/legacy/blob/main/KP_Hibernate02/docs/apidocs/index.html">
Java API Documentation</a><br>
<img src="images/yellowHR-500.png">
</p>

<H2>Application Design & Implementation</H2>

<H3>Build Process</H3>
<P>
The application uses the build automation tool <B>Apache Maven 3</B>.<BR/>
The application was tested with web servers <B>Tomcat 9</B> and <B>Jetty 9</B>.<BR/>
The application URL: <B>http://localhost:8080/kp_hibernate02/</B>.<BR/>
</P>

<H3>Testing</H3>
<P>The application runs test cases for <B>JUnit 4</B>.

<H3>Database Technology</H3>
<P>The application uses <B>MySQL 5.7</B> relational database for data storage.</P>

<H3>Logging</H3>
<P>The application uses standard java loggers.</P>

<H3>Business / Persistence Layer</H3>
<P>
The Persistence Layer is based on <B>Hibernate 5</B> and <B>MySQL</B> database.<BR/>
It uses <B>Hibernate Annotations</B> and  <B>Hibernate EntityManager</B>.<BR/>
<B>Hibernate EntityManager</B> implements the programming interfaces and lifecycle rules as defined by the <B>JPA 2.1</B> specification.<BR/>
The database connection pool is configured with library <B>C3P0</B>.
</P>

<H3>Presentation Layer</H3>
<P>
The application uses <B>JavaServer Faces 2.2</B> web application framework and <B>Facelets</B> (JSF View Definition Framework).<br/>
<UL>
  <LI/>Location-based breadcrumb website navigation
  <LI/>Templates with Facelets templating framework
  <LI/>Composition components with Facelets tag library
</UL>
</P>

<HR/>
<H2>Database Model Diagram</H2>
<IMG src="images/database_model.jpg"/><BR/>
<HR/>

<H2>Application Screens</H2>
<P><IMG src="images/Company.jpg"/></BR>
Welcome page of the application. Overview of the company.</P>
<P><IMG src="images/ListDepartments.jpg"/><BR/>
Listing all departments.</P>
<P><IMG src="images/EditDepartment.jpg"/><BR/>
Editing the existing department.</P>
<P><IMG src="images/AddDepartment.jpg"/><BR/>
Adding a new department.</P>
<P><IMG src="images/ListEmployees.jpg"/><BR/>
Listing all employees of the selected department.</P>
<P><IMG src="images/EditEmployee.jpg"/><BR/>
Editing the existing employee.</P>
<P><IMG src="images/AddEmployee.jpg"/><BR/>
Adding a new employee.</P>

<a href="#top">Back to the top of the page</a>
<HR/>
</BODY>
</HTML>
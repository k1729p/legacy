<!DOCTYPE html>
<HTML>
<HEAD>
	<META charset="UTF-8">
</HEAD>
<BODY>
<H2>KP_Hibernate01 README</H2>
<P>This is a rich client (fat client) application example.</P>
<HR/>

<H2>Use Cases</H2>
<UL>
<LI/><B>Show summary</B>: tree view of the company's departments and number of its employees grouped by titles
<LI/><B>Show details</B>: table view of the company's departments and its employees
<LI/><B>Update department</B>: edit and update the information about a department
<LI/><B>Add department</B>: add a new department to the company
<LI/><B>Delete department</B>: delete existing department (with its employees) from the company
<LI/><B>Update employee</B>: update and edit the information about an employee
<LI/><B>Add employee</B>: add a new employee to the department
<LI/><B>Delete employee</B>: delete the employee from the department
<LI/><B>Join departments</B>: join two departments moving employees from the second department to the first department
</UL>
<HR/>

<p>
<img src="images/yellowHR-500.png"><br>
<img src="images/yellowSquare.png"> 
<a href="http://htmlpreview.github.io/?https://github.com/k1729p/legacy/blob/main/KP_Hibernate01/docs/apidocs/index.html">
Java API Documentation</a><br>
<img src="images/yellowHR-500.png">
</p>

<H2>Application Design & Implementation</H2>

<H3>Build Process</H3>
<P>The application uses the build automation tool <B>Apache Maven 3</B>.</P>

<H3>Database Technology</H3>
<P>The application uses <B>MySQL 5.7</B> relational database for data storage.</P>

<H3>Logging</H3>
<P>The application uses standard java loggers.

<H3>Business / Persistence Layer</H3>
<P>The Persistence Layer is configured to use the <B>MySQL</B> database with <B>Hibernate 5</B>.</P>

<H3>Presentation Layer</H3>
<P>The application uses Eclipse Standard Widget Toolkit.</P>

<HR/>
<H2>Database Model Diagram</H2>
<IMG src="images/database_model.jpg"/><BR/>
<HR/>

<H2>Application Screens</H2>
<P><IMG src="images/Summary.jpg"/></BR>
Summary Tree</P>
<P><IMG src="images/Details.jpg"/><BR/>
Details Tables</P>
<P><IMG src="images/DepartmentsPopupMenu.jpg"/><BR/>
Departments Popup Menu</P>
<P><IMG src="images/EditDepartment.jpg"/><BR/>
Edit Department Dialog</P>
<P><IMG src="images/EmployeesPopupMenu.jpg"/><BR/>
Employees Popup Menu</P>
<P><IMG src="images/EditEmployee.jpg"/><BR/>
Edit Employee Dialog</P>

<P><IMG src="images/JoinDepartmentsMenu.jpg"/><BR/>
Summary before "Join Departments" Execution<BR>
In the menu selected "Join Departments" menu item</P>
<P><IMG src="images/JoinDepartmentsSummary.jpg"/><BR/>
Summary after "Join Departments" Execution</P>
<P><IMG src="images/JoinDepartmentsDetails.jpg"/><BR/>
Details after "Join Departments" Execution<BR>
Employees table sorted by "Title" column.</P>

<a href="#top">Back to the top of the page</a>
<HR/>
</BODY>
</HTML>
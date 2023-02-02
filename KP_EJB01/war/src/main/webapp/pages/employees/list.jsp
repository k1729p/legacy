<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
   "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
  <%@ include file="/pages/head.jsp" %>

  <body>
    <div class="menu">
      <a href="<s:url value='/company/home'/>">Company</a>
      &#160;&#187;&#160;
      <a href="<s:url value='/departments/list'/>">Departments</a>
      &#160;&#187;&#160;
      Employees
    </div>

	<div class="form" >
      <table><tbody><tr>
        <td class="form-label-name">Department:</td>
        <td><span class="form-label-content">
          <s:property value="%{departmentName}"/>
        </span></td>
      </tr></tbody></table>
    </div>
    
	<div class="list" >
      <table class="list-all" border="1" cellpadding="5px" frame="box" rules="all">
      <thead>
        <tr>
          <th class="list-header">First Name</th>
          <th class="list-header">Last Name</th>
          <th class="list-header">Title</th>
          <th class="list-header"></th>
          <th class="list-header"></th>
        </tr>
      </thead>
      <tbody>
        <s:iterator value="employees">
        <tr class="list-row">
          <td class="list-col"><s:property value="firstName"/></td>
          <td class="list-col"><s:property value="lastName"/></td>
          <td class="list-col"><s:property value="title.name"/></td>
          <td class="list-link-2">
            <s:url action="edit" var="editUrl" escapeAmp="false">
              <s:param name="departmentId" value="departmentId"/>
              <s:param name="employeeId" value="id"/>
            </s:url>
            <a href="<s:property value="#editUrl"/>">Edit</a>
          </td>
          <td class="list-link-3">
            <s:url value="delete" var="deleteUrl" escapeAmp="false">
              <s:param name="departmentId" value="departmentId"/>
              <s:param name="employeeId" value="id"/>
            </s:url>
            <a href="<s:property value="#deleteUrl"/>">Delete</a>
          </td>
        </tr>
        </s:iterator>
      </tbody>
      </table>
    </div>
    
    <div class="links" >
    </div>
      
    <div class="links" >
      <s:url action="edit" var="addUrl">
        <s:param name="departmentId" value="departmentId"/>
      </s:url>
      <p><a href="<s:property value="#addUrl"/>">Add Employee</a></p>
    </div>

    <br/><br/>

    <div class="footer">
    </div>
  </body>
</html>
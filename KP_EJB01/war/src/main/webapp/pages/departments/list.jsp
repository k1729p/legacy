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
      Departments
    </div>

	<div class="list" >
      <table class="list-all" border="1" cellpadding="5px" frame="box" rules="all">
      <thead>
        <tr>
          <th class="list-header">Name</th>
          <th class="list-header"></th>
          <th class="list-header"></th>
          <th class="list-header"></th>
        </tr>
      </thead>
      <tbody>
        <s:iterator value="departments">
        <tr class="list-row">
          <td class="list-col"><s:property value="name"/></td>
          <td class="list-link-1">
            <s:url value="/employees/list" var="listUrl">
              <s:param name="departmentId" value="id"/>
            </s:url>
            <a href="<s:property value="#listUrl"/>">Employees</a>
          </td>
          <td class="list-link-2">
            <s:url action="edit" var="editUrl">
              <s:param name="departmentId" value="id"/>
            </s:url>
            <a href="<s:property value="#editUrl"/>">Edit</a>
          </td>
          <td class="list-link-3">
            <s:url value="delete" var="deleteUrl">
              <s:param name="departmentId" value="id"/>
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
      <s:url action="edit" var="addUrl"/>
      <p><a href="<s:property value="#addUrl"/>">Add Department</a></p>
    </div>
    
    <br/><br/>
    <div class="footer">
    </div>
  </body>
</html>
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
      <s:url value="/employees/list" var="listUrl">
        <s:param name="departmentId" value="%{departmentId}"/>
      </s:url>
      <a href="<s:property value="#listUrl"/>">Employees</a>
      &#160;&#187;&#160;
      <s:if test="employee.id == null">Add</s:if>
      <s:else>Edit</s:else>
    </div>

	<div class="form" >
      <s:actionerror />
      <s:actionmessage />
      
      <table><tbody><tr>
        <td class="form-label-name">Department:</td>
        <td><span class="form-label-content">
          <s:property value="%{departmentName}"/>
        </span></td>
      </tr></tbody></table>
      
      <s:form action="save" method="post">
        <s:hidden name="departmentId" value="%{departmentId}"/>
        <s:hidden name="departmentName" value="%{departmentName}"/>
        <s:hidden name="employee.id" value="%{employee.id}"/>

        <s:textfield label="First Name"
                     name="employee.firstName" value="%{employee.firstName}"
                     cssClass="form-field"/>
        <s:textfield label="Last Name" 
                     name="employee.lastName" value="%{employee.lastName}"
                     cssClass="form-field"/>
                     
        <s:select label="Title" name="titleId"
                  list="%{titles}" listKey="id" listValue="name"
                  value="%{employee.title.id}"
                  cssClass="form-field-select" />
                  
        <s:submit align="center" value="OK"/>
      </s:form>
    </div>
    
    <div class="footer">
    </div>
  </body>
</html>

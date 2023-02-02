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
      <s:if test="department.id == null">Add</s:if>
      <s:else>Edit</s:else>
    </div>

	<div class="form" >
      <s:actionerror />
      <s:actionmessage />
      
      <s:form action="save" method="post">
        <s:hidden name="department.id" value="%{department.id}"/>
        <s:textfield label="Name" 
                     name="department.name" value="%{department.name}"
                     cssClass="form-field" size="30"/>
        <s:submit  align="center" value="OK"/>
      </s:form>
    </div>
    
    <div class="footer">
    </div>
  </body>
</html>


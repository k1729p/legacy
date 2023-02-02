<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="/struts-tags" prefix="s" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
  <head>
    <title>Internal Error</title>
  </head>

  <body>
    <p><a href="<s:url action="company/home"  includeParams="none"/>">Home</a></p>

    <h2>Error Message</h2>
    <s:actionerror />
    <p><s:property value="%{exception.message}"/></p>
    <hr/>

    <h2>Technical Details</h2>
    <p><s:property value="%{exceptionStack}"/></p>
    <hr/>
  </body>
</html>
<%@ include file="/WEB-INF/jsp/includes.jsp" %>
<%@ include file="/WEB-INF/jsp/header.jsp" %>

<div id="header">
  Exception:
</div>
<div id="menu">
    <a href="/kp_spring01/">Home</a>
</div>
<%
Exception ex = (Exception) request.getAttribute("exception");
%>

<h2>Data access failure: <%= ex.getMessage() %></h2>
<p/>

<%
ex.printStackTrace(new java.io.PrintWriter(out));
%>

<p/>
<br/>

<%@ include file="/WEB-INF/jsp/footer.jsp" %>
<!-- /*
 * 08-600 Java and J2EE Programming
 * Homework #9
 * Anjal Patni
 * apatni
 * 9th December 2014
 */ -->
 <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="header.jsp" />
<div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
	<c:forEach items="${errors}" var="error">
		<p style="font-size:medium; color:red">${error}</p>
	</c:forEach>
	
	<p>
	    <a href="manage.jsp">Back to manage your Account</a>
	</p>
</div>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%-- <p style="color: red">
    <c:forEach var="error" items="${errors}">
        ${error}
        <br/>
    </c:forEach>
</p> --%>

<c:forEach var="error" items="${errors}">
	<p class="isa_error">
	<i class="fa fa-times-circle">${error}</i>
	</p>
</c:forEach>

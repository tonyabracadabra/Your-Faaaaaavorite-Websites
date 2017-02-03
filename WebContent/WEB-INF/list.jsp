<%@ page import="databean.FavoriteBean" %>
<%@ page import="databean.UserBean" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:include page="template-top.jsp" />
<div class="login">
<h3>Favorite Websites of ${currUser.firstName} ${currUser.lastName}</h3>
<jsp:include page="error-list.jsp" />


    <table style="color:white">
        <c:forEach var="favoriteBean" items="${favoriteList}">
            <tr>
                <td>URL:</td>
                <td>
                    <a href="list.do?favoriteId=${favoriteBean.favoriteId}">
                    ${favoriteBean.URL}</a>
                </td>
            </tr>
            <tr>
                <td>Comment:</td>
                <td>${favoriteBean.comment}</td>
            </tr>
            <tr>
                <td></td>
                <td>${favoriteBean.clickCount}&nbsp;Clicks</td>
            </tr>
        </c:forEach>
    </table>
</div>
<jsp:include page="template-bottom.jsp"/>

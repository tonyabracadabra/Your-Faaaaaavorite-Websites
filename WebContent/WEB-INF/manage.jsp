<%@page import="java.util.*"%>
<%@page import="databean.UserBean"%>
<%@ page import="databean.FavoriteBean"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<jsp:include page="template-top.jsp" />
<jsp:include page="error-list.jsp" />
<div class="login">
	<form action="manage.do" method="POST">
		<input type="text" name="URL" placeholder="URL" value="${form.URL}" />
		<input type="text" name="comment" placeholder="Comment"
			value="${form.comment}" /> <input type="submit" class="btn"
			name="action" value="Add" class="btn btn-primary btn-block btn-large"/>
	</form>
</div>

<div class="wrapper2" align="center">
	<ol>
		<c:forEach var="favoriteBean" items="${favoriteList}">
			<li>
				<ul>
					<li>
						<form action="delete.do" method="POST">
							<input type="hidden" name="id" value="${favoriteBean.favoriteId}" />
							<input type="submit" class="btn" value="Delete"
								style="width: 200px;" />
						</form>
					</li>
					<a href="list.do?favoriteId=${favoriteBean.favoriteId}">${favoriteBean.URL}
					</a>
					<li>
					<p>${favoriteBean.comment}</p>
					</li>
					<li>
					<p>Clicks count:${favoriteBean.clickCount}</p>
					</li>
				</ul>
			</li>
		</c:forEach>
	</ol>
</div>



<jsp:include page="template-bottom.jsp" />

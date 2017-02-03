<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!doctype html>
<html>
<head>
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="pragma" content="no-cache">
<!-- <link rel="stylesheet" type="text/css" href="css/buttons.css">-->
 
<link rel="stylesheet" type="text/css" href="css/info.css">
<link rel="stylesheet" type="text/css" href="css/loginstyle.css">

<title>Your Favorite Websites - Xupeng Tong</title>

</head>

<body>
	<%@ page import="databean.UserBean"%>

	<h2>Your Favorite Websites</h2>
	<table cellpadding="4" cellspacing="0">

		<!-- Spacer row -->
		<tr>
			<td style="font-size: 5px">&nbsp;</td>
			<td colspan="2" style="font-size: 5px">&nbsp;</td>
		</tr>

		<tr>
			<td valign="top" height="500">
				<!-- Navigation bar is one table cell down the left side -->
				<p align="left">
					<c:choose>
						<c:when test="${ (empty user) }">
							<span class="menu-item"><a href="login.do">Login</a></span>
							<br />
							<span class="menu-item"><a href="register.do">Register</a></span>
							<br />
						</c:when>
						<c:otherwise>
							<span class="menu-head">${user.firstName} ${user.lastName}</span>
							<br />
							<span class="menu-item"><a href="manage.do">Manage
									Your Favorite Websites</a></span>
							<br />
							<span class="menu-item"><a href="changepassword.do">Change
									Your Password</a></span>
							<br />
							<span class="menu-item"><a href="logout.do">Logout</a></span>
							<br />
						</c:otherwise>
					</c:choose>
					<span class="menu-item">&nbsp;</span><br /> <span class="menu-head"><font
						color="white">Favorite Lists: </font></span><br />
					<c:forEach var="u" items="${userList}">
						<span class="menu-item"> <a
							href="list.do?emailAddress=${u.emailAddress}"> ${u.firstName}
								${u.lastName} </a>
						</span>
						<br />
					</c:forEach>
				</p>
			</td>
			<td>
				<!-- Padding (blank space) between navbar and content -->
			</td>
			<td valign="top">
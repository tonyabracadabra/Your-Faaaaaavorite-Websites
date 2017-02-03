<jsp:include page="template-top.jsp" />

<div class="login">
	<h1>Change Password</h1>
	<form action="changepassword.do" method="POST">
		<input type="password" name="newPassword"
			placeholder="Enter Your New Password" value="" /> <input
			type="password" name="confirmPassword" placeholder="Confirm Your Password"
			value="" /> 
	<input type="submit" name="action" value="Change" class="btn btn-primary btn-block btn-large"/>
	</form>
</div>

<jsp:include page="error-list.jsp" />

<jsp:include page="template-bottom.jsp" />

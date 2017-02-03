<jsp:include page="template-top.jsp" />
<jsp:include page="error-list.jsp" />

<div class="login">
	<h1>Login</h1>
	<form action="login.do" method="POST">
		<input type="text" name="emailAddress" placeholder="Email Address"
			value="${form.emailAddress}" /> <input type="password"
			name="password" placeholder="Password" /> <a href="login.do">
			<button type="submit" name="button"
				class="btn btn-primary btn-block btn-large">Login</button>
		</a>
	</form>
	<a href="register.do">
		<p>Register now</p>
	</a>
</div>

<jsp:include page="template-bottom.jsp" />
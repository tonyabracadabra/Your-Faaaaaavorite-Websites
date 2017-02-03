<jsp:include page="template-top.jsp" />
<jsp:include page="error-list.jsp" />

<div class="login" style="height: 400px;">
	<h1>Registration</h1>
	<form action="register.do" method="POST">
		<input type="text" name="emailAddress" placeholder="Email Address"
			value="${form.emailAddress}" /> <input type="text" name="firstName"
			placeholder="First Name" value="${form.firstName}" /> <input
			type="text" name="lastName" placeholder="Last Name"
			value="${form.lastName}" /> <input type="password" name="password"
			placeholder="Password" /> <input type="password"
			name="confirmPassword" placeholder="Confirm Password" /> <a
			href="login.do">
			<button type="submit" name="button"
				class="btn btn-primary btn-block btn-large" value="Register">
				Register</button>
		</a>
	</form>
</div>

<jsp:include page="template-bottom.jsp" />

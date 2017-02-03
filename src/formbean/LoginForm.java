/**
 * J2EE 08672
 *
 * @author Xupeng Tong
 * Date:   2016/12/14
 */

package formbean;

import org.mybeans.form.FormBean;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginForm extends FormBean {
	final String sqlRegex = "^(INSERT INTO|UPDATE|SELECT|WITH|DELETE)(?:[^;']|(?:'[^']+'))+;\\s*$";
	final Pattern sqlPattern = Pattern.compile(sqlRegex, Pattern.MULTILINE | Pattern.DOTALL);

	private String emailAddress;
	private String password;
	private String action;

	public String getEmailAddress() {
		return emailAddress;
	}

	public String getPassword() {
		return password;
	}

	public String getAction() {
		return action;
	}

	/**
	 * Set fields
	 */
	public void setEmailAddress(String s) {
		if (s == null) {
			s = "";
		}
		this.emailAddress = s.trim();
	}

	public void setPassword(String s) {
		if (s == null) {
			s = "";
		}
		this.password = s.trim();
	}

	public void setAction(String s) {
		if (s == null) {
			s = "";
		}
		this.action = s.trim();
	}

	/**
	 * Validate email address with regex
	 * 
	 * @param enteredEmail
	 * @return
	 */
	public static boolean isValidEmail(String enteredEmail) {
		String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
				+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

		Pattern pattern = Pattern.compile(EMAIL_PATTERN);
		Matcher matcher = pattern.matcher(enteredEmail);
		return ((!enteredEmail.isEmpty()) && (enteredEmail != null) && (matcher.matches()));
	}

	public List<String> getValidationErrors() {
		List<String> errors = new ArrayList<String>();

		if (emailAddress == null || emailAddress.length() == 0) {
			errors.add("Email address is required");
			emailAddress = "";
		} else {
			if (!isValidEmail(emailAddress)) {
				errors.add("Invalid email address.");
			}
		}

		if (password == null || password.length() == 0) {
			errors.add("Password is required");
			password = "";
		}
		
		if (sqlPattern.matcher(emailAddress).matches()) {
			errors.add("Email may not contain SQL");
		}

		if (emailAddress.matches(".*[<>\"].*")) {
			errors.add("Email address may not contain angle brackets or quotes");
		}
		
		if (sqlPattern.matcher(password).matches()) {
			errors.add("Password may not contain SQL");
		}
		
		if (password.matches(".*[<>\"].*")) {
			errors.add("Password may not contain angle brackets or quotes");
		}

		return errors;
	}
}

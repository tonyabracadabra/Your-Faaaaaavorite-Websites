/**
 * J2EE 08672
 *
 * @author Xupeng Tong
 * Date:   2016/12/14
 */

package formbean;

import java.util.List;
import java.util.regex.Pattern;

public class RegisterForm extends LoginForm {
	final String sqlRegex = "^(INSERT INTO|UPDATE|SELECT|WITH|DELETE)(?:[^;']|(?:'[^']+'))+;\\s*$";
	final Pattern sqlPattern = Pattern.compile(sqlRegex, Pattern.MULTILINE | Pattern.DOTALL);

	private String firstName;
	private String lastName;
	private String confirmPassword;

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setFirstName(String firstName) {
		if (firstName != null) {
			this.firstName = firstName.trim();
		} else {
			this.firstName = "";
		}
	}

	public void setLastName(String lastName) {
		if (lastName != null) {
			this.lastName = lastName.trim();
		} else {
			this.lastName = "";
		}
	}

	public void setConfirmPassword(String confirmPassword) {
		if (confirmPassword != null) {
			this.confirmPassword = confirmPassword.trim();
		} else {
			this.confirmPassword = "";
		}
	}

	/**
	 * Validataion
	 */
	@Override
	public List<String> getValidationErrors() {
		List<String> errors = super.getValidationErrors();

		System.out.println(firstName);
		System.out.println(lastName);
		System.out.println(super.getEmailAddress());
		System.out.println(super.getPassword());
		System.out.println(super.getAction());

		// Validate fields null or zero length
		if (firstName == null || firstName.length() == 0) {
			errors.add("First name is required.");
			firstName = "";
		}
		
		if (lastName == null || lastName.length() == 0) {
			errors.add("Last name is required.");
			lastName = "";
		}
		
		if (confirmPassword == null || confirmPassword.length() == 0) {
			errors.add("Please confirm password");
			confirmPassword = "";
		}
		
		if (sqlPattern.matcher(firstName).matches()) {
			errors.add("First name may not contain SQL");
		}

		if (firstName.matches(".*[<>\"].*")) {
			errors.add("First name may not contain angle brackets or quotes");
		}
		
		if (sqlPattern.matcher(lastName).matches()) {
			errors.add("Last name may not contain SQL");
		}
		
		if (lastName.matches(".*[<>\"].*")) {
			errors.add("Last name may not contain angle brackets or quotes");
		}
		
		if (sqlPattern.matcher(confirmPassword).matches()) {
			errors.add("Email may not contain SQL");
		}
		
		if (confirmPassword.matches(".*[<>\"].*")) {
			errors.add("Password may not contain angle brackets or quotes");
		}

		if (!super.getPassword().equals(confirmPassword)) {
			errors.add("Password and confirm password should be the same");
		}
		return errors;
	}
}

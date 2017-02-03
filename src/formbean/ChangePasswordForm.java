/**
 * J2EE 08672
 *
 * @author Xupeng Tong
 * Date:   2016/12/14
 */

package formbean;

import java.util.ArrayList;
import java.util.List;

import org.mybeans.form.FormBean;

public class ChangePasswordForm extends FormBean {
    private String confirmPassword = "";
	private String newPassword = "";
	
	public String getConfirmPassword() {
		return confirmPassword;
	}

	public String getNewPassword() {
		return newPassword;
	}
	
	public void setConfirmPassword(String s) {
		confirmPassword = s.trim();
	}

	public void setNewPassword(String s) {
		newPassword = s.trim();
	}

	public List<String> getValidationErrors() {
		List<String> errors = new ArrayList<String>();

		if (newPassword == null ||  newPassword.length() == 0) {
			errors.add("New password is required");
		}

		if (confirmPassword == null || confirmPassword.length() == 0) {
			errors.add("Confirm password is required");
		}

		if (errors.size() > 0)
			return errors;

		if (!newPassword.equals(confirmPassword)) {
			errors.add("New password and Confirm password do not match");
		}
		
		if (newPassword.matches(".*[<>\"].*")) {
        	errors.add("Password may not contain angle brackets or quotes");
        }
		
        if (confirmPassword.matches(".*[<>\"].*")) {
        	errors.add("Confirmed password may not contain angle brackets or quotes");
        }
        
        if (!newPassword.equals(confirmPassword)) {
        	errors.add("Two passwords don't match");
        }

		return errors;
	}
}

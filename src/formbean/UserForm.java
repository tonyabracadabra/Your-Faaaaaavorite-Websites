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

public class UserForm extends FormBean {
	private String emailAddress = "";
	
	public String getEmailAddress()  {
		return this.emailAddress;
	}
	
	public void setEmailAddress(String s) {
		if (emailAddress == null) {
			emailAddress = "";
		}
		emailAddress = trimAndConvert(s,"<>>\"]");
	}

	public List<String> getValidationErrors() {
		List<String> errors = new ArrayList<String>();

		if (emailAddress == null || emailAddress.length() == 0) {
			errors.add("User Name is required");
			emailAddress = "";
		}
		
		return errors;
	}
}

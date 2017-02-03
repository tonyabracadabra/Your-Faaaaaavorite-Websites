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

public class IdForm extends FormBean {
	private String id;

	public String getId() {
		return id;
	}
	
	public int getIdAsInt() {
		try {
			return Integer.parseInt(id);
		} catch (NumberFormatException e) {
			return -1;
		}
	}
	public void setId(String id) {
		this.id = id;
	}

	public List<String> getValidationErrors() {
		List<String> errors = new ArrayList<String>();
		
		if (id == null || id.length() == 0) {
			errors.add("Id is required");
			id = "";
			return errors;
		}

		try {
			Integer.parseInt(id);
		} catch (NumberFormatException e) {
			errors.add("Id is not an integer");
		}
		
		return errors;
	}
}

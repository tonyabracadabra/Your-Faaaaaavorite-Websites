/**
 * J2EE 08672
 *
 * @author Xupeng Tong
 * Date:   2016/12/14
 */

package formbean;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.mybeans.form.FormBean;

public class FavoriteForm extends FormBean {
	final String sqlRegex = "^(INSERT INTO|UPDATE|SELECT|WITH|DELETE)(?:[^;']|(?:'[^']+'))+;\\s*$";
	final Pattern sqlPattern = Pattern.compile(sqlRegex, Pattern.MULTILINE | Pattern.DOTALL);

	private String URL;
	private String comment;
	private String action;

	public String getURL() {
		return URL;
	}

	public String getComment() {
		return comment;
	}

	public String getAction() {
		return action;
	}

	public void setURL(String s) {
		if (s == null) {
			s = "";
		}
		this.URL = s.trim();
	}

	public void setComment(String s) {
		if (s == null) {
			s = "";
		}
		this.comment = s.trim();
	}

	public void setAction(String s) {
		if (s == null) {
			s = "";
		}
		this.action = s.trim();
	}

	public List<String> getValidationErrors() {
		List<String> errors = new ArrayList<String>();

		System.out.println("URL: " + URL);
		if (URL == null || URL.length() == 0) {
			errors.add("URL is required");
			URL = "";
		}
		if (comment == null) {
			comment = "";
		}
		if (action == null) {
			action = "";
		}

		if (errors.size() > 0) {
			return errors;
		}

		if (sqlPattern.matcher(URL).matches()) {
			errors.add("URL may not contain SQL");
		}
			
		if (URL.matches(".*[<>\"].*")) {
			errors.add("URL may not contain angle brackets or quotes");
		}
		
		if (sqlPattern.matcher(comment).matches()) {
			errors.add("Comment may not contain SQL");
		}
		
		if (comment.matches(".*[<>\"].*")) {
			errors.add("Comment may not contain angle brackets or quotes");
		}
		
		return errors;
	}
}

/**
 * J2EE 08672
 *
 * @author Xupeng Tong
 * Date:   2016/12/14
 */

package databean;

import org.genericdao.PrimaryKey;

@PrimaryKey("userId")
public class UserBean {
	private int userId;
	
	private String emailAddress;
	private String firstName;
	private String lastName;
	private String password;
	
	// Default constructor
	public UserBean() { }
	
	// The constructor I use
	public UserBean(String emailAddress, String firstName, String lastName, String password) {
		this.emailAddress = emailAddress;
		this.firstName = firstName;
		this.lastName = lastName;
		this.password = password;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}

/**
 * J2EE 08672
 *
 * @author Xupeng Tong
 * Date:   2016/12/14
 */

package model;

import java.util.Arrays;
import java.util.List;

import org.genericdao.ConnectionPool;
import org.genericdao.DAOException;
import org.genericdao.GenericDAO;
import org.genericdao.MatchArg;
import org.genericdao.RollbackException;

import databean.UserBean;

public class UserDAO extends GenericDAO<UserBean> {
	
    public UserDAO(ConnectionPool cp, String tableName) throws DAOException {
        super(UserBean.class, tableName, cp);
    }
    
    
    public UserBean read(String emailAddress) throws RollbackException {
    	System.out.println(emailAddress);
    	UserBean[] selectedBeans = match(MatchArg.equals("emailAddress", emailAddress));
    	UserBean selectedBean;
    	if (selectedBeans == null || selectedBeans.length == 0) {
    		selectedBean = null;
    	} else {
    		selectedBean = selectedBeans[0];
    	}
    	
    	return selectedBean;
    }

    /**
     * Get all users at once
     * 
     * @return
     */
	public List<UserBean> getUserList() {
		try {
			List<UserBean> allUsers = Arrays.asList(match());
			return allUsers;
		} catch(RollbackException e) {
			// Do nothing here, check back later
		} 
		
		return null;
	}
    
	/**
     * Change password
	 * @throws RollbackException 
     * */
    public void changePassword(String emailAddress, String newPassword) throws RollbackException {    	
    	UserBean[] selectedBeans = match(MatchArg.equals("emailAddress", emailAddress));
    	
    	UserBean selectedBean;
    	if (selectedBeans == null || selectedBeans.length == 0) {
    		selectedBean = null;
    		// check here back
    		return;
    	} else {
    		selectedBean = selectedBeans[0];
    	}
    	
    	selectedBean.setPassword(newPassword);
    	
    	update(selectedBean);
    }
	
}

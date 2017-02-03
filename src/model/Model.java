/**
 * J2EE 08672
 *
 * @author Xupeng Tong
 * Date:   2016/12/14
 */

package model;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;

import org.genericdao.ConnectionPool;
import org.genericdao.DAOException;
import org.genericdao.RollbackException;

import databean.FavoriteBean;
import databean.UserBean;

public class Model {
	private FavoriteDAO favoriteDAO;
	private UserDAO userDAO;

	public Model(ServletConfig config) throws ServletException {
		try {
			String jdbcDriver = "com.mysql.jdbc.Driver";
			String jdbcURL = "jdbc:mysql://localhost/test?useSSL=false";

			ConnectionPool pool = new ConnectionPool(jdbcDriver, jdbcURL);

			userDAO = new UserDAO(pool, "xtong_user");
			favoriteDAO = new FavoriteDAO(pool, "xtong_favorite");
		} catch (DAOException e) {
			throw new ServletException(e);
		}
	}
	
	public UserDAO getUserDAO() {
		return userDAO;
	}
	
	public FavoriteDAO getFavoriteDAO() {
        return favoriteDAO;
    }
    
    public int createUser(String emailAddress, String firstName, String lastName, String password) throws RollbackException {
    	UserBean user = new UserBean(emailAddress, firstName, lastName, password);
        userDAO.create(user);
        
        return userDAO.read(emailAddress).getUserId();
    }
    
    public void createFavorite(int userId, String URL, String comment, int clickCount) throws RollbackException {
    	FavoriteBean favorite = new FavoriteBean();
    	favorite.setURL(URL);
    	favorite.setComment(comment);
    	favorite.setUserId(userId);
    	favorite.setClickCount(clickCount);
        favoriteDAO.create(favorite);
    }
	
	
}

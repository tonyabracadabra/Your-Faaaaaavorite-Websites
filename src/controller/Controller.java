/**
 * J2EE 08672
 *
 * @author Xupeng Tong
 * Date:   2016/12/14
 */

package controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.genericdao.RollbackException;

import databean.FavoriteBean;
import databean.UserBean;
import model.FavoriteDAO;
import model.Model;
import model.UserDAO;

public class Controller extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Model model;

	/**
	 * Add all actions
	 */
	public void init() throws ServletException {		
		Model model = new Model(getServletConfig());
		this.model = model;
		Action.add(new LoginAction(model));
        Action.add(new LogoutAction(model));
        Action.add(new RegisterAction(model));
        Action.add(new DeleteAction(model));
        Action.add(new ManageAction(model));
        Action.add(new ListAction(model));
        Action.add(new ChangePasswordAction(model));
        
        FavoriteDAO favoriteDAO = model.getFavoriteDAO();
        UserDAO userDAO = model.getUserDAO();
    	try {
			if (userDAO.getCount() == 0 && favoriteDAO.getCount() == 0) {
				initializeDatabase();
			}
		} catch (RollbackException e) {
			throw new ServletException();
		}
	}
	
	private void initializeDatabase() throws RollbackException {
		int user1id = createUser("user1@gmail.com", "User", "One", "password1");
		int user2id = createUser("user2@gmail.com", "User", "Two", "password2");
		int user3id = createUser("user3@gmail.com", "User", "Three", "password3");

		createFavorite(user1id);
		createFavorite(user2id);
		createFavorite(user3id);
	}
	
	private int createUser(String emailAddress, String firstName, String lastName, String password) throws RollbackException {
		UserDAO userDAO = model.getUserDAO();
		UserBean user = new UserBean(emailAddress, firstName, lastName, password);
		userDAO.create(user);
        return userDAO.read(emailAddress).getUserId();
    }
    
    private void createFavorite(int userId) throws RollbackException {    	
        FavoriteBean favorite1 = new FavoriteBean(userId, "www.facebook.com", "Website of Facebook");
        FavoriteBean favorite2 = new FavoriteBean(userId, "www.linkedin.com", "Website of Linkedin");
        FavoriteBean favorite3 = new FavoriteBean(userId, "www.apple.com", "Website of Apple");
        FavoriteBean favorite4 = new FavoriteBean(userId, "www.google.com", "Website of Google");
        
        model.getFavoriteDAO().create(favorite1);
        model.getFavoriteDAO().create(favorite2);
        model.getFavoriteDAO().create(favorite3);
        model.getFavoriteDAO().create(favorite4);
    }

	public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String nextPage = performTheAction(request);
        sendToNextPage(nextPage, request, response);
    }
	
	/*
     * Extracts the requested action and (depending on whether the user is
     * logged in) perform it (or make the user login).
     * @param request
     * @return the next page (the view)
     */
    private String performTheAction(HttpServletRequest request) {    	
        HttpSession session = request.getSession(true);
        String servletPath = request.getServletPath();
        UserBean user = (UserBean) session.getAttribute("user");
        String action = getActionName(servletPath);
                
        if (action.equals("list.do")) {
        	return Action.perform(action, request);
        } else if (user == null && action.equals("register.do")) {
        	return Action.perform(action, request);
        } else if (user == null) { 
        	return Action.perform("login.do", request);
        } else {
            return Action.perform(action, request);
        }
    }

    /*
     * If nextPage is null, send back 404 If nextPage ends with ".do", redirect
     * to this page. If nextPage ends with ".jsp", dispatch (forward) to the
     * page (the view) This is the common case
     */
    private void sendToNextPage(String nextPage, HttpServletRequest request,
            HttpServletResponse response) throws IOException, ServletException {    	
    	System.out.println("nextPage: " + nextPage);
    	
        if (nextPage == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND,
                    request.getServletPath());
            return;
        }

        if (nextPage.endsWith(".do")) {
            response.sendRedirect(nextPage);
            return;
        }

        if (nextPage.endsWith(".jsp")) {
            RequestDispatcher d = request.getRequestDispatcher("WEB-INF/"
                    + nextPage);
            d.forward(request, response);
            return;
        }
        
        if (nextPage.startsWith("http") || nextPage.startsWith("HTTP")) {
            response.sendRedirect(nextPage);
            return;
        }
        
        if (!(nextPage.startsWith("http") && nextPage.startsWith("HTTP"))) {
            response.sendRedirect("http://" + nextPage);
            return;
        }

        throw new ServletException(Controller.class.getName()
                + ".sendToNextPage(\"" + nextPage + "\"): invalid extension.");
    }

    /*
     * Returns the path component after the last slash removing any "extension"
     * if present.
     */
    private String getActionName(String path) {
    	System.out.println("path: " + path);
        // We're guaranteed that the path will start with a slash
        int slash = path.lastIndexOf('/');
        return path.substring(slash + 1);
    }
}

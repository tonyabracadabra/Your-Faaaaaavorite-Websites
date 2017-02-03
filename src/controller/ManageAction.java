/**
 * J2EE 08672
 *
 * @author Xupeng Tong
 * Date:   2016/12/14
 */

package controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.genericdao.RollbackException;
import org.mybeans.form.FormBeanException;
import org.mybeans.form.FormBeanFactory;

import databean.FavoriteBean;
import databean.UserBean;
import formbean.FavoriteForm;
import model.FavoriteDAO;
import model.Model;
import model.UserDAO;

public class ManageAction extends Action {
	private FormBeanFactory<FavoriteForm> formBeanFactory = FormBeanFactory.getInstance(FavoriteForm.class);
	private UserDAO userDAO;
	private FavoriteDAO favoriteDAO;

    public ManageAction(Model model) {
    	userDAO = model.getUserDAO();
    	favoriteDAO = model.getFavoriteDAO();
    }

    public String getName() {
        return "manage.do";
    }

    public String perform(HttpServletRequest request) {    	
        HttpSession session = request.getSession();

        // If user hasn't logged in, go back to login
        if (session.getAttribute("user") == null) {
            return "login.do";
        }

        List<String> errors = new ArrayList<String>();
        request.setAttribute("errors", errors);

        try {
            FavoriteForm form = formBeanFactory.create(request);
            String URL = request.getParameter("URL");
            System.out.println(URL);
            form.setURL(URL);
            request.setAttribute("form", form);
            request.setAttribute("userList", userDAO.getUserList());
            
            String action = request.getParameter("action");
            
            // Get the user from session
            UserBean user = (UserBean) request.getSession().getAttribute("user");
            request.setAttribute("favoriteList", favoriteDAO.getUserFavorites(user.getUserId()));
                        
            // If clicked, add one
            String favoriteIdS = request.getParameter("favoriteId");
            String nextURL = null;
            if (favoriteIdS != null) {
            	int favoriteId = Integer.parseInt(favoriteIdS);
            	nextURL = favoriteDAO.read(favoriteId).getURL();
                favoriteDAO.incrementClickCount(favoriteId);
                return nextURL;
            }
            
            if (!form.isPresent()) {
                return "manage.jsp";
            }

            // Any validation errors?
            errors.addAll(form.getValidationErrors());
            if (errors.size() != 0) {
                return "manage.jsp";
            }
            
            if (action.equals("Add")) {
            	// Create new favorite bean
                FavoriteBean favoriteBean = new FavoriteBean(user.getUserId(), form.getURL(), form.getComment());
            	favoriteDAO.create(favoriteBean);
            }
                        
            request.setAttribute("favoriteList", favoriteDAO.getUserFavorites(user.getUserId()));        

            return "manage.jsp";
        } catch (RollbackException e) {
            errors.add(e.getMessage());
            e.printStackTrace();
            return "error.jsp";
        } catch (FormBeanException e) {
        	errors.add(e.getMessage());
            e.printStackTrace();
            return "error.jsp";
        }
    }
}

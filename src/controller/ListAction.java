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

import org.genericdao.RollbackException;
import org.mybeans.form.FormBeanException;
import org.mybeans.form.FormBeanFactory;

import databean.FavoriteBean;
import databean.UserBean;
import formbean.UserForm;
import model.FavoriteDAO;
import model.Model;
import model.UserDAO;

public class ListAction extends Action {
	private FormBeanFactory<UserForm> formBeanFactory = FormBeanFactory.getInstance(UserForm.class);
	private UserDAO userDAO;
	private FavoriteDAO favoriteDAO;

    public ListAction(Model model) {
    	userDAO = model.getUserDAO();
    	favoriteDAO = model.getFavoriteDAO();
    }

    public String getName() {
        return "list.do";
    }

    public String perform(HttpServletRequest request) {    
        List<String> errors = new ArrayList<String>();
        request.setAttribute("errors", errors);

        try {
        	UserForm form = formBeanFactory.create(request);
            request.setAttribute("form", form);
            request.setAttribute("userList", userDAO.getUserList());
                                    
            String favoriteId = request.getParameter("favoriteId");
            String nextURL = null;
            if (favoriteId != null) {
            	int id = Integer.parseInt(favoriteId);
            	nextURL = favoriteDAO.read(id).getURL();
                favoriteDAO.incrementClickCount(id);
                return nextURL;
            }
            
            String emailAddress = request.getParameter("emailAddress");
            UserBean user = userDAO.read(emailAddress);
            if (user != null) {
                request.setAttribute("currUser", user);
            }

            FavoriteBean[] favoriteList = favoriteDAO.getUserFavorites(user.getUserId());
	        request.setAttribute("favoriteList", favoriteList);
	        
            return "list.jsp";
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

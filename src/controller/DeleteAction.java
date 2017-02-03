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
import formbean.IdForm;
import model.FavoriteDAO;
import model.Model;
import model.UserDAO;


public class DeleteAction extends Action {
	private FormBeanFactory<IdForm> formBeanFactory = FormBeanFactory.getInstance(IdForm.class);
	private UserDAO userDAO;
	private FavoriteDAO favoriteDAO;

    public DeleteAction(Model model) {
    	userDAO = model.getUserDAO();
    	favoriteDAO = model.getFavoriteDAO();
    }

    public String getName() {
        return "delete.do";
    }

    public String perform(HttpServletRequest request) {    	
        HttpSession session = request.getSession();

        if (session.getAttribute("user") == null) {
            return "login.do";
        }

        List<String> errors = new ArrayList<String>();
        request.setAttribute("errors", errors);

        try {
            IdForm idForm = formBeanFactory.create(request);
            request.setAttribute("form", idForm);
            request.setAttribute("userList",userDAO.getUserList());

            errors.addAll(idForm.getValidationErrors());
            if (errors.size() != 0) {
                return "error.jsp";
            }
            
            UserBean user = (UserBean) request.getSession().getAttribute("user");
            int id = idForm.getIdAsInt();
    		favoriteDAO.delete(id);
            
            FavoriteBean[] favoriteList = favoriteDAO.getUserFavorites(user.getUserId());
	        request.setAttribute("favoriteList", favoriteList);
	        
            return "manage.do";
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

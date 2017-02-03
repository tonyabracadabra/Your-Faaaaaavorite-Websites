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
import formbean.LoginForm;
import model.FavoriteDAO;
import model.Model;
import model.UserDAO;

public class LoginAction extends Action {
    private FormBeanFactory<LoginForm> formBeanFactory = FormBeanFactory.getInstance(LoginForm.class);

    private UserDAO userDAO;
    private FavoriteDAO favoriteDAO;

    public LoginAction(Model model) {
        userDAO = model.getUserDAO();
        favoriteDAO = model.getFavoriteDAO();
    }

    public String getName() {
        return "login.do";
    }

    public String perform(HttpServletRequest request) {    	
        HttpSession session = request.getSession();

        // If user is already logged in, redirect to todolist.do
        if (session.getAttribute("user") != null) {
            return "manage.do";
        }

        List<String> errors = new ArrayList<String>();
        request.setAttribute("errors", errors);

        try {
            LoginForm form = formBeanFactory.create(request);
            request.setAttribute("form", form);
            request.setAttribute("userList", userDAO.getUserList());
                        
            System.out.println(form.getAction());
            System.out.println(form.isPresent());

            if (!form.isPresent()) {
                return "login.jsp";
            }

            // Any validation errors?
            errors.addAll(form.getValidationErrors());
            if (errors.size() != 0) {
                return "login.jsp";
            }

            // Look up the user
            UserBean user = null;
            
            // If need to register
            if (form.getAction() != null && form.getAction().equals("Register")) {
            	return "register.do";
            }
            
            user = userDAO.read(form.getEmailAddress());

            if (user == null) {
                errors.add("User does not exist!");
                return "login.jsp";
            }

            // Check the password
            if (!user.getPassword().equals(form.getPassword())) {
                errors.add("Wrong password, please enter your password again!");
                return "login.jsp";
            }

            session.setAttribute("user", user);
            
            FavoriteBean[] favoriteList = favoriteDAO.getUserFavorites(user.getUserId());
            request.setAttribute("favoriteList",favoriteList);
            
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

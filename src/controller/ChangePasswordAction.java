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

import databean.UserBean;
import formbean.ChangePasswordForm;
import model.Model;
import model.UserDAO;

/*
 * Logs out by setting the "user" session attribute to null.
 * (Actions don't be much simpler than this.)
 */
public class ChangePasswordAction extends Action {
	private FormBeanFactory<ChangePasswordForm> formBeanFactory = FormBeanFactory.getInstance(ChangePasswordForm.class);
	private UserDAO userDAO;

    public ChangePasswordAction(Model model) {
    	userDAO = model.getUserDAO();
    }

    public String getName() {
        return "changepassword.do";
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
            ChangePasswordForm cpform = formBeanFactory.create(request);
            request.setAttribute("form", cpform);
            request.setAttribute("userList", userDAO.getUserList());

            // If no params were passed, return with no errors so that the form
            // will be presented (we assume for the first time).
            
            cpform.isPresent();
            if (!cpform.isPresent()) {
                return "changepassword.jsp";
            }

            errors.addAll(cpform.getValidationErrors());
            
            System.out.println(errors);
            if (errors.size() != 0) {
                return "changepassword.jsp";
            }

            // Get the user from session
            UserBean user = (UserBean) request.getSession().getAttribute("user");
            
            userDAO.changePassword(user.getEmailAddress(), cpform.getNewPassword());

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

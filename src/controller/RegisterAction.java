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
import formbean.RegisterForm;
import model.FavoriteDAO;
import model.Model;
import model.UserDAO;

public class RegisterAction extends Action {
	private FormBeanFactory<RegisterForm> formBeanFactory = FormBeanFactory.getInstance(RegisterForm.class);

	private UserDAO userDAO;
	private FavoriteDAO favoriteDAO;

	public RegisterAction(Model model) {
		userDAO = model.getUserDAO();
		favoriteDAO = model.getFavoriteDAO();
	}

	public String getName() {
		return "register.do";
	}

	public String perform(HttpServletRequest request) {
		HttpSession session = request.getSession();

		// Check if the user has already logged in,
		// otherwise ...
		if (session.getAttribute("user") != null) {
			return "manage.do";
		}

		List<String> errors = new ArrayList<String>();
		request.setAttribute("errors", errors);

		try {
			RegisterForm registerForm = formBeanFactory.create(request);
			request.setAttribute("form", registerForm);
			request.setAttribute("userList", userDAO.getUserList());

			if (!registerForm.isPresent()) {
				return "register.jsp";
			}
			
			errors.addAll(registerForm.getValidationErrors());
			System.out.println("Errors" + errors);

			if (errors.size() != 0) {
				return "register.jsp";
			}
			UserBean newUser = null;

			if (registerForm.getAction() != null && registerForm.getAction().equals("Login")) {
				return "login.do";
			}

			// Check existing users
			try {
				UserBean userBean = userDAO.read(registerForm.getEmailAddress());
				if (userBean != null) {
					errors.add("This email has been used!");
					return "register.jsp";
				}
			} catch (RollbackException e) {
				errors.add(e.getMessage());
			}

			newUser = new UserBean(registerForm.getEmailAddress(), registerForm.getFirstName(), 
								   registerForm.getLastName(), registerForm.getPassword());

			try {
				userDAO.create(newUser);
				newUser = userDAO.read(registerForm.getEmailAddress());
				session.setAttribute("user", newUser);

				FavoriteBean[] favoriteList = favoriteDAO.getUserFavorites(newUser.getUserId());
				request.setAttribute("favoriteList", favoriteList);
				return ("manage.do");
			} catch (RollbackException e) {
				errors.add(e.getMessage());
				e.printStackTrace();
				return "manage.jsp";
			}
		} catch (FormBeanException e) {
        	errors.add(e.getMessage());
            e.printStackTrace();
            return "error.jsp";
        }
	}
}
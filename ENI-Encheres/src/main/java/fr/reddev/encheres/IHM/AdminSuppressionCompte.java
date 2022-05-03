package fr.reddev.encheres.IHM;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.reddev.encheres.BLL.Administration;
import fr.reddev.encheres.BLL.UserManager;
import fr.reddev.encheres.Exception.DALException;

/**
 * Servlet implementation class AdminSuppressionCompte
 */
@WebServlet("/AdminSuppressionCompte")
public class AdminSuppressionCompte extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Administration administration = new Administration();
		// AUTHENTIFICATION
		boolean valid = administration.AuthentificationAdmin(request, response);
		try {
			if (valid) {// AUTH-if
				UserManager manager = UserManager.getInstance();
				String id = (String) request.getParameter("id");
				manager.deleteUser(Integer.parseInt(id));
			}else {
				request.getSession().invalidate();
				response.sendRedirect(request.getContextPath() + "/Connexion");
			}
		} catch (NumberFormatException | DALException | SQLException e) {
			e.printStackTrace();
			response.sendRedirect(request.getContextPath() + "/Error500");
		}
		request.setAttribute("titlePage", "Administrateur");
		response.sendRedirect(request.getContextPath()+"/Admin");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}

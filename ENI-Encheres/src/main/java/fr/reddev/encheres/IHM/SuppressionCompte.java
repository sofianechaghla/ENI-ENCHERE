/**
 * PROJET ENI-ENCHERES
 * 
 */
package fr.reddev.encheres.IHM;

/**
 * @author REDDEV
 */
import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import fr.reddev.encheres.BLL.Administration;
import fr.reddev.encheres.BLL.UserManager;
import fr.reddev.encheres.BO.Utilisateur;
import fr.reddev.encheres.Exception.DALException;

/**
 * Supprimer un profil Servlet implementation class SuppresionCompte
 */
@WebServlet("/SuppressionCompte")
public class SuppressionCompte extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * Supprime le profil
	 * 
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// INITIALISATION
		UserManager manager = UserManager.getInstance();
		HttpSession session = request.getSession();
		try {
			// On récupère l'utilisateur stocké dans la session
			Utilisateur utilisateur = (Utilisateur) session.getAttribute("utilisateur");
			// On supprime de la BDD
			manager.deleteUser(utilisateur.getno_utilisateur());
		} catch (DALException | SQLException e) {
			e.printStackTrace();
			response.sendRedirect(request.getContextPath() + "/Error500");
		}
		// On détruit la session
		session.invalidate();
		request.setAttribute("titlePage", "Administrateur");
		response.sendRedirect(request.getContextPath() + "/Admin");
	}

	/**
	 * Aucun post vers cette page
	 * 
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}

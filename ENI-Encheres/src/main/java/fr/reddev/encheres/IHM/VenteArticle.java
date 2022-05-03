/**
 * PROJET ENI-ENCHERES
 * 
 */
package fr.reddev.encheres.IHM;

/**
 * @author REDDEV
 */
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.reddev.encheres.BLL.Articles_vendusManager;
import fr.reddev.encheres.BLL.CategorieManager;
import fr.reddev.encheres.BO.Articles_vendus;
import fr.reddev.encheres.BO.Categorie;
import fr.reddev.encheres.BO.Utilisateur;
import fr.reddev.encheres.Exception.BusinessException;
import fr.reddev.encheres.Exception.DALException;
import fr.reddev.encheres.Exception.MessageConfException;
import fr.reddev.encheres.Exception.CodesMessages.MSG_CONF;

/**
 * Page article - Création d'un article Servlet implementation class
 * VenteArticle
 */
@WebServlet("/VendreUnArticle")
public class VenteArticle extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		List<Categorie> categorie;
		RequestDispatcher rd;

		try {
				categorie = new CategorieManager().getCategories();
				request.setAttribute("categorie", categorie);
		}catch (Exception e) {
			e.printStackTrace();
			response.sendRedirect(request.getContextPath() + "/Error500");
		}
		request.setAttribute("titlePage", "Creer un Article");
		rd = request.getRequestDispatcher("WEB-INF/jsp/pages/VendreUnArticle.jsp");
		rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Articles_vendusManager articleMG = new Articles_vendusManager();
		MessageConfException messages = new MessageConfException();
		Integer no_article = null;
		BusinessException exceptions = new BusinessException();

		try {

				// Utilisateur courant dans la session
				Utilisateur curr_user = (Utilisateur) request.getSession().getAttribute("utilisateur");
				// Nouvelle article à créer
				Articles_vendus newArticle = new Articles_vendus(request.getParameter("name").toLowerCase(),
						request.getParameter("story").toLowerCase(),
						java.sql.Date.valueOf(LocalDate.parse(request.getParameter("date_debut"),
								DateTimeFormatter.ofPattern("yyyy-MM-dd"))),
						java.sql.Date.valueOf(LocalDate.parse(request.getParameter("date_fin"),
								DateTimeFormatter.ofPattern("yyyy-MM-dd"))),
						!request.getParameter("howmuch").equals("") ? Integer.valueOf(request.getParameter("howmuch"))
								: 0,
						null, Integer.valueOf(curr_user.getno_utilisateur()),
						Integer.valueOf(request.getParameter("categorie")), "CR", curr_user.getPseudo());
				// Id de l'article pour le renvoyer dans l'url pour l'affichage
				// Validation de l'article
				exceptions = articleMG.validateArticleVendu(newArticle);
				if (!exceptions.hasErreurs()) {
					// Insère l'article et récupère son id
					no_article = articleMG.creerArticle(newArticle);
					messages.ajouterMessage(MSG_CONF.NEW_ARTICLES);
				} 
	
		} catch (DALException e) {
			e.printStackTrace();
			response.sendRedirect(request.getContextPath() + "/Error500");
		}
		request.setAttribute("listeCodesMessage",messages.getListeCodesMessage()); 
		request.setAttribute("listeCodesErreur", exceptions.getListeCodesErreur());
		request.setAttribute("idArticle", no_article);
		request.setAttribute("titlePage", "Article");
//		request.getRequestDispatcher("WEB-INF/jsp/pages/Article.jsp").forward(request, response);
		response.sendRedirect(request.getContextPath() + "/Article?idArticle=" + no_article);
	}
}

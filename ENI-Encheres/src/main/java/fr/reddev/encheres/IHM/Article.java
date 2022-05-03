/**
 * PROJET ENI-ENCHERES
 * 
 */
package fr.reddev.encheres.IHM;

/**
 * @author REDDEV
 */
import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import fr.reddev.encheres.BLL.Articles_vendusManager;
import fr.reddev.encheres.BLL.CategorieManager;
import fr.reddev.encheres.BLL.EncheresManager;
import fr.reddev.encheres.BLL.UserManager;
import fr.reddev.encheres.BO.Articles_vendus;
import fr.reddev.encheres.BO.Categorie;
import fr.reddev.encheres.BO.Encheres;
import fr.reddev.encheres.BO.Utilisateur;
import fr.reddev.encheres.Exception.BusinessException;
import fr.reddev.encheres.Exception.DALException;
import fr.reddev.encheres.Exception.MessageConfException;
import fr.reddev.encheres.Exception.CodesMessages.MSG_BLL;
import fr.reddev.encheres.Exception.CodesMessages.MSG_CONF;

/**
 * Servlet implementation class Article
 */
@WebServlet("/Article")
public class Article extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
//		request.setAttribute("listeCodesErreur", null);
//		request.setAttribute("listeCodesMessage", null);
		UserManager userMG = new UserManager();
		EncheresManager enchereMG = new EncheresManager();
		List<Categorie> categorie = null;
		Articles_vendus article = null;
		Utilisateur vendeur = null;
		Utilisateur curr_user = null;
		Utilisateur bestEncherisseur = null;
		Encheres curr_enchere = null;
		HttpSession session = request.getSession();
		List<Encheres> catalogueEnchere = null;

			curr_user = (Utilisateur) session.getAttribute("utilisateur");
			if (curr_user != null) {
				try {
					curr_user = userMG.GetUtilisateur(curr_user.getno_utilisateur());
				} catch (DALException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			request.setAttribute("utilisateur", curr_user);
			// LES ENCHERES
			try {
				catalogueEnchere = enchereMG.getAllEncheres();
			} catch (DALException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			request.setAttribute("listeEnchereArticle", catalogueEnchere);
			if (!request.getParameter("idArticle").equals("") && request.getParameter("idArticle") != null) {
				// recuperation de l'article
				try {
					article = new Articles_vendusManager()
							.getArticleById(Integer.parseInt(request.getParameter("idArticle")));
				} catch (NumberFormatException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (DALException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				request.setAttribute("article", article);
				// recuperer la liste des encherer de l'article
				List<Encheres> listeEnchereArticles = new ArrayList<>();
				try {
					listeEnchereArticles = enchereMG.recupereToutesEnchereUtilisation();
				} catch (DALException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				List<Encheres> listeEnchereArticle = new ArrayList<>();
				for (Encheres ench : listeEnchereArticles) {
					if (ench.getNo_article() == article.getNo_article()) {
						listeEnchereArticle.add(ench);
					}
				}
				request.setAttribute("encheresArticle", listeEnchereArticle);
				// Récupération des catégories dans la BDD
				try {
					categorie = new CategorieManager().getCategories();
				} catch (DALException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				request.setAttribute("categorie", categorie);
				// Récupération du vendeur dans la BDD
				try {
					vendeur = userMG.GetUtilisateur(article.getNo_utilisateur());
				} catch (DALException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				request.setAttribute("vendeur", vendeur);
				// Récupération de l'enchere si elle existe
				try {
					curr_enchere = enchereMG.rechercheMaxEnchere(article.getNo_article());
				} catch (DALException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				request.setAttribute("enchere", curr_enchere);
				String etatEnchere = article.getEtat_vente();
				System.out.println(etatEnchere);
				request.setAttribute("etatEnchere", etatEnchere);
				if (curr_enchere != null) {
					try {
						bestEncherisseur = userMG.GetUtilisateur(curr_enchere.getNo_utilisateur());
					} catch (DALException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					request.setAttribute("bestEnchere", bestEncherisseur);
				}
			}
		request.setAttribute("titlePage", "Article");
		request.getSession().setAttribute("idArticle", article.getNo_article());
		RequestDispatcher rd = request.getRequestDispatcher("WEB-INF/jsp/pages/Article.jsp");
		rd.forward(request, response);
//		response.sendRedirect(request.getContextPath()+"/Article?idArticle="+article.getNo_article());
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
//		request.setAttribute("listeCodesErreur", null);
//		request.setAttribute("listeCodesMessage", null);
		BusinessException exceptions = new BusinessException();
		MessageConfException confirmations = new MessageConfException();
		EncheresManager enchereMG = new EncheresManager();
		UserManager userMG = new UserManager();
		Utilisateur encherisseur = new Utilisateur();
		Utilisateur userMAJ = new Utilisateur();
		Integer somme = null;
		Articles_vendus article = null;
		Encheres enchereMax = null;
		Encheres newEnchere = null;
		RequestDispatcher rd;

		try {
			if (!request.getParameter("idArticle").equals("") && request.getParameter("idArticle") != null) {
				// recupération de la somme
				if (!request.getParameter("howmuch").equals("") && request.getParameter("howmuch") != null) {
					somme = Integer.parseInt(request.getParameter("howmuch"));
				} else {
					exceptions.ajouterErreur(MSG_BLL.ENCHERE_NULL_PROPOSITION);
				}
				if (!exceptions.hasErreurs()) {
					// recuperation de l'article
					article = new Articles_vendusManager()
							.getArticleById(Integer.parseInt(request.getParameter("idArticle")));
					// Récupération de l'enchere si elle existe
					enchereMax = enchereMG.rechercheMaxEnchere(article.getNo_article());
					if (enchereMax == null) {
						enchereMax = new Encheres(article.getNo_article(), 0, Date.valueOf(LocalDate.now()), 0);
					}
					// validation
					if (!enchereMG.sommeSuperieurPrixInitial(somme, article.getPrix_initial())) {
						exceptions.ajouterErreur(MSG_BLL.PROPOSITION_INFERIEUR_ENCHERE);
					}
					// Récupération des infos de l'encherisseur
					Utilisateur tmp = (Utilisateur) request.getSession().getAttribute("utilisateur");
					encherisseur = userMG.GetUtilisateur(tmp.getno_utilisateur());
					if (encherisseur == null) {
						System.err.println(
								"Une erreur est intervenue la de la récupération d'info de l'enrichisseur - doPost - l74");
					}
					// Validation de l'enchere
					if (!enchereMG.validerSoldeSuffisant(somme, encherisseur.getCredit())) {
						exceptions.ajouterErreur(MSG_BLL.NOMBRE_DE_POINTS_INSUFFISANT);
					}
					if (!enchereMG.enchereSuperieurAncienne(enchereMax, somme)) {
						exceptions.ajouterErreur(MSG_BLL.PROPOSITION_INFERIEUR_ENCHERE);
					}
					if (!exceptions.hasErreurs()) {
						newEnchere = new Encheres(encherisseur.getno_utilisateur(), article.getNo_article(),
								Date.valueOf(LocalDate.now()), somme);
						// ajouter l'enchere
						if (enchereMG.verifExisteEnchere(newEnchere.getNo_utilisateur(), newEnchere.getNo_article())) {
							enchereMG.miseAjour(newEnchere);
							// credit l'ancien
							enchereMG.recrediterUtilisateur(enchereMax.getMontant_enchere(),
									enchereMax.getNo_utilisateur());
							// debit le nouveau
							userMAJ = enchereMG.debiterUtilisateur(somme, encherisseur.getno_utilisateur());
							confirmations.ajouterMessage(MSG_CONF.ENCHERE_OK);
						} else {
							enchereMG.ajouteEnchere(newEnchere);
							// debit le nouveau
							userMAJ = enchereMG.debiterUtilisateur(somme, encherisseur.getno_utilisateur());
							confirmations.ajouterMessage(MSG_CONF.ENCHERE_OK);
						}
						if (confirmations.hasMessages()) {
							// set dans la requete la liste de messages a la jsp
							request.setAttribute("listesCodesMessage", confirmations.getListeCodesMessage());
						}
						// MAJ de l'utilisateur dans la session
						request.setAttribute("utilisateur", userMAJ);
						request.setAttribute("utilisateur", userMAJ);
						request.setAttribute("bestEnchere", userMAJ);
						request.setAttribute("titlePage", "Article");
						request.setAttribute("idArticle", article.getNo_article());
						request.setAttribute("titlePage", "Article");
					} else {
						// set dans la session la liste d'erreurs a la jsp
						request.setAttribute("listeCodesErreur", exceptions.getListeCodesErreur());
						request.setAttribute("idArticle", article.getNo_article());
						request.setAttribute("titlePage", "Article");
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
//		rd = request.getRequestDispatcher("WEB-INF/jsp/pages/Article.jsp");
//		rd.forward(request, response);
		doGet(request, response);
	}
}

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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.reddev.encheres.BLL.Administration;
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
import fr.reddev.encheres.Exception.CodesMessages.MSG_CONF;

/**
 * Accueil Servlet implementation class Home
 */
@WebServlet({ "/Home", "/" })
public class Home extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// INITIALISATION DES MANAGER
		Articles_vendusManager artMG = new Articles_vendusManager();
		CategorieManager categoriesMG = new CategorieManager();
		UserManager userMG = new UserManager();

		BusinessException exceptions = new BusinessException();


		// LES UTILISATEURS
		try {
			List<Utilisateur> users = null;
			users = userMG.AfficherTousUtilisateurs();
			request.setAttribute("users", users);
		} catch (DALException | SQLException e2) {
			exceptions.ajouterErreur(0);// erreur de mise en place
			e2.printStackTrace();
		}

		// LES CATEGORIES
		try {
			List<Categorie> categorie = null;
			categorie = categoriesMG.getCategories();
			request.setAttribute("categorie", categorie);
		} catch (DALException | SQLException e5) {
			exceptions.ajouterErreur(0);// erreur de mise en place
			e5.printStackTrace();
		}

		// LES ARTICLES ENCHERES OUVERTES
		try {
			List<Articles_vendus> catalogueEncheres = null;
			catalogueEncheres = artMG.getEnchereOuverte(artMG.getCatalogue());
			request.setAttribute("catalogue", catalogueEncheres);
		} catch (DALException | SQLException e) {
			e.printStackTrace();
			response.sendRedirect(request.getContextPath() + "/Error500");
		}

		if (exceptions.hasErreurs()) {
			request.setAttribute("listeCodesErreur", exceptions.getListeCodesErreur());
			response.sendRedirect(request.getContextPath() + "/Error500");
		}
		// REDIRECTION SUR LA JSP HOME
		request.setAttribute("titlePage", "Accueil");
		request.getRequestDispatcher("WEB-INF/jsp/pages/Home.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// INITIALISATION EXCEPTIONS
		BusinessException exceptions = new BusinessException();
		MessageConfException messages = new MessageConfException();

		// INITIALISATION DES MANAGER
		Articles_vendusManager artMG = new Articles_vendusManager();
		CategorieManager categoriesMG = new CategorieManager();
		EncheresManager enchereMG = new EncheresManager();
		UserManager userMG = new UserManager();


		// RECUPERATION DES CHECKBOX
		String[] names = request.getParameterValues("e-achats");
		List<String> list = null;
		if (names != null) {
			list = Arrays.asList(names);
		}
		boolean param3 = false;
		if (names != null) {
			list = Arrays.asList(names);
			if(list.size()!=0) {
				param3 = true;
			}
		}

		// USER CURRENT
		Utilisateur userCurrent = null;
		userCurrent = (Utilisateur) request.getSession().getAttribute("utilisateur");

		// LES UTILISATEURS
		List<Utilisateur> users = null;
		try {
			users = userMG.AfficherTousUtilisateurs();
			request.setAttribute("users", users);
		} catch (Exception e2) {
			e2.printStackTrace();
			response.sendRedirect(request.getContextPath() + "/Error500");
		}

		// LES CATEGORIES
		List<Categorie> categorie = null;
		try {
			categorie = categoriesMG.getCategories();
			request.setAttribute("categorie", categorie);
		} catch (Exception e4) {
			e4.printStackTrace();
			response.sendRedirect(request.getContextPath() + "/Error500");
		}

		// LES ARTICLES
		List<Articles_vendus> catalogue = null;
		try {
			catalogue = artMG.getCatalogue();
			request.setAttribute("catalogue", catalogue);
		} catch (Exception e4) {
			e4.printStackTrace();
			response.sendRedirect(request.getContextPath() + "/Error500");
		}

		// LES ENCHERES
		List<Encheres> catalogueEnchere = null;
		try {
			catalogueEnchere = enchereMG.getAllEncheres();
			request.setAttribute("catalogue", catalogueEnchere);
		} catch (Exception e2) {
			e2.printStackTrace();
			response.sendRedirect(request.getContextPath() + "/Error500");
		}
		List<Articles_vendus> aRetourner = new ArrayList<>();
		if (userCurrent != null && list != null) {
			try {
				/* #####FILTRES CHECHBOX####6 fonctions */
				aRetourner = artMG.getUserAchats(list, catalogue, userCurrent, catalogueEnchere);
			} catch (DALException e) {
				e.printStackTrace();
				response.sendRedirect(request.getContextPath() + "/Error500");
			}
		}
		/* #####FILTRES CLASSIQUES#### */
		List<Articles_vendus> catalogueARenvoyer = new ArrayList<>();
		// Si on reçoit la catégorie "Toutes" et : *1 ou *2
		if (request.getParameter("categorie").equals("Toutes")) {
			// *1 : Si l'utilisateur n'a pas entré de valeur dans la recherche
			if (request.getParameter("search").strip().length() == 0) {
				// Récupération du catalogue d'articles dans la BDD
				if (aRetourner.size() != 0) {
					catalogueARenvoyer = aRetourner;
				} else {
					try {
						if (!param3) {
							for (Articles_vendus article : artMG.getCatalogue()) {
								if (!article.getEtat_vente().equals("TR") && !article.getEtat_vente().equals("RE")) {
									catalogueARenvoyer.add(article);
								}
							}
						} else {
							messages.ajouterMessage(MSG_CONF.NULL_ARTICLES);
						}
					} catch (Exception e) {
						e.printStackTrace();
						response.sendRedirect(request.getContextPath() + "/Error500");
					}
				}
				// *2 : Si l'utilisateur a entré une valeur dans la recherche
			} else {
				// Récupération de tous les articles qui contient la valeur de la recherche
				try {
					if (aRetourner.size() != 0) {
						for (Articles_vendus article : aRetourner) {
							if (article.toString().contains(request.getParameter("search").trim())) {
								catalogueARenvoyer.add(article);
							}
						}
					} else {
						if (!param3) {
							List<Articles_vendus> cataTrie = artMG.getCatalogueByName(request.getParameter("search"));
							for (Articles_vendus article : cataTrie) {
								if (!article.getEtat_vente().equals("TR") && !article.getEtat_vente().equals("RE")) {
									catalogueARenvoyer.add(article);
								}
							}
						}

						messages.ajouterMessage(MSG_CONF.NULL_ARTICLES);
					}
				} catch (DALException e) {
					e.printStackTrace();
					response.sendRedirect(request.getContextPath() + "/Error500");
				}
			}
			// Si on ne reçoit pas la catégorie "Toutes"
			// c'est à dire que l'on va reçevoir n'importe quelles catégories existantes et
			// : *1 et *2 , *3 ou *4
		} else {
			Categorie CategorieRechercher = null;
			try {
				CategorieRechercher = categoriesMG.getCatalogueLibelle(request.getParameter("categorie")).get(0);
				// *1 Récupère la catégorie recherchée
				if (aRetourner.size() != 0) {
					for (Articles_vendus article : aRetourner) {
						if (article.getNo_categorie() == CategorieRechercher.getNo_categorie()) {
							catalogueARenvoyer.add(article);
						}
					}
				} else {
					// *2 Récupère le catalogue avec tous les articles de la catégories demandée
					for (Articles_vendus article : artMG.getCatalogueCategorie(CategorieRechercher.getNo_categorie())) {
						if (!article.getEtat_vente().equals("TR") && !article.getEtat_vente().equals("RE")) {
							catalogueARenvoyer.add(article);
						}
					}
				}
				if (request.getParameter("search").strip().length() != 0) {
					// si param
					if (aRetourner.size() != 0) {
						for (Articles_vendus article : aRetourner)
							if (article.getNo_categorie() == CategorieRechercher.getNo_categorie())
								if (article.toString().contains(request.getParameter("search").trim()))
									catalogueARenvoyer.add(article);
					} else {
						List<Articles_vendus> listTmp = new ArrayList<>();
						for (Articles_vendus article : catalogueARenvoyer) {
							if (article.toString().contains(request.getParameter("search").trim())) {
								listTmp.add(article);
							}
						}
						catalogueARenvoyer = listTmp;
					}
				}

				if (catalogueARenvoyer.size() != 0) {
					messages.ajouterMessage(MSG_CONF.NULL_ARTICLES);
				}
			} catch (DALException e) {
				e.printStackTrace();
				response.sendRedirect(request.getContextPath() + "/Error500");
			}
///######################
		}
		if (exceptions.hasErreurs()) {
			// set dans la requete la liste d'erreurs pour la jsp
			request.setAttribute("listeCodesErreur", exceptions.getListeCodesErreur());
		}
		if (messages.hasMessages()) {
			// set dans la requete la liste d'erreurs pour la jsp
			request.setAttribute("listeCodesMessage",messages.getListeCodesMessage()); 
		}
		request.setAttribute("catalogue", catalogueARenvoyer);
		// Affiche la page Home
		request.setAttribute("titlePage", "Accueil");
		request.getRequestDispatcher("WEB-INF/jsp/pages/Home.jsp").forward(request, response);
	}
}
package fr.reddev.encheres.IHM;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.reddev.encheres.BLL.Administration;
import fr.reddev.encheres.BLL.CategorieManager;
import fr.reddev.encheres.Exception.DALException;

/**
 * Servlet implementation class AdminAjoutCategorie
 */
@WebServlet("/AdminAjoutCategorie")
public class AdminAjoutCategorie extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getSession().invalidate();
		response.sendRedirect(request.getContextPath() + "/Connexion");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//INITIALISATION
		Administration administration = new Administration();
		CategorieManager manager = new CategorieManager();
		//AUTHENTIFICATION
		boolean valid = administration.AuthentificationAdmin(request,response);
		try {
			if(valid) {//AUTH-if
					String cat = (String) request.getParameter("categorie");
					manager.ajouterCategorie(cat);
			}else {
				request.getSession().invalidate();
				response.sendRedirect(request.getContextPath() + "/Connexion");
			}
		} catch (DALException e) {
			e.printStackTrace();
			response.sendRedirect(request.getContextPath() + "/Error500");
		}
		request.setAttribute("titlePage", "Administrateur");
		response.sendRedirect(request.getContextPath()+"/Admin");
	}

}

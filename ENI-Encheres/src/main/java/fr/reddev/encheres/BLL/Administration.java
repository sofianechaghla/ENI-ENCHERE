/**
 * PROJET ENI-ENCHERES
 * 
 */
package fr.reddev.encheres.BLL;

import java.io.IOException;
import java.util.ArrayList;

/**
 * @author REDDEV
 */
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.reddev.encheres.BO.Utilisateur;

public class Administration {

	public Administration() {
		super();
	}

	public boolean Authentification(HttpServletRequest request, HttpServletResponse response) throws IOException {
		Utilisateur userCurrent = null;
		try {
			userCurrent = (Utilisateur) request.getSession().getAttribute("utilisateur");
			if(userCurrent != null) {
//				request.setAttribute("listeCodesErreur",new ArrayList<>());
//				request.setAttribute("listeCodesMessage",new ArrayList<>());
				request.setCharacterEncoding("UTF-8");
			}
		} catch (Exception e) {
			e.printStackTrace();
			response.sendRedirect(request.getContextPath() + "/Error500");
		}
		return userCurrent != null && userCurrent.getActive() ?true : false;
	}
	
	public  void setUp(HttpServletRequest request, HttpServletResponse response) throws IOException{
			try {
				request.setAttribute("listeCodesErreur",new ArrayList<>());
				request.setAttribute("listeCodesMessage",new ArrayList<>());
				request.setCharacterEncoding("UTF-8");
			} catch (Exception e) {
				e.printStackTrace();
				response.sendRedirect(request.getContextPath() + "/Error500");
			}
	}

	public boolean AuthentificationAdmin(HttpServletRequest request, HttpServletResponse response) throws IOException {
		Utilisateur userCurrent = null;
		try {
			userCurrent = (Utilisateur) request.getSession().getAttribute("utilisateur");
			if(userCurrent != null ) {
				request.setAttribute("listeCodesErreur",new ArrayList<>());
				request.setAttribute("listeCodesMessage",new ArrayList<>());
				request.setCharacterEncoding("UTF-8");
			}
		} catch (Exception e) {
			e.printStackTrace();
			response.sendRedirect(request.getContextPath() + "/Error500");
		}
		return userCurrent != null && userCurrent.getAdministrateur() && userCurrent.getActive() ? true : false;
	}

}

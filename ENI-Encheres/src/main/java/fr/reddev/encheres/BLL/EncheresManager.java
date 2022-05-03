/**
 * PROJET ENI-ENCHERES
 * 
 */
package fr.reddev.encheres.BLL;

/**
 * @author REDDEV
 */
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

import fr.reddev.encheres.BO.Articles_vendus;
import fr.reddev.encheres.BO.Encheres;
import fr.reddev.encheres.BO.Utilisateur;
import fr.reddev.encheres.DAL.DAOFactory;
import fr.reddev.encheres.DAL.EncheresDAO;
import fr.reddev.encheres.Exception.BLLException;
import fr.reddev.encheres.Exception.DALException;

public class EncheresManager {

	public List<Encheres> getAllEncheres() throws DALException, SQLException {
		return DAOFactory.getEncheresDAO().selectAll();
	}

	public boolean verifExisteEnchere(Integer no_utilisateur, Integer no_article) throws BLLException, DALException {
		EncheresDAO enchereDao = DAOFactory.getEncheresDAO();
		boolean existe = false;
		Encheres enchere = enchereDao.selectByUser(no_article, no_utilisateur);
		if (enchere != null) {
			existe = true;
		}
		return existe;
	}

	public Utilisateur debiterUtilisateur(int somme, Integer no_utilisateur) throws DALException, SQLException {
		UserManager userMG = new UserManager();
		Utilisateur user = userMG.GetUtilisateur(no_utilisateur);
		user.setCredit(user.getCredit() - somme);
		userMG.modifierProfil(user);
		return user;
	}

	public void recrediterUtilisateur(Integer montant_enchere, Integer no_utilisateur) throws DALException, SQLException {
		UserManager userMG = new UserManager();
		Utilisateur oldUser = userMG.GetUtilisateur(no_utilisateur);
		oldUser.setCredit(oldUser.getCredit() + montant_enchere);
		userMG.modifierProfil(oldUser);
	}

	public boolean validerSoldeSuffisant(Integer somme, Integer credit) {
		return credit > somme ? true : false;
	}

	public boolean sommeSuperieurPrixInitial(Integer somme, Integer prix) {
		return somme >= prix ? true : false;
	}

	public boolean enchereSuperieurAncienne(Encheres curr_enchere, int NouveauMontant) {
		return curr_enchere.getMontant_enchere() == 0 || curr_enchere.getMontant_enchere() < NouveauMontant ? true: false;

	}

	public Encheres rechercheMaxEnchere(Integer no_article) throws DALException {
		return DAOFactory.getEncheresDAO().recuprerMaxEnchere(no_article);
	}

	public boolean etatEnCours(Articles_vendus article) {
		return article.getDate_debut_encheres().before(Date.valueOf(LocalDate.now().plusDays(1)))
				&& article.getDate_fin_encheres().after(Date.valueOf(LocalDate.now())) ? true : false;

	}

	public void miseAjour(Encheres enchere) throws BLLException, SQLException, DALException {
		DAOFactory.getEncheresDAO().update(enchere);

	}

	public void ajouteEnchere(Encheres newEnchere) throws BLLException, DALException, SQLException {
		DAOFactory.getEncheresDAO().insert(newEnchere);
	}

	public boolean etatTerminer(Articles_vendus article) {
		return article.getDate_fin_encheres().before(Date.valueOf(LocalDate.now())) ? true : false;
	}
	
	
	public List<Encheres> recupereToutesEnchereUtilisation() throws DALException {
		return DAOFactory.getEncheresDAO().selectByAuction();
	}
	public List<Encheres> recupereToutesEnchereArticle(int id) throws DALException, SQLException {
		return DAOFactory.getEncheresDAO().selectByIdSpec(id);
	}

}

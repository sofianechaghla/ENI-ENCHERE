/**
 * PROJET ENI-ENCHERES
 * 
 */
package fr.reddev.encheres.DAL;

import java.sql.SQLException;

/**
 * @author REDDEV
 */
import fr.reddev.encheres.BO.Utilisateur;
import fr.reddev.encheres.Exception.DALException;

public interface UtilisateurDAO extends DAO<Utilisateur> {
	public Utilisateur selectByLogin(String login) throws DALException, SQLException;
	public boolean UniquePseudoMail(String pseudo, String email) throws SQLException, DALException;
	void updateActivate(boolean active, int id) throws DALException, SQLException;
	public Utilisateur selectByMail(String email) throws DALException, SQLException;
}

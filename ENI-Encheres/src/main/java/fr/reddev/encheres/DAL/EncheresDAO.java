/**
 * PROJET ENI-ENCHERES
 * 
 */
package fr.reddev.encheres.DAL;

import java.sql.SQLException;
import java.util.List;

/**
 * @author REDDEV
 */

import fr.reddev.encheres.BO.Encheres;
import fr.reddev.encheres.Exception.DALException;

public interface EncheresDAO extends DAO<Encheres> {

	public Encheres recuprerMaxEnchere(int idArticle) throws DALException;

	public Encheres selectByUser(int idArticle, Integer no_utilisateur) throws DALException;

	public List<Encheres>  selectByIdSpec(int id) throws DALException, SQLException;

	public List<Encheres> selectByAuction() throws DALException;
}

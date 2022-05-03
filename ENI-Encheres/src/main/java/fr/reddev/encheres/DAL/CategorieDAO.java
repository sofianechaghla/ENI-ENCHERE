/**
 * PROJET ENI-ENCHERES
 * 
 */
package fr.reddev.encheres.DAL;

/**
 * @author REDDEV
 */
import java.util.List;

import fr.reddev.encheres.BO.Categorie;
import fr.reddev.encheres.Exception.DALException;

public interface CategorieDAO extends DAO<Categorie> {
	public List<Categorie> selectByLibelle(String libelle) throws DALException;
	public List<Categorie> getCatalogue() throws DALException;
	public void insertCategorie(String libelle) throws DALException;
	public void update(String libelle,Integer id) throws DALException;
}
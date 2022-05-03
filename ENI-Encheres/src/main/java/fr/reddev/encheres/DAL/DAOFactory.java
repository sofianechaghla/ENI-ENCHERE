/**
 * PROJET ENI-ENCHERES
 * 
 */
package fr.reddev.encheres.DAL;

/**
 * @author REDDEV
 */
import fr.reddev.encheres.DAL.JDBC.Articles_vendusDaoImpl;
import fr.reddev.encheres.DAL.JDBC.CategorieDaoImpl;
import fr.reddev.encheres.DAL.JDBC.EncheresDaoImpl;
/**
 * @author REDDEV
 */
import fr.reddev.encheres.DAL.JDBC.UtilisateurDaoImpl;

public class DAOFactory {

	public static UtilisateurDAO getUtilisateurDAO() {
		return new UtilisateurDaoImpl();
	}

	public static Articles_vendusDAO getArticles_vendusDAO() {
		return new Articles_vendusDaoImpl();
	}

	public static CategorieDAO getCategorieDAO() {
		return new CategorieDaoImpl();
	}

	public static EncheresDAO getEncheresDAO() {
		return new EncheresDaoImpl();
	}

}

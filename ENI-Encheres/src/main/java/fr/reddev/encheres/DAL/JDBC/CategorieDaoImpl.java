/**
 * PROJET ENI-ENCHERES
 * 
 */
package fr.reddev.encheres.DAL.JDBC;

/**
 * @author REDDEV
 */
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import fr.reddev.encheres.BO.Categorie;
import fr.reddev.encheres.DAL.CategorieDAO;
import fr.reddev.encheres.Exception.DALException;

public class CategorieDaoImpl implements CategorieDAO {

	public static final String SELECT_ALL = "SELECT * FROM CATEGORIES";
	public static final String SELECT_BY_LIBELLE = "SELECT * FROM CATEGORIES WHERE libelle LIKE ?";
	public static final String INSERT_CATEGORIES = "INSERT INTO CATEGORIES VALUES (?)";
	public static final String DELETE_CATEGORIES = "DELETE FROM CATEGORIES WHERE no_categorie = ?";
	public static final String UPDATE_CATEGORIES = "UPDATE CATEGORIES SET libelle = ? WHERE no_categorie = ?";

	public List<Categorie> selectAll() throws DALException {
		List<Categorie> listCategorie = new ArrayList<>();
		Categorie categorie;
		try {
			Connection cnx = JdbcTools.getConnection();
			PreparedStatement pstmt = cnx.prepareStatement(SELECT_ALL);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				categorie = new Categorie(rs.getInt("no_categorie"), rs.getString("libelle"));
				listCategorie.add(categorie);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new DALException("CategorieDaoImpl - selectAll() ");
		}
		return listCategorie;
	}

	public List<Categorie> selectByLibelle(String libelle) throws DALException {
		List<Categorie> listCategorie = new ArrayList<>();
		Categorie categorie;
		try {
			Connection cnx = JdbcTools.getConnection();
			PreparedStatement pstmt = cnx.prepareStatement(SELECT_BY_LIBELLE);
			pstmt.setString(1, libelle);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				categorie = new Categorie(rs.getInt("no_categorie"), rs.getString("libelle"));
				listCategorie.add(categorie);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new DALException("CategorieDaoImpl - selectByLibelle() ");
		}
		return listCategorie;
	}

	public void insertCategorie(String libelle) throws DALException {
		try {
			Connection cnx = JdbcTools.getConnection();
			PreparedStatement pstmt = cnx.prepareStatement(INSERT_CATEGORIES);
			pstmt.setString(1, libelle);
			pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			throw new DALException("Echec à l'insert de la catégorie");
		}
	}

	@Override
	public void delete(int id) throws DALException {
		try {
			Connection cnx = JdbcTools.getConnection();
			PreparedStatement pstmt = cnx.prepareStatement(DELETE_CATEGORIES);
			pstmt.setInt(1, id);
			pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			throw new DALException("Echec à la suppression de la catégorie");
		}

	}

	public void update(String libelle, Integer id) throws DALException {

		try {
			Connection cnx = JdbcTools.getConnection();
			PreparedStatement pstmt = cnx.prepareStatement(UPDATE_CATEGORIES);
			pstmt.setString(1, libelle);
			pstmt.setInt(2, id);
			pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			throw new DALException("Echec à la mise a jour de la catégorie");
		}

	}

	@Override
	public Categorie selectById(int id) throws DALException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void update(Categorie data) throws DALException {
		// TODO Auto-generated method stub

	}

	@Override
	public void insert(Categorie data) throws DALException {
		// TODO Auto-generated method stub

	}


	@Override
	public List<Categorie> getCatalogue() throws DALException {
		// TODO Auto-generated method stub
		return null;
	}

}

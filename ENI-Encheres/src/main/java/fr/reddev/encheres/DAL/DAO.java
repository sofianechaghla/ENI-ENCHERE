/**
 * PROJET ENI-ENCHERES
 * 
 */
package fr.reddev.encheres.DAL;

import java.sql.SQLException;
/**
 * @author REDDEV
 */
import java.util.List;

import fr.reddev.encheres.Exception.DALException;

public interface DAO<T> {
	public T selectById(int id) throws DALException, SQLException;

	public List<T> selectAll() throws DALException, SQLException;

	public void update(T data) throws DALException, SQLException;

	public void insert(T data) throws DALException, SQLException;

	public void delete(int id) throws DALException, SQLException;

}

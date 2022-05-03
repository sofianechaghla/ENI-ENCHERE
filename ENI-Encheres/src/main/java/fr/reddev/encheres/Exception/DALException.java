/**
 * PROJET ENI-ENCHERES
 * 
 */
package fr.reddev.encheres.Exception;

/**
 * @author REDDEV
 */
public class DALException extends Exception {
	private static final long serialVersionUID = 1L;

	public DALException() {
		super();
	}

	public DALException(String message) {
		super(message);
	}

	@Override
	public String toString() {
		return "***DALException***\n" + super.getMessage();
	}
}
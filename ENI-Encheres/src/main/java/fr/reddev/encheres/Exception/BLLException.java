/**
 * PROJET ENI-ENCHERES
 * 
 */
package fr.reddev.encheres.Exception;

/**
 * @author REDDEV
 */
public class BLLException extends Exception {
	private static final long serialVersionUID = 1L;

	public BLLException() {
		super();
	}

	public BLLException(String message) {
		super(message);
	}

	@Override
	public String toString() {
		return "***BLLException***\n" + super.getMessage();
	}
}
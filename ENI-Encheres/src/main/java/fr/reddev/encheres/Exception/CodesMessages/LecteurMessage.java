/**
 * PROJET ENI-ENCHERES
 * 
 */
package fr.reddev.encheres.Exception.CodesMessages;

/**
 * @author REDDEV
 */
import java.util.ResourceBundle;

public abstract class LecteurMessage {
	private static ResourceBundle rb;

	static {
		try {
			rb = ResourceBundle.getBundle("fr.reddev.encheres.Exception.CodesMessages.messages_erreur");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @param code
	 * @return
	 */
	public static String getMessageErreur(int code) {
		String message = "";
		try {
			if (rb != null) {
				message = rb.getString(String.valueOf(code));
			} else {
				message = "Problème à la lecture du fichier contenant les messages";
			}
		} catch (Exception e) {
			e.printStackTrace();
			message = "Une erreur inconnue est survenue";
		}
		return message;
	}
}

/**
 * PROJET ENI-ENCHERES
 * 
 */
package fr.reddev.encheres.BO;

/**
 * @author REDDEV
 */
public class Retraits {
	private int no_article;
	private String rue;
	private String code_postal;
	private String ville;

	public Retraits(int no_article, String rue, String code_postal, String ville) {
		super();
		this.no_article = no_article;
		this.rue = rue;
		this.code_postal = code_postal;
		this.ville = ville;
	}

	public Retraits() {
		super();
		// TODO Auto-generated constructor stub
	}

	public int getNo_article() {
		return no_article;
	}

	public void setNo_article(int no_article) {
		this.no_article = no_article;
	}

	public String getRue() {
		return rue;
	}

	public void setRue(String rue) {
		this.rue = rue;
	}

	public String getCode_postal() {
		return code_postal;
	}

	public void setCode_postal(String code_postal) {
		this.code_postal = code_postal;
	}

	public String getVille() {
		return ville;
	}

	public void setVille(String ville) {
		this.ville = ville;
	}

}

package sinNombre;

public class Triad {
	
	private String source;
	private String cible;
	private String mot;
	
	public Triad( String source, String cible, String mot) {
		this.source = source;
		this.cible = cible;
		this.mot = mot;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getCible() {
		return cible;
	}

	public void setCible(String cible) {
		this.cible = cible;
	}

	public String getMot() {
		return mot;
	}

	public void setMot(String mot) {
		this.mot = mot;
	}

	@Override
	public String toString() {
		return "Triad [source=" + source + ", cible=" + cible + ", mot=" + mot + "]";
	}

	
}

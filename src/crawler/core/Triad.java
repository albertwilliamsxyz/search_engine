package crawler.core;

/**
 * Clase que crea objetos de tipo Triad, demandados en el enunciado del proyecto
 * y necesarios para implementar el algoritmo hits
 * @author ujarky
 */
public class Triad {
	
	//URL de la pagina origen
	private String source;
	//URL de la pagina destino
	private String cible;
	//Palabra que representa el link de la pagina destino en la pagina origen
	private String mot;
	
	/**
	 * Crea una Trad iniciando sus 3 valores
	 * @param source
	 * @param cible
	 * @param mot
	 */
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cible == null) ? 0 : cible.hashCode());
		result = prime * result + ((source == null) ? 0 : source.hashCode());
		return result;
	}

	/**
	 * Dos Triads son iguales si esta contiene
	 * el mismo origen y el mismo destino
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Triad other = (Triad) obj;
		if (cible == null) {
			if (other.cible != null)
				return false;
		} else if (!cible.equals(other.cible))
			return false;
		if (source == null) {
			if (other.source != null)
				return false;
		} else if (!source.equals(other.source))
			return false;
		return true;
	}

}

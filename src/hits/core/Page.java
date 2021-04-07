package hits.core;

/**
 * Clase encargada de crear Onjetos de tipo paginas
 * que contiene la informacion necesaria para 
 * ejecutar el algortmo hits
 * @author ujarky
 */
public class Page {
	
	//url de la pagina
	private String url;
	//valor autority de la pagina
	private double auth;
	//valo hub de la pagina
	private double hub;
	
	/**
	 * Obtiene la url e inicia el autority y el hub 1
	 * @param url
	 */
	public Page(String url) {
		this.url = url;
		this.auth = 1;
		this.hub = 1;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public double getAuth() {
		return auth;
	}

	public void setAuth(double auth) {
		this.auth = auth;
	}

	public double getHub() {
		return hub;
	}

	public void setHub(double hub) {
		this.hub = hub;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((url == null) ? 0 : url.hashCode());
		return result;
	}

	/**
	 * Dos pginas son iguales si contienen la misma url
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Page other = (Page) obj;
		if (url == null) {
			if (other.url != null)
				return false;
		} else if (!url.equals(other.url))
			return false;
		return true;
	}


	@Override
	public String toString() {
		return "{\"url\":\"" + url + "\",\"auth\":" + auth + ",\"hub\":" + hub + "}";
	}

}

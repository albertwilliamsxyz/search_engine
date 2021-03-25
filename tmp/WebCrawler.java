/*
// La programación de red no puede limitarse a capturar páginas HTML (parte del rastreador), su sistema debería poder funcionar mediante el equilibrio de carga en varias máquinas, por ejemplo la parte de indexación y el algoritmo HITS deberán ejecutarse en otra máquina que vendrá ver el repositorio de la página web.
// TODO: Rename to WebCrawler
class WebCrawler {
	public static void main(String[] args) {
		run(10);
	}

	public static String[] getListOfLinks(int depth) {
		// Un módulo de indexación que debe ser multihilo y / o multi-máquina y que extrae, para cada página, la fuente y el destino de una URL especificada por HREF y el destino de una etiqueta A en el código HTML. Por lo tanto, para la misma página HTML obtenemos una serie de triples (URL de página, palabras en las que se puede hacer clic, URL de destino). Los trillizos se almacenarán y se podrá acceder a ellos mediante uno o más índices (por ejemplo, tablas hash). Los trillizos también se renderizarán persistente en un archivo mediante el mecanismo de serialización.
		String[] root = {
			"https://en.wikipedia.org/",
			"https://www.reddit.com/",
		};
		for (String item: root) {
			// Que analizan páginas descargadas rápidamente para descubrir nuevas URL
			Descargar documento referenciado por el root;
			Parsing del documento;
			Leer las etiquetas "a" del documento; (asumimos que las etiquetas estan cargadas en un iterador);
			Por cada elemento de la lista de etiquetas a {
				// Recordar mover esto a funcion recursiva
				Leer href
				// Un repositorio de páginas web que contiene los archivos HTML de las páginas que luego serán procesadas por un mecanismo de indexación
				lista de links = run(depth-1);
			}
			retorna toda la lista de links leidos
		} 
	}
}
*/

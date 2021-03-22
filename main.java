class Main {
	public static void main(String[] args) {
		String[] root = {
			"https://en.wikipedia.org/",
			"https://www.reddit.com/",
		};
		for (String item: root) {
			System.out.println(item);
		} 
	}
}

/*
Motor de búsqueda

El objetivo es desarrollar la infraestructura de un motor de búsqueda e implementar un algoritmo muy popular (HITS) que permite determinar y clasificar las páginas autorizadas (autoridades) y las páginas que concentran los enlaces hacia las autoridades (hubs).

Un motor de búsqueda generalmente se compone de varios elementos:

- Un módulo crawler que explora la Web siguiendo los enlaces contenidos en las páginas. Varias maquinas o hilos pueden realizar esta tarea. Las URL de las páginas para descargar están contenidas en una lista de espera que se llena con las diversas instancias del rastreador que analizan páginas descargadas rápidamente para descubrir nuevas URL.

- Un repositorio de páginas web que contiene los archivos HTML de las páginas que luego serán procesadas por un mecanismo de indexación.

- Un módulo de indexación que debe ser multihilo y / o multi-máquina y que extrae, para cada página, la fuente y el destino de una URL especificada por HREF y el destino de una etiqueta A en el código HTML. Por lo tanto, para la misma página HTML obtenemos una serie de triples (URL de página, palabras en las que se puede hacer clic, URL de destino). Los trillizos se almacenarán y se podrá acceder a ellos mediante uno o más índices (por ejemplo, tablas hash). Los trillizos también se renderizarán persistente en un archivo mediante el mecanismo de serialización.

- Un conjunto de algoritmos que puede utilizar un módulo de consulta para clasificar páginas web y responder a la solicitud de un usuario. En nuestro caso es principalmente del algoritmo HITS.


Su producción debe hacer que estos diferentes elementos aparezcan con claridad. Para el algoritmo HITS, tendrá que implementar una versión iterativa (la versión que utiliza valores propios y vectores propios no se solicita).

La programación de red no puede limitarse a capturar páginas HTML (parte del rastreador), su sistema debería poder funcionar mediante el equilibrio de carga en varias máquinas, por ejemplo la parte de indexación y el algoritmo HITS deberán ejecutarse en otra máquina que vendrá ver el repositorio de la página web.

Deberá proporcionar conjuntos de datos (por ejemplo, un archivo que contenga más de 500.000 triples), los resultados producidos y un escenario de ejecución para mostrar la relevancia de los resultados en relación con buscando un área específica, por ejemplo, en cursos o habilidades informáticas.
*/

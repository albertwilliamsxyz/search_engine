// Su producción debe hacer que estos diferentes elementos aparezcan con claridad. Para el algoritmo HITS, tendrá que implementar una versión iterativa (la versión que utiliza valores propios y vectores propios no se solicita).
class HITSAlgorithms {
	public static void run(String[] listOfLinks) {
		G := set of pages
		for each page p in G do
			p.auth = 1 // p.auth is the authority score of the page p
			p.hub = 1 // p.hub is the hub score of the page p
		for step from 1 to k do // run the algorithm for k steps
			norm = 0
			for each page p in G do  // update all authority values first
				p.auth = 0
				for each page q in p.incomingNeighbors do // p.incomingNeighbors is the set of pages that link to p
					p.auth += q.hub
				norm += square(p.auth) // calculate the sum of the squared auth values to normalise
			norm = sqrt(norm)
			for each page p in G do  // update the auth scores 
				p.auth = p.auth / norm  // normalise the auth values
			norm = 0
			for each page p in G do  // then update all hub values
				p.hub = 0
				for each page r in p.outgoingNeighbors do // p.outgoingNeighbors is the set of pages that p links to
					p.hub += r.auth
				norm += square(p.hub) // calculate the sum of the squared hub values to normalise
			norm = sqrt(norm)
			for each page p in G do  // then update all hub values
				p.hub = p.hub / norm   // normalise the hub values
		//Un conjunto de algoritmos que puede utilizar un módulo de consulta para clasificar páginas web y responder a la solicitud de un usuario
		//En nuestro caso es principalmente del algoritmo HITS
		// Deberá proporcionar conjuntos de datos (por ejemplo, un archivo que contenga más de 500.000 triples), los resultados producidos y un escenario de ejecución para mostrar la relevancia de los resultados en relación con buscando un área específica, por ejemplo, en cursos o habilidades informáticas.
	}
}


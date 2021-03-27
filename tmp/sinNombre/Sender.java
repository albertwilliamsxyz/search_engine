package sinNombre;

import java.util.HashSet;

public class Sender {
	
	private DDBB ddbb;
	
	public Sender() {
		this.ddbb = new DDBB();
		this.ddbb.connection();
		this.ddbb.createTriadsTable();
	}
	
	public void send(HashSet<Triad>  toSend) {
		for (Triad o : toSend) {
			this.ddbb.insertTriad(o);
			System.out.println(o.toString());
			
		}
	
	}

}

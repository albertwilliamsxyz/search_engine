package sinNombre;

public class Test {
	
	public static void main(String[] args) {
		Sender sender = new Sender();
		Crawler c = new Crawler("https://www.wikipedia.com/", sender);
		c.start();
		
		try {
			c.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("Listo");
		
	}

}

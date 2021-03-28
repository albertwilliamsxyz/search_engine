package crawler;

public class Main {
	
	public static void main(String[] args) {
		
		String roots[]= {
				"https://www.wikipedia.com/",
				"https://techterms.com/definition/recursive_function"
		};
		
		WebCrawlingManager wcm = new WebCrawlingManager(roots);
		System.out.println("End Program");
		
	}
	

}

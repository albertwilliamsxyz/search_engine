package principal;

import java.util.ArrayList;


public class WebCrawler {
  public static void main(String[] args) {
    String start_url[] ={"http://agustin-cartaya.epizy.com/?i=1",
	"https://en.wikipedia.org/wiki/HITS_algorithm",
    "https://prismic.io/docs/technologies/html-serializer-java",
    "https://jsoup.org/",
    "https://www.w3schools.com/java/java_arrays.asp",
    "https://www.reddit.com/",
    "https://fr.wikipedia.org/wiki/Liste_d%27adjacence"};
    
    Bot bot = new Bot( start_url[0] );
    bot.start();
    
  }
  
  
}


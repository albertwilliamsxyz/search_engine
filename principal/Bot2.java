package principal;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

import org.jsoup.Jsoup;
import org.jsoup.nodes.*;
import org.jsoup.select.*;


public class Bot2 {
	
	private  String strat_url;
	public Bot2(String strat_url) {
		this.strat_url = strat_url;	
	}
	
	public void start() {
		String html = getHTML(this.strat_url);
		System.out.println(html);
	}
	
	private String getHTML( String url) {
		URL u;
		try {
			u = new URL(url);
			java.net.URLConnection conn = u.openConnection();
			conn.setRequestProperty("User-Agent", "BBot/1.0");
			conn.setRequestProperty("Accept-Cherset", "UTF-8");
			
			InputStream  is = conn.getInputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(is));
			
			String line;
			String html = "";
			while ( (line = reader.readLine()) != null)  {
				html += line +"\n";
			}
			html = html.trim();
			
			return html;
			
			
		}catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}

}

package webcrawlingTools;
/**
 * @author Eric Le Fort
 * @version 01
 */
import java.io.IOException;
import java.util.ArrayList;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class Webcrawler{

	public static void main(String...args){
		Document site;
		Elements elems;
		String[] links;

		try{
			site = Jsoup.connect("http://www.google.ca").get();
			elems = site.getElementsByTag("a");
			links = grabAnchorLinks(elems);

			for(int i = 0; i < links.length; i++){
				System.out.println(links[i]);
			}
		}catch(IOException ioe){
			ioe.printStackTrace();
		}
	}//main()

	public static String[] grabAnchorLinks(Elements elems){
		ArrayList<String> links = new ArrayList<String>();

		for(int i = 0; i < elems.size(); i++){
			links.add(elems.get(i).attr("href"));
		}

		return links.toArray(new String[0]);
	}//grabAnchorLinks()

}//Webcrawler
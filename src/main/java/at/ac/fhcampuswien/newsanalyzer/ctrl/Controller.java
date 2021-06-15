package at.ac.fhcampuswien.newsanalyzer.ctrl;

import at.ac.fhcampuswien.newsapi.NewsApi;
import at.ac.fhcampuswien.newsapi.beans.Article;
import at.ac.fhcampuswien.newsapi.beans.NewsResponse;
import com.sun.org.apache.xalan.internal.xsltc.compiler.Template;

import java.util.List;

public class Controller {

	public static final String APIKEY = "edd9f780730647ae87fec76ec98b7416";  //TODO add your api key
	private Template template;
	private String q = "corona";
	private List<Article> article;
	private NewsResponse newsResponse;
	private final String pageSize = "100";

	public void process() {
		System.out.println("Start process");

		//TODO implement Error handling

		//TODO load the news based on the parameters
		public String Data

		//TODO implement methods for analysis

		System.out.println("End process");
	}
	

	public Object getData() {
		
		return null;
	}
}

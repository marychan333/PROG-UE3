package at.ac.fhcampuswien.newsanalyzer.ctrl;

import at.ac.fhcampuswien.newsapi.NewsApi;
import at.ac.fhcampuswien.newsapi.NewsApiBuilder;
import at.ac.fhcampuswien.newsapi.beans.Article;
import at.ac.fhcampuswien.newsapi.beans.NewsResponse;
import at.ac.fhcampuswien.newsapi.enums.Category;
import at.ac.fhcampuswien.newsapi.enums.Country;
import at.ac.fhcampuswien.newsapi.enums.Endpoint;
//import com.sun.org.apache.xalan.internal.xsltc.compiler.Template;

import java.io.*;
import java.util.stream.Collectors;
import java.net.URL;
import java.util.List;
import java.util.Objects;
import java.util.Comparator;

public class Controller {

	public static final String APIKEY = "edd9f780730647ae87fec76ec98b7416";  //TODO add your api key

	private String q = "corona";
	private List<Article> article;
	private NewsResponse newsResponse;
	private final String pageSize = "100";

	public void process(String corona, Category health) {
		System.out.println("Start process");

		//TODO implement Error handling
		NewsApi newsApi = null;
		Category category = null;

		if (category != null) {
			newsApi = new NewsApiBuilder().setApiKey(APIKEY).setQ(q).setEndPoint(Endpoint.TOP_HEADLINES).setSourceCountry(Country.ch).setSourceCategory(category).createNewsApi();
		} else if (category == null) {
			newsApi = new NewsApiBuilder().setApiKey(APIKEY).setQ(q).setEndPoint(Endpoint.TOP_HEADLINES).createNewsApi();
		}
		NewsResponse newsResponse = null;
		try {
			newsResponse = newsApi.getNews();
		} catch (Exception exception) {
			System.err.println("Error: " + exception.getMessage());
		}

		if (newsResponse != null) {
			List<Article> articles = newsResponse.getArticles();
			System.out.println("Number of articles: " + articles.size());
			if (articles.isEmpty()) {
				System.out.println("There are no articles can be shown.");
				return;
			}

			//TODO load the news based on the parameters
			List<Article> sortedArticles = articles.stream().sorted(Comparator.comparingInt(article -> article.getTitle().length())).sorted(Comparator.comparing(Article::getTitle)).collect(Collectors.toList());

			String provider = articles.stream().collect(Collectors.groupingBy(article -> article.getSource().getName(), Collectors.counting())).entrySet().stream().max(Comparator.comparingInt(t -> t.getValue().intValue())).get().getKey();

			String author = articles.stream().filter(article -> Objects.nonNull(article.getAuthor())).min(Comparator.comparingInt(article -> article.getAuthor().length())).get().getAuthor();

			if (sortedArticles != null) {
				System.out.println("The first article from sorted List: " + sortedArticles.get(0));
			}

			if (provider != null) {
				System.out.println("Maximum Articles that provided by: " + provider);
			}

			if (author != null) {
				System.out.println("Shortest name of Author: " + author);
			}
			//TODO implement methods for analysis

			for (Article article : articles) {
				try {
					URL url = new URL(article.getUrl()); //had to import URL class
					BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(article.getTitle().substring(0, 10) + "html"));
					InputStream inputStream = url.openStream();
					BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
					String line = bufferedReader.readLine();
					while (line != null) {
						bufferedWriter.write(line);
					}
					bufferedReader.close();
					bufferedWriter.close();
				} catch (Exception e) {
					System.err.println("Failed to save webpage: " + e.getMessage());
				}
			}
			System.out.println("End process");
		}
	}

		public Object getData() {

			return null;
		}
	}
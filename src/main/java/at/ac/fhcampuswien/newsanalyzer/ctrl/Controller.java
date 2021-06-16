package at.ac.fhcampuswien.newsanalyzer.ctrl;

import at.ac.fhcampuswien.newsapi.NewsApi;
import at.ac.fhcampuswien.newsapi.NewsApiBuilder;
import at.ac.fhcampuswien.newsapi.beans.Article;
import at.ac.fhcampuswien.newsapi.beans.NewsResponse;
import at.ac.fhcampuswien.newsapi.enums.Category;
import at.ac.fhcampuswien.newsapi.enums.Country;
import at.ac.fhcampuswien.newsapi.enums.Endpoint;

import java.io.*;
import java.net.URLConnection;
import java.util.*;
import java.util.stream.Collectors;
import java.net.URL;

public class Controller {

	public static final String APIKEY = "edd9f780730647ae87fec76ec98b7416";  //TODO add your api key

	public void process(String strings, Category menu) {
		System.out.println("Start process");

		//TODO implement Error handling
			NewsApi newsApi;
			if (menu != null) {
				newsApi = new NewsApiBuilder().setApiKey(APIKEY).setQ(strings).setEndPoint(Endpoint.TOP_HEADLINES).setSourceCountry(Country.at).setSourceCategory(menu).createNewsApi();
			} else {
				newsApi = new NewsApiBuilder().setApiKey(APIKEY).setQ(strings).setEndPoint(Endpoint.TOP_HEADLINES).createNewsApi();
			}
			NewsResponse news = null;

			try {
				news = newsApi.getNews();
			} catch (Exception e) {
				System.err.println("Error: " + e.getMessage());
			}
			if (news != null) {
				List<Article> articles = news.getArticles();
				Map<String, Long> map = new HashMap<>();
				for (Article article1 : articles) {
					map.merge(article1.getSource().getName(), 1L, Long::sum);
				}
				String server = map.entrySet().stream().max(Comparator.comparingInt(t -> t.getValue().intValue())).get().getKey();
				String author = articles.stream().filter(article -> Objects.nonNull(article.getAuthor())).min(Comparator.comparingInt(article -> article.getAuthor().length())).get().getAuthor();

				List<Article> sortedArticles = articles.stream().sorted(Comparator.comparingInt(article -> article.getTitle().length())).sorted(Comparator.comparing(Article::getTitle)).collect(Collectors.toList());

				System.out.println("Articles: " + articles.size());
				if (articles.isEmpty()) {
					System.out.println("No articles.");
					return;
				}

				if (server != null)
					System.out.println("Server: " + server);

				if (author != null)
					System.out.println("Author: " + author);

				System.out.println("First: " + sortedArticles.get(0));

				for (Article article : articles) {
					try {
						URL newURL = new URL(article.getUrl());
						URLConnection connection = newURL.openConnection();
						connection.setDoOutput(true);

						BufferedReader in = new BufferedReader(
								new InputStreamReader(
										connection.getInputStream()));

						String decodedString;
						while ((decodedString = in.readLine()) != null) {
							System.out.println(decodedString);
						}

						BufferedWriter writer = new BufferedWriter(new FileWriter(article.getTitle().substring(0, 100) + "html"));
						String newsText = in.readLine();
						while (newsText != null) {
							writer.write(newsText);
						}
						in.close();
						writer.close();
					} catch (Exception e) {
						System.err.println("Failed: " + Arrays.toString(e.getStackTrace()));
					}
				}
			}
				System.out.println("End process");
			}
			//TODO load the news based on the parameters


			//TODO implement methods for analysis

		public Object getData() {

			return null;
		}
	}
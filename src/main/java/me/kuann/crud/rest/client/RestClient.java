package me.kuann.crud.rest.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class RestClient {
	private static final Gson gson;
	private final HttpClient client;
	private List<String> paths;
	private String body;

	static {
		gson = new GsonBuilder().create();
	}

	private RestClient(String root) {
		this.paths = new ArrayList<>();
		this.paths.add(root);
		this.client = HttpClientBuilder.create().build();
		this.body = "";
	}

	public static RestClient build(String root) {
		return new RestClient(root);
	}

	public RestClient onPath(String path) {
		this.paths.add(path);
		return this;
	}

	public RestClient withBody(Object body) {
		this.body = gson.toJson(body);
		return this;
	}

	public <T> T getForResponse(Class<T> clazz) {
		HttpResponse response = executeGet();
		return convertResponse(response, clazz);
	}

	public void getNoReponse() {
		executeGet();
	}

	private HttpResponse executeGet() {
		String url = buildFinalUrl();
		HttpGet request = new HttpGet(url);
		HttpResponse response = executeHttpRequest(request);
		return response;
	}

	public <T> T postForReponse(Class<T> clazz) {
		HttpResponse response = executePost();
		return convertResponse(response, clazz);
	}

	public void postNoReponse() {
		executePost();
	}

	private HttpResponse executePost() {
		try {
			String url = buildFinalUrl();
			HttpPost request = new HttpPost(url);
			HttpEntity entity = new StringEntity(gson.toJson(body));
			request.setEntity(entity);
			HttpResponse response = executeHttpRequest(request);
			return response;
		} catch (IOException ex) {
			throw new RestClientException(ex);
		}
	}

	private <T> T convertResponse(HttpResponse response, Class<T> clazz) {
		if (response.getStatusLine().getStatusCode() == 204) {
			return null;
		}
		try {
			BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

			String line = "";
			StringBuilder jsonData = new StringBuilder();
			while ((line = rd.readLine()) != null) {
				jsonData.append(line);
			}

			response.getEntity().getContent().close();
			rd.close();
			return gson.fromJson(jsonData.toString(), clazz);
		} catch (IOException ex) {
			throw new RestClientException(ex);
		}
	}

	private HttpResponse executeHttpRequest(HttpUriRequest request) {
		try {
			HttpResponse response = client.execute(request);
			int statusCode = response.getStatusLine().getStatusCode();
			if (statusCode >= 400) {
				throw new RestClientException("Request error with code:" + statusCode);
			}
			return response;
		} catch (IOException ex) {
			throw new RestClientException(ex);
		}
	}

	private String buildFinalUrl() {
		return paths.stream().collect(Collectors.joining("/"));
	}

}

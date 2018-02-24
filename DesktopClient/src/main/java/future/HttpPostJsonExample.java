package future;

import java.io.IOException;
import java.net.URL;
import java.nio.charset.Charset;

import javax.json.Json;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;

public class HttpPostJsonExample {
	public static void main(String[] args) {

		try {
			System.out.println(
					HttpPostJsonExample.makePostRequestJson("http://127.0.0.1/add/song", "{\"hallo\":\"hi\"}"));
		} catch (org.apache.http.conn.HttpHostConnectException e) {
			System.err.println("Could not build a connection to the server!");
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException f) {
			f.printStackTrace();
		}

		try {
			String a = IOUtils.toString(new URL("https://jsonplaceholder.typicode.com/posts/1"),
					Charset.forName("UTF-8"));
			System.out.println(a);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public static int makePostRequestJson(final String URL_STRING, final String JSON_STRING)
			throws ClientProtocolException, IOException {

		// make HttpClient
		final HttpClient httpClient = HttpClientBuilder.create().build();

		// make HttpPost
		final HttpPost request = new HttpPost(URL_STRING);
		request.setEntity(new StringEntity(JSON_STRING, ContentType.APPLICATION_JSON));

		System.out.println(">> Make post request");

		// get HttpResponse
		final HttpResponse response2 = httpClient.execute(request);

		// return server status code
		return response2.getStatusLine().getStatusCode();
	}

	public static Json getJsonFromRequest(final String URL_STRING) {
		HttpClient client = HttpClientBuilder.create().build();
		HttpGet request = new HttpGet(URL_STRING);
		request.addHeader("accept", "application/json");
		// HttpResponse response = client.execute(request);
		// String json = response.getEntity().getContent().toString();
		return null;
	}
}

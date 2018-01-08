package future;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;

public class HttpPostJsonExample {
	public static void main(String[] args) {

		try {
			System.out.println(HttpPostJsonExample.makePostRequest("http://127.0.0.1/add/song", "{\"hallo\":\"hi\"}"));
		} catch (org.apache.http.conn.HttpHostConnectException e) {
			System.err.println("Could not build a connection to the server!");
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException f) {
			f.printStackTrace();
		}

	}

	public static int makePostRequest(final String URL_STRING, final String JSON_STRING)
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
}

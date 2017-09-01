package anonymerniklasistanonym.karaokemusicvideomanager.desktopclient.json;

import java.io.StringReader;
import java.math.BigDecimal;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;

public class JsonModule {

	public static void main(String[] args) {

		// Create Json and print
		JsonObject json = Json.createObjectBuilder().add("name", "Falco").add("age", BigDecimal.valueOf(3))
				.add("biteable", Boolean.FALSE).build();
		String result = json.toString();

		System.out.println(result);

		// Read back
		JsonReader jsonReader = Json.createReader(new StringReader("{\"name\":\"Falco\",\"age\":3,\"bitable\":false}"));
		JsonObject jobj = jsonReader.readObject();
		System.out.println(jobj);

		// Output
		// {"name":"Falco","age":3,"biteable":false}
		// {"name":"Falco","age":3,"bitable":false}
	}

}

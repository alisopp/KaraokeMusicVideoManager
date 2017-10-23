package anonymerniklasistanonym.karaokemusicvideomanager.desktopclient;

import java.math.BigDecimal;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;

import anonymerniklasistanonym.karaokemusicvideomanager.desktopclient.libaries.JsonModule;

public class JsonModuleTest {

	// get JSON data:
	// -----------------------
	// String jsonContent = "{\"name\":\"Falco\",\"age\":3,\"bitable\":false}";
	// JsonObject a = JsonModule.loadJsonFromString(jsonContent);
	// System.out.println(a.containsKey("name"));
	// System.out.println(a.getString("name"));
	// System.out.println(a.getInt("age"));
	// System.out.println(a.("age"));
	// System.out.println(a.getJsonArray("?"));
	// System.out.println(a.getJsonObject("?"));

	public static void main(String[] args) {

		// Create Json and convert to String for saving in a text file:
		JsonObjectBuilder jsonBuilder = Json.createObjectBuilder();
		jsonBuilder.add("name", "Falco");
		jsonBuilder.add("age", BigDecimal.valueOf(3));
		jsonBuilder.add("biteable", Boolean.FALSE);
		System.out.println(JsonModule.toString(jsonBuilder));

		// Get Json data:
		String jsonContent = "{\"name\":\"Falco\",\"age\":3,\"bitable\":false}";
		JsonObject a = JsonModule.loadJsonFromString(jsonContent);
		System.out.println(a.containsKey("name"));
		System.out.println(a.getString("name"));
		System.out.println(a.getInt("age"));

		if (a.containsKey("null")) {

			try {

				int value = a.getInt("null");
				System.out.println("Key has the value: " + value);

			} catch (NullPointerException notFoundException) {
				System.err.println("Key not found!");
			} catch (ClassCastException ex) {
				System.err.println("Key value is not anticipated type!");
			}

		} else {
			System.err.println("Key does not exist!");
		}
	}

}

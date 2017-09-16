package anonymerniklasistanonym.karaokemusicvideomanager.desktopclient.libaries;

import java.io.StringReader;
import java.math.BigDecimal;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonReader;
import javax.json.JsonValue;

public class JsonModule {

	public static enum DataTypeJson {
		JSTRING(0), JINTEGER(1), JBOOLEAN(2), JARRAY(3), JOBJECT(4);

		private final int value;

		DataTypeJson(final int newValue) {
			value = newValue;
		}

		public int getValue() {
			return value;
		}
	}

	/**
	 * Convert a string of JSON content to a JsonObject
	 * 
	 * @param jsonContent
	 *            (String)
	 * @return readableJsonObject (JsonObject)
	 */
	public static JsonObject loadJsonFromString(String jsonContent) {

		try {
			JsonReader jsonReader = Json.createReader(new StringReader(jsonContent));
			return jsonReader.readObject();

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Convert Json data to a string
	 * 
	 * @param jsonObject
	 *            (JsonObject)
	 * @return jsonDataString (String)
	 */
	public static String saveJsonToString(JsonObject jsonObject) {

		try {
			return jsonObject.toString();

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Convert Json data to a string
	 *
	 * 
	 * @param jsonBuilder
	 *            (JsonObjectBuilder)
	 * @return jsonDataString (String)
	 */
	public static String dumpStringToJson(JsonObjectBuilder jsonBuilder) {

		try {
			return saveJsonToString(jsonBuilder.build());

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Get the value of a key of a JsonObject
	 * 
	 * @param jsonObject
	 *            (JsonObject | which holds the JSON data)
	 * @param key
	 *            (String | key to the desired value)
	 * @return JsonValue (anything that can be in a JSON file)
	 */
	public static JsonValue getValue(JsonObject jsonObject, String key) {

		if (jsonObject == null || (key == null || key.isEmpty())) {
			System.err.println("JsonObject or key cannot be empty or null!");
			return null;
		}

		if (jsonObject.containsKey(key)) {

			try {

				JsonValue value = jsonObject.get(key);
				System.out.println("Key has the value: " + value);
				return value;

			} catch (NullPointerException notFoundException) {
				System.err.println("Key not found!");
				return null;

			} catch (ClassCastException ex) {
				System.err.println("Key value is not anticipated type!");
				return null;
			}

		} else {
			System.err.println("Key does not exist!");
			return null;
		}

	}

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
		System.out.println(JsonModule.dumpStringToJson(jsonBuilder));

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

		// Read keys from Json (coming soon):
		System.out.println(DataTypeJson.JARRAY.value);
	}

}

package anonymerniklasistanonym.karaokemusicvideomanager.desktopclient.libaries;

import java.io.StringReader;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonReader;
import javax.json.JsonValue;

/**
 * Essential and quick static methods to handle JSON files (write/load)
 * 
 * @author AnonymerNiklasistanonym <niklas.mikeler@gmail.com>
 *
 */
public class JsonModule {

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
	 * Convert a JSON Object data to a string
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
	 * Convert a JSON Object Builder to a string
	 * 
	 * @param jsonBuilder
	 *            (JsonObjectBuilder)
	 * @return jsonDataString (String)
	 */
	public static String dumpJsonObjectToString(JsonObjectBuilder jsonBuilder) {

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

		System.out.print(">> Look for the key " + key);

		if (jsonObject.containsKey(key)) {

			try {

				JsonValue value = jsonObject.get(key);
				System.out.println(" << Key has the value: " + value);
				return value;

			} catch (NullPointerException notFoundException) {
				System.err.println(" << Key not found!");
				return null;

			} catch (ClassCastException ex) {
				System.err.println(" << Key value is not anticipated type!");
				return null;
			}

		} else {
			System.err.println(" << Key does not exist!");
			return null;
		}

	}

	/**
	 * Get the value of a key of a JsonObject as a String
	 * 
	 * @param jsonObject
	 *            (JsonObject | contains the key or null)
	 * @param key
	 *            (String | key to the desired value)
	 * @return stringValueOfTheKey (String)
	 */
	public static String getValueString(JsonObject jsonObject, String key) {

		if (jsonObject == null || (key == null || key.isEmpty())) {
			System.err.println("JsonObject or key cannot be empty or null!");
			return null;
		}

		System.out.print(">> Look for the key " + key);

		if (jsonObject.containsKey(key)) {

			try {

				String value = jsonObject.getString(key);
				System.out.println(" << Key has the String: " + value);
				return value;

			} catch (NullPointerException notFoundException) {
				System.err.println(" << Key not found!");
				return null;

			} catch (ClassCastException ex) {
				System.err.println(" << Key value is not anticipated type!");
				return null;
			}

		} else {
			System.err.println(" << Key does not exist!");
			return null;
		}
	}

	/**
	 * Get the value of a key of a JsonObject as a String
	 * 
	 * @param jsonObject
	 *            (JsonObject | contains the key or null)
	 * @param key
	 *            (String | key to the desired value)
	 * @return stringValueOfTheKey (String)
	 */
	public static boolean getValueBoolean(JsonObject jsonObject, String key) {

		if (jsonObject == null || (key == null || key.isEmpty())) {
			System.err.println("JsonObject or key cannot be empty or null!");
			return false;
		}

		System.out.print(">> Look for the key " + key);

		if (jsonObject.containsKey(key)) {

			try {

				boolean value = jsonObject.getBoolean(key);
				System.out.println(" << Key has the String: " + value);
				return value;

			} catch (NullPointerException notFoundException) {
				System.err.println(" << Key not found!");
				return false;

			} catch (ClassCastException ex) {
				System.err.println(" << Key value is not anticipated type!");
				return false;
			}

		} else {
			System.err.println(" << Key does not exist!");
			return false;
		}
	}

	/**
	 * Compare two JSON contents
	 * 
	 * @param jsonBuilder
	 *            (JsonObjectBuilder)
	 * @return jsonDataString (String)
	 */
	public static boolean compareJsonStrings(String one, String two) {

		System.out.print(">> Compare two JSON strings");

		if (one != null || two != null) {
			try {
				if (loadJsonFromString(one).equals(loadJsonFromString(two))) {
					System.out.println(" << The strings are the same!");
					return true;
				} else {
					System.out.println(" << The strings are not the same!");
					return false;
				}

			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}
		} else {
			System.err.println(" << The Strings can't be null!");
			return false;
		}

	}

}

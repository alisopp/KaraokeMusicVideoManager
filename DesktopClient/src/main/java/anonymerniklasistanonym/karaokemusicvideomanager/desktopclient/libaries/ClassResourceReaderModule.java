package anonymerniklasistanonym.karaokemusicvideomanager.desktopclient.libaries;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class ClassResourceReaderModule {

	/**
	 * Load a text file into a String array and return it
	 * 
	 * @param path
	 *            (String | path to resource file)
	 * @return content (String[])
	 */
	public static String[] getTextContent(String path) {

		ArrayList<String> cache = new ArrayList<String>();

		try {

			ClassLoader cl = ClassResourceReaderModule.class.getClassLoader();

			BufferedReader beforeTableHtmlData = new BufferedReader(
					new InputStreamReader(cl.getResourceAsStream(path)));
			String line;
			while ((line = beforeTableHtmlData.readLine()) != null) {
				cache.add(line);
			}

			return cache.toArray(new String[0]);
		} catch (Exception e) {
			return null;
		}
	}

}

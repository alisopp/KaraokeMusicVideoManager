package anonymerniklasistanonym.karaokemusicvideomanager.desktopclient.libaries;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;

public class ClassResourceReaderModule {

	/**
	 * Load a text file into a String array and return it
	 * 
	 * @param path
	 *            (String | path to resource file)
	 * @return content (String[])
	 */
	public static String[] getTextContent(String path) {

		// create empty list for the file content
		ArrayList<String> cache = new ArrayList<String>();

		try {
			// open content of file in a buffered reader
			BufferedReader beforeTableHtmlData = new BufferedReader(new InputStreamReader(getInputStream(path)));
			String line;
			// as long as there is a next line that isn't null add this line to the cache
			while ((line = beforeTableHtmlData.readLine()) != null) {
				cache.add(line);
			}
			return cache.toArray(new String[0]);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Load a text file into a String and return it
	 * 
	 * @param path
	 *            (String | path to resource file)
	 * @return content (String[])
	 */
	public static String getTextContentinOneString(String path) {

		// open the file with lines
		String[] contentOfFile = getTextContent(path);

		if (contentOfFile != null) {
			// if content isn't null convert all lines to one String
			return Arrays.toString(contentOfFile);
		}

		return null;
	}

	/**
	 * Load a file from the res folder (in build path)
	 * 
	 * @param path
	 *            (String | Path to file)
	 * @return input stream of the file (InputStream or null)
	 */
	public static InputStream getInputStream(String path) {

		if (path == null) {
			System.err.println("Path could not be read because it's null!");
			return null;
		}

		try {

			System.out.println(">> Get InputStream: " + path);

			ClassLoader cl = ClassResourceReaderModule.class.getClassLoader();

			InputStream resourceAsStream = cl.getResourceAsStream(path);

			if (resourceAsStream != null) {
				System.out.println("<< InputStream succsessfully loaded.");
				return resourceAsStream;
			} else {
				System.err.println("<< InputStream is null!");
				return null;
			}

		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("<< InputStream loading operation didn't work!");
			return null;
		}

	}

}

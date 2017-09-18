package anonymerniklasistanonym.karaokemusicvideomanager.desktopclient.functions;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class ExportMusicVideoData {

	/**
	 * Generate a HTML table
	 * 
	 * @return table (String)
	 */
	public static String generateHtmlTable(Object[][] data, String[] columnNames) {

		StringBuilder sb = new StringBuilder("<table>");

		String[] columnClasses = { "class=\"number\"", "class=\"artist\"", "class=\"title\"" };

		sb.append("<thead><tr>");
		for (int a = 0; a < columnNames.length; a++) {
			sb.append("<th " + columnClasses[a] + " >" + columnNames[a] + "</th>");
		}
		sb.append("</tr></thead><tbody>");

		sb.append(createHtmlTableData(data));

		sb.append("</tbody></table>");

		return sb.toString();
	}

	private static String createHtmlTableData(Object[][] data) {

		int columnNumber = data[0].length;
		int rowNumber = data.length;

		StringBuilder sb = new StringBuilder("");

		for (int a = 0; a < rowNumber; a++) {
			sb.append("<tr>");
			for (int b = 0; b < columnNumber; b++) {
				sb.append("<td>" + data[a][b] + "</td>");
			}
			sb.append("</tr>");
		}
		sb.append("</tbody></table>");

		return sb.toString();

	}

	public static enum HTML_SITE {
		STATIC(0), SEARCH(1), PARTY(2);

		private final int value;

		HTML_SITE(final int newValue) {
			value = newValue;
		}
	}

	public static String generateHtmlTableWithSearch(Object[][] data, String[] columnNames) {

		StringBuilder sb = new StringBuilder("placeholder");

		sb.append("=\"Input your search query to find your music videos...\"/></div>");

		sb.append("<table class=\"order-table table\">");

		String[] columnClasses = { "class=\"number\"", "class=\"artist\"", "class=\"title\"" };

		sb.append("<thead><tr>");
		for (int a = 0; a < columnNames.length; a++) {
			sb.append("<th " + columnClasses[a] + " >" + columnNames[a] + "</th>");
		}
		sb.append("</tr></thead><tbody>");

		sb.append(createHtmlTableData(data));

		sb.append("</tbody></table>");

		return sb.toString();
	}

	public static String generateHtmlTableParty(Object[][] data, String[] columnNames) {

		StringBuilder sb = new StringBuilder("placeholder");

		sb.append("=\"Input your search query to find your music videos...\"/></div>");

		sb.append("<form action=\"demo_form.php\" method=\"get\">\n");

		sb.append("<table class=\"order-table table\">");

		String[] columnClasses = { "class=\"number\"", "class=\"artist\"", "class=\"title\"" };

		sb.append("<thead><tr>");
		for (int a = 0; a < columnNames.length; a++) {
			sb.append("<th " + columnClasses[a] + " >" + columnNames[a] + "</th>");
		}
		sb.append("<th " + "class=\"playlist\"" + " >" + "Sing!" + "</th>");
		sb.append("</tr></thead><tbody>\n");

		for (int a = 0; a < data.length; a++) {
			sb.append("<tr>");
			for (int b = 0; b < columnNames.length; b++) {
				sb.append("<td>" + data[a][b] + "</td>");
			}
			sb.append("<td><button class=\"button\" name=\"index\" type=\"submit\" value=\"" + a + "," + data[a][1]
					+ "," + data[a][2] + "\">Sing!</button></td>");

			sb.append("</tr>\n");
		}

		sb.append("</tbody></table>\n");

		sb.append("</form>");

		return sb.toString();
	}

	public static String[] generateHtmlSiteStatic(Object[][] data, String[] columnNames) {
		return generateHtmlSiteMain(data, columnNames, "website_data/static_html_page_begin.html",
				"website_data/static_html_page_end.html", HTML_SITE.STATIC.value);
	}

	public static String[] generateHtmlSiteDynamic(Object[][] data, String[] columnNames) {
		return generateHtmlSiteMain(data, columnNames, "website_data/searchable_html_page_begin.html",
				"website_data/searchable_html_page_end.html", HTML_SITE.SEARCH.value);
	}

	public static String[] generateHtmlSiteParty(Object[][] data, String[] columnNames) {
		return generateHtmlSiteMain(data, columnNames, "website_data/party_html_page_begin.html",
				"website_data/party_html_page_end.html", HTML_SITE.PARTY.value);
	}

	private static String[] generateHtmlSiteMain(Object[][] data, String[] columnNames, String beforeTable,
			String afterTable, int typeOfHtml) {

		// save all lines in this array list
		ArrayList<String> cache = new ArrayList<String>();

		try {
			// class loader
			ClassLoader cl = ExportMusicVideoData.class.getClassLoader();

			BufferedReader beforeTableHtmlData = new BufferedReader(
					new InputStreamReader(cl.getResourceAsStream(beforeTable)));
			String line;
			while ((line = beforeTableHtmlData.readLine()) != null) {
				cache.add(line);
			}

			// create specific table data for each type
			if (typeOfHtml == HTML_SITE.STATIC.value) {
				cache.add(generateHtmlTable(data, columnNames));
			} else if (typeOfHtml == HTML_SITE.SEARCH.value) {
				cache.add(generateHtmlTableWithSearch(data, columnNames));
			} else if (typeOfHtml == HTML_SITE.PARTY.value) {
				cache.add(generateHtmlTableParty(data, columnNames));
			}

			BufferedReader afterTableHtmlData = new BufferedReader(
					new InputStreamReader(cl.getResourceAsStream(afterTable)));
			while ((line = afterTableHtmlData.readLine()) != null) {
				cache.add(line);
			}

			return cache.toArray(new String[0]);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}

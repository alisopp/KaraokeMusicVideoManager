package anonymerniklasistanonym.karaokemusicvideomanager.desktopclient.functions;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;

import anonymerniklasistanonym.karaokemusicvideomanager.desktopclient.libaries.ClassResourceReaderModule;
import anonymerniklasistanonym.karaokemusicvideomanager.desktopclient.libaries.JsonModule;
import anonymerniklasistanonym.karaokemusicvideomanager.desktopclient.objects.MusicVideo;

public class ExportMusicVideoData {

	/**
	 * Generate a HTML table string
	 * 
	 * @return table (String)
	 */
	public static String generateHtmlTable(Object[][] data, String[] columnNames) {

		return generateHtmlTableWithSearch(data, columnNames, "", "");
	}

	/**
	 * Generate a HTML table string
	 * 
	 * @return table (String)
	 */
	public static String generateHtmlTableWithSearch(Object[][] data, String[] columnNames, String tableClass,
			String rowClass) {

		StringBuilder sb = new StringBuilder("<table" + tableClass + ">");

		sb.append("<thead><tr>");

		for (int a = 0; a < columnNames.length; a++) {
			sb.append("<th>" + columnNames[a] + "</th>");
		}
		sb.append("</tr></thead><tbody>");

		sb.append(createHtmlTableRows(data, rowClass));

		sb.append("</tbody></table>");

		return sb.toString();
	}

	private static String createHtmlTableRows(Object[][] data, String rowClass) {

		if (data == null || data.length == 0) {
			System.out.println("There is no data");
			return "";
		}

		int columnNumber = data[0].length;
		int rowNumber = data.length;

		StringBuilder sb = new StringBuilder("");

		for (int a = 0; a < rowNumber; a++) {
			sb.append("<tr" + rowClass + ">");
			for (int b = 0; b < columnNumber; b++) {
				sb.append("<td>" + data[a][b] + "</td>");
			}
			sb.append("</tr>");
		}

		return sb.toString();

	}

	public static enum HTML_SITE {
		STATIC(0), SEARCH(1), PARTY(2);

		private final int value;

		HTML_SITE(final int newValue) {
			value = newValue;
		}
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

		if (data == null || data.length == 0) {
			System.err.println("There is no table data!");
		} else {
			for (int a = 0; a < data.length; a++) {
				sb.append("<tr>");
				for (int b = 0; b < columnNames.length; b++) {
					sb.append("<td>" + data[a][b] + "</td>");
				}
				sb.append("<td><button class=\"button\" name=\"index\" type=\"submit\" value=\"" + a + "," + data[a][1]
						+ "," + data[a][2] + "\">Sing!</button></td>");

				sb.append("</tr>\n");
			}
		}

		sb.append("</tbody></table>\n");

		sb.append("</form>");

		return sb.toString();
	}

	public static String generateHtmlSiteStatic(Object[][] data, String[] columnNames) {
		return generateHtmlSiteMain(data, columnNames, "websites/html_page_static.json", HTML_SITE.STATIC.value);
	}

	public static String generateHtmlSiteDynamic(Object[][] data, String[] columnNames) {
		return generateHtmlSiteMain(data, columnNames, "websites/html_page_searchable.json", HTML_SITE.SEARCH.value);
	}

	public static String generateHtmlSiteParty(Object[][] data, String[] columnNames) {
		return generateHtmlSiteMain(data, columnNames, "websites/html_page_party.json", HTML_SITE.PARTY.value);
	}

	private static String generateHtmlSiteMain(Object[][] data, String[] columnNames, String jsonHtmlPath,
			int typeOfHtml) {

		// save all lines in this array list
		StringBuilder webPage = new StringBuilder("");

		try {
			// class loader
			String jsonContent = ClassResourceReaderModule.getTextContent(jsonHtmlPath)[0];
			JsonObject jsonObject = JsonModule.loadJsonFromString(jsonContent);

			webPage.append(JsonModule.getValueString(jsonObject, "head"));

			// create specific table data for each type
			if (typeOfHtml == HTML_SITE.STATIC.value) {

				webPage.append(JsonModule.getValueString(jsonObject, "body-begin"));

				webPage.append(generateHtmlTable(data, columnNames));

			} else if (typeOfHtml == HTML_SITE.SEARCH.value) {

				webPage.append(JsonModule.getValueString(jsonObject, "body-begin"));

				webPage.append(
						generateHtmlTableWithSearch(data, columnNames, " id=\"search-table\"", " class=\"item\""));

			} else if (typeOfHtml == HTML_SITE.PARTY.value) {

				webPage.append(JsonModule.getValueString(jsonObject, "body-begin"));

				webPage.append(
						generateHtmlTableWithSearch(data, columnNames, " id=\"search-table\"", " class=\"item\""));
			}

			webPage.append(JsonModule.getValueString(jsonObject, "end"));

			return webPage.toString();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String exportJavascriptW3() {
		String jsonContent = ClassResourceReaderModule.getTextContent("websites/libraries/javascript.json")[0];
		JsonObject jsonObject = JsonModule.loadJsonFromString(jsonContent);

		return JsonModule.getValueString(jsonObject, "w3-js");
	}

	/**
	 * Convert all the data of the musicVideoList to a Object[][] for the JTable
	 * 
	 * @return Object[][] ([][#, artist, title])
	 */
	public Object[][] musicVideoListToObjectArray(MusicVideo[] musicVideoList, Integer rowNumber) {

		// create a array of objects with a object that has 3 entries
		Object[][] tableData = new Object[musicVideoList.length][rowNumber];

		for (int a = 0; a < musicVideoList.length; a++) {
			tableData[a][0] = a + 1;
			tableData[a][1] = musicVideoList[a].getArtist();
			tableData[a][2] = musicVideoList[a].getTitle();
		}

		return tableData;
	}

	/**
	 * Generate CSV table content
	 * 
	 * @return content (String[])
	 */
	public String generateCsvContent(MusicVideo[] musicVideoList, String[] columns) {

		int columnNumber = columns.length;
		int rowNumber = musicVideoList.length;

		Object[][] csvData = musicVideoListToObjectArray(musicVideoList, columnNumber);

		// save everything in one string
		StringBuilder csvDataString = new StringBuilder("");

		for (int a = 0; a < columnNumber - 1; a++) {
			csvDataString.append(columns[a] + ",");
		}
		csvDataString.append(columns[columnNumber - 1] + "\n");

		for (int a = 0; a < rowNumber; a++) {
			for (int b = 0; b < columnNumber - 1; b++) {
				csvDataString.append(csvData[a][b].toString() + ",");
			}
			csvDataString.append(csvData[a][columnNumber - 1].toString() + "\n");
		}

		return csvDataString.toString();
	}

	/**
	 * Generate CSV table content
	 * 
	 * @return content (String[])
	 */
	public String generateJsonContent(MusicVideo[] musicVideoList, String[] columns) {

		int columnNumber = columns.length;
		int rowNumber = musicVideoList.length;

		Object[][] csvData = musicVideoListToObjectArray(musicVideoList, columnNumber);

		// the whole object
		JsonObjectBuilder jsonObject = Json.createObjectBuilder();

		// add the columns
		JsonArrayBuilder tableHead = Json.createArrayBuilder();

		for (String column : columns) {
			tableHead.add(column);
		}

		jsonObject.add("table-head", tableHead);

		// add the data
		JsonArrayBuilder tableBody = Json.createArrayBuilder();

		for (int a = 0; a < rowNumber; a++) {
			JsonArrayBuilder tableRow = Json.createArrayBuilder();
			tableRow.add((Integer) csvData[a][0]);
			tableRow.add(csvData[a][1].toString());
			tableRow.add(csvData[a][2].toString());
			tableBody.add(tableRow);
		}

		jsonObject.add("table-body", tableBody);

		return JsonModule.dumpJsonObjectToString(jsonObject);
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}

package anonymerniklasistanonym.karaokemusicvideomanager.desktopclient.functions;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;

import anonymerniklasistanonym.karaokemusicvideomanager.desktopclient.libaries.JsonModule;
import anonymerniklasistanonym.karaokemusicvideomanager.desktopclient.objects.MusicVideo;
import anonymerniklasistanonym.karaokemusicvideomanager.desktopclient.objects.MusicVideoPlaylistElement;

public class ExportMusicVideoData {

	/**
	 * Generate a HTML table string for the static site
	 * 
	 * @return table (String)
	 */
	public static String generateHtmlTableDataStatic(Object[][] data) {

		return createHtmlTableRows(data, "", false);
	}

	/**
	 * Generate a HTML table string for the search site
	 * 
	 * @return table (String)
	 */
	public static String generateHtmlTableDataSearch(Object[][] data) {

		return createHtmlTableRows(data, " class=\"item\"", false);
	}

	/**
	 * Generate a HTML table string for the party site
	 * 
	 * @return table (String)
	 */
	public static String generateHtmlTableDataParty(Object[][] data) {

		return createHtmlTableRows(data, " class=\"item\"", true);
	}

	/**
	 * Generate a HTML table string (for a different use than this program)
	 * 
	 * @return table (String)
	 */
	public static String generateHtmlTable(Object[][] data, String[] columnNames, String tableClass, String rowClass) {

		StringBuilder sb = new StringBuilder("<table" + tableClass + ">");

		sb.append("<thead><tr>");

		for (int a = 0; a < columnNames.length; a++) {
			sb.append("<th>" + columnNames[a] + "</th>");
		}
		sb.append("</tr></thead><tbody>");

		sb.append(createHtmlTableRows(data, rowClass, false));

		sb.append("</tbody></table>");

		return sb.toString();
	}

	/**
	 * Main method to create the html table
	 * 
	 * @param data
	 *            (Object[][] | MusicVideoList)
	 * @param rowClass
	 *            (String | Add something like a class to list rows for search)
	 * @param useButtons
	 *            (Boolean | Convert title to button or not)
	 * @return TableBody (String)
	 */
	private static String createHtmlTableRows(Object[][] data, String rowClass, boolean useButtons) {

		if (data == null || data.length == 0) {
			System.out.println("There is no data");
			return "";
		}

		int columnNumber = data[0].length;
		int rowNumber = data.length;

		StringBuilder sb = new StringBuilder("");

		if (!useButtons) {
			for (int a = 0; a < rowNumber; a++) {
				sb.append("<tr" + rowClass + ">");
				for (int b = 0; b < columnNumber; b++) {
					sb.append("<td>" + data[a][b] + "</td>");
				}
				sb.append("</tr>");
			}
		} else {
			for (int a = 0; a < rowNumber; a++) {
				sb.append("<tr" + rowClass + ">");
				for (int b = 0; b < columnNumber - 1; b++) {
					sb.append("<td>" + data[a][b] + "</td>");
				}
				sb.append("<td><button name=\"index\" type=\"submit\" value=\"" + data[a][0] + ',' + data[a][1] + ','
						+ data[a][2] + "\">" + data[a][2] + "</button></td>");
				sb.append("</tr>");
			}
		}

		return sb.toString();

	}

	/**
	 * Convert all the data of the musicVideoList to a Object[][] for the JTable
	 * 
	 * @return Object[][] ([][#, artist, title])
	 */
	public static Object[][] musicVideoListToObjectArray(MusicVideo[] musicVideoList, Integer rowNumber) {

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
	public static String generateCsvContent(MusicVideo[] musicVideoList, String[] columns) {

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
	 * Generate JSON table content
	 * 
	 * @return content (String)
	 */
	public static String generateJsonContentTable(MusicVideo[] musicVideoList, String[] columns) {

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

	/**
	 * Generate JSON for a music video playlist
	 * 
	 * @return content (String)
	 */
	public static String generateJsonContentPlaylist(MusicVideoPlaylistElement[] playList) {

		if (playList == null) {
			return null;
		}

		// the whole object
		JsonObjectBuilder jsonObject = Json.createObjectBuilder();

		// add the data
		JsonArrayBuilder tableBody = Json.createArrayBuilder();

		for (MusicVideoPlaylistElement entry : playList) {
			JsonObjectBuilder entryArray = Json.createObjectBuilder();
			entryArray.add("file-path", entry.getMusicVideoFile().getPath().toAbsolutePath().toString());
			entryArray.add("author", entry.getAuthor());
			entryArray.add("comment", entry.getComment());
			entryArray.add("time", entry.getUnixTime());
			entryArray.add("created-locally", entry.getCreatedLocally());
			tableBody.add(entryArray);
		}

		jsonObject.add("playlist", tableBody);

		return JsonModule.dumpJsonObjectToString(jsonObject);
	}

}

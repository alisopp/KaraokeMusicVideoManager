package anonymerniklasistanonym.karaokemusicvideomanager.desktopclient.handler;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;

import anonymerniklasistanonym.karaokemusicvideomanager.desktopclient.libaries.JsonModule;
import anonymerniklasistanonym.karaokemusicvideomanager.desktopclient.objects.MusicVideo;
import anonymerniklasistanonym.karaokemusicvideomanager.desktopclient.objects.MusicVideoPlaylistElement;

/**
 * Class that handles the export of the music video list data
 * 
 * @author AnonymerNiklasistanonym <niklas.mikeler@gmail.com> | <a href=
 *         "https://github.com/AnonymerNiklasistanonym">https://github.com/AnonymerNiklasistanonym</a>
 */
public class MusicVideoDataExportHandler {

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
	public static Object[][] musicVideoListToObjectArray(MusicVideo[] musicVideoList) {

		if (musicVideoList == null) {
			System.err.println("Given list was null!");
			return null;
		}

		// create a array of objects with a object that has 3 entries
		Object[][] tableData = new Object[musicVideoList.length][3];

		for (int i = 0; i < musicVideoList.length; i++) {
			tableData[i][0] = i + 1;
			tableData[i][1] = musicVideoList[i].getArtist();
			tableData[i][2] = musicVideoList[i].getTitle();
		}

		return tableData;
	}

	/**
	 * Generate CSV table content
	 * 
	 * @return content (String[])
	 */
	public static String generateCsvContent(MusicVideo[] musicVideoList, String[] columns) {

		// save everything in one string
		StringBuilder csvDataString = new StringBuilder("");

		// add the table head
		csvDataString.append(columns[0] + ",");
		csvDataString.append(columns[1] + ",");
		csvDataString.append(columns[2] + "\n");

		// add the table content
		for (Object[] musicVideo : musicVideoListToObjectArray(musicVideoList)) {
			csvDataString.append(musicVideo[0] + ",");
			csvDataString.append(musicVideo[1] + ",");
			csvDataString.append(musicVideo[2] + "\n");
		}

		return csvDataString.toString();
	}

	/**
	 * Generate JSON table content
	 * 
	 * @return content (String)
	 */
	public static String generateJsonContentTable(MusicVideo[] musicVideoList) {

		// the whole object
		JsonObjectBuilder jsonObject = Json.createObjectBuilder();

		// the table data
		JsonArrayBuilder tableBody = Json.createArrayBuilder();

		// every music video
		for (int i = 0; i < musicVideoList.length; i++) {

			// a music video element
			JsonObjectBuilder tableRow = Json.createObjectBuilder();
			tableRow.add("artist", musicVideoList[i].getArtist());
			tableRow.add("title", musicVideoList[i].getTitle());
			tableRow.add("path", musicVideoList[i].getPath().toString());

			// add entry to table data
			tableBody.add(tableRow);
		}

		// add table data to the whole object
		jsonObject.add("music-videos", tableBody);

		// return the whole object
		return JsonModule.toString(jsonObject);
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

		// the playlist entries object
		JsonArrayBuilder playlist = Json.createArrayBuilder();

		for (MusicVideoPlaylistElement entry : playList) {
			JsonObjectBuilder entryArray = Json.createObjectBuilder();
			entryArray.add("file-path", entry.getMusicVideoFile().getPath().toString());
			entryArray.add("author", entry.getAuthor());
			entryArray.add("comment", entry.getComment());
			entryArray.add("time", entry.getUnixTime());
			entryArray.add("created-locally", entry.getCreatedLocally());
			playlist.add(entryArray);
		}

		jsonObject.add("playlist", playlist);

		return JsonModule.toString(jsonObject);
	}

}

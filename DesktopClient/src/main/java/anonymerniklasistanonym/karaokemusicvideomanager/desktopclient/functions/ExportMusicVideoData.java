package anonymerniklasistanonym.karaokemusicvideomanager.desktopclient.functions;

public class ExportMusicVideoData {

	/**
	 * Generate a HTML table
	 * 
	 * @return table (String)
	 */
	public String generateHtmlTable(Object[][] data, String[] columnNames) {

		StringBuilder sb = new StringBuilder("<table>");
		int columnNumber = columnNames.length;
		int rowNumber = data.length;

		sb.append("<thead><tr>");
		for (int a = 0; a < columnNumber; a++) {
			sb.append("<th>" + columnNames[a] + "</th>");
		}
		sb.append("</tr></thead><tbody>");

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

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}

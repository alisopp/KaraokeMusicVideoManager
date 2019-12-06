package anonymerniklasistanonym.karaokemusicvideomanager.desktopclient.generator;

import javax.json.JsonObject;

import anonymerniklasistanonym.karaokemusicvideomanager.desktopclient.handler.MusicVideoDataExportHandler;
import anonymerniklasistanonym.karaokemusicvideomanager.desktopclient.libaries.ClassResourceReaderModule;
import anonymerniklasistanonym.karaokemusicvideomanager.desktopclient.libaries.JsonModule;
import anonymerniklasistanonym.karaokemusicvideomanager.desktopclient.translations.Internationalization;

public class HtmlContentStaticGenerator extends HtmlContentGenerator {

	@Override
	public String generateHtml(String phpDirectoryName, Object[][] musicVideoTable, String[] columnNames) {

		// string builder for the whole site
		StringBuilder htmlStatic = new StringBuilder("");

		// json data html file
		JsonObject htmlJsonContent = JsonModule
				.loadJsonFromString(ClassResourceReaderModule.getTextContent("websiteData/html.json")[0]);
		JsonObject cssJsonContent = JsonModule
				.loadJsonFromString(ClassResourceReaderModule.getTextContent("websiteData/css.json")[0]);

		// add default head
		htmlStatic.append("<!DOCTYPE html><html lang=\"" + Internationalization.getLocaleString() + "\"><head>");
		// add generic head
		htmlStatic.append(JsonModule.getValueString(htmlJsonContent, "head"));
		// add custom head for static
		htmlStatic.append(JsonModule.getValueString(htmlJsonContent, "custom-head-html_static"));

		// add title and more
		htmlStatic.append(generateHeadName());

		// add links to all the images
		htmlStatic.append(generateFaviconLinks(""));

		// add css
		htmlStatic.append("<style>");
		htmlStatic.append(JsonModule.getValueString(cssJsonContent, "styles_static"));

		// close head and open body
		htmlStatic.append("</style></head><body>");
		// add section begin: section-start-html_static
		htmlStatic.append(JsonModule.getValueString(htmlJsonContent, "section-start-html_static"));

		// add table header
		String tableHeader = JsonModule.getValueString(htmlJsonContent, "table-header-html_static");
		tableHeader = tableHeader.replaceFirst("#", columnNames[0]).replaceFirst("#", columnNames[0])
				.replaceFirst("Artist", Internationalization.translate(columnNames[1]))
				.replaceFirst("Title", Internationalization.translate(columnNames[2]));
		htmlStatic.append(tableHeader);

		// table data
		htmlStatic.append(MusicVideoDataExportHandler.generateHtmlTableDataStatic(musicVideoTable));

		// add after table data
		htmlStatic.append(JsonModule.getValueString(htmlJsonContent, "after-table-html_static"));

		htmlStatic.append("</body></html>");

		return htmlStatic.toString();
	}

	@Override
	public boolean isTypeParty() {
		return false;
	}
}

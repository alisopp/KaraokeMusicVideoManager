package anonymerniklasistanonym.karaokemusicvideomanager.desktopclient.generator;

import javax.json.JsonObject;

import anonymerniklasistanonym.karaokemusicvideomanager.desktopclient.handler.MusicVideoDataExportHandler;
import anonymerniklasistanonym.karaokemusicvideomanager.desktopclient.libaries.ClassResourceReaderModule;
import anonymerniklasistanonym.karaokemusicvideomanager.desktopclient.libaries.JsonModule;
import anonymerniklasistanonym.karaokemusicvideomanager.desktopclient.translations.Internationalization;

public class HtmlContentPartyGenerator extends HtmlContentGenerator {

	private boolean withVotes = false;
	
	public HtmlContentPartyGenerator() {
		
	}
	
	public HtmlContentPartyGenerator(boolean votes) {
		this.withVotes = votes;
	}
	
	public boolean getWithVotes() {
		return withVotes;
	}
	
	@Override
	public String generateHtml(String phpDirectoryName, Object[][] musicVideoTable, String[] columnNames) {
		// string builder for the whole site
		StringBuilder htmlStatic = new StringBuilder("");

		// json data html file
		JsonObject htmlJsonContent = JsonModule
				.loadJsonFromString(ClassResourceReaderModule.getTextContent("websiteData/html.json")[0]);
		JsonObject cssJsonContent = JsonModule
				.loadJsonFromString(ClassResourceReaderModule.getTextContent("websiteData/css.json")[0]);
		JsonObject jsJsonContent = JsonModule
				.loadJsonFromString(ClassResourceReaderModule.getTextContent("websiteData/js.json")[0]);

		// add default head
		htmlStatic.append("<!DOCTYPE html><html lang=\"" + Internationalization.getLocaleString() + "\"><head>");
		// add generic head
		htmlStatic.append(JsonModule.getValueString(htmlJsonContent, "head"));
		// add custom head for static
		htmlStatic.append(JsonModule.getValueString(htmlJsonContent, "custom-head-html_party"));

		// add title and more
		htmlStatic.append(generateHeadName());

		// add links to all the images
		htmlStatic.append(generateFaviconLinks(""));

		// add js
		htmlStatic.append("<script>");
		htmlStatic.append(JsonModule.getValueString(jsJsonContent, "w3"));

		// add css
		htmlStatic.append("</script><style>");
		htmlStatic.append(JsonModule.getValueString(cssJsonContent, "styles_static"));
		htmlStatic.append(JsonModule.getValueString(cssJsonContent, "styles_searchable"));

		htmlStatic.append(JsonModule.getValueString(cssJsonContent, "styles_party"));
		
		// close head and open body
		htmlStatic.append("</style></head><body>");

		htmlStatic.append(JsonModule.getValueString(htmlJsonContent, "floating-button-html_party")
			.replace("html_party_live.html", "index.php")
			.replace("Party Playlist", Internationalization.translate("Party Playlist")));
		
		// add section begin
		htmlStatic.append(JsonModule.getValueString(htmlJsonContent, "section-start-html_party"));

		// add the overlay / table header
		htmlStatic.append(JsonModule.getValueString(htmlJsonContent, "overlay-html_party")
				.replaceFirst("#", columnNames[0])
				.replaceFirst("Search for artist/title",
						Internationalization.translate("Search for") + " " + Internationalization.translate("artist")
								+ "/" + Internationalization.translate("title") + "/"
								+ Internationalization.translate("nummer"))
				.replaceFirst("Title", Internationalization.translate(columnNames[2]))
				.replaceFirst("Artist", Internationalization.translate(columnNames[1])));

		// add the second table header (for print)
		htmlStatic.append(JsonModule.getValueString(htmlJsonContent, "table-header-html_party")
				.replaceFirst("#", columnNames[0])
				.replaceFirst("Artist", Internationalization.translate(columnNames[1]))
				.replaceFirst("Title", Internationalization.translate(columnNames[2])));

		htmlStatic.append(JsonModule.getValueString(htmlJsonContent, "form-start-html_party")
				.replaceFirst("../php/form.php", phpDirectoryName + "/form.php"));
		
		// table data
		htmlStatic.append(MusicVideoDataExportHandler.generateHtmlTableDataParty(musicVideoTable));
		
		// add after table data
		htmlStatic.append(JsonModule.getValueString(htmlJsonContent, "before-form-html_party"));

		htmlStatic.append(JsonModule.getValueString(htmlJsonContent, "form-end-html_party"));
		
		htmlStatic.append(JsonModule.getValueString(htmlJsonContent, "after-table-html_party"));

		htmlStatic.append("</body></html>");

		return htmlStatic.toString();
	}

	@Override
	public boolean isTypeParty() {
		return true;
	}

}

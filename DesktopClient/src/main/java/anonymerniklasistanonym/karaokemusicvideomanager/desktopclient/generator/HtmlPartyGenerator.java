package anonymerniklasistanonym.karaokemusicvideomanager.desktopclient.generator;

import javax.json.JsonObject;

import anonymerniklasistanonym.karaokemusicvideomanager.desktopclient.libaries.ClassResourceReaderModule;
import anonymerniklasistanonym.karaokemusicvideomanager.desktopclient.libaries.JsonModule;
import anonymerniklasistanonym.karaokemusicvideomanager.desktopclient.translations.Internationalization;

public class HtmlPartyGenerator extends HtmlGenerator {

	public String generateHtmlPartyVote() {
		// string builder for the whole site
		StringBuilder phpProcess = new StringBuilder("");

		JsonObject phpJsonContent = JsonModule
				.loadJsonFromString(ClassResourceReaderModule.getTextContent("websiteData/php.json")[0]);

		// add php before everything
		phpProcess.append(JsonModule.getValueString(phpJsonContent, "php-data-vote"));

		return phpProcess.toString();
	}

	public String generateHtmlPartyView(boolean withVotes) {
		// string builder for the whole site
		StringBuilder phpProcess = new StringBuilder("");

		JsonObject phpJsonContent = JsonModule
				.loadJsonFromString(ClassResourceReaderModule.getTextContent("websiteData/php.json")[0]);

		// add php before everything
		if (withVotes) {
			phpProcess.append(JsonModule.getValueString(phpJsonContent, "php-data-live")
					.replace("Vote", Internationalization.translate("Vote"))
					.replace("from", Internationalization.translate("from")));
		} else {
			phpProcess.append(JsonModule.getValueString(phpJsonContent, "php-data-live_without_votes").replace("from",
					Internationalization.translate("from")));
		}

		return phpProcess.toString();
	}

	public String generateHtmlPartyProcess() {

		// string builder for the whole site
		StringBuilder phpProcess = new StringBuilder("");

		JsonObject phpJsonContent = JsonModule
				.loadJsonFromString(ClassResourceReaderModule.getTextContent("websiteData/php.json")[0]);

		// add php before everything
		phpProcess.append(JsonModule.getValueString(phpJsonContent, "before-link-process")
				.replaceAll("html/html_party_live.html", "index.php"));
		phpProcess.append(JsonModule.getValueString(phpJsonContent, "link-process").replace("html/html_party_live.html",
				"index.php"));
		phpProcess.append(JsonModule.getValueString(phpJsonContent, "after-link-process"));

		return phpProcess.toString();
	}

	public String generateHtmlPartyPlaylist(String phpDirectoryName, boolean floatingButton, boolean withVotes) {

		// string builder for the whole site
		StringBuilder phpPlaylist = new StringBuilder("");

		// json data html file
		JsonObject htmlJsonContent = JsonModule
				.loadJsonFromString(ClassResourceReaderModule.getTextContent("websiteData/html.json")[0]);
		JsonObject cssJsonContent = JsonModule
				.loadJsonFromString(ClassResourceReaderModule.getTextContent("websiteData/css.json")[0]);
		JsonObject phpJsonContent = JsonModule
				.loadJsonFromString(ClassResourceReaderModule.getTextContent("websiteData/php.json")[0]);
		JsonObject jsJsonContent = JsonModule
				.loadJsonFromString(ClassResourceReaderModule.getTextContent("websiteData/js.json")[0]);

		// add default head
		phpPlaylist.append("<!DOCTYPE html><html lang=\"" + Internationalization.getLocaleString() + "\"><head>");
		// add generic head
		phpPlaylist.append(JsonModule.getValueString(htmlJsonContent, "head"));
		// add custom head for static
		phpPlaylist.append(JsonModule.getValueString(htmlJsonContent, "custom-head-html_party_live"));		

		// add title and more
		phpPlaylist.append(generateHeadName());

		// add links to all the images
		phpPlaylist.append(generateFaviconLinks(""));

		// add js
		phpPlaylist.append("<script>");
		phpPlaylist.append(JsonModule.getValueString(jsJsonContent, "jquery.min"));

		// add css
		phpPlaylist.append("</script><style>");
		phpPlaylist.append(JsonModule.getValueString(cssJsonContent, "styles_party_live"));

		// close head and open body
		phpPlaylist.append("</style></head><body>");

		if (floatingButton) {
			phpPlaylist.append(JsonModule.getValueString(htmlJsonContent, "floating-button-html_party_live")
					.replace("html_party.html", "list.html")
					.replace("View Song List", Internationalization.translate("View Song List")));
		}
		phpPlaylist.append(JsonModule.getValueString(htmlJsonContent, "section-start-html_party_live"));

		if (withVotes) {
			phpPlaylist.append(JsonModule.getValueString(phpJsonContent, "php-data-live")
					.replace("path = \"./\"", "path = \"php/\"").replace("Vote", Internationalization.translate("Vote"))
					.replace("from", Internationalization.translate("from")));
		} else {
			phpPlaylist.append(JsonModule.getValueString(phpJsonContent, "php-data-live_without_votes")
					.replace("path = \"./\"", "path = \"php/\"")
					.replace("from", Internationalization.translate("from")));
		}

		phpPlaylist.append(JsonModule.getValueString(htmlJsonContent, "after-table-html_party_live"));

		if (phpDirectoryName != null) {
			phpPlaylist.append(JsonModule.getValueString(htmlJsonContent, "repeat-script-html_party_live")
					.replace("../php/live.php", phpDirectoryName + "/live.php"));
		}

		phpPlaylist.append("</body></html>");
		return phpPlaylist.toString();
	}

	public String generateHtmlPartyForm() {
		// string builder for the whole site
		StringBuilder phpForm = new StringBuilder("");

		// JSON data HTML file
		JsonObject htmlJsonContent = JsonModule
				.loadJsonFromString(ClassResourceReaderModule.getTextContent("websiteData/html.json")[0]);		
		JsonObject phpJsonContent = JsonModule
				.loadJsonFromString(ClassResourceReaderModule.getTextContent("websiteData/php.json")[0]);
		JsonObject cssJsonContent = JsonModule
				.loadJsonFromString(ClassResourceReaderModule.getTextContent("websiteData/css.json")[0]);

		// add PHP before everything
		phpForm.append(JsonModule.getValueString(phpJsonContent, "before-html-form"));

		// add default head
		phpForm.append("<!DOCTYPE html><html lang=\"" + Internationalization.getLocaleString() + "\"><head>");
		// add generic head
		phpForm.append(JsonModule.getValueString(htmlJsonContent, "head"));
		// add custom head for php form
		phpForm.append(JsonModule.getValueString(phpJsonContent, "custom-head-form"));

		// add title and more
		phpForm.append(generateHeadName());

		// add links to all the images
		phpForm.append(generateFaviconLinks("../"));

		// add CSS
		phpForm.append("<style>");
		phpForm.append(JsonModule.getValueString(cssJsonContent, "styles_php_form"));

		// close head and open body
		phpForm.append("</style></head><body>");

		// add floating button for PHP form
		phpForm.append(JsonModule.getValueString(phpJsonContent, "floating-button-form"));

		phpForm.append(JsonModule.getValueString(phpJsonContent, "before-title-form"));
		phpForm.append(Internationalization.translate("Submit this song to the playlist") + ":");
		phpForm.append(JsonModule.getValueString(phpJsonContent, "before-artist-form"));
		phpForm.append(Internationalization.translate("from") + "&nbsp;");
		phpForm.append(JsonModule.getValueString(phpJsonContent, "before-input-form"));
		phpForm.append(JsonModule.getValueString(phpJsonContent, "input-form")
				.replace("Your name/s", Internationalization.translate("Your names"))
				.replace("Your comment", Internationalization.translate("Your comment")));
		phpForm.append(JsonModule.getValueString(phpJsonContent, "before-submit-form"));
		phpForm.append(JsonModule.getValueString(phpJsonContent, "submit-form").replace("Submit",
				Internationalization.translate("Submit")));
		phpForm.append(JsonModule.getValueString(phpJsonContent, "after-submit-form"));

		phpForm.append("</body></html>");

		return phpForm.toString();
	}
}
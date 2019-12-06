package anonymerniklasistanonym.karaokemusicvideomanager.desktopclient.generator;

import anonymerniklasistanonym.karaokemusicvideomanager.desktopclient.gui.Main;

public class HtmlGenerator {
	
	protected String generateHeadName() {

		// string builder for all links
		StringBuilder specialHead = new StringBuilder("");

		String name = Main.PROGRAM_NAME;
		specialHead.append("<title>" + name + "</title>");
		specialHead.append("<meta name=\"apple-mobile-web-app-title\" content=\"" + name + "\">");
		specialHead.append("<meta name=\"apple-mobile-web-app-capable\" content=\"yes\">");
		specialHead.append("<meta name=\"application-name\" content=\"" + name + "\">");

		return specialHead.toString();
	}
	
	protected String generateFaviconLinks(String getBack) {

		// string builder for all links
		StringBuilder faviconLinks = new StringBuilder("");

		// firefox svg link
		faviconLinks.append("<link rel=\"icon\" type=\"image/svg+xml\" href=\"" + getBack + "favicons/favicon.svg\">");

		// apple links
		faviconLinks.append("<link rel=\"apple-touch-icon\" href=\"" + getBack + "favicons/favicon-180x180.png\">");
		faviconLinks.append("<link rel=\"mask-icon\" href=\"" + getBack + "favicons/favicon.svg\" color=\"#000000\">");

		// add all .png images
		Integer[] sizes = { 16, 32, 48, 64, 94, 128, 160, 180, 194, 256, 512 };

		for (Integer size : sizes) {
			faviconLinks.append("<link rel=\"icon\" type=\"image/png\" href=\"" + getBack + "favicons/favicon-" + size
					+ "x" + size + ".png\" sizes=\"" + size + "x" + size + "\">");
		}

		return faviconLinks.toString();
	}
}

package anonymerniklasistanonym.karaokemusicvideomanager.desktopclient.generator;

public abstract class HtmlContentGenerator extends HtmlGenerator {

	public abstract String generateHtml(String phpDirectoryName, 
			Object[][] musicVideoTable, String[] columnNames);
	
	public abstract boolean isTypeParty();
}

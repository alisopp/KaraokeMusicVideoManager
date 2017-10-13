package anonymerniklasistanonym.karaokemusicvideomanager.desktopclient.handler;

import java.util.Locale;
import java.util.ResourceBundle;

public class LanguageHandler {

	private Locale currentLocale;

	private ResourceBundle currentResourceBundle;

	public LanguageHandler() {
		this.setCurrentLocale(Locale.getDefault());
	}

	public Locale getCurrentLocale() {
		return this.currentLocale;
	}

	public void setCurrentLocale(Locale currentLocale) {
		this.currentLocale = currentLocale;
		System.out.println(currentLocale);
		this.currentResourceBundle = ResourceBundle.getBundle("languages/MessagesBundle", currentLocale);

	}

	public String getText(String text) {

		String newText = this.currentResourceBundle.getString(text);

		if (newText == null || newText.isEmpty()) {
			return text;
		} else {
			return newText;
		}
	}

	public ResourceBundle getCurrentResourceBundle() {
		return currentResourceBundle;
	}

	public void setCurrentResourceBundle(ResourceBundle currentResourceBundle) {
		this.currentResourceBundle = currentResourceBundle;
	}

}

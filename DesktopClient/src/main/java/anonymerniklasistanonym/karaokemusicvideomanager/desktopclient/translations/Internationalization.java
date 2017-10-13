package anonymerniklasistanonym.karaokemusicvideomanager.desktopclient.translations;

import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class Internationalization {

	private static String bundleName = "anonymerniklasistanonym/karaokemusicvideomanager/desktopclient/translations/ApplicationMessages";

	public static ResourceBundle bundle = ResourceBundle.getBundle(bundleName);

	private static Locale locale = Locale.getDefault();

	public static void setBundle() {
		bundle = ResourceBundle.getBundle(bundleName);
	}

	public static void setBundle(Locale localeNew) {
		try {
			locale = localeNew;
			bundle = ResourceBundle.getBundle(bundleName, locale);
		} catch (NullPointerException n) {
			n.printStackTrace();
			bundle = ResourceBundle.getBundle(bundleName);
		} catch (MissingResourceException m) {
			m.printStackTrace();
			bundle = ResourceBundle.getBundle(bundleName);
		}
	}

	public static String translate(String text) {
		try {
			String translation = bundle.getString(text);

			if (translation != null && !translation.isEmpty()) {
				return translation;
			}

		} catch (NullPointerException e) {
			System.err.println("Key \"" + text + "\" is null!");
		} catch (MissingResourceException f) {
			System.err.println("No object could be found for the key \"" + text + "\"!");
		} catch (ClassCastException g) {
			System.err.println("The found object for the key \"" + text + "\" is not a String!");
		}

		return text;
	}

	public static String getLocaleString() {
		return locale.getLanguage();
	}

}

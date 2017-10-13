package anonymerniklasistanonym.karaokemusicvideomanager.desktopclient;

import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class JavaInternationalizationExample {

	private static String bundleName = "anonymerniklasistanonym/karaokemusicvideomanager/desktopclient/translations/ApplicationMessages";

	public static ResourceBundle bundle = ResourceBundle.getBundle(bundleName);

	public static void main(String[] args) {
		// default locale
		ResourceBundle bundle = ResourceBundle.getBundle(
				"anonymerniklasistanonym/karaokemusicvideomanager/desktopclient/translations/ApplicationMessages");
		// Get ResourceBundle with Locale that are already defined
		ResourceBundle bundleFR = ResourceBundle.getBundle(
				"anonymerniklasistanonym/karaokemusicvideomanager/desktopclient/translations/ApplicationMessages",
				Locale.FRANCE);
		// Get resource bundle when Locale needs to be created
		ResourceBundle bundleSWE = ResourceBundle.getBundle(
				"anonymerniklasistanonym/karaokemusicvideomanager/desktopclient/translations/ApplicationMessages",
				new Locale("sv", "SE"));

		// lets print some messages
		printMessages(bundle);
		printMessages(bundleFR);
		printMessages(bundleSWE);

		setBundle(new Locale("sv", "SE"));

		System.out.println(translate("CountryName"));

		setBundle(Locale.FRANCE);

		System.out.println(translate("CountryName"));
		System.out.println(translate("CountryNameaaaa"));

	}

	public static void setBundle() {
		bundle = ResourceBundle.getBundle(bundleName);
	}

	public static void setBundle(Locale locale) {
		bundle = ResourceBundle.getBundle(bundleName, locale);
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

	private static void printMessages(ResourceBundle bundle) {
		System.out.println(bundle.getString("CountryName"));
		System.out.println(bundle.getString("CurrencyCode"));
	}

}

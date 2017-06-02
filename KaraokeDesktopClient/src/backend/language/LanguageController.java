package backend.language;

import java.util.Enumeration;
import java.util.Locale;
import java.util.ResourceBundle;

public class LanguageController {

	private static Locale currentLanguage = Locale.getDefault();
	private static ResourceBundle rb = ResourceBundle.getBundle("SystemMessages", currentLanguage);

	/**
	 * Get the current language back
	 * 
	 * @return current language (Locale)
	 */
	public static Locale getCurrentLanguage() {
		return currentLanguage;
	}

	/**
	 * Set the current Language of the LanguageController
	 * 
	 * @param currentLanguage
	 *            (new language | Locale)
	 */
	public static void setCurrentLanguage(Locale currentLanguage) {
		LanguageController.currentLanguage = currentLanguage;

		System.out.println();
		if (currentLanguage.equals(Locale.GERMAN)) {
			System.out.println("Language is GERMAN!");
		} else if (currentLanguage.equals(Locale.ENGLISH)) {
			System.out.println("Language is US Enlgish!");
		}

		System.out.println(currentLanguage);

		rb = ResourceBundle.getBundle("SystemMessages", currentLanguage);

		printWords();
	}

	/**
	 * Print out all the actual words from the current ResourceBundle
	 */
	public static void printWords() {
		Enumeration<String> keys = rb.getKeys();
		while (keys.hasMoreElements()) {
			String key = keys.nextElement();
			String value = getTranslation(key);
			System.out.println(key + ": " + value);
		}
	}

	/**
	 * Search key in ResourceBundle and return the proper String of the current
	 * set language
	 * 
	 * @param a
	 *            (Search String | String)
	 * @return the proper String of the current set language (String)
	 */
	public static String getTranslation(String a) {

		boolean good = false;

		Enumeration<String> keys = rb.getKeys();
		while (keys.hasMoreElements()) {
			String key = keys.nextElement();

			if (key.equals(a)) {
				good = true;
			}
		}

		if (currentLanguage == Locale.ENGLISH) {
			good = false;
		}

		if (good) {
			return rb.getString(a);
		} else {
			System.err.println("Error at: " + "\"" + a + "\"");
			return a;
		}
	}

}
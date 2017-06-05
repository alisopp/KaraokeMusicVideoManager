package backend.language;

import java.util.Enumeration;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

/**
 * This class contains static methods for translate any kind of GUI text into
 * the user wanted language
 * 
 * @author Niklas | https://github.com/AnonymerNiklasistanonym
 * @version 0.7 (beta)
 *
 */
public class LanguageController {

	private static Locale currentLanguage = Locale.getDefault();

	private static ResourceBundle rbEnglish = ResourceBundle.getBundle("SystemMessages", Locale.ENGLISH);
	private static ResourceBundle rbGerman = ResourceBundle.getBundle("SystemMessages", Locale.GERMAN);

	private static ResourceBundle currentResourceBundle = rbEnglish;

	private static boolean german = false;

	/**
	 * Get the current language back
	 * 
	 * @return current language (Locale)
	 */
	public static Locale getCurrentLanguage() {
		return currentLanguage;
	}

	/**
	 * Set the ResourceBundle to the default language
	 */
	public static void setDefaultLanguage() {
		setCurrentLanguageRb(Locale.getDefault());
	}

	/**
	 * Set the current Language of the LanguageController
	 * 
	 * @param currentLanguage
	 *            (new language | Locale)
	 */
	public static void setCurrentLanguage(Locale currentLang) {
		currentLanguage = currentLang;

		if (currentLanguage.equals(Locale.GERMAN) || currentLanguage.toString().equals("de_DE")) {
			System.out.println("Language is at the next start GERMAN!");

		} else {
			System.out.println("Language is at the next start English!");

		}
	}

	public static void setCurrentLanguageRb(Locale currentLang) {
		currentLanguage = currentLang;

		if (currentLanguage.equals(Locale.GERMAN) || currentLanguage.toString().equals("de_DE")) {
			System.out.println("Language is GERMAN!");
			currentResourceBundle = rbGerman;
			german = true;
		} else {
			System.out.println("Language is US English!");
			currentResourceBundle = rbEnglish;
			german = false;
		}
	}

	public static String getLanguageToSpeech(Locale lang) {
		String language;
		if (lang.equals(Locale.GERMAN) || lang.toString().equals("de_DE")) {
			language = getTranslation("German");
		} else {
			language = getTranslation("English");
		}
		return language;
	}

	public static boolean changeLanguageRestart(Locale currentLang) {

		if (currentLang != currentLanguage) {
			int dialogResult = JOptionPane.showConfirmDialog(null,
					getTranslation("Do you really want to change the language of the program from") + " "
							+ getLanguageToSpeech(currentLanguage) + " " + getTranslation("to") + " "
							+ getLanguageToSpeech(currentLang) + "?",
					getTranslation("Warning"), JOptionPane.YES_NO_OPTION);

			if (dialogResult == JOptionPane.YES_OPTION) {

				setCurrentLanguage(currentLang);
				String option1 = getTranslation("now automatically");
				String option2 = getTranslation("later manually");
				Object[] options1 = { option1, option2 };
				dialogResult = JOptionPane.showOptionDialog(null,
						getTranslation(
								"If you want to continue you either have to save your current configuration automatically now or by yourself later in the sub menu More")
								+ ".",
						getTranslation("Important information"), JOptionPane.YES_NO_OPTION,
						JOptionPane.QUESTION_MESSAGE, null, options1, null);

				if (dialogResult == JOptionPane.YES_OPTION) {
					return true;
				}

				JOptionPane.showMessageDialog(null,
						getTranslation("Please restart the program after this step to view it in") + " "
								+ getLanguageToSpeech(currentLang) + "!");
			}
		}
		return false;
	}

	/**
	 * Print out all the actual words from the current ResourceBundle
	 */
	public static void printWords(ResourceBundle rb) {
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

		if (german) {
			boolean good = false;

			Enumeration<String> keys = currentResourceBundle.getKeys();
			while (keys.hasMoreElements()) {
				String key = keys.nextElement();

				if (key.equals(a)) {
					good = true;
				}
			}

			if (good) {
				return currentResourceBundle.getString(a);
			} else {
				System.err.println("Error at: " + "\"" + a + "\"");
				return a;
			}
		} else {
			return a;
		}
	}

}
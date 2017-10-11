package anonymerniklasistanonym.karaokemusicvideomanager.desktopclient.gui.tables;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Class for table elements: Wrong formatted files of music video files
 * 
 * @author AnonymerNiklasistanonym <niklas.mikeler@gmail.com> | <a href=
 *         "https://github.com/AnonymerNiklasistanonym">https://github.com/AnonymerNiklasistanonym</a>
 */
public class WrongFormattedFilesTableView {

	/**
	 * The path to the file
	 */
	private final StringProperty filePath;

	/**
	 * WrongFormattedFilesTableView constructor
	 * 
	 * @param filePath
	 *            (String | The path to the file)
	 */
	public WrongFormattedFilesTableView(String filePath) {
		this.filePath = new SimpleStringProperty(filePath);
	}

	/**
	 * Get the wrong formatted files path
	 * 
	 * @return filePath (StringProperty)
	 */
	public StringProperty getFilePathProperty() {
		return this.filePath;
	}

	/**
	 * Get the wrong formatted files path
	 * 
	 * @return filePath (String)
	 */
	public String getFilePath() {
		return this.filePath.get();
	}

}

package anonymerniklasistanonym.karaokemusicvideomanager.desktopclient.gui.tables;

import java.nio.file.Path;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Class for table elements: Directories of music video files
 * 
 * @author AnonymerNiklasistanonym <niklas.mikeler@gmail.com> | <a href=
 *         "https://github.com/AnonymerNiklasistanonym">https://github.com/AnonymerNiklasistanonym</a>
 */
public class DirectoryPathTableView {

	/**
	 * directory path as a String
	 */
	private final StringProperty filePath;

	/**
	 * @param filePath
	 *            (String | absolute path to directory)
	 */
	public DirectoryPathTableView(String filePath) {
		this.filePath = new SimpleStringProperty(filePath);
	}

	/**
	 * @param filePath
	 *            (Path | absolute path to directory)
	 */
	public DirectoryPathTableView(Path filePath) {
		this.filePath = new SimpleStringProperty(filePath.toString());
	}

	/**
	 * @return get table usable String of file path
	 */
	public StringProperty getFilePathProperty() {
		return filePath;
	}

	/**
	 * @return get directory path as a String (String)
	 */
	public String getFilePath() {
		return filePath.get();
	}

}

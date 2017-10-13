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
public class MusicVideoSourceDirectoriesTableView {

	/**
	 * The path of the directory as String
	 */
	private final StringProperty filePath;

	/**
	 * MusicVideoSourceDirectoriesTableView constructor #1
	 * 
	 * @param filePath
	 *            (String | Absolute path to directory as String)
	 */
	public MusicVideoSourceDirectoriesTableView(String filePath) {
		this.filePath = new SimpleStringProperty(filePath);
	}

	/**
	 * MusicVideoSourceDirectoriesTableView constructor #2
	 * 
	 * @param filePath
	 *            (Path | Absolute path to directory)
	 */
	public MusicVideoSourceDirectoriesTableView(Path filePath) {
		this.filePath = new SimpleStringProperty(filePath.toString());
	}

	/**
	 * @return get table usable String of file path
	 */
	public StringProperty getFilePathProperty() {
		return this.filePath;
	}

	/**
	 * @return get directory path as a String (String)
	 */
	public String getFilePath() {
		return this.filePath.get();
	}

}

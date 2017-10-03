package anonymerniklasistanonym.karaokemusicvideomanager.desktopclient.gui.tables;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class DirectoryPathTableView {

	private final StringProperty filePath;

	public DirectoryPathTableView(String filePath) {
		this.filePath = new SimpleStringProperty(filePath);
	}

	public StringProperty getFilePathProperty() {
		return filePath;
	}

	public String getFilePath() {
		return filePath.get();
	}

}

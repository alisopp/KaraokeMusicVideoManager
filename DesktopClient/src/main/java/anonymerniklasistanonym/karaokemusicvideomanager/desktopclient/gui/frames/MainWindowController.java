package anonymerniklasistanonym.karaokemusicvideomanager.desktopclient.gui.frames;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import anonymerniklasistanonym.karaokemusicvideomanager.desktopclient.gui.Main;
import anonymerniklasistanonym.karaokemusicvideomanager.desktopclient.libaries.ExternalApplicationHandler;
import anonymerniklasistanonym.karaokemusicvideomanager.desktopclient.objects.MusicVideo;
import javafx.beans.property.ReadOnlyIntegerWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class MainWindowController {

	// Views
	@FXML
	private Label label;
	@FXML
	private TextField searchBox;
	@FXML
	private TableView<Integer> musicVideoListTable;
	@FXML
	private TableColumn<Integer, Integer> musicVideoListTabelNumber;
	@FXML
	private TableColumn<Integer, String> musicVideoListTabelArtist;
	@FXML
	private TableColumn<Integer, String> musicVideoListTabelTitle;

	public Main mainWindow;

	public void setMainWindow(Main window) {
		this.mainWindow = window;
		this.searchBox.setText("hi");

		MusicVideo[] listOfVideos = window.musicVideohandler.getMusicVideoList();

		for (int i = 0; i < listOfVideos.length; i++) {
			this.musicVideoListTable.getItems().add(i);
		}

		TableColumn<Integer, Number> intColumn = new TableColumn<>("#");
		intColumn.setCellValueFactory(cellData -> {
			Integer rowIndex = cellData.getValue();
			return new ReadOnlyIntegerWrapper(rowIndex + 1);
		});

		TableColumn<Integer, String> artistColumn = new TableColumn<>("Artist");
		artistColumn.setCellValueFactory(cellData -> {
			Integer rowIndex = cellData.getValue();
			return new ReadOnlyStringWrapper(listOfVideos[rowIndex].getArtist());
		});

		TableColumn<Integer, String> titleColumn = new TableColumn<>("Title");
		titleColumn.setCellValueFactory(cellData -> {
			Integer rowIndex = cellData.getValue();
			return new ReadOnlyStringWrapper(listOfVideos[rowIndex].getTitle());
		});

		this.musicVideoListTable.getColumns().clear();

		intColumn.prefWidthProperty().bind(this.musicVideoListTable.widthProperty().multiply(0.2));
		artistColumn.prefWidthProperty().bind(this.musicVideoListTable.widthProperty().multiply(0.3));
		titleColumn.prefWidthProperty().bind(this.musicVideoListTable.widthProperty().multiply(0.5));

		this.musicVideoListTable.getColumns().add(intColumn);
		this.musicVideoListTable.getColumns().add(artistColumn);
		this.musicVideoListTable.getColumns().add(titleColumn);

	}

	/**
	 * Do the following when a row is selected/clicked with the mouse
	 */
	@FXML
	private void openSelectedVideoFile() {
		Integer rowIndex = this.musicVideoListTable.getSelectionModel().getSelectedItem();
		if (rowIndex != null) {
			this.mainWindow.musicVideohandler.openMusicVideo(rowIndex);
		}

	}

	/**
	 * Open on enter the video file that is on the top of the table
	 */
	@FXML
	public void openTopMusicVideoFile() {

		// select the top item
		this.musicVideoListTable.getSelectionModel().select(0);

		// open the music video that is selected
		openSelectedVideoFile();

		// clear the selection
		this.musicVideoListTable.getSelectionModel().clearSelection();
	}

	/**
	 * Clears the current selection
	 */
	@FXML
	private void unSelectVideoFile() {
		// clear the current selection
		this.musicVideoListTable.getSelectionModel().clearSelection();

	}

	/**
	 * Search for the text in the input field on YouTube with the external default
	 * browser
	 */
	@FXML
	public void searchOnYouTube() {

		// Text we want to search on YouTube
		String searchQuery = searchBox.getText();

		// The URL of YouTube
		String youTubeUrl = "https://www.youtube.com";

		// if text field has text try to add it to the urlToOpen
		try {
			if (searchQuery != null && searchQuery.length() > 0) {

				// encode the text to a browser query
				String textToSearchQuery = URLEncoder.encode(searchQuery, "UTF-8");

				// add the search query to the YouTube URL
				youTubeUrl += "/results?search_query=" + textToSearchQuery;

				// open the new URL
				ExternalApplicationHandler.openUrl(youTubeUrl);
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

	}

}

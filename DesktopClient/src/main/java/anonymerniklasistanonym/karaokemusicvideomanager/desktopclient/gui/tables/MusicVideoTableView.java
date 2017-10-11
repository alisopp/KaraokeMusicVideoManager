package anonymerniklasistanonym.karaokemusicvideomanager.desktopclient.gui.tables;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Class for table elements: Correctly formatted music video files
 * 
 * @author AnonymerNiklasistanonym <niklas.mikeler@gmail.com> | <a href=
 *         "https://github.com/AnonymerNiklasistanonym">https://github.com/AnonymerNiklasistanonym</a>
 */
public class MusicVideoTableView {

	/**
	 * The index in the music video list of the file
	 */
	private final IntegerProperty index;

	/**
	 * The artist of the file
	 */
	private final StringProperty artist;

	/**
	 * The title of the file
	 */
	private final StringProperty title;

	/**
	 * MusicVideoTableView constructor
	 * 
	 * @param index
	 *            (Integer | Index of the music video file in the music video list)
	 * @param artist
	 *            (String | Artist of the music video file)
	 * @param title
	 *            (String | Title of the music video file)
	 */
	public MusicVideoTableView(int index, String artist, String title) {
		this.index = new SimpleIntegerProperty(index);
		this.artist = new SimpleStringProperty(artist);
		this.title = new SimpleStringProperty(title);
	}

	/**
	 * Get the index in the music video list of the file
	 * 
	 * @return index (IntegerProperty)
	 */
	public IntegerProperty getIndexProperty() {
		return this.index;
	}

	/**
	 * Get the artist of the file
	 * 
	 * @return artist (StringProperty)
	 */
	public StringProperty getArtistProperty() {
		return this.artist;
	}

	/**
	 * Get the title of the file
	 * 
	 * @return title (StringProperty)
	 */
	public StringProperty getTitleProperty() {
		return this.title;
	}

	/**
	 * Get the index in the music video list of the file
	 * 
	 * @return index (Integer)
	 */
	public int getIndex() {
		return this.index.get();
	}

	/**
	 * Get the artist of the file
	 * 
	 * @return artist (String)
	 */
	public String getArtist() {
		return this.artist.get();
	}

	/**
	 * Get the title of the file
	 * 
	 * @return title (String)
	 */
	public String getTitle() {
		return this.title.get();
	}

}

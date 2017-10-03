package anonymerniklasistanonym.karaokemusicvideomanager.desktopclient.gui.tables;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class MusicVideoTableView {

	private final IntegerProperty index;
	private final StringProperty artist;
	private final StringProperty title;

	public MusicVideoTableView(int index, String artist, String title) {
		this.index = new SimpleIntegerProperty(index);
		this.artist = new SimpleStringProperty(artist);
		this.title = new SimpleStringProperty(title);
	}

	public IntegerProperty getIndexProperty() {
		return index;
	}

	public StringProperty getArtistProperty() {
		return artist;
	}

	public StringProperty getTitleProperty() {
		return title;
	}

	public int getIndex() {
		return index.get();
	}

	public String getArtist() {
		return artist.get();
	}

	public String getTitle() {
		return title.get();
	}

}

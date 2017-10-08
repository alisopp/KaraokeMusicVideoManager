package anonymerniklasistanonym.karaokemusicvideomanager.desktopclient.gui.tables;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class MusicVideoPlaylistTableView {

	private final int index;
	private final int musicVideoindex;
	private final StringProperty time;
	private final StringProperty title;
	private final StringProperty artist;
	private final StringProperty author;
	private final StringProperty comment;

	public MusicVideoPlaylistTableView(int index, int musicVideoindex, String time, String title, String artist, String author,
			String comment) {
		this.index = index;
		this.musicVideoindex = musicVideoindex;
		this.time = new SimpleStringProperty(time);
		this.artist = new SimpleStringProperty(artist);
		this.title = new SimpleStringProperty(title);
		this.author = new SimpleStringProperty(author);
		this.comment = new SimpleStringProperty(comment);
	}

	public StringProperty getTimeProperty() {
		return this.time;
	}

	public StringProperty getArtistProperty() {
		return this.artist;
	}

	public StringProperty getTitleProperty() {
		return this.title;
	}

	public StringProperty getAuthorProperty() {
		return this.author;
	}

	public StringProperty getCommentProperty() {
		return this.comment;
	}

	public String getTime() {
		return this.time.get();
	}

	public String getArtist() {
		return this.artist.get();
	}

	public String getTitle() {
		return this.title.get();
	}

	public String getAuthor() {
		return this.author.get();
	}

	public String getComment() {
		return this.comment.get();
	}

	public int getIndex() {
		return this.index;
	}

	public int getMusicVideoIndex() {
		return this.musicVideoindex;
	}

}

package anonymerniklasistanonym.karaokemusicvideomanager.desktopclient.gui.tables;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class PlaylistTableView {

	private final StringProperty time;
	private final StringProperty title;
	private final StringProperty artist;
	private final StringProperty author;
	private final StringProperty comment;

	public PlaylistTableView(String index, String artist, String title, String author, String comment) {
		this.time = new SimpleStringProperty(index);
		this.artist = new SimpleStringProperty(artist);
		this.title = new SimpleStringProperty(title);
		this.author = new SimpleStringProperty(author);
		this.comment = new SimpleStringProperty(comment);
	}

	public StringProperty getTimeProperty() {
		return time;
	}

	public StringProperty getArtistProperty() {
		return artist;
	}

	public StringProperty getTitleProperty() {
		return title;
	}

	public StringProperty getAuthorProperty() {
		return author;
	}

	public StringProperty getCommentProperty() {
		return comment;
	}

	public String getTime() {
		return time.get();
	}

	public String getArtist() {
		return artist.get();
	}

	public String getTitle() {
		return title.get();
	}

	public String getAuthor() {
		return author.get();
	}

	public String getComment() {
		return comment.get();
	}

}

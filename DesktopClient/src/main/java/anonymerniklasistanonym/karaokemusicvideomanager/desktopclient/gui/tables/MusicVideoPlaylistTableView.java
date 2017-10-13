package anonymerniklasistanonym.karaokemusicvideomanager.desktopclient.gui.tables;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Class for table elements: Playlist entries of music video files
 * 
 * @author AnonymerNiklasistanonym <niklas.mikeler@gmail.com> | <a href=
 *         "https://github.com/AnonymerNiklasistanonym">https://github.com/AnonymerNiklasistanonym</a>
 */
public class MusicVideoPlaylistTableView {

	/**
	 * The index of the playlist entry for deleting an entry????
	 */
	private final int index;

	/**
	 * The index of the music video file of the playlist entry
	 */
	private final int musicVideoIndex;

	/**
	 * The creation date of the playlist entry
	 */
	private final StringProperty time;

	/**
	 * The title of the music video file of the playlist entry
	 */
	private final StringProperty title;

	/**
	 * The artist of the music video file of the playlist entry
	 */
	private final StringProperty artist;

	/**
	 * The name of the creator of the playlist entry
	 */
	private final StringProperty author;

	/**
	 * A comment from the creator of the playlist entry
	 */
	private final StringProperty comment;

	/**
	 * The votes of the playlist entry
	 */
	private final IntegerProperty votes;

	/**
	 * MusicVideoPlaylistTableView constructor
	 * 
	 * @param index
	 *            (Integer | Index of the playlist entry)
	 * @param musicVideoindex
	 *            (Integer | The index of the music video file of the playlist
	 *            entry)
	 * @param time
	 *            (String | The creation date of the playlist entry)
	 * @param title
	 *            (String | The title of the music video file of the playlist entry)
	 * @param artist
	 *            (String | The artist of the music video file of the playlist
	 *            entry)
	 * @param author
	 *            (String | The name of the creator of the playlist entry)
	 * @param comment
	 *            (String | A comment from the creator of the playlist entry)
	 * @param votes
	 *            (Integer | The votes of the playlist entry)
	 */
	public MusicVideoPlaylistTableView(int index, int musicVideoindex, String time, String title, String artist,
			String author, String comment, int votes) {
		this.index = index;
		this.musicVideoIndex = musicVideoindex;
		this.time = new SimpleStringProperty(time);
		this.artist = new SimpleStringProperty(artist);
		this.title = new SimpleStringProperty(title);
		this.author = new SimpleStringProperty(author);
		this.comment = new SimpleStringProperty(comment);
		this.votes = new SimpleIntegerProperty(votes);
	}

	/**
	 * Get the creation date of the playlist entry
	 * 
	 * @return time (StringProperty)
	 */
	public StringProperty getTimeProperty() {
		return this.time;
	}

	/**
	 * Get the artist of the music video file of the playlist entry
	 * 
	 * @return artist (StringProperty)
	 */
	public StringProperty getArtistProperty() {
		return this.artist;
	}

	/**
	 * Get the title of the music video file of the playlist entry
	 * 
	 * @return title (StringProperty)
	 */
	public StringProperty getTitleProperty() {
		return this.title;
	}

	/**
	 * Get the author name of the playlist entry
	 * 
	 * @return author (StringProperty)
	 */
	public StringProperty getAuthorProperty() {
		return this.author;
	}

	/**
	 * Get the comment of the playlist entry creator
	 * 
	 * @return comment (StringProperty)
	 */
	public StringProperty getCommentProperty() {
		return this.comment;
	}

	/**
	 * Get the votes of the playlist entry
	 * 
	 * @return votes (IntegerProperty)
	 */
	public IntegerProperty getVotesProperty() {
		return this.votes;
	}

	/**
	 * Get the creation date of the playlist entry
	 * 
	 * @return time (String)
	 */
	public String getTime() {
		return this.time.get();
	}

	/**
	 * Get the artist of the music video file of the playlist entry
	 * 
	 * @return artist (String)
	 */
	public String getArtist() {
		return this.artist.get();
	}

	/**
	 * Get the title of the music video file of the playlist entry
	 * 
	 * @return title (String)
	 */
	public String getTitle() {
		return this.title.get();
	}

	/**
	 * Get the author name of the playlist entry
	 * 
	 * @return author (String)
	 */
	public String getAuthor() {
		return this.author.get();
	}

	/**
	 * Get the comment of the playlist entry creator
	 * 
	 * @return comment (String)
	 */
	public String getComment() {
		return this.comment.get();
	}

	/**
	 * Get the votes of the playlist entry
	 * 
	 * @return votes (Integer)
	 */
	public int getVotes() {
		return this.votes.get();
	}

	/**
	 * Get the index of the playlist entry
	 * 
	 * @return index (Integer)
	 */
	public int getIndex() {
		return this.index;
	}

	/**
	 * Get the index of the music video file of the playlist entry
	 * 
	 * @return musicVideoIndex (Integer)
	 */
	public int getMusicVideoIndex() {
		return this.musicVideoIndex;
	}

}

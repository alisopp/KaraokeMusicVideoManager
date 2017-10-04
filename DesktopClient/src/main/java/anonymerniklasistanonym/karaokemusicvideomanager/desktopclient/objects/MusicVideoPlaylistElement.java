package anonymerniklasistanonym.karaokemusicvideomanager.desktopclient.objects;

import java.time.Instant;
import java.util.Date;

/**
 * Properties of every music video element in the playlist
 * 
 * @author AnonymerNiklasistanonym
 *
 */
public final class MusicVideoPlaylistElement {

	/**
	 * Music video file with all information about the music video
	 */
	private final MusicVideo musicVideoFile;

	/**
	 * Name of the author of the element
	 */
	private String author;

	/**
	 * Comment of the author about this element
	 */
	private String comment;

	/**
	 * Time the entry was added
	 */
	private final long unixTime;

	/**
	 * Was it locally or online created
	 */
	private final boolean createdLocally;

	/**
	 * Constructor: All possibilities
	 * 
	 * @param musicVideoFile
	 *            (MusicVideo)
	 * @param author
	 *            (String)
	 * @param comment
	 *            (String)
	 * @param createdLocally
	 *            (Boolean)
	 */
	public MusicVideoPlaylistElement(MusicVideo musicVideoFile, String author, String comment, boolean createdLocally) {
		this.musicVideoFile = musicVideoFile;
		this.author = author;
		this.comment = comment;
		this.createdLocally = createdLocally;
		this.unixTime = Instant.now().getEpochSecond();
	}

	/**
	 * Constructor: All possibilities without comment
	 * 
	 * @param musicVideoFile
	 *            (MusicVideo)
	 * @param author
	 *            (String)
	 * @param comment
	 *            (String)
	 * @param createdLocally
	 *            (Boolean)
	 */
	public MusicVideoPlaylistElement(MusicVideo musicVideoFile, String author, boolean createdLocally) {
		this.musicVideoFile = musicVideoFile;
		this.author = author;
		this.comment = null;
		this.createdLocally = createdLocally;
		this.unixTime = Instant.now().getEpochSecond();
	}

	/**
	 * Constructor: Local fast creation
	 * 
	 * @param musicVideoFile
	 *            (MusicVideo)
	 */
	public MusicVideoPlaylistElement(MusicVideo musicVideoFile) {
		this.musicVideoFile = musicVideoFile;
		this.author = null;
		this.comment = null;
		this.createdLocally = true;
		this.unixTime = Instant.now().getEpochSecond();
	}

	/**
	 * Get MusicVideo object
	 * 
	 * @return MusicVideoObject (MusicVideo)
	 */
	public MusicVideo getMusicVideoFile() {
		return this.musicVideoFile;
	}

	/**
	 * Get author of playlist element
	 * 
	 * @return author (String)
	 */
	public String getAuthor() {
		return this.author;
	}

	/**
	 * Get comment of playlist element
	 * 
	 * @return comment (String)
	 */
	public String getComment() {
		return this.comment;
	}

	/**
	 * Get if element was created locally or on the server
	 * 
	 * @return createdLocally (Boolean | true if locally created)
	 */
	public boolean isCreatedLocally() {
		return this.createdLocally;
	}

	public void edit(String author, String comment) {
		this.author = author;
		this.comment = comment;
	}

	public long getUnixTime() {
		return this.unixTime;
	}

	public String getUnixTimeString() {
		return new Date(this.unixTime).toString();
	}

}

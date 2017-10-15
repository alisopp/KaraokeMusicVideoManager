package anonymerniklasistanonym.karaokemusicvideomanager.desktopclient.objects;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

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
	 * Playlist integer
	 */
	private final int musicVideoIndex;

	/**
	 * Votes integer
	 */
	private int votes;

	/**
	 * Name of the file on the server
	 */
	private final String fileName;

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
	public MusicVideoPlaylistElement(int musicVideoIndex, MusicVideo musicVideoFile, String author, String comment,
			boolean createdLocally) {
		this.musicVideoIndex = musicVideoIndex;
		this.musicVideoFile = musicVideoFile;
		this.author = author;
		this.comment = comment;
		this.createdLocally = createdLocally;
		this.unixTime = Instant.now().getEpochSecond();
		this.votes = 0;
		this.fileName = "nada";
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
	public MusicVideoPlaylistElement(int musicVideoIndex, MusicVideo musicVideoFile, String author,
			boolean createdLocally) {
		this.musicVideoIndex = musicVideoIndex;
		this.musicVideoFile = musicVideoFile;
		this.author = author;
		this.comment = null;
		this.createdLocally = createdLocally;
		this.unixTime = Instant.now().getEpochSecond();
		this.votes = 0;
		this.fileName = "nada";
	}

	/**
	 * Constructor: Local fast creation
	 * 
	 * @param musicVideoFile
	 *            (MusicVideo)
	 */
	public MusicVideoPlaylistElement(int musicVideoIndex, MusicVideo musicVideoFile) {
		this.musicVideoIndex = musicVideoIndex;
		this.musicVideoFile = musicVideoFile;
		this.author = null;
		this.comment = null;
		this.createdLocally = true;
		this.unixTime = Instant.now().getEpochSecond();
		this.votes = 0;
		this.fileName = "nada";
	}

	/**
	 * Constructor: For loading files
	 * 
	 * @param musicVideoFile
	 *            (MusicVideo)
	 * @param author
	 *            (String)
	 * @param comment
	 *            (String)
	 * @param createdLocally
	 *            (Boolean)
	 * @param votes
	 */
	public MusicVideoPlaylistElement(long unixTime, int musicVideoIndex, MusicVideo musicVideoFile, String author,
			String comment, boolean createdLocally, int votes, String fileName) {
		this.musicVideoIndex = musicVideoIndex;
		this.musicVideoFile = musicVideoFile;
		this.author = author;
		this.comment = comment;
		this.createdLocally = createdLocally;
		this.unixTime = unixTime;
		this.votes = votes;
		this.fileName = fileName;
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

	public MusicVideoPlaylistElement edit(String author, String comment) {
		this.author = author;
		this.comment = comment;

		return this;
	}

	public long getUnixTime() {
		return this.unixTime;
	}

	public String getUnixTimeString() {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		return Instant.ofEpochSecond(this.unixTime).atZone(ZoneId.systemDefault()).format(formatter);
	}

	public int getMusicVideoIndex() {
		return this.musicVideoIndex;
	}

	public boolean getCreatedLocally() {
		return this.createdLocally;
	}

	public int getVotes() {
		return this.votes;
	}

	public void setVotes(int vote) {
		this.votes = vote;
	}

	public String getFileName() {
		return this.fileName;
	}

}

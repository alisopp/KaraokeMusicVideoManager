package anonymerniklasistanonym.karaokemusicvideomanager.desktopclient.objects;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

/**
 * The properties of every music video element in the playlist
 * 
 * @author AnonymerNiklasistanonym <niklas.mikeler@gmail.com> | <a href=
 *         "https://github.com/AnonymerNiklasistanonym">https://github.com/AnonymerNiklasistanonym</a>
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
	 * Index of music video file in list
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
	 * Format of time
	 */
	private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

	/**
	 * Constructor: Local creation of a playlist file
	 * 
	 * @param musicVideoIndex
	 *            (Integer)
	 * @param musicVideoFile
	 *            (MusicVideo)
	 * @param author
	 *            (String)
	 * @param comment
	 *            (String)
	 * @param createdLocally
	 *            (boolean)
	 */
	public MusicVideoPlaylistElement(int musicVideoIndex, MusicVideo musicVideoFile, String author, String comment,
			boolean createdLocally) {

		this.musicVideoIndex = musicVideoIndex;
		this.musicVideoFile = musicVideoFile;
		this.author = author;
		this.comment = comment;
		this.createdLocally = createdLocally;

		// set on creation the current time as unixTime
		this.unixTime = Instant.now().getEpochSecond();

		// set votes at the begin to zero
		this.votes = 0;

		// set file name to null if there is none
		this.fileName = null;

	}

	/**
	 * Constructor: For loading playlist files
	 * 
	 * @param unixTime
	 *            (long | Creation time)
	 * @param musicVideoIndex
	 *            (Index)
	 * @param musicVideoFile
	 *            (MusicVideo)
	 * @param author
	 *            (String)
	 * @param comment
	 *            (String)
	 * @param createdLocally
	 *            (boolean)
	 * @param votes
	 *            (Integer)
	 * @param fileName
	 *            (String)
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
	 * @return createdLocally (boolean | true if locally created)
	 */
	public boolean isCreatedLocally() {
		return this.createdLocally;
	}

	/**
	 * Edit author and comment
	 * 
	 * @param author
	 *            (String)
	 * @param comment
	 *            (String)
	 * @return the new edited object (MusicVideoPlaylistElement)
	 */
	public MusicVideoPlaylistElement editAuthorComment(String author, String comment) {

		// edit author and comment
		this.author = author;
		this.comment = comment;

		// return the new object
		return this;

	}

	/**
	 * @return creation time of object (long)
	 */
	public long getUnixTime() {
		return this.unixTime;
	}

	/**
	 * @return creation time of object in human readable format (String)
	 */
	public String getUnixTimeString() {
		return Instant.ofEpochSecond(this.unixTime).atZone(ZoneId.systemDefault()).format(formatter);
	}

	/**
	 * @return get the index of the music video file (Integer)
	 */
	public int getMusicVideoIndex() {
		return this.musicVideoIndex;
	}

	/**
	 * @return was the file locally created or on a server (boolean)
	 */
	public boolean getCreatedLocally() {
		return this.createdLocally;
	}

	/**
	 * @return votes of the object (Integer)
	 */
	public int getVotes() {
		return this.votes;
	}

	/**
	 * Set votes of the object
	 * 
	 * @param vote
	 *            (Integer)
	 */
	public void setVotes(int vote) {
		this.votes = vote;
	}

	/**
	 * @return get the file name of the file that contains the playlist entry
	 */
	public String getFileName() {
		return this.fileName;
	}

}

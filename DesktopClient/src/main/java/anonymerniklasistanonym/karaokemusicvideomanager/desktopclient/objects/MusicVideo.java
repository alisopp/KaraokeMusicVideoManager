package anonymerniklasistanonym.karaokemusicvideomanager.desktopclient.objects;

import java.nio.file.Path;
import java.util.Comparator;

/**
 * The properties of every music video file
 * 
 * @author AnonymerNiklasistanonym <niklas.mikeler@gmail.com> | <a href=
 *         "https://github.com/AnonymerNiklasistanonym">https://github.com/AnonymerNiklasistanonym</a>
 */
public final class MusicVideo implements Comparator<MusicVideo> {

	/**
	 * The path of the music video file
	 */
	private final Path path;
	/**
	 * The name/title of the music video file
	 */
	private final String title;
	/**
	 * The artist/s of the music video file
	 */
	private final String artist;

	/**
	 * Constructor: Create a MusicVideo object
	 * 
	 * @param path
	 *            (Path | path to the file)
	 * @param title
	 *            (String | name of the music video title)
	 * @param artist
	 *            (String | artists of the music video)
	 */
	public MusicVideo(Path path, String title, String artist) {
		this.path = path;
		this.title = title;
		this.artist = artist;
	}

	/**
	 * Empty constructor for Comparator
	 */
	public MusicVideo() {
		this.path = null;
		this.title = null;
		this.artist = null;
	}

	/**
	 * Returns the title/name of the music video
	 * 
	 * @return the title/name of the music video (String)
	 */
	public String getTitle() {
		return this.title;
	}

	/**
	 * Sets the artist/s of the music video
	 * 
	 * @return the name of the music video (String)
	 */
	public String getArtist() {
		return this.artist;
	}

	/**
	 * Returns the (file) path of the music video
	 * 
	 * @return the (file) path of the music video (Path)
	 */
	public Path getPath() {
		return this.path;
	}

	/**
	 * Comparator: Compare two MusicVideo objects for sorting
	 * 
	 * @param o1
	 *            (MusicVideo)
	 * @param o2
	 *            (MusicVideo)
	 * @return comparatorValue (Integer)
	 */
	@Override
	public int compare(MusicVideo o1, MusicVideo o2) {
		// check how the artist names are compared to each other
		int areTheArtistsTheSame = o1.getArtist().toUpperCase().compareTo(o2.getArtist().toUpperCase());
		// if the artist are the same artist (==0)
		if (areTheArtistsTheSame == 0) {
			// return the compare value for the title to sort titles from
			// the same artist also alphabetically
			return String.CASE_INSENSITIVE_ORDER.compare(o1.getTitle().toUpperCase(), o2.getTitle().toUpperCase());
		} else {
			// if the artist is not the same return the compare Integer
			// calculated before
			return areTheArtistsTheSame;
		}
	}
}
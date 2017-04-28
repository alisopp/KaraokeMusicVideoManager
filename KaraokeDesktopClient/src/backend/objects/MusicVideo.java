package backend.objects;

import java.nio.file.Path;

/**
 * The properties of every music video ([karaoke] file)
 * 
 * @author Niklas | https://github.com/AnonymerNiklasistanonym
 * @version 0.3 (beta)
 *
 */
public class MusicVideo {

	/**
	 * The path of the music video file
	 */
	private Path path;
	/**
	 * The name/title of the music video file
	 */
	private String name;
	/**
	 * The artist/s of the music video file
	 */
	private String artist;

	/**
	 * normal constructor: nothing happens
	 */
	public MusicVideo() {
		// empty constructor
	}

	/**
	 * advanced constructor: create instantly a music video object
	 * 
	 * @param a
	 *            (Path | path of the file)
	 * @param b
	 *            (String | name of the music video title)
	 * @param c
	 *            (String | artists of the music video)
	 */
	public MusicVideo(Path a, String b, String c) {
		path = a;
		name = b;
		artist = c;
	}

	/**
	 * Returns the title/name of the music video
	 * 
	 * @return the title/name of the music video (String)
	 */
	public String getTitle() {
		return name;
	}

	/**
	 * Sets the title/name of the music video
	 * 
	 * @param name
	 *            (String | name/title of the music video)
	 */
	public void setTitle(String name) {
		this.name = name;
	}

	/**
	 * Sets the artist/s of the music video
	 * 
	 * @return the name of the music video (String)
	 */
	public String getArtist() {
		return artist;
	}

	/**
	 * Sets the artist/s of the actual music video
	 * 
	 * @param artist
	 *            (String | artist/s of the music video)
	 */
	public void setArtist(String artist) {
		this.artist = artist;
	}

	/**
	 * Returns the (file) path of the music video
	 * 
	 * @return the (file) path of the music video (Path)
	 */
	public Path getPath() {
		return path;
	}

	/**
	 * Sets the (file) path of the music video
	 * 
	 * @param path
	 *            (Path | path of the music video file)
	 */
	public void setPath(Path path) {
		this.path = path;
	}
}
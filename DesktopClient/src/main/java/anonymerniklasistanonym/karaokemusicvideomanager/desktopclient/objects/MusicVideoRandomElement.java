package anonymerniklasistanonym.karaokemusicvideomanager.desktopclient.objects;

/**
 * The properties a random music video element
 * 
 * @author AnonymerNiklasistanonym <niklas.mikeler@gmail.com> | <a href=
 *         "https://github.com/AnonymerNiklasistanonym">https://github.com/AnonymerNiklasistanonym</a>
 */
public class MusicVideoRandomElement {

	/**
	 * MusicVideo object
	 */
	private MusicVideo musicVideo;

	/**
	 * Index in music video file
	 */
	private int indexInList;

	/**
	 * Constructor: Create a random video element object
	 * 
	 * @param musicVideo
	 *            (MusicVideo)
	 * @param index
	 *            (Integer | Position in musicVideoList)
	 */
	public MusicVideoRandomElement(MusicVideo musicVideo, int index) {
		this.musicVideo = musicVideo;
		this.indexInList = index;
	}

	/**
	 * @return Get the MusicVideo object (MusicVideo)
	 */
	public MusicVideo getMusicVideo() {
		return musicVideo;
	}

	/**
	 * @return Index of MusicVideo in musicVideoList (Integer)
	 */
	public int getIndex() {
		return indexInList;
	}

}

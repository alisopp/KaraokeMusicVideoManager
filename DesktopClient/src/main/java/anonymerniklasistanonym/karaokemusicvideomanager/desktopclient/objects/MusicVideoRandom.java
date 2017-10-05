package anonymerniklasistanonym.karaokemusicvideomanager.desktopclient.objects;

public class MusicVideoRandom {

	private MusicVideo musicVideo;

	private int index;

	public MusicVideoRandom(MusicVideo musicVideo, int index) {
		this.musicVideo = musicVideo;
		this.index = index;
	}

	public MusicVideo getMusicVideo() {
		return musicVideo;
	}

	public int getIndex() {
		return index;
	}

}

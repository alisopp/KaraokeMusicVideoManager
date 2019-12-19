package anonymerniklasistanonym.karaokemusicvideomanager.desktopclient.handler;

import anonymerniklasistanonym.karaokemusicvideomanager.desktopclient.objects.MusicVideo;

public interface IMusicVideoPlaylist {

	/**
	 * Get the current music video list
	 * 
	 * @return musicVideoList (MusicVideo[])
	 */
	MusicVideo[] getMusicVideoList();

	/**
	 * Add a music video to the current playlist
	 * 
	 * @param index
	 *            (Integer | Position of music video in the musicVideoList)
	 * @param author
	 *            (String | Author of playlist entry)
	 * @param comment
	 *            (String | Comment of author)
	 */
	void addMusicVideoToPlaylist(int index, String author, String comment);

}
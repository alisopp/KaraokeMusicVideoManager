package anonymerniklasistanonym.karaokemusicvideomanager.desktopclient.objects;

import java.util.Arrays;
import java.util.stream.Stream;

public class MusicVideoPlaylistHandler {

	private MusicVideoPlaylistElement[] playlistElements;

	public MusicVideoPlaylistElement[] getPlaylistElements() {
		return playlistElements;
	}

	public void setPlaylistElements(MusicVideoPlaylistElement[] playlistElements) {
		Arrays.sort(playlistElements, (a, b) -> Long.valueOf(a.getUnixTime()).compareTo(Long.valueOf(b.getUnixTime())));
		this.playlistElements = playlistElements;
	}

	/**
	 * Constructor of playlist
	 */
	public MusicVideoPlaylistHandler() {
		reset();
	}

	/**
	 * Reset the playlist
	 */
	public void reset() {
		this.playlistElements = null;
	}

	/**
	 * Add an entry to the playlist
	 * 
	 * @param musicVideo
	 *            (MusicVideo | Index in playlist)
	 * @param author
	 *            (String | Author of entry)
	 * @param comment
	 *            (String | Comment to entry)
	 */
	public MusicVideoPlaylistElement add(int musicVideoIndex, MusicVideo musicVideo, String author, String comment) {

		// create new entry
		MusicVideoPlaylistElement newEntry = new MusicVideoPlaylistElement(musicVideoIndex, musicVideo, author, comment,
				true);

		MusicVideoPlaylistElement[] oldPlaylist = this.playlistElements;
		MusicVideoPlaylistElement[] newPlaylist = new MusicVideoPlaylistElement[] { newEntry };

		if (oldPlaylist != null) {
			setPlaylistElements(Stream.concat(Arrays.stream(oldPlaylist), Arrays.stream(newPlaylist))
					.toArray(MusicVideoPlaylistElement[]::new));
		} else {
			setPlaylistElements(newPlaylist);
		}

		return newEntry;
	}

	/**
	 * Remove an entry from the playlist
	 * 
	 * @param index
	 *            (Integer | Index in playlist)
	 */
	public MusicVideoPlaylistElement remove(int index) {

		if (index >= this.playlistElements.length || index < 0) {
			System.err.println("Index is too big/small");
			return null;
		}

		MusicVideoPlaylistElement elementToRemove = this.playlistElements[index];
		this.playlistElements[index] = null;
		MusicVideoPlaylistElement[] newPlaylist = new MusicVideoPlaylistElement[this.playlistElements.length - 1];

		int i = 0;
		for (MusicVideoPlaylistElement a : this.playlistElements) {
			if (a != null) {
				newPlaylist[i] = a;
				i++;
			}
		}

		this.playlistElements = newPlaylist;

		return elementToRemove;
	}

	public MusicVideoPlaylistElement load(long unixTime, int musicVideoIndex, MusicVideo musicVideo, String author,
			String comment, boolean createdLocally) {
		// create new entry
		MusicVideoPlaylistElement newEntry = new MusicVideoPlaylistElement(unixTime, musicVideoIndex, musicVideo,
				author, comment, true);

		MusicVideoPlaylistElement[] oldPlaylist = this.playlistElements;
		MusicVideoPlaylistElement[] newPlaylist = new MusicVideoPlaylistElement[] { newEntry };

		if (oldPlaylist != null) {
			setPlaylistElements(Stream.concat(Arrays.stream(oldPlaylist), Arrays.stream(newPlaylist))
					.toArray(MusicVideoPlaylistElement[]::new));
		} else {
			setPlaylistElements(newPlaylist);
		}

		return newEntry;
	}

	/**
	 * Edit an entry
	 * 
	 * @param index
	 *            (Integer | Index in playlist)
	 * @param author
	 *            (String | New author)
	 * @param comment
	 *            (String | New comment)
	 */
	public MusicVideoPlaylistElement edit(int index, String author, String comment) {
		return this.playlistElements[index].edit(author, comment);
	}

}

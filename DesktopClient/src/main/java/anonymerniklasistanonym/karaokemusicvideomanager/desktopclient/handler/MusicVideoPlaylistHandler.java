package anonymerniklasistanonym.karaokemusicvideomanager.desktopclient.handler;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Stream;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonValue;

import anonymerniklasistanonym.karaokemusicvideomanager.desktopclient.libaries.FileReadWriteModule;
import anonymerniklasistanonym.karaokemusicvideomanager.desktopclient.libaries.JsonModule;
import anonymerniklasistanonym.karaokemusicvideomanager.desktopclient.objects.MusicVideo;
import anonymerniklasistanonym.karaokemusicvideomanager.desktopclient.objects.MusicVideoPlaylistElement;

/**
 * Class that handles the music video playlist (the list and the entries)
 * 
 * @author AnonymerNiklasistanonym <niklas.mikeler@gmail.com> | <a href=
 *         "https://github.com/AnonymerNiklasistanonym">https://github.com/AnonymerNiklasistanonym</a>
 */
public class MusicVideoPlaylistHandler {

	/**
	 * List that contains all MusicVideoPlaylistElements
	 */
	private MusicVideoPlaylistElement[] playlistElements;

	/**
	 * @return the list that contains all MusicVideoPlaylistElements
	 *         (MusicVideoPlaylistElement[])
	 */
	public MusicVideoPlaylistElement[] getPlaylistElements() {
		return playlistElements;
	}

	/**
	 * @param playlistElements
	 *            (MusicVideoPlaylistElement[] | new list with playlist elements)
	 */
	public void setPlaylistElements(MusicVideoPlaylistElement[] playlistElements) {

		if (playlistElements == null) {
			this.playlistElements = null;
		} else {
			Arrays.sort(playlistElements,
					(a, b) -> Long.valueOf(a.getUnixTime()).compareTo(Long.valueOf(b.getUnixTime())));

			// Arrays.stream(playlistElements).forEach(x ->
			// System.out.println(x.getUnixTimeString()));

			// Set<Long> set = new HashSet<Long>();
			ArrayList<MusicVideoPlaylistElement> newList = new ArrayList<MusicVideoPlaylistElement>(
					playlistElements.length);

			for (int i = 0; i < playlistElements.length; i++) {
				// if (set.add(playlistElements[i].getUnixTime())) {
				newList.add(playlistElements[i]);
				// }
			}
			// Arrays.stream(newList.toArray(new MusicVideoPlaylistElement[0])).forEach(x ->
			// System.out.println(x.getUnixTimeString()));
			this.playlistElements = newList.toArray(new MusicVideoPlaylistElement[0]);
		}

	}

	/**
	 * Get the votes of a music video playlist entry
	 * 
	 * @param index
	 *            (Integer | Index in playlist)
	 */
	public int getVotes(int index) {
		return this.playlistElements[index].getVotes();
	}

	/**
	 * Change the vote number of a playlist entry
	 * 
	 * @param vote
	 *            (Integer | New vote number)
	 * @param index
	 *            (Integer | Index of music video file in musicVideoList)
	 * @return changed MusicVideoPlaylistElement (MusicVideoPlaylistElement)
	 */
	public MusicVideoPlaylistElement setVotes(int vote, int index) {
		this.playlistElements[index].setVotes(vote);
		return this.playlistElements[index];
	}

	/**
	 * Constructor of playlist
	 */
	public MusicVideoPlaylistHandler() {
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

	/**
	 * Load a playlist entry to the music video playlist
	 * 
	 * @param unixTime
	 *            (Long)
	 * @param musicVideoIndex
	 *            (Integer)
	 * @param musicVideo
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
	 *            (String | Name of file on server to delete it later)
	 * @return the new entry (MusicVideoPlaylistElement)
	 */
	public MusicVideoPlaylistElement load(long unixTime, int musicVideoIndex, MusicVideo musicVideo, String author,
			String comment, boolean createdLocally, int votes, String fileName) {
		// create new entry
		MusicVideoPlaylistElement newEntry = new MusicVideoPlaylistElement(unixTime, musicVideoIndex, musicVideo,
				author, comment, true, votes, fileName);

		if (newEntry != null) {

			MusicVideoPlaylistElement[] oldPlaylist = this.playlistElements;
			MusicVideoPlaylistElement[] newPlaylist = new MusicVideoPlaylistElement[] { newEntry };

			if (oldPlaylist != null) {
				setPlaylistElements(Stream.concat(Arrays.stream(oldPlaylist), Arrays.stream(newPlaylist))
						.toArray(MusicVideoPlaylistElement[]::new));
			} else {
				setPlaylistElements(newPlaylist);
			}
		}

		return newEntry;
	}

	/**
	 * Load a playlist entry to the music video playlist
	 * 
	 * @param unixTime
	 *            (Long)
	 * @param musicVideoIndex
	 *            (Integer)
	 * @param musicVideo
	 *            (MusicVideo)
	 * @param author
	 *            (String)
	 * @param comment
	 *            (String)
	 * @param createdLocally
	 *            (boolean)
	 * @param votes
	 *            (Integer)
	 * @return the new entry (MusicVideoPlaylistElement)
	 */
	public MusicVideoPlaylistElement load(long unixTime, int musicVideoIndex, MusicVideo musicVideo, String author,
			String comment, boolean createdLocally, int votes) {
		return load(unixTime, musicVideoIndex, musicVideo, author, comment, true, votes, null);
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
		return this.playlistElements[index].editAuthorComment(author, comment);
	}

	/**
	 * Read from a playlist entry (JSON) file a plalyist entry data
	 * 
	 * @param file
	 *            (File | File that contains a playlist entry)
	 * @return playlist entry data (Object[])
	 */
	public static Object[] readPlaylistEntryFile(File file) {
		System.out.println("READ PLAYLIST ENTRY FILE");

		if (file == null) {
			System.err.println("<< File is null!");
			return null;
		}

		try {

			String[] contentOfFile = FileReadWriteModule.readTextFile(file);

			if (contentOfFile == null) {

				return null;
			}

			int playlistElementDataSongIndex;
			long playlistElementDataUnixTime;
			String playlistElementDataAuthor;
			String playlistElementDataComment;
			boolean playlistElementPlaceCreated;

			// read settings to one string
			StringBuilder strBuilder = new StringBuilder();
			for (String line : contentOfFile)
				strBuilder.append(line);

			// convert string to a JSON object
			JsonObject jsonObject = JsonModule.loadJsonFromString(strBuilder.toString());

			// -> (try to) get the song index
			int keyValueSongIndex = JsonModule.getValueInteger(jsonObject, "song");

			if (keyValueSongIndex != -1) {

				playlistElementDataSongIndex = keyValueSongIndex;
			} else {
				System.err.println(" << No index found");
				return null;
			}

			// -> (try to) get the author
			String keyValueAuthor = JsonModule.getValueString(jsonObject, "author");

			if (keyValueAuthor != null && !keyValueAuthor.equals("")) {

				playlistElementDataAuthor = keyValueAuthor;
			} else {
				System.err.println(" << No author found");
				return null;
			}

			// -> (try to) get the comment
			String keyValueComment = JsonModule.getValueString(jsonObject, "comment");

			if (keyValueComment != null) {

				playlistElementDataComment = keyValueComment;
			} else {
				System.err.println(" << No comment found");
				playlistElementDataComment = "";
			}

			// -> (try to) get the song index
			JsonValue keyValueTime = JsonModule.getValue(jsonObject, "time");

			if (keyValueTime != null) {

				playlistElementDataUnixTime = Long.parseLong(keyValueTime.toString());

			} else {
				System.err.println(" << No time found");
				return null;
			}

			// -> (try to) get if always the changes should be saved
			boolean keyValuePlaceCreated = JsonModule.getValueBoolean(jsonObject, "created-locally");

			playlistElementPlaceCreated = keyValuePlaceCreated;

			return new Object[] { playlistElementDataUnixTime, playlistElementDataSongIndex, playlistElementDataAuthor,
					playlistElementDataComment, playlistElementPlaceCreated };

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Convert a playlist entry to (JSON) String
	 * 
	 * @param playlistElement
	 *            (MusicVideoPlaylistElement | plalyist entry)
	 * @return plalist entry as a String (String)
	 */
	public String writePlaylistEntryFile(MusicVideoPlaylistElement playlistElement) {
		System.out.println("WRITE PLAYLIST ENTRY FILE");

		if (playlistElement == null) {
			return null;
		}

		try {

			JsonObjectBuilder mainJsonBuilder = Json.createObjectBuilder();

			// add song number
			if (playlistElement.getMusicVideoIndex() != -1) {
				mainJsonBuilder.add("song", BigDecimal.valueOf(playlistElement.getMusicVideoIndex()));
			}

			// add music video (title, artist)
			MusicVideo currentFile = playlistElement.getMusicVideoFile();

			if (currentFile != null) {
				mainJsonBuilder.add("title", currentFile.getTitle());
				mainJsonBuilder.add("artist", currentFile.getArtist());
			}

			mainJsonBuilder.add("author", playlistElement.getAuthor());
			mainJsonBuilder.add("comment", playlistElement.getComment());

			mainJsonBuilder.add("time", playlistElement.getUnixTime());

			mainJsonBuilder.add("created-locally", playlistElement.getCreatedLocally());

			mainJsonBuilder.add("votes", playlistElement.getVotes());

			return JsonModule.toString(mainJsonBuilder);

		} catch (

		Exception e) {
			return null;
		}
	}

	/**
	 * Load a playlist from a playlist file
	 * 
	 * @param file
	 *            (File | File that contains the playlist data)
	 * @param musicVideoList
	 *            (MusicVideo[] | The current music video list)
	 */
	public void loadPlaylistData(File file, MusicVideo[] musicVideoList) {
		if (file == null || file.isDirectory()) {
			System.err.println("Playlist element could not be loaded because the file doesn't exist!");
			return;
		}
		Object[] data = readPlaylistEntryFile(file);

		if (data != null) {

			load((long) data[0], (int) data[1] + 1, musicVideoList[(int) data[1]], (String) data[2], (String) data[3],
					(boolean) data[4], (int) data[5]);

			return;
		}
		System.err.println("Playlist element could not be loaded");
	}

	/**
	 * Convert a String of text to data to create a playlist entry
	 * 
	 * @param contentOfFile
	 *            (String)
	 * @return playlist entry data or null (Object[])
	 */
	public Object[] readPlaylistEntryFile(String contentOfFile) {
		System.out.println("READ PLAYLIST ENTRY FILE");

		if (contentOfFile == null) {
			System.err.println("<< Content is null!");
			return null;
		}

		System.out.println(contentOfFile);

		try {

			final int playlistElementDataSongIndex;
			final long playlistElementDataUnixTime;
			final String playlistElementDataAuthor;
			final String playlistElementDataComment;
			final boolean playlistElementPlaceCreated;
			final int playlistElementDataVoteNumber;

			// convert string to a JSON object
			JsonObject jsonObject = JsonModule.loadJsonFromString(contentOfFile);

			// -> (try to) get the song index
			int keyValueSongIndex = JsonModule.getValueInteger(jsonObject, "song");

			if (keyValueSongIndex != -1) {

				playlistElementDataSongIndex = keyValueSongIndex;
			} else {
				System.err.println(" << No index found");
				return null;
			}

			// -> (try to) get the author
			String keyValueAuthor = JsonModule.getValueString(jsonObject, "author");

			if (keyValueAuthor != null && !keyValueAuthor.equals("")) {

				playlistElementDataAuthor = keyValueAuthor;
			} else {
				System.err.println(" << No author found");
				return null;
			}

			// -> (try to) get the comment
			String keyValueComment = JsonModule.getValueString(jsonObject, "comment");

			if (keyValueComment != null) {

				playlistElementDataComment = keyValueComment;
			} else {
				System.err.println(" << No comment found");
				playlistElementDataComment = "";
			}

			// -> (try to) get the song index
			JsonValue keyValueTime = JsonModule.getValue(jsonObject, "time");

			if (keyValueTime != null) {

				playlistElementDataUnixTime = Long.parseLong(keyValueTime.toString());

			} else {
				System.err.println(" << No time found");
				return null;
			}

			// -> (try to) get the vote number
			int keyValueVoteNumber = JsonModule.getValueInteger(jsonObject, "votes");

			if (keyValueVoteNumber != -1) {

				playlistElementDataVoteNumber = keyValueVoteNumber;
			} else {
				System.err.println(" << No votes found");
				return null;
			}

			// -> (try to) get if always the changes should be saved
			boolean keyValuePlaceCreated = JsonModule.getValueBoolean(jsonObject, "created-locally");

			playlistElementPlaceCreated = keyValuePlaceCreated;

			return new Object[] { playlistElementDataUnixTime, playlistElementDataSongIndex, playlistElementDataAuthor,
					playlistElementDataComment, playlistElementPlaceCreated, playlistElementDataVoteNumber };

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Reset the whole voting of the playlist
	 */
	public void resetPlaylistVoting() {

		// set votes for every element to 0
		for (int i = 0; i < this.playlistElements.length; i++) {
			this.playlistElements[i].setVotes(0);
		}

	}

}

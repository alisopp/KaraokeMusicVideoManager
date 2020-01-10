package anonymerniklasistanonym.karaokemusicvideomanager.desktopclient.handler;

import anonymerniklasistanonym.karaokemusicvideomanager.desktopclient.objects.MusicVideo;

public interface IMusicVideoPlaylist {

	MusicVideo[] getMusicVideoList();
	void addMusicVideoToPlaylist(int index, String author, String comment);
}


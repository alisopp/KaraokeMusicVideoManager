import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.Scanner;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import anonymerniklasistanonym.karaokemusicvideomanager.desktopclient.generator.HtmlContentGenerator;
import anonymerniklasistanonym.karaokemusicvideomanager.desktopclient.generator.HtmlContentStaticGenerator;
import anonymerniklasistanonym.karaokemusicvideomanager.desktopclient.handler.MusicVideoDataExportHandler;
import anonymerniklasistanonym.karaokemusicvideomanager.desktopclient.handler.MusicVideoHandler;
import anonymerniklasistanonym.karaokemusicvideomanager.desktopclient.handler.ProgramDataHandler;
import anonymerniklasistanonym.karaokemusicvideomanager.desktopclient.objects.MusicVideo;

public class FirstTest {
	
	public HtmlContentGenerator generator;
	private Object[][] musicVideoTable;
	private String[] columnNames;

	@Before
	public void setUp() throws Exception {
		generator = new HtmlContentStaticGenerator();
		
		File directory = new File("C:\\Users\\Manuel\\Desktop\\data\\");
		
		ProgramDataHandler dataHandler = new ProgramDataHandler();
		dataHandler.addPathToPathList(directory.toPath());
		
		MusicVideoHandler handler = new MusicVideoHandler(dataHandler);
		handler.updateMusicVideoList();
		MusicVideo[] list = handler.getMusicVideoList();
		
		musicVideoTable = MusicVideoDataExportHandler.musicVideoListToObjectArray(list);
		columnNames = handler.getColumnNames();
	}

	@Test
	public void testHtmlContentStaticGenerator() {	
		try {
			String expected = readFileToString("C:\\Users\\Manuel\\Desktop\\test\\index.html");
			
			String result = generator.generateHtml(null, musicVideoTable, columnNames);
			
			assertEquals(expected, result);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	private String readFileToString(String fileName) throws FileNotFoundException {
		File file = new File(fileName);
		Scanner scanner = new Scanner(file);
		String content = "";
		
		while (scanner.hasNextLine()) {
			content += scanner.nextLine();
		}
		
		scanner.close();
		
		return content;
	}
}

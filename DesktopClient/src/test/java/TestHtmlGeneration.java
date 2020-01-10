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
import anonymerniklasistanonym.karaokemusicvideomanager.desktopclient.generator.HtmlContentPartyGenerator;
import anonymerniklasistanonym.karaokemusicvideomanager.desktopclient.generator.HtmlContentSearchGenerator;
import anonymerniklasistanonym.karaokemusicvideomanager.desktopclient.generator.HtmlContentStaticGenerator;
import anonymerniklasistanonym.karaokemusicvideomanager.desktopclient.generator.HtmlPartyGenerator;
import anonymerniklasistanonym.karaokemusicvideomanager.desktopclient.handler.MusicVideoDataExportHandler;
import anonymerniklasistanonym.karaokemusicvideomanager.desktopclient.handler.MusicVideoHandler;
import anonymerniklasistanonym.karaokemusicvideomanager.desktopclient.handler.ProgramDataHandler;
import anonymerniklasistanonym.karaokemusicvideomanager.desktopclient.libaries.FileReadWriteModule;
import anonymerniklasistanonym.karaokemusicvideomanager.desktopclient.objects.MusicVideo;

public class TestHtmlGeneration {
	
	private HtmlContentGenerator contentGenerator;
	private Object[][] musicVideoTable;
	private String[] columnNames;
	private final String DAT_DIR = "C:\\Users\\Manuel\\Desktop\\data\\";
	private final String PHP_DIR = "php";
	private final String REF_DIR = "C:\\Users\\Manuel\\Desktop\\reference\\";
	private final String OUT_DIR = "C:\\Users\\Manuel\\Desktop\\output\\party\\php\\";
	

	@Before
	public void setUp() throws Exception {		
		File directory = new File(DAT_DIR);
		File out_dir = new File(OUT_DIR);
		
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
			String expected = readFileToString(REF_DIR + "static\\index.html");
			contentGenerator = new HtmlContentStaticGenerator();
			String result = contentGenerator.generateHtml(PHP_DIR, musicVideoTable, columnNames);
			assertEquals(expected, result);
		} catch (FileNotFoundException e) { /*...*/	}
	}
	
	@Test
	public void testHtmlContentSearchGenerator() {	
		try {
			String expected = readFileToString(REF_DIR + "search\\index.html");
			contentGenerator = new HtmlContentSearchGenerator();
			String result = contentGenerator.generateHtml(PHP_DIR, musicVideoTable, columnNames);
			assertEquals(expected, result);
		} catch (FileNotFoundException e) { /*...*/	}
	}
	
	@Test
	public void testHtmlContentPartyGenerator() {	
		try {
			String expected = readFileToString(REF_DIR + "party\\list.html");
			contentGenerator = new HtmlContentPartyGenerator();
			String result = contentGenerator.generateHtml(PHP_DIR, musicVideoTable, columnNames);
			assertEquals(expected, result);
		} catch (FileNotFoundException e) { /*...*/	}
	}
	
	@Test
	public void testHtmlPartyFormGenerator() {	
		try {
			String expected = readFileToString(REF_DIR + "party\\php\\form.php");
			HtmlPartyGenerator partyGenerator = new HtmlPartyGenerator();
			String phpContent = partyGenerator.generateHtmlPartyForm();
			
			FileReadWriteModule.writeTextFile(new File(OUT_DIR + "form.php"), new String[] { phpContent });
			
			String result = readFileToString(OUT_DIR + "form.php");
			
			assertEquals(expected, result);
		} catch (FileNotFoundException e) { /*...*/	}
	}
	
	@Test
	public void testHtmlPartyLiveGenerator() {	
		try {
			String expected = readFileToString(REF_DIR + "party\\php\\live.php");
			HtmlPartyGenerator partyGenerator = new HtmlPartyGenerator();
			String phpContent = partyGenerator.generateHtmlPartyView(true);
			
			FileReadWriteModule.writeTextFile(new File(OUT_DIR + "live.php"), new String[] { phpContent });
			
			String result = readFileToString(OUT_DIR + "live.php");
			
			assertEquals(expected, result);
		} catch (FileNotFoundException e) { /*...*/	}
	}
	
	@Test
	public void testHtmlPartyProcessGenerator() {	
		try {
			String expected = readFileToString(REF_DIR + "party\\php\\process.php");
			HtmlPartyGenerator partyGenerator = new HtmlPartyGenerator();
			String phpContent = partyGenerator.generateHtmlPartyProcess();
			
			FileReadWriteModule.writeTextFile(new File(OUT_DIR + "process.php"), new String[] { phpContent });
			
			String result = readFileToString(OUT_DIR + "process.php");
			
			assertEquals(expected, result);
		} catch (FileNotFoundException e) { /*...*/	}
	}
	
	@Test
	public void testHtmlPartyVoteGenerator() {	
		try {
			String expected = readFileToString(REF_DIR + "party\\php\\vote.php");
			HtmlPartyGenerator partyGenerator = new HtmlPartyGenerator();
			String phpContent = partyGenerator.generateHtmlPartyVote();
			
			FileReadWriteModule.writeTextFile(new File(OUT_DIR + "vote.php"), new String[] { phpContent });
			
			String result = readFileToString(OUT_DIR + "vote.php");
			
			assertEquals(expected, result);
		} catch (FileNotFoundException e) { /*...*/	}
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

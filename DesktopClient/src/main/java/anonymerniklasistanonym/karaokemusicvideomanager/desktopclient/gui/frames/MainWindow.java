package anonymerniklasistanonym.karaokemusicvideomanager.desktopclient.gui.frames;

import java.io.File;
import java.nio.file.Paths;

import anonymerniklasistanonym.karaokemusicvideomanager.desktopclient.gui.Main;
import anonymerniklasistanonym.karaokemusicvideomanager.desktopclient.gui.dialogs.Dialogs;
import anonymerniklasistanonym.karaokemusicvideomanager.desktopclient.libaries.WindowMethods;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class MainWindow {

	public MainWindow(Stage hi) {
		this.setMainWindowStage(hi);

	}

	public MainWindow(Main hih) {
		this.main = hih;
		this.setMainWindowStage(hih.primaryStage);

	}

	private Stage MainWindowStage;

	private Main main;

	private String windowTitle = "Karaoke Desktop Client [Beta]";
	private int[] normalWindowSize = { 500, 650 };
	private int[] minimalWindowSize = { 450, 300 };

	public void createStage() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getClassLoader().getResource("windows/MainWindow.fxml"));
			BorderPane mainPane = loader.load();

			this.MainWindowStage.setTitle(this.windowTitle);

			// Connection to the Controller from the primary Stage
			MainWindowController mainWindowController = loader.getController();
			mainWindowController.setMainWindow(this.main);

			this.MainWindowStage.setScene(new Scene(mainPane, this.normalWindowSize[0], this.normalWindowSize[1]));

			this.MainWindowStage.setResizable(true);
			this.MainWindowStage.setMinWidth(this.minimalWindowSize[0]);
			this.MainWindowStage.setMinHeight(this.minimalWindowSize[1]);
			this.MainWindowStage.centerOnScreen();

			// try to add a window icon
			try {
				getMainWindowStage().getIcons().addAll(WindowMethods.getWindowIcons());
			} catch (Exception e) {
				System.err.println("Exception while loding icons");
			}
			getMainWindowStage().setOnCloseRequest(new EventHandler<WindowEvent>() {

				@Override
				public void handle(WindowEvent e) {
					onCloseDialog(e);
				}
			});

			// show the window
			getMainWindowStage().show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void onCloseDialog(WindowEvent e) {
		File savedSettings = Paths.get("settings.json").toFile();

		if (!savedSettings.exists() || this.main.musicVideohandler.getAlwaysSave() == true) {
			// save changes if nothing is there
			this.main.musicVideohandler.saveSettings(savedSettings);
			System.out.println("Later...");
		} else {

			if (!this.main.musicVideohandler.compareSettings(savedSettings)) {
				Dialogs.mainStageClose(e, this.main.musicVideohandler, savedSettings);
			} else {
				System.out.println("Settings were the same");
			}

		}
	}

	public Stage getMainWindowStage() {
		return MainWindowStage;
	}

	public void setMainWindowStage(Stage mainWindowStage) {
		MainWindowStage = mainWindowStage;
	}
}

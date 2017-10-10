package anonymerniklasistanonym.karaokemusicvideomanager.desktopclient.gui.frames;

import anonymerniklasistanonym.karaokemusicvideomanager.desktopclient.gui.Main;
import anonymerniklasistanonym.karaokemusicvideomanager.desktopclient.gui.controller.MainWindowController;
import anonymerniklasistanonym.karaokemusicvideomanager.desktopclient.libaries.DialogModule;
import anonymerniklasistanonym.karaokemusicvideomanager.desktopclient.libaries.WindowModule;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;

public class MainWindow {

	public MainWindow(Main hih) {
		this.main = hih;
	}

	private Stage MainWindowStage;
	private Scene scene;

	private Main main;

	private String windowTitle = "Karaoke Desktop Client [Beta]";
	private int[] normalWindowSize = { 600, 600 };
	private int[] minimalWindowSize = { 540, 450 };

	public void createScene() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getClassLoader().getResource("windows/MainWindow.fxml"));
			BorderPane mainPane = loader.load();
			this.scene = new Scene(mainPane, this.normalWindowSize[0], this.normalWindowSize[1]);

			// Connection to the Controller from the primary Stage
			MainWindowController mainWindowController = loader.getController();
			mainWindowController.setMainWindow(this.main);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Stage createStage() {
		try {
			this.MainWindowStage = new Stage(StageStyle.DECORATED);

			this.MainWindowStage.setTitle(this.windowTitle);

			this.MainWindowStage.setScene(scene);

			this.MainWindowStage.setResizable(true);
			this.MainWindowStage.setMinWidth(this.minimalWindowSize[0]);
			this.MainWindowStage.setMinHeight(this.minimalWindowSize[1]);
			this.MainWindowStage.centerOnScreen();

			// try to add a window icon
			try {
				getMainWindowStage().getIcons().addAll(WindowModule.getWindowIcons());
			} catch (Exception e) {
				System.err.println("Exception while loding icons");
			}
			getMainWindowStage().setOnCloseRequest(new EventHandler<WindowEvent>() {

				@Override
				public void handle(WindowEvent e) {
					onCloseDialog(e);
				}
			});

			this.MainWindowStage.show();

			return MainWindowStage;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public void onCloseDialog(WindowEvent e) {

		if (!this.main.getMusicVideohandler().settingsFileExist()
				|| this.main.getMusicVideohandler().getAlwaysSave() == true) {
			// save changes if nothing is there
			this.main.getMusicVideohandler().saveSettingsToFile();

			Platform.exit();
		} else {

			if (!this.main.getMusicVideohandler().compareSettings()) {
				DialogModule.mainStageClose(e, this.main.getMusicVideohandler());
			} else {
				System.out.println("Settings were the same");
				Platform.exit();
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

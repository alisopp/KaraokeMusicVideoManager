package anonymerniklasistanonym.karaokemusicvideomanager.desktopclient.gui.frames;

import java.io.File;
import java.nio.file.Paths;

import anonymerniklasistanonym.karaokemusicvideomanager.desktopclient.gui.dialogs.Dialogs;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class MainWindow {

	public MainWindow(Stage hi) {
		this.setMainWindowStage(hi);

	}

	private Stage MainWindowStage;

	private String windowTitle = "Karaoke Desktop Client [Beta]";
	private int[] normalWindowSize = { 500, 650 };
	private int[] minimalWindowSize = { 450, 300 };

	public void createStage() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/windows/MainWindow.fxml"));
			Parent root = loader.load();
			getMainWindowStage().setTitle(this.windowTitle);
			getMainWindowStage().setScene(new Scene(root, this.normalWindowSize[0], this.normalWindowSize[1]));
			getMainWindowStage().setResizable(true);
			getMainWindowStage().setMinWidth(this.minimalWindowSize[0]);
			getMainWindowStage().setMinHeight(this.minimalWindowSize[1]);
			getMainWindowStage().centerOnScreen();

			getMainWindowStage().setOnCloseRequest(new EventHandler<WindowEvent>() {

				@Override
				public void handle(WindowEvent e) {

					File savedSettings = Paths.get("settings.json").toFile();

					if (!savedSettings.exists()) {
						// save changes if nothing is there
						System.out.println("Later...");
					} else {
						Dialogs.mainStageClose(e);
					}

				}
			});

			// primaryStage.getIcons().add(new
			// Image(getClass().getResourceAsStream("icon.png")));

			// show the window
			getMainWindowStage().show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Stage getMainWindowStage() {
		return MainWindowStage;
	}

	public void setMainWindowStage(Stage mainWindowStage) {
		MainWindowStage = mainWindowStage;
	}
}

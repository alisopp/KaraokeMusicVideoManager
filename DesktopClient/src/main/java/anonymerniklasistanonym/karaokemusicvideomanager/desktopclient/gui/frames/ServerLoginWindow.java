package anonymerniklasistanonym.karaokemusicvideomanager.desktopclient.gui.frames;

import anonymerniklasistanonym.karaokemusicvideomanager.desktopclient.gui.dialogs.Dialogs;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class ServerLoginWindow {

	private Stage MainWindowStage;

	private String windowTitle = "Karaoke Desktop Client [Beta]";
	private int[] normalWindowSize = { 500, 650 };
	private int[] minimalWindowSize = { 450, 300 };

	public Stage createStage() {
		try {
			Parent root = FXMLLoader.load(getClass().getResource("ServerLoginWindow.fxml"));
			MainWindowStage.setTitle(this.windowTitle);
			MainWindowStage.setScene(new Scene(root, this.normalWindowSize[0], this.normalWindowSize[1]));
			MainWindowStage.setResizable(true);
			MainWindowStage.setMinWidth(this.minimalWindowSize[0]);
			MainWindowStage.setMinHeight(this.minimalWindowSize[1]);
			MainWindowStage.centerOnScreen();

			MainWindowStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
				@Override
				public void handle(WindowEvent e) {
					Dialogs.mainStageClose(e);
				}
			});

			// primaryStage.getIcons().add(new
			// Image(getClass().getResourceAsStream("icon.png")));

			return MainWindowStage;
			// show the window
			MainWindowStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

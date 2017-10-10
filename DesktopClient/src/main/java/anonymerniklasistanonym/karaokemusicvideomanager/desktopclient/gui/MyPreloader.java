package anonymerniklasistanonym.karaokemusicvideomanager.desktopclient.gui;

import anonymerniklasistanonym.karaokemusicvideomanager.desktopclient.libaries.ClassResourceReaderModule;
import javafx.application.Platform;
import javafx.application.Preloader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * Preloader. Shows a image and progress bar before starting the main
 * window.<br>
 * Preloader could only be implemented thanks to a great tutorial from
 * <a href="https://blog.codecentric.de/en/author/aron-sreder/">aron.sreder</a>
 * from <a href=
 * "https://blog.codecentric.de/en/2015/09/javafx-how-to-easily-implement-application-preloader-2/">codecentric
 * Blog</a>
 *
 * @author AnonymerNiklasistanonym <niklas.mikeler@gmail.com>
 * @version 2.0
 *
 */
public class MyPreloader extends Preloader {

	private Stage preloaderStage;
	private Scene scene;
	private ProgressBar progresBar;

	public MyPreloader() {
		// Constructor is called before everything.
		System.out.println("Preloader constructor");
	}

	@Override
	public void init() throws Exception {
		System.out.println("Preloader initalisation");

		// If preloader has complex UI it's initialization can be done in here
		Platform.runLater(() -> {

			final Text versionAndNameText = new Text(10, 20, "MusicVideoManager v2.0.0");
			versionAndNameText.setText("MusicVideoManager v2.0.0");
			versionAndNameText.setFont(Font.font(16));
			versionAndNameText.setFill(Color.GREY);

			progresBar = new ProgressBar(100.00);
			progresBar.setProgress(0);
			progresBar.setStyle("-fx-padding: 10 10 10 10;");

			final VBox vBoxTextAndProgress = new VBox(versionAndNameText, progresBar);
			vBoxTextAndProgress.setAlignment(Pos.CENTER);
			vBoxTextAndProgress.setStyle("-fx-background-color: #FFFFFF;-fx-padding: 10 10 10 10;");

			final ImageView preloadImage = new ImageView();
			preloadImage.setImage(new Image(ClassResourceReaderModule.getInputStream("images/preload.png")));

			// create a vBox with the image and the vBox with the progress and text
			final VBox root = new VBox(preloadImage, vBoxTextAndProgress);
			root.setAlignment(Pos.CENTER);
			root.setStyle("-fx-background-color: #FFFFFF;-fx-padding: 20 0 0 0;");

			// set the scene and a window width and height
			scene = new Scene(root, 500, 420);
		});
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		System.out.println("Preloader srtart the stage");

		primaryStage.setScene(scene);

		// Set preloader scene and show stage.
		primaryStage.setScene(scene);
		primaryStage.initStyle(StageStyle.UNDECORATED);
		primaryStage.show();

		this.preloaderStage = primaryStage;
	}

	@Override
	public void handleApplicationNotification(PreloaderNotification info) {

		if (info instanceof ProgressNotification) {
			progresBar.setProgress(((ProgressNotification) info).getProgress() / 100);
		}
	}

	@Override
	public void handleStateChangeNotification(StateChangeNotification info) {

		StateChangeNotification.Type type = info.getType();
		switch (type) {
		case BEFORE_LOAD:
			System.out.println("Preloader >> BEFORE_LOAD");
			break;
		case BEFORE_INIT:
			System.out.println("Preloader >> BEFORE_INIT");
			break;
		case BEFORE_START:
			System.out.println("Preloader >> BEFORE_START >> Hide the stage");
			preloaderStage.hide();
			break;
		}
	}
}

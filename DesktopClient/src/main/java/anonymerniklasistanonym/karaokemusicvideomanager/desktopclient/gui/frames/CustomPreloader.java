package anonymerniklasistanonym.karaokemusicvideomanager.desktopclient.gui.frames;

import java.util.Locale;

import anonymerniklasistanonym.karaokemusicvideomanager.desktopclient.libaries.ClassResourceReaderModule;
import anonymerniklasistanonym.karaokemusicvideomanager.desktopclient.translations.Internationalization;
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
 * @author AnonymerNiklasistanonym <niklas.mikeler@gmail.com> | <a href=
 *         "https://github.com/AnonymerNiklasistanonym">https://github.com/AnonymerNiklasistanonym</a>
 */
public class CustomPreloader extends Preloader {

	/**
	 * Stage of the preloader
	 */
	private Stage preloaderStage;

	/**
	 * Scene of the preloader
	 */
	private Scene preloaderScene;

	/**
	 * Progress bar of the preloader
	 */
	private ProgressBar progresBar;

	/**
	 * Constructor
	 */
	public CustomPreloader() {

		// Constructor is called before everything
		System.out.println("Preloader constructor");
	}

	@Override
	public void init() throws Exception {

		System.out.println("Preloader initalisation");

		// set the language in relation to the Java Runtime language
		Internationalization.setBundle(Locale.getDefault());

		// setup a preloader image
		final ImageView preloadImage = new ImageView();
		preloadImage.setImage(new Image(ClassResourceReaderModule.getInputStream("images/preload.png")));

		// setup the preloader's bottom text
		final Text versionAndNameText = new Text(10, 20, "KaraokeMusicVideoManager v2.0.0");
		versionAndNameText.setFont(Font.font(16));
		versionAndNameText.setFill(Color.GREY);

		// setup the preloader's progress
		this.progresBar = new ProgressBar(100.00);
		this.progresBar.setProgress(0);
		this.progresBar.setStyle("-fx-padding: 10 10 10 10;");

		// vertical box for the text and the progress bar
		final VBox vBoxTextAndProgress = new VBox(versionAndNameText, this.progresBar);
		vBoxTextAndProgress.setAlignment(Pos.CENTER);
		vBoxTextAndProgress.setStyle("-fx-background-color: #FFFFFF;-fx-padding: 10 10 10 10;");

		// vertical box for everything
		final VBox root = new VBox(preloadImage, vBoxTextAndProgress);
		root.setAlignment(Pos.CENTER);
		root.setStyle("-fx-background-color: #FFFFFF;-fx-padding: 20 0 0 0;");

		// set the scene and a window width and height
		this.preloaderScene = new Scene(root, 500, 420);

	}

	@Override
	public void start(Stage primaryStage) throws Exception {

		System.out.println("Open Preloader stage");

		// add the scene to the primary stage
		primaryStage.setScene(this.preloaderScene);

		// Set preloader scene and show stage.
		primaryStage.setScene(preloaderScene);

		// no symbols to close or do anything on the stage
		primaryStage.initStyle(StageStyle.UNDECORATED);

		// show the stage
		primaryStage.show();

		// connect the stage to the handleStateChangeNotification method
		this.preloaderStage = primaryStage;

	}

	@Override
	public void handleApplicationNotification(PreloaderNotification info) {

		// get the current process and change the progress bar
		if (info instanceof ProgressNotification) {
			progresBar.setProgress(((ProgressNotification) info).getProgress() / 100);
		}
	}

	@Override
	public void handleStateChangeNotification(StateChangeNotification info) {

		// get the states to know when to hide the preloader
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

			// hide the stage before start() in main get's executed
			this.preloaderStage.hide();
			break;
		}

	}

}

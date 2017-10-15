package anonymerniklasistanonym.karaokemusicvideomanager.desktopclient.gui.frames;

import anonymerniklasistanonym.karaokemusicvideomanager.desktopclient.libaries.ClassResourceReaderModule;
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
	 * Stage of this preloader
	 */
	private Stage preloaderStage;

	/**
	 * Scene of this preloader
	 */
	private Scene preloaderScene;

	/**
	 * Progress bar of this preloader
	 */
	private ProgressBar preloaderProgressBar;

	/**
	 * Constructor
	 */
	public CustomPreloader() {
		System.out.println(">> Preloader constructor");
	}

	@Override
	public void init() throws Exception {

		System.out.println(">> Preloader initialization");

		// load the preloader image
		final ImageView preloadImage = new ImageView();
		preloadImage.setImage(new Image(ClassResourceReaderModule.getInputStream("images/preload.png")));

		// set the preloader text of version and name of the program
		final Text versionAndNameText = new Text(10, 20, "KaraokeMusicVideoManager v2.0.0");
		versionAndNameText.setFont(Font.font(16));
		versionAndNameText.setFill(Color.GREY);

		// setup the preloader progress bar which starts at 0 and goes to 100
		this.preloaderProgressBar = new ProgressBar(100.00);
		this.preloaderProgressBar.setProgress(0);
		this.preloaderProgressBar.setStyle("-fx-padding: 10 10 10 10;");

		// create vertical box and insert the version/name text and the progress bar
		final VBox vBoxTextAndProgress = new VBox(versionAndNameText, this.preloaderProgressBar);
		vBoxTextAndProgress.setAlignment(Pos.CENTER);
		vBoxTextAndProgress.setStyle("-fx-background-color: #FFFFFF;-fx-padding: 10 10 10 10;");

		// create another vertical box and insert in it the image and the other box
		final VBox root = new VBox(preloadImage, vBoxTextAndProgress);
		root.setAlignment(Pos.CENTER);
		root.setStyle("-fx-background-color: #FFFFFF;-fx-padding: 20 0 0 0;");

		// set the scene and a window width and height
		this.preloaderScene = new Scene(root, 500, 420);

		System.out.println("<< Preloader initialization finished");

	}

	@Override
	public void start(Stage primaryStage) throws Exception {

		// connect the stage to the handleStateChangeNotification method
		this.preloaderStage = primaryStage;

		System.out.println(">> Open Preloader stage");

		// add the created scene to the primary stage
		this.preloaderStage.setScene(this.preloaderScene);

		// show only the stage no real window
		this.preloaderStage.initStyle(StageStyle.UNDECORATED);

		// show the primary stage/preloader window
		this.preloaderStage.show();

		System.out.println("PRELOADER");

	}

	@Override
	public void handleApplicationNotification(PreloaderNotification info) {

		// get the current process and change the progress bar
		if (info instanceof ProgressNotification) {
			preloaderProgressBar.setProgress(((ProgressNotification) info).getProgress() / 100);
		}

	}

	@Override
	public void handleStateChangeNotification(StateChangeNotification info) {

		// react to different stages while loading
		switch (info.getType()) {
		case BEFORE_LOAD:
			System.out.println(">> Preloader >> BEFORE_LOAD");
			break;
		case BEFORE_INIT:
			System.out.println(">> Preloader >> BEFORE_INIT");
			break;
		case BEFORE_START:
			System.out.println(">> Preloader >> BEFORE_START");

			System.out.println(">> Preloader hide stage");
			this.preloaderStage.hide();
			System.out.println("<< Preloader stage hidden");

			break;
		}

	}

}

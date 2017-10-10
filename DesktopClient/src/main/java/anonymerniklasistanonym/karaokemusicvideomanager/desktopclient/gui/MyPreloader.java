package anonymerniklasistanonym.karaokemusicvideomanager.desktopclient.gui;

import anonymerniklasistanonym.karaokemusicvideomanager.desktopclient.libaries.ClassResourceReaderModule;
import javafx.application.Platform;
import javafx.application.Preloader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class MyPreloader extends Preloader {
	private Parent parentNode;

	private static final double WIDTH = 500;
	private static final double HEIGHT = 420;

	private Stage preloaderStage;
	private Scene scene;

	private Label progress;
	private ProgressIndicator pin;
	private ProgressBar pb;

	public MyPreloader() {
		// Constructor is called before everything.
		System.out.println(Main.STEP() + "MyPreloader constructor called, thread: " + Thread.currentThread().getName());
	}

	@Override
	public void init() throws Exception {
		System.out.println(Main.STEP() + "MyPreloader#init (could be used to initialize preloader view), thread: "
				+ Thread.currentThread().getName());

		// If preloader has complex UI it's initialization can be done in
		// MyPreloader#init
		Platform.runLater(() -> {
			Label title = new Label("MusicVideoManager v2.0.0");
			title.setTextAlignment(TextAlignment.CENTER);
			title.setStyle("-fx-color: #000000;");

			Text t = new Text(10, 20, "MusicVideoManager v2.0.0");
			t.setText("MusicVideoManager v2.0.0");
			t.setFont(Font.font(16));
			t.setFill(Color.GREY);

			// progress = new Label("0%");
			pb = new ProgressBar(100.00);
			pb.setProgress(0);

			pb.setStyle("-fx-padding: 10 10 10 10;");

			VBox textAndCo = new VBox(t, pb);
			textAndCo.setAlignment(Pos.CENTER);

			textAndCo.setStyle("-fx-background-color: #FFFFFF;-fx-padding: 10 10 10 10;");

			final ImageView imv = new ImageView();
			final Image image2 = new Image(ClassResourceReaderModule.getInputStream("images/preload.png"));
			imv.setImage(image2);

			VBox root = new VBox(imv, textAndCo);
			root.setAlignment(Pos.CENTER);

			root.setStyle("-fx-background-color: #FFFFFF;-fx-padding: 20 0 0 0;");

			scene = new Scene(root, WIDTH, HEIGHT);
		});
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		System.out.println(Main.STEP() + "MyPreloader#start (showing preloader stage), thread: "
				+ Thread.currentThread().getName());

		this.preloaderStage = primaryStage;

		preloaderStage.setScene(scene);

		// Set preloader scene and show stage.
		preloaderStage.setScene(scene);
		preloaderStage.initStyle(StageStyle.UNDECORATED);
		preloaderStage.show();

	}

	@Override
	public void handleApplicationNotification(PreloaderNotification info) {
		// Handle application notification in this point (see MyApplication#init).
		if (info instanceof ProgressNotification) {
			// progress.setText(((ProgressNotification) info).getProgress() + "%");
			pb.setProgress(((ProgressNotification) info).getProgress() / 100);
			System.out.println(pb.getProgress());
		}
	}

	@Override
	public void handleStateChangeNotification(StateChangeNotification info) {
		// Handle state change notifications.
		StateChangeNotification.Type type = info.getType();
		switch (type) {
		case BEFORE_LOAD:
			// Called after MyPreloader#start is called.
			System.out.println(Main.STEP() + "BEFORE_LOAD");
			break;
		case BEFORE_INIT:
			// Called before MyApplication#init is called.
			System.out.println(Main.STEP() + "BEFORE_INIT");
			break;
		case BEFORE_START:
			// Called after MyApplication#init and before MyApplication#start is called.
			System.out.println(Main.STEP() + "BEFORE_START");

			preloaderStage.hide();
			break;
		}
	}
}

package application;

import java.io.IOException;

import application.view.MainTableViewController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class Main extends Application {

	private Stage primaryStage;
	private BorderPane tableBorderPane;
	private MainTableViewController tableController;

	@Override
	public void start(Stage primaryStage) {
		this.primaryStage = primaryStage;
		this.primaryStage.setTitle("CalcTo100 Rechner");
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("view/MainTableView.fxml"));
			tableBorderPane = (BorderPane) loader.load();
			Scene scene = new Scene(tableBorderPane);
			tableController = loader.getController();
			tableController.setMainApp(this);
			this.primaryStage.setScene(scene);
			this.primaryStage.show();
		} catch (IOException e) {
			System.err.println("Unable to load central table view");
			System.exit(1); // error code
		}
	}

	public Stage getPrimaryStage() {
		return primaryStage;
	}

	public static void main(String[] args) {
		launch(args);
	}

}

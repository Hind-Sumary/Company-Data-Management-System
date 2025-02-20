package application;

import java.sql.SQLException;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Main extends Application {

	FXPane fxPane = new FXPane();
	Stage primaryStage;

	@Override
	public void start(Stage primaryStage) throws ClassNotFoundException, SQLException {

        // Connect to the database and fetch data for each table
        DBase.connectDB();
        DBase.fetchEmployeeData();
        DBase.fetchDepartmentData();
        DBase.fetchCompanyData();
        DBase.fetchProductData();
        DBase.fetchOrderData();
        
		fxPane.fillEmployeeTable();
		fxPane.fillDepartmentTable();
		fxPane.fillCompanyTable();
		fxPane.fillProductTable();
		fxPane.fillOrderTable();
		
		Scene scene = new Scene(createMainScene(), 920, 600);
		this.primaryStage = primaryStage;
		fxPane.setPrimaryStage(primaryStage);
		scene.setFill(Color.web("#E5E5E5")); // Set the background color
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	public BorderPane createMainScene() {
		BorderPane root = new BorderPane();
		root.setStyle("-fx-background-color: #E5E5E5;");
		root.setPadding(new Insets(0, 10, 0, 0));
		root.setMinHeight(600);
		root.setMinWidth(1000);

		VBox buttonVBox = FXPane.createButtonVBox();

		VBox mainBox = new VBox();
		Label adminLabel = new Label("This section is for admins only!");
		adminLabel.setStyle("-fx-text-fill: black; -fx-font-size: 24; -fx-opacity: 0.4; -fx-font-weight: bold;");
		mainBox.setAlignment(Pos.CENTER);
		mainBox.getChildren().add(adminLabel);

		// Left VBox for buttons
		root.setLeft(buttonVBox);
		root.setCenter(mainBox);

		return root;
	}

	public static void main(String[] args) {
		Application.launch(args);
	}
}

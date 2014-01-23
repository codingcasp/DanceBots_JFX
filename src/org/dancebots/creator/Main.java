package org.dancebots.creator;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.dancebots.creator.model.Song;

import java.io.IOException;
import java.util.logging.Level;


public class Main extends Application {

    private Stage primaryStage;
    private BorderPane rootLayout;


    @Override
    public void start(Stage primaryStage) throws Exception{
        this.primaryStage = primaryStage;
        primaryStage.setTitle("DanceBot Choreographer v0.1 - 2014");

        primaryStage.setTitle("DanceBot Choreographer v0.1");


        try {
            // Load the root layout from the fxml file
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("view/RootLayout.fxml"));
            rootLayout = (BorderPane) loader.load();
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            // Exception gets thrown if the fxml file could not be loaded
            e.printStackTrace();
        }

        showChoreographer();

    }

    /**
     * Returns the main stage.
     * @return
     */
    public Stage getPrimaryStage() {
        return primaryStage;
    }

    /**
     * Shows the person overview scene.
     */
    public void showChoreographer() {
        try {
            // Load the fxml file and set into the center of the main layout
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("view/Choreographer.fxml"));
            AnchorPane overviewPage = (AnchorPane) loader.load();
            ChoreographerController controller = (ChoreographerController)loader.getController();
            controller.setStage(primaryStage);
            rootLayout.setCenter(overviewPage);

        } catch (IOException e) {
            // Exception gets thrown if the fxml file could not be loaded
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}

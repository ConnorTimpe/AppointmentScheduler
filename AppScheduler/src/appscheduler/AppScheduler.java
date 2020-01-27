/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package appscheduler;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author conno
 */
public class AppScheduler extends Application {

    @Override
    public void start(Stage stage) throws Exception {

        //Load Log In Screen
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/LogInScreen.fxml"));
        Controller.LogInScreenController controller = new Controller.LogInScreenController();
        loader.setController(controller);
        Parent root = loader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
        

    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}

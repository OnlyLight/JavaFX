/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package login;

import insidefx.undecorator.UndecoratorScene;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author QuangDuy
 */
public class Login extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("FXMLLogin_1.fxml"));
        
        UndecoratorScene undecoratorScene = new UndecoratorScene(stage, (Region) root);
//        Scene scene = new Scene(root);
        undecoratorScene.setFadeInTransition();
        
//        stage.initStyle(StageStyle.UNDECORATED);
        
        stage.getIcons().add(new Image("/images/download.jpg"));
        stage.setTitle("OL! Tea");
        stage.setMinHeight(590);
        stage.setMinWidth(967);
        stage.setScene(undecoratorScene);
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}

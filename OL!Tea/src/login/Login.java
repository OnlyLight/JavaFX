/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package login;

import animatefx.animation.FadeIn;
import insidefx.undecorator.UndecoratorScene;
import javafx.animation.ScaleTransition;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

/**
 *
 * @author QuangDuy
 */
public class Login extends Application {
    private static Stage guiStage;

    public static Stage getStage() {
        return guiStage;
    }
    
    public static void setScene(Parent root) {
        guiStage.close();
        UndecoratorScene undecoratorScene = new UndecoratorScene(guiStage, (Region) root);
        guiStage.setScene(undecoratorScene);
        new FadeIn(guiStage.getScene().getRoot());
        guiStage.show();
        
    }

    @Override
    public void start(Stage stage) throws Exception {
        guiStage = stage;
        Parent root = FXMLLoader.load(getClass().getResource("FXMLLogin_1.fxml"));
        
        UndecoratorScene undecoratorScene = new UndecoratorScene(guiStage, (Region) root);
        
        guiStage.getIcons().add(new Image("/images/download.jpg"));
        guiStage.setTitle("OL! Tea");
        guiStage.setMinHeight(590);
        guiStage.setMinWidth(967);
        guiStage.setScene(undecoratorScene);
        new FadeIn(root).play();
        guiStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}

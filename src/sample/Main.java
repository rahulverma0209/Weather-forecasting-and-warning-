package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class Main extends Application {

    public static Stage mainStage=new Stage();
    @Override
    public void start(Stage primaryStage) throws Exception{
        mainStage=primaryStage;

        FXMLLoader loader =new FXMLLoader(getClass().getResource("sample.fxml"));
        AnchorPane pane=loader.load();
        Scene scene=new Scene(pane);

        mainStage.setTitle(" Weather");
        mainStage.getIcons().add(new Image("file:///F:/CurrentlyWorking/MiniProject_sem6/Weather/src/sample/media/logo.png"));
        mainStage.setScene(scene);
        mainStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}

package sample;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class Admin {

    public void ViewWeather()throws Exception{
        Stage weather=new Stage();
        FXMLLoader loader =new FXMLLoader(getClass().getResource("weather.fxml"));
        weather.initModality(Modality.APPLICATION_MODAL);
        AnchorPane pane=loader.load();

        Scene scene=new Scene(pane);

        weather.setTitle(" Weather");
        weather.getIcons().add(new Image("file:///F:/CurrentlyWorking/MiniProject_sem6/Weather/src/sample/media/logo.png"));
        //primaryStage.setResizable(false);
        weather.setScene(scene);
        weather.show();

        weather.setOnCloseRequest(event -> callMainClass());
        Controller.admin.close();
    }

    private void callMainClass()
    {
        Controller c=new Controller();
        try {
            c.openAdmin();
        }
        catch (java.lang.Exception e)
        {
            e.getMessage();
        }
    }
    public void loadUser()throws Exception{

        Stage userPage =new Stage();
        FXMLLoader loader =new FXMLLoader(getClass().getResource("User.fxml"));
        userPage.initModality(Modality.APPLICATION_MODAL);
        AnchorPane pane=loader.load();

        Scene scene=new Scene(pane);

        userPage.setTitle(" UserPage");
        userPage.getIcons().add(new Image("file:///F:/CurrentlyWorking/MiniProject_sem6/Weather/src/sample/media/logo.png"));
        userPage.setScene(scene);
        userPage.show();
        userPage.setOnCloseRequest(event -> callMainClass());
        Controller.admin.close();
    }
    public void loadSystem()throws Exception{

        Stage SystemPage =new Stage();
        FXMLLoader loader =new FXMLLoader(getClass().getResource("systeminfo.fxml"));
        SystemPage.initModality(Modality.APPLICATION_MODAL);
        AnchorPane pane=loader.load();

        Scene scene=new Scene(pane);

        SystemPage.setTitle(" System Info");
        SystemPage.getIcons().add(new Image("file:///F:/CurrentlyWorking/MiniProject_sem6/Weather/src/sample/media/logo.png"));
        SystemPage.setScene(scene);
        SystemPage.show();
        SystemPage.setOnCloseRequest(event -> callMainClass());
        Controller.admin.close();
    }

    public void sendMail() throws Exception{
        SendingWarning sendingWarning=new SendingWarning();
        sendingWarning.sendMsg();
    }
}

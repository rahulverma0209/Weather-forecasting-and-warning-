package sample;

import com.jfoenix.controls.*;
import javafx.fxml.*;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.*;
import javafx.stage.*;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;


public class Controller implements Initializable {

    @FXML
    private MediaView mediaVideo;
    @FXML
    private JFXTextField username;
    @FXML
    private JFXPasswordField password;

    public static Stage admin=new Stage();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Media media =new Media("file:///F:/CurrentlyWorking/MiniProject_sem6/Weather/src/sample/media/loginscreen.mp4");
        MediaPlayer mediaPlayer=new MediaPlayer(media);

        mediaVideo.setMediaPlayer(mediaPlayer);
        mediaPlayer.setVolume(0);
        mediaPlayer.play();
    }

    public void login() throws Exception
    {
        if(username.getText().equals("") || password.getText().equals(""))
        {
            AlertBox.error("Some Field is Empty");
            return;
        }
        if(check_username_password(username.getText(),password.getText()))
        {
            openAdmin();
        }
        else
        {
            AlertBox.error("Username or Password is incorrect");
        }
    }
    public void openAdmin()throws Exception{

        admin=new Stage();
        FXMLLoader loader =new FXMLLoader(getClass().getResource("Admin.fxml"));
        admin.initModality(Modality.APPLICATION_MODAL);
        AnchorPane pane=loader.load();

        Scene scene=new Scene(pane);

        admin.setTitle(" Admin");
        admin.getIcons().add(new Image("file:///F:/CurrentlyWorking/MiniProject_sem6/Weather/src/sample/media/logo.png"));
        //primaryStage.setResizable(false);
        admin.setScene(scene);
        admin.show();

        admin.setOnCloseRequest(event -> callMainClass());
        Main.mainStage.close();

    }
    private void callMainClass()
    {
        Main m=new Main();
        try {
            m.start(new Stage());
        }
        catch (java.lang.Exception e)
        {
            e.getMessage();
        }
    }
    private boolean check_username_password(String username,String password) throws Exception
    {
        Connection con=DB.getConnection();
        Statement st=con.createStatement();
        String q="select * from admin where username = '" + username +"';";

        ResultSet rs=st.executeQuery(q);

        while (rs.next()) {
            if(password.compareTo(rs.getString("password"))==0) {
                return true;
            }
        }
        return false;
    }

    public void resetPassword() throws Exception {
        Stage graph=new Stage();
        FXMLLoader loader =new FXMLLoader(getClass().getResource("resetAdminPassword.fxml"));
        graph.initModality(Modality.APPLICATION_MODAL);
        AnchorPane pane=loader.load();

        Scene scene=new Scene(pane);

        graph.setTitle(" Reset Password");
        graph.getIcons().add(new Image("file:///F:/CurrentlyWorking/MiniProject_sem6/Weather/src/sample/media/logo.png"));
        graph.setScene(scene);
        graph.show();
    }
}

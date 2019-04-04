package sample;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.net.URL;
import java.sql.Connection;
import java.sql.Statement;
import java.util.Properties;
import java.util.ResourceBundle;


public class ResetAdminPassword implements Initializable {

    public static Integer otp=0;
    String to ="rahul0512.pi@gmail.com";
    String from = "amrita002010@gmail.com";

    @FXML
    public Button send_otp_btn,saveButton;

    @FXML
    public TextField inputOTP;

    @FXML
    public PasswordField passwordField;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        saveButton.setVisible(false);
        passwordField.setVisible(false);
    }

    public void sendOTP(){

        OTP o=new OTP();
        o.start();

        /*int n1=(int)(Math.random()*100);
        int n2=(int)(Math.random()*100);
        int n3=(int)(Math.random()*100);

        otp=(n1*10000)+(n2*100)+n3;

        System.out.println(otp);


        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", 587);
        Session session = Session.getDefaultInstance(properties, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("amrita002010@gmail.com", "RahuL@0209");
            }

        });
        try {
            MimeMessage message = new MimeMessage(session);

            message.setFrom(new InternetAddress(from));

            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

            message.setSubject("OTP");

            BodyPart messageBodyPart1 = new MimeBodyPart();
            messageBodyPart1.setText(otp.toString());
            Multipart multipartObject = new MimeMultipart();
            multipartObject.addBodyPart(messageBodyPart1);

            message.setContent(multipartObject);

            Transport.send(message);
            AlertBox.confirmation("Mail successfully sent");
            System.out.println("Mail successfully sent");

            send_otp_btn.setDisable(true);

        } catch (Exception e) {
            e.printStackTrace();
        }*/
        send_otp_btn.setDisable(true);
    }

    public void verifyOTP(){
        String str=inputOTP.getText();

        if(str.equals(""))
        {
            AlertBox.warning("Enter OTP");
            return;
        }
        if(!otp.toString().equals(str)){
            System.out.println("Invalid");
            return;
        }
        System.out.println("Valid");

        saveButton.setVisible(true);
        passwordField.setVisible(true);
    }
    public void savePassword()throws Exception{
        String str=passwordField.getText();

        if(str.equals(""))
        {
            AlertBox.warning("Create some password");
            return;
        }

        Connection con=DB.getConnection();
        Statement st=con.createStatement();

        String q="update admin set password='"+str+"';";
        st.executeUpdate(q);

        AlertBox.information("Password Updated");

        saveButton.setVisible(false);
        passwordField.setVisible(false);
    }
}


class OTP extends Thread{

    String to ="rahul0512.pi@gmail.com";
    String from = "amrita002010@gmail.com";

    public void run(){
        int n1=(int)(Math.random()*100);
        int n2=(int)(Math.random()*100);
        int n3=(int)(Math.random()*100);

        ResetAdminPassword.otp=(n1*10000)+(n2*100)+n3;

        System.out.println(ResetAdminPassword.otp);


        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", 587);
        Session session = Session.getDefaultInstance(properties, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("amrita002010@gmail.com", "RahuL@0209");
            }

        });
        try {
            MimeMessage message = new MimeMessage(session);

            message.setFrom(new InternetAddress(from));

            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

            message.setSubject("OTP");

            BodyPart messageBodyPart1 = new MimeBodyPart();
            messageBodyPart1.setText(ResetAdminPassword.otp.toString());
            Multipart multipartObject = new MimeMultipart();
            multipartObject.addBodyPart(messageBodyPart1);

            message.setContent(multipartObject);

            Transport.send(message);

            /*Alert alert=new Alert(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText(null);
            alert.setContentText("Mail successfully sent");
            alert.show();*/

            System.out.println("Mail successfully sent");

            //send_otp_btn.setDisable(true);

        } catch (Exception e) {

            /*Alert alert=new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Error in sending mail");
            alert.show();*/

            e.printStackTrace();
        }
    }
}
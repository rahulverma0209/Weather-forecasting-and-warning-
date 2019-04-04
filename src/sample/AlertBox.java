package sample;

import javafx.scene.control.Alert;

public class AlertBox {
    static void error(String str)
    {
        Alert alert=new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(null);
        alert.setContentText(str);
        alert.show();
    }
    static void information(String str)
    {
        Alert alert=new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setContentText(str);
        alert.show();
    }
    static void warning(String str)
    {
        Alert alert=new Alert(Alert.AlertType.WARNING);
        alert.setHeaderText(null);
        alert.setContentText(str);
        alert.show();
    }
    static void confirmation(String str)
    {
        Alert alert=new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText(null);
        alert.setContentText(str);
        alert.show();
    }
    static void none(String str)
    {
        Alert alert=new Alert(Alert.AlertType.NONE);
        alert.setHeaderText(null);
        alert.setContentText(str);
        alert.show();
    }
}

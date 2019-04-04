package sample;

import javafx.scene.control.Button;

import java.awt.*;
import java.net.URI;

public class SystemInfo {
    public Button gh;
    public void github() throws Exception {
        System.out.println("ds");
        Desktop d=Desktop.getDesktop();
        d.browse(new URI("https://github.com/rahulverma0209"));
    }

    public void fb() throws Exception {
        Desktop d=Desktop.getDesktop();
        d.browse(new URI("https://www.facebook.com/rahulverma0209"));
    }

    public void linkedin() throws Exception{
        Desktop d=Desktop.getDesktop();
        d.browse(new URI("https://www.linkedin.com/in/rahulverma0209/"));
    }
}

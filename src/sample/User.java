package sample;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

public class User implements Initializable {

    @FXML
    public AnchorPane addap,delap,modap,searchap;

    @FXML
    public TextField mname,mpno,memail,mlon,mlat,msearchb,dpno,name,pno,email,lon,lat,sname,spno,semail,slon,slat,ssearchb;

    @FXML
     public Button yes,no,del_but;

    @FXML
    public Label del_msg;

    String pnoToBeDeleted="";

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        addap.setVisible(true);
        searchap.setVisible(false);
        delap.setVisible(false);
        modap.setVisible(false);
        yes.setVisible(false);
        no.setVisible(false);
        del_msg.setVisible(false);

    }

    public void addButton()
    {
        addap.setVisible(true);
        delap.setVisible(false);
        searchap.setVisible(false);
        modap.setVisible(false);
    }
    public void deleteButton()
    {
        addap.setVisible(false);
        delap.setVisible(true);
        searchap.setVisible(false);
        modap.setVisible(false);
    }
    public void modifyButton()
    {
        addap.setVisible(false);
        delap.setVisible(false);
        searchap.setVisible(false);
        modap.setVisible(true);
    }
    public void searchButton()
    {
        addap.setVisible(false);
        delap.setVisible(false);
        searchap.setVisible(true);
        modap.setVisible(false);
    }

    public void save() throws Exception
    {
        Connection con=DB.getConnection();
        Statement st=con.createStatement();

        String namee = name.getText();
        String pnoo = pno.getText();
        String emaill = email.getText();
        String lonn = lon.getText();
        String latt = lat.getText();

        if(namee.equals("") ||pnoo.equals("") ||emaill.equals("") ||lonn.equals("") ||latt.equals(""))
        {
            AlertBox.warning("Some Field is Empty");
            return;
        }

        if(!Pattern.matches("[0-9]{10}",pnoo))
        {
            AlertBox.warning("Invalid Pno");
            return;
        }

        if(!check_pno(st.executeQuery("select pno from user where pno = "+pnoo+";"))) {
            AlertBox.error("Mobile no already exist");
            return;
        }


        String q="insert into user values('" +namee+"',"+pnoo+",'"+emaill+"',"+lonn+","+latt+");";

        try{
            st.executeUpdate(q);
            AlertBox.information("Data Added");
            name.setText("");
            pno.setText("");
            email.setText("");
            lon.setText("");
            lat.setText("");
        }
        catch (Exception e)
        {
            AlertBox.error("Some Error occurred");
        }
    }

    public void msearch() throws Exception
    {
        String searchh=msearchb.getText();
        if (searchh.equals(""))
        {
            AlertBox.warning("Some Field is Empty");
            return;
        }

        if(!Pattern.matches("[0-9]{10}",searchh))
        {
            AlertBox.warning("Invalid Pno");
            return;
        }
        Connection con=DB.getConnection();
        Statement st=con.createStatement();

        if(check_pno(st.executeQuery("select pno from user where pno = "+searchh+";"))) {
            AlertBox.error("Mobile no does not exist");
            return;
        }

        String q="select * from user where pno = " + searchh +";";
        try
        {
            ResultSet rs=st.executeQuery(q);
            while (rs.next()) {
                mname.setText(rs.getString(1));
                mpno.setText(rs.getString(2));
                memail.setText(rs.getString(3));
                mlon.setText(rs.getString(4));
                mlat.setText(rs.getString(5));
            }
            mname.setDisable(false);
            mpno.setDisable(false);
            memail.setDisable(false);
            mlon.setDisable(false);
            mlat.setDisable(false);
        }
        catch (Exception e)
        {
            AlertBox.error("Some Error occurred");
        }
    }

    public void ssearch() throws Exception
    {
        String searchh=ssearchb.getText();
        if (searchh.equals(""))
        {
            AlertBox.warning("Some Field is Empty");
            return;
        }

        if(!Pattern.matches("[0-9]{10}",searchh))
        {
            AlertBox.warning("Invalid Pno");
            return;
        }
        Connection con=DB.getConnection();
        Statement st=con.createStatement();

        if(check_pno(st.executeQuery("select pno from user where pno = "+searchh+";"))) {
            AlertBox.error("Mobile no does not exist");
            return;
        }

        String q="select * from user where pno = " + searchh +";";
        try
        {
            ResultSet rs=st.executeQuery(q);
            while (rs.next()) {
                sname.setText(rs.getString(1));
                spno.setText(rs.getString(2));
                semail.setText(rs.getString(3));
                slon.setText(rs.getString(4));
                slat.setText(rs.getString(5));
            }
        }
        catch (Exception e)
        {
            AlertBox.error("Some Error occurred");
        }
    }

    public void msave() throws Exception
    {
        Connection con=DB.getConnection();
        Statement st=con.createStatement();

        String namee = mname.getText();
        String pnoo = mpno.getText();
        String emaill = memail.getText();
        String lonn = mlon.getText();
        String latt = mlat.getText();
        String searchkey=msearchb.getText();

        if(namee.equals("") ||emaill.equals("") ||lonn.equals("") ||latt.equals(""))
        {
            AlertBox.warning("Some Field is Empty");
            return;
        }

        if(!Pattern.matches("[0-9]{10}",pnoo))
        {
            AlertBox.warning("Invalid Pno");
            return;
        }

        String q="update user set name='"+namee+"',pno="+pnoo+",email='"+emaill+"',lon="+lonn+",lat="+latt+" where pno = "+searchkey+";";
        System.out.println(q);
        try{
            st.executeUpdate(q);
            AlertBox.information("Data Modified");
            mname.setText("");
            mpno.setText("");
            memail.setText("");
            mlon.setText("");
            mlat.setText("");
            mname.setDisable(true);
            mpno.setDisable(true);
            memail.setDisable(true);
            mlon.setDisable(true);
            mlat.setDisable(true);
        }
        catch (Exception e)
        {
            AlertBox.error("Some Error occurred");
        }
    }

    boolean check_pno(ResultSet rss)throws Exception{
        if(rss.next())
            return false;
        return true;
    }
    public void deleteData() throws  Exception{
        Connection con=DB.getConnection();
        Statement st=con.createStatement();
        pnoToBeDeleted=dpno.getText();
        if(!Pattern.matches("[0-9]{10}",pnoToBeDeleted))
        {
            AlertBox.warning("Invalid Pno");
            return;
        }
        if(check_pno(st.executeQuery("select pno from user where pno = "+pnoToBeDeleted+";"))) {
            AlertBox.error("Mobile no does not exist");
            return;
        }
        del_but.setDisable(true);
        yes.setVisible(true);
        no.setVisible(true);
        del_msg.setVisible(true);
    }
    public void confirmYes()throws Exception{
        Connection con=DB.getConnection();
        Statement st=con.createStatement();
        String q1="delete from user where pno = "+ pnoToBeDeleted +";";
        try {
            st.executeUpdate(q1);
            AlertBox.information("Data deleted");
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        dpno.setText("");
        del_but.setDisable(false);
        yes.setVisible(false);
        no.setVisible(false);
        del_msg.setVisible(false);
    }
    public void confirmNo()
    {
        dpno.setText("");
        del_but.setDisable(false);
        yes.setVisible(false);
        no.setVisible(false);
        del_msg.setVisible(false);
    }
}

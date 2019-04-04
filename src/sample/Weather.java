package sample;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.*;

class WeatherByLocation{


    Integer weather_code;
    String weather_main;
    String weather_description;
    Integer temperature;
    Float pressure;
    Float humadity;
    float wind_speed;

    protected static  float lon=77.595f;
    protected static float lat=12.972f;

    static String city;

    public static Map<String, Object> jsonToMap(String str){
        Map<String, Object> map = new Gson().fromJson(
                str,new TypeToken<HashMap<String, Object>>(){}.getType()
        );
        return map;
    }

    public ArrayList<WeatherByLocation> getData(float longg,float latt) {
        String api_key="5bafc75a2b49be448bfe718c66905341";
        lat=latt;
        lon=longg;
        String urlString ="https://api.openweathermap.org/data/2.5/forecast?lat=" + lat + "&lon=" + lon +"&appid="+api_key+"&units=metric";

        ArrayList<WeatherByLocation> res_data = new ArrayList<>();
        boolean cur_data=false;


        try {
            StringBuilder result = new StringBuilder();
            URL url = new URL(urlString);
            URLConnection conn = url.openConnection();
            BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = rd.readLine()) != null) {
                result.append(line);
            }
            rd.close();

            //to find city name
            String tempstr=result.substring(result.indexOf("city"));
            tempstr=tempstr.substring(tempstr.indexOf("{"),tempstr.indexOf("}}")+1);

            Map<String, Object> respMapp = jsonToMap(tempstr);
            WeatherByLocation.city=respMapp.get("name").toString();

            //to find res of data
            String list=result.substring(result.indexOf("[")+1,result.lastIndexOf("]"));

            while(list.length()>300)
            {
                String details=list.substring(0,list.indexOf(",{"));
                list=list.substring(list.indexOf(",{")+1,list.lastIndexOf("}"));

                String weather=details.substring(details.indexOf("[")+1,details.indexOf("]"));

                Map<String, Object> respMap = jsonToMap(details);
                Map<String, Object> mainMap = jsonToMap(respMap.get("main").toString());
                Map<String, Object> windMap = jsonToMap(respMap.get("wind").toString());
                Map<String, Object> wth = jsonToMap(weather);


                //System.out.println("Date & time : "+ respMap.get("dt_txt"));

                WeatherByLocation data=new WeatherByLocation();

                Float temp=new Float(mainMap.get("temp").toString());
                data.temperature= Math.round(temp);
                Float weather_code=new Float(wth.get("id").toString());
                data.weather_code= Math.round(weather_code);
                data.humadity=new Float(mainMap.get("humidity").toString());
                data.wind_speed=new Float(windMap.get("speed").toString());
                data.pressure=new Float(mainMap.get("pressure").toString());
                data.weather_main=wth.get("main").toString();
                data.weather_description=wth.get("description").toString();

                //adding current temp data
                if(cur_data==false){
                    res_data.add(data);
                    cur_data=true;
                }
                //adding data of next 4 daya of time 12:00:00
                if(respMap.get("dt_txt").toString().indexOf("12:00:00")>0 && cur_data==true)
                {
                    res_data.add(data);
                }


                //data.city=respMap.get("name").toString();


                //System.out.print("\n\n\n");
                //System.out.println("Temp : "+ data.temperature);
                //System.out.println("Humidity : "+ data.humadity);
                //System.out.println("Wind Speed : "+ data.wind_speed);
                //System.out.println("Pressure : "+ data.pressure);
               // System.out.println("City Name : "+ WeatherByLocation.city);
                //System.out.println("Weather Id : "+ data.weather_code);



            }
        }
        catch (java.io.IOException e){
            AlertBox.error("No internet");
            e.getMessage();
        }
        catch (Exception e){
            e.getMessage();
        }

        return res_data;
    }

}

/*class WeatherByPlace extends WeatherByLocation{
    String place;
}*/

public class Weather implements Initializable {

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //fun();
    }

    @FXML
    private JFXTextField lon;
    @FXML
    private JFXTextField lat;
    @FXML
    private Label temp_day,weather_main,city,l_humadity,l_pressure,weather_main1,weather_main2,weather_main3,weather_main4,
            temp_day1,temp_day2,temp_day3,temp_day4;
    @FXML
    private ImageView icon,icon1,icon2,icon3,icon4;

    public void fun(){
        ArrayList<WeatherByLocation> data;
        WeatherByLocation w=new WeatherByLocation();

        String latt=lat.getText();
        String lonn=lon.getText();
        if(latt.equals("") || lonn.equals(""))
        {
            AlertBox.error("Empty Field Found");
            return;
        }
        data=w.getData(Float.parseFloat(lonn),Float.parseFloat(latt));

        if (data.size()<5)
            return;
        //System.out.println("size : " + data.size());

        //Current Day
        w=data.get(0);

        city.setText(w.city);
        temp_day.setText(w.temperature.toString()+"°C");
        String u="file:///F:/CurrentlyWorking//MiniProject_sem6//Weather/src/sample/media/icon/"+w.weather_code.toString()+".png";
        System.out.println(w.weather_code.toString());
        //System.out.println(u);
        icon.setImage(new Image(u));
        weather_main.setText(w.weather_description);
        l_humadity.setText("Humadity : " + w.humadity.toString() + " %");
        l_pressure.setText("Pressure : " + w.pressure.toString() + " mb");


        //1 Day
        w=data.get(1);
        u="file:///F:/CurrentlyWorking//MiniProject_sem6//Weather/src/sample/media/icon/"+w.weather_code.toString()+".png";
        icon1.setImage(new Image(u));
        weather_main1.setText(w.weather_description);
        temp_day1.setText(w.temperature.toString()+"°C");

        //2 Day
        w=data.get(2);
        u="file:///F:/CurrentlyWorking//MiniProject_sem6//Weather/src/sample/media/icon/"+w.weather_code.toString()+".png";
        icon2.setImage(new Image(u));
        weather_main2.setText(w.weather_description);
        temp_day2.setText(w.temperature.toString()+"°C");

        //3 Day
        w=data.get(3);
        u="file:///F:/CurrentlyWorking//MiniProject_sem6//Weather/src/sample/media/icon/"+w.weather_code.toString()+".png";
        icon3.setImage(new Image(u));
        weather_main3.setText(w.weather_description);
        temp_day3.setText(w.temperature.toString()+"°C");

        //4 Day
        w=data.get(4);
        u="file:///F:/CurrentlyWorking//MiniProject_sem6//Weather/src/sample/media/icon/"+w.weather_code.toString()+".png";
        icon4.setImage(new Image(u));
        weather_main4.setText(w.weather_description);
        temp_day4.setText(w.temperature.toString()+"°C");

    }

    public void displayGraph() throws Exception
    {
        Stage graph=new Stage();
        FXMLLoader loader =new FXMLLoader(getClass().getResource("Graph.fxml"));
        graph.initModality(Modality.APPLICATION_MODAL);
        AnchorPane pane=loader.load();

        Scene scene=new Scene(pane);

        graph.setTitle(" Weather");
        graph.getIcons().add(new Image("file:///F:/CurrentlyWorking/MiniProject_sem6/Weather/src/sample/media/logo.png"));
        graph.setScene(scene);
        graph.show();
    }
}

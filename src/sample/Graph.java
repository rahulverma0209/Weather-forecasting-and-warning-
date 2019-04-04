package sample;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class Graph implements Initializable {

    @FXML
    public LineChart<String,Integer> lc1;

    public void initialize(URL location, ResourceBundle resources) {
        lc1.getData().clear();

        WeatherByLocation w=new WeatherByLocation();

        ArrayList<WeatherByLocation> data;

        data=w.getData(WeatherByLocation.lon,WeatherByLocation.lat);
        XYChart.Series<String,Integer> series=new XYChart.Series<>();

        for (int i=0;i<data.size();i++)
        {
            int y=Integer.parseInt(data.get(i).temperature.toString());
            series.getData().add(new XYChart.Data<>(new Integer(i).toString(),y));
        }
        lc1.getData().add(series);

    }
}

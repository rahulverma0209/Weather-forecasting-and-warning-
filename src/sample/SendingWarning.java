package sample;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.net.*;
import java.sql.*;
import java.util.*;

public class SendingWarning{

    private  ArrayList<Integer> criticalCondition=new ArrayList<>();


    SendingWarning(){
        int code[]=new int[]{201,202,212,221,232,302,314,502,503,504,511,522,600,601,602,603,611,612,
                613,615,616,620,621,622,762,771,781};
        for(int i=0;i<code.length;i++){
            criticalCondition.add(code[i]);
            //System.out.println(criticalCondition.get(i));
        }
    }

    void sendMsg() throws Exception
    {
        Connection con=DB.getConnection();
        Statement st=con.createStatement();

        String q="select * from user;";

        ResultSet rs=st.executeQuery(q);

        while (rs.next()) {
            System.out.println("\n\nName : "+rs.getString(1));
            System.out.println("Email : "+rs.getString(3));
            System.out.println("Location : "+rs.getFloat(4) + " " + rs.getFloat(5));
            diplayWeatherData(rs.getFloat(4),rs.getFloat(5));
        }
    }

    public void diplayWeatherData(float longg, float latt) {
        String api_key="5bafc75a2b49be448bfe718c66905341";
        float lat=latt;
        float lon=longg;
        String urlString ="https://api.openweathermap.org/data/2.5/forecast?lat=" + lat + "&lon=" + lon +"&appid="+api_key+"&units=metric";

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

            //to find res of data
            String list=result.substring(result.indexOf("[")+1,result.lastIndexOf("]"));

            while(list.length()>300)
            {
                String details=list.substring(0,list.indexOf(",{"));
                list=list.substring(list.indexOf(",{")+1,list.lastIndexOf("}"));

                String weather=details.substring(details.indexOf("[")+1,details.indexOf("]"));

                Map<String, Object> respMap = jsonToMap(details);
                Map<String, Object> mainMap = jsonToMap(respMap.get("main").toString());
                Map<String, Object> wth = jsonToMap(weather);

                Float temp=new Float(mainMap.get("temp").toString());
                float temperature= Math.round(temp);
                Float weather_code=new Float(wth.get("id").toString());
                Integer code = Math.round(weather_code);

                //adding current temp data
                if(cur_data==false){
                    System.out.print(temperature + "\t");
                    System.out.print(code + "\t");

                    if(criticalCondition.indexOf(code)<=0)
                        System.out.print("Temperature OK\n");
                    else
                        System.out.print("Temperature Not OK\n");
                    cur_data=true;
                }
                //adding data of next 4 daya of time 12:00:00
                if(respMap.get("dt_txt").toString().indexOf("12:00:00")>0 && cur_data==true)
                {
                    System.out.print(temperature + "\t");
                    System.out.print(code + "\t");

                    if(criticalCondition.indexOf(code)<=0)
                        System.out.print("Temperature OK\n");
                    else{
                        System.out.print("Temperature Not OK\n");
                        Translator.translate("en", "kn", "Bad weather");
                    }
                    //Translator.translate("en", "hi", "who are you?");
                }
            }
        }
        catch (java.io.IOException e){
            AlertBox.error("No internet");
            e.getMessage();
        }
        catch (Exception e){
            e.getMessage();
        }
    }

    private static Map<String, Object> jsonToMap(String str){
        Map<String, Object> map = new Gson().fromJson(
                str,new TypeToken<HashMap<String, Object>>(){}.getType()
        );
        return map;
    }


}


class Translator {
    private static final String CLIENT_ID = "FREE_TRIAL_ACCOUNT";
    private static final String CLIENT_SECRET = "PUBLIC_SECRET";
    private static final String ENDPOINT = "http://api.whatsmate.net/v1/translation/translate";

    public static void translate(String fromLang, String toLang, String text) throws Exception {
        String jsonPayload = new StringBuilder()
                .append("{")
                .append("\"fromLang\":\"")
                .append(fromLang)
                .append("\",")
                .append("\"toLang\":\"")
                .append(toLang)
                .append("\",")
                .append("\"text\":\"")
                .append(text)
                .append("\"")
                .append("}")
                .toString();

        URL url = new URL(ENDPOINT);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setDoOutput(true);
        conn.setRequestMethod("POST");
        conn.setRequestProperty("X-WM-CLIENT-ID", CLIENT_ID);
        conn.setRequestProperty("X-WM-CLIENT-SECRET", CLIENT_SECRET);
        conn.setRequestProperty("Content-Type", "application/json");

        OutputStream os = conn.getOutputStream();
        os.write(jsonPayload.getBytes());
        os.flush();
        os.close();

        int statusCode = conn.getResponseCode();
        //System.out.println("Status Code: " + statusCode);
        BufferedReader br = new BufferedReader(new InputStreamReader(
                (statusCode == 200) ? conn.getInputStream() : conn.getErrorStream()
        ));
        String output;
        StringBuilder sb=new StringBuilder();
        while ((output = br.readLine()) != null) {
            sb.append(output);
        }
        System.out.println(sb.toString()+"\n\n");
        conn.disconnect();
    }
}
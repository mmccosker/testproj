package com.grossmont.ws;

// Classes for reading web service.
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

// Classes for JSON conversion to java objects using Google's gson.
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Scanner;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


public class WeatherServiceManager{
	
    private Weather m_oWeather = null;

    private String m_sWeatherJson;



    // Gets the overall weather JSON string from the 3rd party web service.
    public void callWeatherWebService(String sCity){

    	String sServiceReturnJson = "";

    	try {

            // Call weather API.
            URL url = new URL("http://api.openweathermap.org/data/2.5/weather?q=" +
                    sCity + "&appid=1868f2463a960613c0a78b66a99b5e5f&units=imperial");
            BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));
            String strTemp = "";
            while (null != (strTemp = br.readLine())) {
                    sServiceReturnJson += strTemp;
            }

            // sServiceReturnJson now looks something like this:
            /*
            {"coord":{"lon":-116.96,"lat":32.79},
            "weather":[{"id":802,"main":"Clouds","description":"scattered clouds","icon":"03n"}],
            "base":"cmc stations",
            "main":{"temp":62.65,"pressure":1007.4,"humidity":93,"temp_min":62.65,"temp_max":62.65,"sea_level":1028.19,"grnd_level":1007.4},
            "wind":{"speed":7.29,"deg":310.501},"clouds":{"all":32},"dt":1463026609,
            "sys":{"message":0.0078,"country":"US","sunrise":1463057430,"sunset":1463107097},
            "id":5345529,"name":"El Cajon","cod":200}
            */

            // *****************
            // UNCOMMENT THIS if you wish to print out raw json that came back from web service during testing.
            // System.out.println("Returned json:");
            // System.out.println(sServiceReturnJson);
            // *****************


        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("An error occurred in callWeatherWebService() in WeatherServiceManager: " + ex.toString());
        }

        m_sWeatherJson = sServiceReturnJson;

        // Turn raw json into java object heirarchy using Google's gson.
        convertJsonToJavaObject();
    }

	// Uses Google's gson library to convert json into filled java objects
	// using the java object heirarchy that you already created.
    private void convertJsonToJavaObject(){

        Gson gson = new GsonBuilder().create();

        m_oWeather = gson.fromJson(m_sWeatherJson, Weather.class);
    }

    // This uses Google's gson library for parsing json.
    public float getCurrentTemp(){

        return m_oWeather.main.temp;
    }


    public String getCityName(){

        return m_oWeather.name;
    }

    public float getHighTemp(){

        return m_oWeather.main.temp_max;
    }

    public float getLowTemo(){

        return m_oWeather.main.temp_min;
    }


    public static void main(String[] args){

		// 1. Instantiate two instances of this class: WeatherServiceManager

        WeatherServiceManager oCity1 = new WeatherServiceManager();
        WeatherServiceManager oCity2 = new WeatherServiceManager();
		// 2. Get user input two different times to get 2 cities.

        Scanner sc = new Scanner(System.in);

        System.out.println("Enter City 1: ");
        String sCity1 = sc.nextLine();
        System.out.println("Enter City 2: ");
        String sCity2 = sc.nextLine();
		// 3. IMPORTANT: Take any space in the city of user input with %20 (e.g. "san diego, california" becomes "san%20diego,california").
					sCity1 = sCity1.replaceAll(" ","%20");
                    sCity2 = sCity2.replaceAll(" ","%20");
		// 3. Call callWeatherWebService on each WeatherServiceManager instance passing in each city.

        oCity1.callWeatherWebService(sCity1);
        oCity2.callWeatherWebService(sCity2);

		// 4. Then make comparisons of temps between cities on each WeatherServiceManager instance by using the get methods created above:

        if(oCity1.getCurrentTemp() > oCity2.getCurrentTemp()){
            System.out.println(oCity1.getCityName() + " has the highest current temp");
        }
        else{
            System.out.println(oCity2.getCityName() + " has the highest current temp");
        }

        float range1 = oCity1.getHighTemp() - oCity1.getLowTemo();
        float range2 = oCity2.getHighTemp() - oCity2.getLowTemo();

        if(range1 > range2){
            System.out.println(oCity1.getCityName() + " has the highest range in temp");
        }
        else{
            System.out.println(oCity2.getCityName() + " has the highest range in temp");
        }
    }

	public float getTempManualParse(){

		String sTemp = "";
		float fTemp;

		// Parse "temp" out of JSON reply.
		int iTempIndex = m_sWeatherJson.indexOf("\"temp\":") + 7;
		sTemp = m_sWeatherJson.substring(iTempIndex);
		sTemp = sTemp.substring(0, sTemp.indexOf(","));
		fTemp = Float.parseFloat(sTemp);

		return fTemp;
	}
	
	public Integer getTimeZone(){

		String sTZ = "";
		Integer fTemp;

		// Parse "timezone" out of JSON reply.
		int iTempIndex = m_sWeatherJson.indexOf("\"timezone\":") + 14;
		sTZ = m_sWeatherJson.substring(iTempIndex);
		sTZ = sTZ.substring(0, sTZ.indexOf(","));
		fTemp = Integer.parseInt(sTZ);
		return fTemp;
	}

}

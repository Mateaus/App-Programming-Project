package Controllers;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.List;
import java.util.ResourceBundle;

import javax.swing.SwingWorker;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;

import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Document;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class WeatherAppController implements Initializable
{
	@FXML private Label Weather, prompt, output, summary;
	@FXML private Button btn;
	@FXML private ImageView img;
	@FXML private TextField cityLocation;
	@FXML private ProgressIndicator progIndicator;
	private String latitude, longitude, developerAPIKey, city;
	private String temperature, condition, status, errMsg = "None";
	private String icon, postcode = "San Antonio";

	@Override
	public void initialize(URL location, ResourceBundle resources) 
	{
		// Trys to obtain information from text field
		try
		{
			// Sets the post code to the value in the city location text field
			String latLongs[] = getLatLongPositions(postcode);
			//condition = readJSONObject("minutely", "summary");
		    latitude = (latLongs[0]);
			longitude = (latLongs[1]);
			city = (latLongs[2]);
			
			//latitude = "16.2455";
			//longitude = "-89.0239";
			
			// Sets developer key from api to default
			//developerAPIKey = "da16336962592e022a90a30895ec83b3"; // my developer key
			
			// If the site returns an error with retrieving information
        	//  then update the key to a new key
        	developerAPIKey = "714eda33e43cde0ca430cedc8fa306a7"; // Ko's developer key
			progIndicator.setOpacity(0);
			
			// Sets various graphic component variables to information obtained
			btn.setText("Search!");
			output.setText(city);
			
			// Sets the summary condition
			//summary.setText("Summary: " + condition);
		    
			// Sets the temperature, icon, and weather to original values
			temperature = readJSONObject("currently", "temperature");
			condition = readJSONObject("minutely", "summary");
			icon = readJSONObject("currently", "icon");
			Weather.setText(temperature);
			summary.setText(condition);
			currentWeather(icon);
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
	}
	
	public void button(ActionEvent event) throws Exception
	{
		progIndicator.setOpacity(1);
		progIndicator.setProgress(-1);
		startThread(); 
		
	}
	
	public void returnToUI(ActionEvent event) throws Exception
	{
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/layout/userInterface_layout.fxml"));
        Pane userInterface = loader.load(); // userInterface holds the loader information
        Scene scene = new Scene(userInterface); // new scene holding userInterface

        // Stage now under windows, calls scene and loads the scene.
        //TODO: New screen when clicked for this APP
        Stage windows = (Stage) ((Node)event.getSource()).getScene().getWindow(); // hides previous window
        windows.setScene(scene);
        windows.show();
	}
	
	public void startThread()
	{
		SwingWorker sw1 = new SwingWorker() 
        {
 
            @SuppressWarnings("unchecked")
			@Override
            protected String doInBackground() throws Exception 
            {
                // define what thread will do here
            	runtime();
                Thread.sleep(50);
                publish(1);
                
                String res = "Finished Execution";
                return res;
            }
 
            @Override
            protected void process(List chunks)
            {
                // define what the event dispatch thread 
                // will do with the intermediate results received
                // while the thread is executing
                progIndicator.setOpacity(1);
            }
 
            @Override
            protected void done() 
            {
                // this method is called when the background 
                // thread finishes execution
        		progIndicator.setOpacity(0);
        		Platform.runLater(new Runnable() {
        	        @Override
        	        public void run() {
        	          //javaFX operations should go here
        	        	setLabels();
        	        }
        	   });
            }
        };
        // executes the swingworker on worker thread
        sw1.execute(); 
    }
	
	public void setLabels()
	{
		// I changed the progressbar to a progress indicator so it would visually look better
		if(status.equals("OK"))
		{	
			// Updates the summary condition
			if(errMsg.contains("minutely")) // minutely
				summary.setText("Summary for location is not available.");
			else if(errMsg.contains("OVER")) // if over allowed query
				summary.setText("");
			else
				summary.setText(condition);
			
			// Sets the output label to the current address from the city location text field
			if(errMsg.contains("currently") || errMsg.contains("OVER"))
				output.setText("Location not available.");
			else if(errMsg.contains("OVER")) // if over daily allowed amount of queries
				output.setText(status);
			else
				output.setText(city); // currently
			
			// Sets the weather label's text to the temperature
			if(errMsg.contains("currently")) // currently
				Weather.setText("temperature for location is not available.");
			else if(errMsg.contains("OVER")) // if over daily allowed amount of queries
				Weather.setText("");
			else
				Weather.setText(temperature);
			
			// Sets the weather icon
			if(errMsg.startsWith("currently")) // currently
				img.setOpacity(0);
			else if(errMsg.contains("OVER")) // if over daily allowed amount of queries
				img.setOpacity(0);
			else
			{
				img.setOpacity(1);
				currentWeather(icon); 
			}
			errMsg = "None"; // Resets error message to no errors
		}
		else
		{
			// Updates the summary condition
			summary.setText(null);
			// Sets the output label to the current address from the city location text field
			output.setText(status);
			// Sets the weather label's text to the temperature
			Weather.setText(null);
			// Sets the weather icon
			img.setOpacity(0);
		}
	}
	
	public double runtime()
	{
		double totalTime;
		long beginingTime = System.nanoTime();
		// Trys to obtain information from text field
		try
		{
			// Sets the post code to the value in the city location text field
			postcode = cityLocation.getText();
			String latLongs[] = getLatLongPositions(postcode);
		    latitude = latLongs[0];
			longitude = latLongs[1];
			city = (latLongs[2]);
			
			// Captures the temperature from the API
			temperature = readJSONObject("currently", "temperature");
			condition = readJSONObject("minutely", "summary");
			JSONArray tokens = new JSONArray(readJSONObject("minutely", "data"));
			
			// Captures the Icon message from the API
			icon = readJSONObject("currently", "icon");
			
			// Use for daily forecast
			for(int i = 0; i < tokens.length(); i++) // Can change to 7 to get weekly forecast
			{
				JSONObject jObj = tokens.getJSONObject(i);
				// Used for testing and possibly usable later
				/*String probability = jObj.get("precipProbability").toString();
				String intensity = jObj.get("precipIntensity").toString();
				String time = jObj.get("time").toString();
				System.out.println(condition + "\nProbability: " +probability + "\n" + "Intensity: " + intensity + 
								   "\n" + "Time: "+ time + "\n");*/
			}    
		}        
		catch(Exception err) // Throws an error
		{
			errMsg = err + "";
		}
		long endTime = System.nanoTime();
		totalTime = beginingTime - endTime;
		return totalTime / 1000000000;
	}
	
	// Sets the weather image to the corresponding image
	private void currentWeather(String condition)
	{
		if(condition.equals("clear-day"))
		{
			img.setImage(new Image("/resources/drawable/ClearDay.png"));
		}
		else if(condition.equals("clear-night"))
		{
			img.setImage(new Image("/resources/drawable/ClearNight.png"));
		}
		else if(condition.equals("rain"))
		{
			img.setImage(new Image("/resources/drawable/Rain.png"));
		}
		else if(condition.equals("snow"))
		{
			img.setImage(new Image("/resources/drawable/Snow.png"));
		}
		else if(condition.equals("sleet"))
		{
			img.setImage(new Image("/resources/drawable/Sleet.png"));
		}
		else if(condition.equals("wind"))
		{
			img.setImage(new Image("/resources/drawable/Wind.png"));
		}
		else if(condition.equals("fog"))
		{
			img.setImage(new Image("/resources/drawable/Fog.png"));
		}
		else if(condition.equals("cloudy"))
		{
			img.setImage(new Image("/resources/drawable/Cloudy.png"));
		}
		else if(condition.equals("partly-cloudy-day"))
		{
			img.setImage(new Image("/resources/drawable/PartlyCloudyDay.png"));
		}
		else if(condition.equals("partly-cloudy-night"))
		{
			img.setImage(new Image("/resources/drawable/PartlyCloudyNight.png"));
		}
		else if(condition.equals("hail"))
		{
			img.setImage(new Image("/resources/drawable/Hail.png"));
		}
		else if(condition.equals("thunderstorm"))
		{
			img.setImage(new Image("/resources/drawable/Thunderstorm.png"));
		}
		else if(condition.equals("tornado"))
		{
			img.setImage(new Image("/resources/drawable/Tornado.png"));
		}
	}
	
	// Opens the weatherAPI with the provided latitude and longitude of a given city
	private URL openURL(String developerKey, String latitude, String longitude) throws Exception
	{
		URL url = new URL("https://api.darksky.net/forecast/" + developerKey + "/" + latitude + "," + longitude);
		URLConnection request = url.openConnection();
		request.connect();
		return url;
	}
	
	// Creates a JSONObject and returns a String of the location within the JSONObject's
	//  URL String
	private String readJSONObject(String parent, String child) throws Exception
	{
		JSONObject json = new JSONObject(IOUtils.toString(openURL(developerAPIKey, latitude, longitude), Charset.forName("UTF-8")));
		String location = json.getJSONObject(parent).get(child).toString();
		return location;
	}
	
	// Obtains the latitude and longitude of every address in the world, reading the information 
	//   in via an XML file
	public String[] getLatLongPositions(String address) throws Exception
    {
      int responseCode = 0;
      String api = "http://maps.googleapis.com/maps/api/geocode/xml?address=" + URLEncoder.encode(address, "UTF-8") + "&sensor=true";
      URL url = new URL(api);
      HttpURLConnection httpConnection = (HttpURLConnection)url.openConnection();
      httpConnection.connect();
      responseCode = httpConnection.getResponseCode();
      if(responseCode == 200)
      {
        DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        Document document = builder.parse(httpConnection.getInputStream());
        XPathFactory xPathfactory = XPathFactory.newInstance();
        XPath xpath = xPathfactory.newXPath();
        XPathExpression expr = xpath.compile("/GeocodeResponse/status");
        status = (String)expr.evaluate(document, XPathConstants.STRING);
        if(status.equals("OK"))
        {
           expr = xpath.compile("//geometry/location/lat");
           String latitude = (String)expr.evaluate(document, XPathConstants.STRING);
           
           expr = xpath.compile("//result/formatted_address");
           String formattedCity = (String)expr.evaluate(document, XPathConstants.STRING);
           
           expr = xpath.compile("//geometry/location/lng");
           String longitude = (String)expr.evaluate(document, XPathConstants.STRING);
           return new String[] {latitude, longitude, formattedCity};
        }
        else
        {
        	throw new Exception("Error from the API - response status: "+ status);
        }
      }
      return null;
  }
}

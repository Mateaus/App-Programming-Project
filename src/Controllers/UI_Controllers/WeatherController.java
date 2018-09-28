package Controllers.UI_Controllers;

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
import org.json.JSONObject;
import org.w3c.dom.Document;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class WeatherController implements Initializable
{
	@FXML private Label Weather, prompt, output, summary;
	@FXML private Button btn;
	@FXML private ImageView img;
	@FXML private TextField cityLocation;
	@FXML private ProgressIndicator progIndicator;
	private String latitude, longitude, 
	//developerAPIKey = "da16336962592e022a90a30895ec83b3"; // Charles's developer key
	temperature, condition, status = "OK", errMsg = "None",
	icon, postcode = "San Antonio", formattedAddress;
	// If the site returns an error with retrieving information
	//  then update the key to a new key
	private String developerAPIKey = "714eda33e43cde0ca430cedc8fa306a7"; // Ko's developer key
	
	@Override
	public void initialize(URL location, ResourceBundle resources) 
	{
		progIndicator.setOpacity(0);
		// Sets various graphic component variables to information obtained
		btn.setText("Search!");
		output.setWrapText(true);
		
		/* San Antonio's latitude and longitude
		 *	latitude = "29.424349";
		 *	longitude = "-98.491142"; 
		*/
		
    	String latLongs[];
		try 
		{
			latLongs = tryLatLongsUntilSuccess(postcode);
			if(latLongs != null)
			{
				latitude = latLongs[0];
				longitude = latLongs[1];
				tryWeatherCaptureUntilSuccess();
			}
			weatherDataCapture();
			setLabels();
		} 
		catch (Exception e) 
		{
			
		}
		output.setText(formattedAddress);
	}
	
	public void button(ActionEvent event) throws Exception
	{
		progIndicator.setOpacity(1);
		progIndicator.setProgress(-1);
		
		// Sets the post code to the value in the city location text field
		postcode = cityLocation.getText();
		startThread();
	}
	
	public void returnToUI(ActionEvent event) throws Exception
	{
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/layout/TempBurner_Layouts/userInterface_layout.fxml"));
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
            	String latLongs[] = tryLatLongsUntilSuccess(postcode);
        		if(latLongs != null)
        		{
        			latitude = latLongs[0];
        			longitude = latLongs[1];
        			tryWeatherCaptureUntilSuccess();
        		}
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
        		Platform.runLater(new Runnable() 
        		{
        	        @Override
        	        public void run() 
        	        {
        	            //javaFX operations should go here
        	        	if(errMsg.equals("none"))
        	        	{
        	        		summary.setText(null);
        	        		output.setText("Location Not Available");
        	        		Weather.setText(null);
        	        		img.setOpacity(0);
        	        	}
        	        	else if(errMsg.equals("ZERO_RESULTS"))
        	        	{
        	        		summary.setText(null);
        	        		output.setText("Location Not Available");
        	        		Weather.setText(null);
        	        		img.setOpacity(0);
        	        	}
        	        	else
        	        	{
        	        		setLabels();
        	        	}
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
			summary.setText(condition);
			output.setText(formattedAddress);
			Weather.setText(temperature);
			img.setOpacity(1);
			currentWeather(icon); 
		}
	}
	
	public void weatherDataCapture()
	{
		// Trys to obtain information from text field
		try
		{	
			// Captures the temperature from the API
			temperature = readJSONObject("currently", "temperature");
			condition = readJSONObject("minutely", "summary");
			/* tokenizes the information in order to get more precise data from API
			JSONArray tokens = new JSONArray(readJSONObject("minutely", "data")); */
			
			// Captures the Icon message from the API
			icon = readJSONObject("currently", "icon");
			
			/* Use for daily forecast
			for(int i = 0; i < tokens.length(); i++) // Can change to 7 to get weekly forecast
			{
				JSONObject jObj = tokens.getJSONObject(i);
				// Used for testing and possibly usable later
				/*String probability = jObj.get("precipProbability").toString();
				String intensity = jObj.get("precipIntensity").toString();
				String time = jObj.get("time").toString();
				System.out.println(condition + "\nProbability: " +probability + "\n" + "Intensity: " + intensity + 
								   "\n" + "Time: "+ time + "\n");
			}*/
		}        
		catch(Exception err) // Throws an error
		{
			errMsg = err + ""; // Stores error in variable
		}
	}
	
	private void tryWeatherCaptureUntilSuccess()
	{
		weatherDataCapture();
		if(!errMsg.equals("None") && !errMsg.contains("minutely"))
		{
			while(!errMsg.equals("None"))
			{
				weatherDataCapture();
			}
		}
		else if(errMsg.contains("minutely"))
		{
			return;
		}
		else if(errMsg.contains("ZERO_RESULTS"))
		{
			return;
		}
	}

	// Sets the weather image to the corresponding image
	private void currentWeather(String condition)
	{
		if(condition.equals("clear-day"))
		{
			img.setImage(new Image("/resources/drawable/Weather_Icons/ClearDay.png"));
		}
		else if(condition.equals("clear-night"))
		{
			img.setImage(new Image("/resources/drawable/Weather_Icons/ClearNight.png"));
		}
		else if(condition.equals("rain"))
		{
			img.setImage(new Image("/resources/drawable/Weather_Icons/Rain.png"));
		}
		else if(condition.equals("snow"))
		{
			img.setImage(new Image("/resources/drawable/Weather_Icons/Snow.png"));
		}
		else if(condition.equals("sleet"))
		{
			img.setImage(new Image("/resources/drawable/Weather_Icons/Sleet.png"));
		}
		else if(condition.equals("wind"))
		{
			img.setImage(new Image("/resources/drawable/Weather_Icons/Wind.png"));
		}
		else if(condition.equals("fog"))
		{
			img.setImage(new Image("/resources/drawable/Weather_Icons/Fog.png"));
		}
		else if(condition.equals("cloudy"))
		{
			img.setImage(new Image("/resources/drawable/Weather_Icons/Cloudy.png"));
		}
		else if(condition.equals("partly-cloudy-day"))
		{
			img.setImage(new Image("/resources/drawable/Weather_Icons/PartlyCloudyDay.png"));
		}
		else if(condition.equals("partly-cloudy-night"))
		{
			img.setImage(new Image("/resources/drawable/Weather_Icons/PartlyCloudyNight.png"));
		}
		else if(condition.equals("hail"))
		{
			img.setImage(new Image("/resources/drawable/Weather_Icons/Hail.png"));
		}
		else if(condition.equals("thunderstorm"))
		{
			img.setImage(new Image("/resources/drawable/Weather_Icons/Thunderstorm.png"));
		}
		else if(condition.equals("tornado"))
		{
			img.setImage(new Image("/resources/drawable/Weather_Icons/Tornado.png"));
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
	private String[] getLatLongPositions(String address) throws Exception
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
  	           	expr = xpath.compile("//geometry/location/lng");
  	           	String longitude = (String)expr.evaluate(document, XPathConstants.STRING);
  	           	expr = xpath.compile("//result/formatted_address");
	           	formattedAddress = (String)expr.evaluate(document, XPathConstants.STRING);
  	           	errMsg = "None";
  	           	return new String[] {latitude, longitude, formattedAddress};
  	      	}
			else
			{
				errMsg = (status);
			}
		}
		return null;
    }
	
	private String[] tryLatLongsUntilSuccess(String address) throws Exception
	{
		String[] result = getLatLongPositions(address);
		if(errMsg.equals("ZERO_RESULTS"))
		{
			return null;
		}
		else if(!errMsg.equals("None"))
		{
			while(!errMsg.equals("None"))
			{
				result = getLatLongPositions(address);
			}
		}
		return result;
	}
}
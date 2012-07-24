package Search_around.pkpkpkppp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIUtils;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Search_aroundActivity extends Activity {
	private String api_bash_url = "https://maps.googleapis.com/maps/api/place/search/json";
	private String location = "22.764825,120.374837";
	private String radius = "500000";
	private String key = "AIzaSyD-kVz1NZIo43OEx9360W0ZF2GGhuVBbDw";
	private String types = "car_dealer|car_rental|car_repair|car_wash|electrician|electronics_store";
	private String name = "車|廠";
	private String api_param_str = "?location=" + location + "&radius="
			+ radius + "&name=" + name + "&types=" + types
			+ "&sensor=false&key=" + key;

	private URL url;
	private String str = null;
	private String json = "";
	Button bt1;
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		bt1 = (Button) findViewById(R.id.bt1);
		this.bt1.setOnClickListener(dateSetButtonClick); 
		
	}
	private View.OnClickListener dateSetButtonClick = new View.OnClickListener() { 
		public void onClick(View v) {
			str = null;
			json = "";
			try {
				init();
			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (URISyntaxException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	};

	private void init() throws URISyntaxException, ClientProtocolException, IOException {
		
		HttpClient httpclient = new DefaultHttpClient();		

        List<NameValuePair> qparams = new ArrayList<NameValuePair>();
        qparams.add(new BasicNameValuePair("location", location));
        qparams.add(new BasicNameValuePair("radius", radius));
        qparams.add(new BasicNameValuePair("name", name));
        qparams.add(new BasicNameValuePair("types", types));
        qparams.add(new BasicNameValuePair("sensor", "false"));
        qparams.add(new BasicNameValuePair("key", key));

        URI uri = URIUtils.createURI("https", "maps.googleapis.com", -1, "/maps/api/place/search/json", 
            URLEncodedUtils.format(qparams, "UTF-8"), null);
        HttpGet httpget = new HttpGet(uri);
        System.out.println(httpget.getURI());
        System.out.println("######executing request " + httpget.getURI());

        // Create a response handler
        ResponseHandler<String> responseHandler = new BasicResponseHandler();
        String responseBody = httpclient.execute(httpget, responseHandler);
        System.out.println("----------------------------------------");
        System.out.println(responseBody);
        System.out.println("----------------------------------------");
		System.out.println("########");
		
		
		/*
		try {
			url = new URL(api_bash_url + api_param_str);
			BufferedReader in = new BufferedReader(new InputStreamReader(
					url.openStream()));
			while ((str = in.readLine()) != null) {
				// System.out.println(str);
				json += str += "\n";
			}

			in.close();

		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
*/
		
		
		
		
		// ArrayList<HashMap<String,Object>> apiResult = new
		// ArrayList<HashMap<String,Object>>();
		// HashMap<String, HashMap<String,Object>> apiResult = new
		// HashMap<String, HashMap<String,Object>>();
		// System.out.println(json);

		// json = "[0,{\"1\":{\"2\":{\"3\":{\"4\":[5,{\"6\":7}]}}}}]";
		// json = "{\"x\" => 123}";
		
		System.out.println(responseBody);
		JSONObject apiResult = parseJSON(responseBody);

		/*
		 * Object obj=JSONValue.parse(s); JSONArray array=(JSONArray)obj;
		 * System.out.println("======the 2nd element of array======");
		 * System.out.println(array.get(1)); System.out.println();
		 */

		JSONArray searchResult = (JSONArray) apiResult.get("results");
		System.out.println("!!!!!!!" + searchResult.size());
		for (int i = 0; i < searchResult.size(); ++i) {
			System.out.println(searchResult.get(i).toString());
			JSONObject obj = (JSONObject) searchResult.get(i);
			System.out.println(obj.get("name"));
			JSONObject geo = (JSONObject) obj.get("geometry");
			JSONObject location = (JSONObject) geo.get("location");
			double lng = Double.parseDouble(location.get("lng").toString());
			double lat = Double.parseDouble(location.get("lat").toString());
			System.out.println(lng + "");
			System.out.println(lat + "");
			System.out.println();
		}
	}

	public JSONObject parseJSON(String jsonstr) {
		/*
		 * Gson g = new Gson(); //g.fromJson(str,
		 * ArrayList<HashMap<String,Object>>)
		 * 
		 * //Type FooType = new TypeToken<ArrayList<HashMap<String,Object>>>()
		 * {}.getType(); Type FooType = new TypeToken<HashMap<String,
		 * HashMap<String,Object>>>() {}.getType();
		 * //ArrayList<HashMap<String,Object>> apiResult = new
		 * ArrayList<HashMap<String,Object>>();
		 * 
		 * //System.out.println(str); apiResult = g.fromJson(str, FooType);
		 */
		HashMap<String, HashMap<String, Object>> apiResult = new HashMap<String, HashMap<String, Object>>();
		Object obj = JSONValue.parse(jsonstr);
		JSONObject ooo = (JSONObject) obj;

		return ooo;
	}
	
}
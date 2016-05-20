package ro.pub.cs.systems.eim.practicaltest02;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


public class PracticalTest02MainActivity extends Activity {

	private EditText server_port_edit_text = null;
	private EditText client_address_edit_text = null;
	private EditText client_port_edit_text = null;
	private EditText city_edit_text = null;

	private Button connect_button = null;
	private Button get_weather_forecast_button = null;
	private Spinner information_type_spinner = null;
	private TextView weather_forecast_text_view = null;
	
	
	private ServerThread serverThread = null;
	private ClientThread clientThread = null;
	
	private ConnectButtonClickListener connectButtonListener = new ConnectButtonClickListener();
	private class ConnectButtonClickListener implements Button.OnClickListener {
		  @Override
		  public void onClick(View view) {
		    String serverPort = server_port_edit_text.getText().toString();
		    if (serverPort == null || serverPort.isEmpty()) {
		      Toast.makeText(
		        getApplicationContext(),
		        "Server port should be filled!",
		        Toast.LENGTH_SHORT
		      ).show();
		      return;
		    }
		    serverThread = new ServerThread(Integer.parseInt(serverPort));
		    if (serverThread.getServerSocket() != null) {
		      serverThread.start();
		    } else {
		      Log.e(Constants.TAG, "[MAIN ACTIVITY] Could not creat server thread!");
		    }
		  }
		}
	//pentru trimitere date
	private WeatherForecastClickListener weatherForecastClickListener = new WeatherForecastClickListener();
	private class WeatherForecastClickListener implements Button.OnClickListener {

		  @Override
		  public void onClick(View view) {
		    
			String clientAddress = client_address_edit_text.getText().toString();
		    String clientPort    = client_port_edit_text.getText().toString();
		    
		    if (clientAddress == null || clientAddress.isEmpty() ||
		      clientPort == null || clientPort.isEmpty()) {
		      Toast.makeText(
		        getApplicationContext(),
		        "Client connection parameters should be filled!",
		        Toast.LENGTH_SHORT
		      ).show();
		      return;
		    }
		    
		    if (serverThread == null || !serverThread.isAlive()) {
		      Log.e(Constants.TAG, "[MAIN ACTIVITY] There is no server to connect to!");
		      return;
		    }
		    
		    //parametri necesari serverului
		    String city = city_edit_text.getText().toString();
		    String informationType = information_type_spinner.getSelectedItem().toString();
		    
		    if (city == null || city.isEmpty() ||
		      informationType == null || informationType.isEmpty()) {
		      Toast.makeText(
		        getApplicationContext(),
		        "Parameters from client (city / information type) should be filled!",
		        Toast.LENGTH_SHORT
		      ).show();
		      return;
		    }
		    weather_forecast_text_view.setText(Constants.EMPTY_STRING);
		    
		    clientThread = new ClientThread(
		      clientAddress,
		      Integer.parseInt(clientPort),
		      city,
		      informationType,
		      weather_forecast_text_view);
		    clientThread.start();
		  }
		
	}
	
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practical_test02_main);
        
        server_port_edit_text = (EditText) findViewById(R.id.server_port_edit_text);
        connect_button = (Button) findViewById(R.id.connect_button);
        connect_button.setOnClickListener(connectButtonListener);
        
        
        client_address_edit_text = (EditText) findViewById(R.id.client_address_edit_text);
        client_port_edit_text = (EditText) findViewById(R.id.client_port_edit_text);
        city_edit_text = (EditText) findViewById(R.id.city_edit_text);
        
        get_weather_forecast_button = (Button)findViewById(R.id.get_weather_forecast_button);
        get_weather_forecast_button.setOnClickListener(weatherForecastClickListener);
        
        weather_forecast_text_view = (TextView)findViewById(R.id.weather_forecast_text_view);
        
        information_type_spinner = (Spinner) findViewById(R.id.information_type_spinner);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.practical_test02_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    
    
    @Override
    protected void onDestroy() {
      if (serverThread != null) {
        serverThread.stopThread();
      }
      super.onDestroy();
    }
}

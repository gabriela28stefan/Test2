package ro.pub.cs.systems.eim.practicaltest02;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

import org.apache.http.client.ClientProtocolException;

import android.util.Log;
 

public class ServerThread extends Thread{

	
	private int port = 0;
	private ServerSocket serverSocket = null;
	private HashMap<String, WeatherForecastInformation> data = null;
	
	public ServerThread(int port) {
		this.port = port;
		try {
			  serverSocket = new ServerSocket(port);
			} catch (IOException ioException) {
			  Log.e(Constants.TAG, "An exception has occurred: " + ioException.getMessage());
			  if (Constants.DEBUG) {
			    ioException.printStackTrace();
			  }
			}
		
		this.data = new HashMap<String, WeatherForecastInformation>();
	}
	
	@Override
	public void run() {
	  try {
	    while (!Thread.currentThread().isInterrupted()) {
	      Log.i(Constants.TAG, "[SERVER] Waiting for a connection...");
	      Socket socket = serverSocket.accept();
	      Log.i(Constants.TAG, "[SERVER] A connection request was received from " + socket.getInetAddress() + ":" + socket.getLocalPort());
	      //fac thread separat pentru comunicatie
	      CommunicationThread communicationThread = new CommunicationThread(this, socket);
	      communicationThread.start();
	    }
	  } catch (ClientProtocolException clientProtocolException) {
	    Log.e(Constants.TAG, "An exception has occurred: " + clientProtocolException.getMessage());
	    if (Constants.DEBUG) {
	      clientProtocolException.printStackTrace();
	    }
	  } catch (IOException ioException) {
	    Log.e(Constants.TAG, "An exception has occurred: " + ioException.getMessage());
	    if (Constants.DEBUG) {
	      ioException.printStackTrace();
	    }
	  }
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public ServerSocket getServerSocket() {
		return serverSocket;
	}

	public void setServerSocket(ServerSocket serverSocket) {
		this.serverSocket = serverSocket;
	}

	public synchronized void setData(String city, WeatherForecastInformation weatherForecastInformation) {
		  this.data.put(city, weatherForecastInformation);
		}
		 
		public synchronized HashMap<String, WeatherForecastInformation> getData() {
		  return data;
		}
	

    public void stopThread() {
        if (serverSocket != null) {
            interrupt();
            try {
                serverSocket.close();
            } catch (IOException ioException) {
                Log.e(Constants.TAG, "An exception has occurred: " + ioException.getMessage());
                if (Constants.DEBUG) {
                    ioException.printStackTrace();
                }
            }
        }
    }
	
}

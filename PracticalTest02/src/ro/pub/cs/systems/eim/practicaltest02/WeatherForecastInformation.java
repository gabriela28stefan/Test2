package ro.pub.cs.systems.eim.practicaltest02;

public class WeatherForecastInformation {
	
	private String temperature;
	private String condition;
	private String pressure;
	private String humidity;
	private String windSpeed;
	
	
	public WeatherForecastInformation(){
		this.temperature = null;
		this.condition = null;
		this.pressure = null;
		this.humidity = null;
		this.windSpeed = null;
	}
	
	
	public WeatherForecastInformation(
			String temperature,
			String condition,
			String pressure,
			String humidity,
			String windSpeed){
		this.temperature = null;
		this.condition = null;
		this.pressure = null;
		this.humidity = null;
		this.windSpeed = null;
	}
	
	public String getTemperature() {
		return temperature;
	}
	public void setTemperature(String temperature) {
		this.temperature = temperature;
	}
	public String getCondition() {
		return condition;
	}
	public void setCondition(String condition) {
		this.condition = condition;
	}
	public String getPressure() {
		return pressure;
	}
	public void setPressure(String pressure) {
		this.pressure = pressure;
	}
	public String getHumidity() {
		return humidity;
	}
	public void setHumidity(String humidity) {
		this.humidity = humidity;
	}
	public String getWindSpeed() {
		return windSpeed;
	}
	public void setWindSpeed(String windSpeed) {
		this.windSpeed = windSpeed;
	}

	   @Override
	    public String toString() {
	        return  Constants.TEMPERATURE + ": " + temperature + "\n\r" +
	                Constants.WIND_SPEED + ": " + windSpeed + "\n\r" +
	                Constants.CONDITION + ": " + condition + "\n\r" +
	                Constants.PRESSURE + ": " + pressure + "\n\r" +
	                Constants.HUMIDITY + ": " + humidity;
	    }

}

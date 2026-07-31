package com.javastarterkit.patterns.observer;

/**
 * Observer Pattern Example (Behavioral)
 * 
 * Defines a one-to-many dependency between objects.
 * Like a weather station notifying multiple displays.
 * 
 * @author Java Starter Kit
 * @version 1.0.0
 */
public class Observer {
    
    public static void demonstrate() {
        System.out.println("\n=== Observer Pattern (Behavioral) ===");
        System.out.println("Notifies dependents of state changes\n");
        
        // Create weather station
        WeatherStation station = new WeatherStation();
        
        // Create displays
        Display currentDisplay = new CurrentConditionsDisplay();
        Display forecastDisplay = new ForecastDisplay();
        
        // Register displays
        station.addObserver(currentDisplay);
        station.addObserver(forecastDisplay);
        
        // Update weather
        System.out.println("Updating weather data...");
        station.setWeather(25.5, 65.0, 1013.2);
        
        // Remove one observer
        System.out.println("\nRemoving forecast display...");
        station.removeObserver(forecastDisplay);
        
        // Update again
        System.out.println("\nUpdating weather data again...");
        station.setWeather(22.0, 70.0, 1015.5);
        
        System.out.println("\nBenefits:");
        System.out.println("- Loose coupling between subject and observers");
        System.out.println("- Supports broadcast communication");
        System.out.println("- Easy to add/remove observers");
    }
    
    interface Observer {
        void update(float temp, float humidity, float pressure);
    }
    
    interface Display {
        void display();
    }
    
    static class WeatherStation {
        private float temperature;
        private float humidity;
        private float pressure;
        private java.util.List<Observer> observers = new java.util.ArrayList<>();
        
        public void addObserver(Observer observer) {
            observers.add(observer);
        }
        
        public void removeObserver(Observer observer) {
            observers.remove(observer);
        }
        
        public void setWeather(float temp, float humidity, float pressure) {
            this.temperature = temp;
            this.humidity = humidity;
            this.pressure = pressure;
            notifyObservers();
        }
        
        private void notifyObservers() {
            for (Observer observer : observers) {
                observer.update(temperature, humidity, pressure);
            }
        }
    }
    
    static class CurrentConditionsDisplay implements Observer, Display {
        private float temp;
        private float humidity;
        
        @Override
        public void update(float temp, float humidity, float pressure) {
            this.temp = temp;
            this.humidity = humidity;
            display();
        }
        
        @Override
        public void display() {
            System.out.println("  Current conditions: " + temp + "C and " + humidity + "% humidity");
        }
    }
    
    static class ForecastDisplay implements Observer, Display {
        private float pressure;
        
        @Override
        public void update(float temp, float humidity, float pressure) {
            this.pressure = pressure;
            display();
        }
        
        @Override
        public void display() {
            System.out.println("  Forecast: Pressure is " + pressure + " hPa");
        }
    }
    
    public static void main(String[] args) {
        demonstrate();
    }
}
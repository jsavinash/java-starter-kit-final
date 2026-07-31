package com.javastarterkit.patterns.facade;

/**
 * Facade Pattern Example
 * 
 * Provides a simplified interface to a complex subsystem.
 * Like a home theater remote that simplifies controlling multiple devices.
 * 
 * @author Java Starter Kit
 * @version 1.0.0
 */
public class Facade {
    
    public static void demonstrate() {
        System.out.println("\n=== Facade Pattern ===");
        System.out.println("Provides simplified interface to complex subsystem\n");
        
        // Create home theater system
        HomeTheaterFacade homeTheater = new HomeTheaterFacade();
        
        // Watch a movie with one simple call
        System.out.println("Starting movie night...");
        homeTheater.watchMovie("The Matrix");
        
        System.out.println("\n--- Movie playing ---\n");
        
        // End movie with one simple call
        System.out.println("Ending movie night...");
        homeTheater.endMovie();
        
        System.out.println("\nBenefits:");
        System.out.println("- Simplifies complex subsystems");
        System.out.println("- Reduces dependencies on complex code");
        System.out.println("- Makes code more maintainable");
    }
    
    // Subsystem classes
    static class Amplifier {
        public void on() { System.out.println("  Amplifier on"); }
        public void setVolume(int level) { System.out.println("  Volume set to " + level); }
        public void off() { System.out.println("  Amplifier off"); }
    }
    
    static class Projector {
        public void on() { System.out.println("  Projector on"); }
        public void setInput(String input) { System.out.println("  Input set to " + input); }
        public void wideScreenMode() { System.out.println("  Widescreen mode enabled"); }
        public void off() { System.out.println("  Projector off"); }
    }
    
    static class DVDPlayer {
        public void on() { System.out.println("  DVD Player on"); }
        public void play(String movie) { System.out.println("  Playing: " + movie); }
        public void stop() { System.out.println("  DVD Player stopped"); }
        public void off() { System.out.println("  DVD Player off"); }
    }
    
    static class TheaterLights {
        public void on() { System.out.println("  Lights on"); }
        public void off() { System.out.println("  Lights off"); }
        public void dim(int level) { System.out.println("  Lights dimmed to " + level + "%"); }
    }
    
    static class PopcornPopper {
        public void on() { System.out.println("  Popcorn popper on"); }
        public void pop() { System.out.println("  Popping popcorn!"); }
        public void off() { System.out.println("  Popcorn popper off"); }
    }
    
    // Facade class
    static class HomeTheaterFacade {
        private Amplifier amplifier;
        private Projector projector;
        private DVDPlayer dvdPlayer;
        private TheaterLights lights;
        private PopcornPopper popcornPopper;
        
        public HomeTheaterFacade() {
            this.amplifier = new Amplifier();
            this.projector = new Projector();
            this.dvdPlayer = new DVDPlayer();
            this.lights = new TheaterLights();
            this.popcornPopper = new PopcornPopper();
        }
        
        public void watchMovie(String movie) {
            System.out.println("Get ready to watch a movie...");
            popcornPopper.on();
            popcornPopper.pop();
            lights.dim(10);
            projector.on();
            projector.wideScreenMode();
            amplifier.on();
            amplifier.setVolume(5);
            dvdPlayer.on();
            dvdPlayer.play(movie);
        }
        
        public void endMovie() {
            System.out.println("Shutting down movie theater...");
            popcornPopper.off();
            lights.on();
            projector.off();
            amplifier.off();
            dvdPlayer.stop();
            dvdPlayer.off();
        }
    }
    
    public static void main(String[] args) {
        demonstrate();
    }
}
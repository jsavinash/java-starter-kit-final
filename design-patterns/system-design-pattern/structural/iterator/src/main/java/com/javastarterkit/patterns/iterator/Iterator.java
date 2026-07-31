package com.javastarterkit.patterns.iterator;

/**
 * Iterator Pattern Example
 * 
 * Provides a way to access elements of a collection without exposing its internal structure.
 * Like browsing through a playlist or menu items.
 * 
 * @author Java Starter Kit
 * @version 1.0.0
 */
public class Iterator {
    
    public static void demonstrate() {
        System.out.println("\n=== Iterator Pattern ===");
        System.out.println("Accesses collection elements sequentially\n");
        
        // Create a playlist
        Playlist playlist = new Playlist();
        playlist.addSong("Bohemian Rhapsody - Queen");
        playlist.addSong("Hotel California - Eagles");
        playlist.addSong("Stairway to Heaven - Led Zeppelin");
        playlist.addSong("Purple Rain - Prince");
        
        // Iterate through songs
        System.out.println("Playing playlist:");
        SongIterator iterator = playlist.createIterator();
        while (iterator.hasNext()) {
            String song = iterator.next();
            System.out.println("  Now playing: " + song);
        }
        
        System.out.println("\nBenefits:");
        System.out.println("- Hides collection implementation");
        System.out.println("- Supports multiple iterations");
        System.out.println("- Single Responsibility Principle");
    }
    
    // Iterator interface
    interface SongIterator {
        boolean hasNext();
        String next();
    }
    
    // Concrete iterator
    static class PlaylistIterator implements SongIterator {
        private String[] songs;
        private int position = 0;
        
        public PlaylistIterator(String[] songs) {
            this.songs = songs;
        }
        
        @Override
        public boolean hasNext() {
            return position < songs.length;
        }
        
        @Override
        public String next() {
            if (hasNext()) {
                return songs[position++];
            }
            return null;
        }
    }
    
    // Collection class
    static class Playlist {
        private java.util.List<String> songs = new java.util.ArrayList<>();
        
        public void addSong(String song) {
            songs.add(song);
            System.out.println("  Added to playlist: " + song);
        }
        
        public SongIterator createIterator() {
            return new PlaylistIterator(songs.toArray(new String[0]));
        }
    }
    
    public static void main(String[] args) {
        demonstrate();
    }
}
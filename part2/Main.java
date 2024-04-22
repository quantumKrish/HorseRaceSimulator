package horseRacingSim.part2;

public class Main {
    public static void main(String[] args) {
        
        // Create horses
        Horse horse1 = new Horse('A', "Thunder", 0.7);
        Horse horse2 = new Horse('B', "Lightning", 0.8);
        Horse horse3 = new Horse('♘', "Storm", 0.6); 
        // Create a race with a distance of 50 units
        Race race = new Race(50);

        // Add horses to the race in lanes
        race.addHorse(horse1, 1);
        race.addHorse(horse2, 2);
        race.addHorse(horse3, 3);

        // Start the race
        race.startRace();

        // Print the winner's name
        System.out.println("Winner: " + race.getWinnerName());
    }
}

package horseRacingSim.part1;

public class main {

    public static void main(String[] args) {

        Race race = new Race(15);

        // Create three horses and add them to the race
        Horse horse1 = new Horse('A', "Thunder", 0.7);
        Horse horse2 = new Horse('B', "Lightning", 0.8);
        Horse horse3 = new Horse('♘', "Storm", 0.6); 

        race.addHorse(horse1, 1); // Add horse1 to lane 1
        race.addHorse(horse2, 2); // Add horse2 to lane 2
        race.addHorse(horse3, 3); // Add horse3 to lane 3


        // Start the race
        race.startRace();
    }
}

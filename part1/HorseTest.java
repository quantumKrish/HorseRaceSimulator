package horseRacingSim.part1;

public class HorseTest {

    public static void main(String[] args) {
        testConstructor();
        testSetConfidenceOutOfRange();
        testSetSymbol();
        testSetNameNull();
        testMove();
        testFall();
        testConfidencDecrease();
        testBackToStart();
    }

    public static void testConstructor() {
        char symbol = '\uD83D';
        String name = "testHorse";
        double confidence = 0.8;

        Horse horse = new Horse(symbol, name, confidence);

        // Check if constructor initializes the Horse object correctly
        if (horse.getSymbol() == symbol && horse.getName().equals(name)
                && horse.getConfidence() == confidence && horse.getDistanceTravelled() == 0
                && !horse.hasFallen()) {

            System.out.println("Test passed: Constructor initializes the Horse object correctly.");

        } else {

            System.out.println("Test failed: Constructor does not initialize the Horse object correctly.");
        }
    }
    

    public static void testSetConfidenceOutOfRange() {

        Horse horse = new Horse('\uD83D', "Shadowfax", 0.8);

        try {

            horse.setConfidence(1.5); // attempt to set confidence outside the valid range
            System.out.println("Test failed: Confidence outside range accepted.");

        } catch (IllegalArgumentException e) {

            System.out.println("Test passed: Confidence outside range correctly rejected.");
        }
    }

    public static void testSetSymbol() {

        Horse horse = new Horse('A', "Thunder", 0.7);

        // Set the symbol to 'B'
        horse.setSymbol('B');
        
        // Check if the symbol is 'B'
        if (horse.getSymbol() == 'B') {

            System.out.println("Test passed: Symbol set to 'B' correctly.");

        } else {

            System.out.println("Test failed: Symbol 'B' not set correctly.");
        }
    }

    public static void testSetNameNull() {

        Horse horse = new Horse('\uD83D', "Shadowfax", 0.8);

        try {

            horse.setName(null); // attempt to set name to null
            System.out.println("Test failed: Null name accepted.");

        } catch (IllegalArgumentException e) {

            System.out.println("Test passed: Null name correctly rejected.");
        }
    }


    public static void testMove() {

        Horse horse = new Horse('A', "Thunder", 0.7);

        // Move horse forward 5 times
        for (int i = 0; i < 5; i++) {
            horse.moveForward();
        }

        // Check if distance travelled is 5
        if (horse.getDistanceTravelled() == 5) {

            System.out.println("Test passed: Horse moved forward 5 times.");

        } else {

            System.out.println("Test failed: Horse did not move forward 5 times.");
        }
    }

    public static void testFall() {

        Horse horse = new Horse('A', "Thunder", 0.7);

        // Check if horse initially has not fallen
        if (!horse.hasFallen()) {

            System.out.println("Test passed: Horse has not fallen initially.");

        } else {

            System.out.println("Test failed: Horse should not have fallen initially.");
        }

        // Make horse fall
        horse.fall();

        // Check if horse has fallen after calling fall()
        if (horse.hasFallen()) {

            System.out.println("Test passed: Horse has fallen after calling fall().");

        } else {

            System.out.println("Test failed: Horse should have fallen after calling fall().");
        }
    }

    public static void testBackToStart() {

        Horse horse = new Horse('A', "Thunder", 0.7);

        // Move horse forward 5 times
        for (int i = 0; i < 5; i++) {
            horse.moveForward();
        }

        // Go back to start
        horse.goBackToStart();

        // Check if the distance travelled is 0 after going back to start
        if (horse.getDistanceTravelled() == 0) {

            System.out.println("Test passed: Horse went back to start.");

        } else {

            System.out.println("Test failed: Horse did not go back to start.");
        }
    }

    public static void testConfidencDecrease() {

        Horse horse = new Horse('A', "Thunder", 0.7);

        // Initially, confidence should be 0.7
        double initialConfidence = horse.getConfidence();
        System.out.println("Initial Confidence: " + initialConfidence);

        // Make the horse fall
        horse.fall();

        // Confidence should decrease by 0.05
        double expectedConfidence = initialConfidence - 0.05;

        // Check if the confidence decreased
        if (horse.getConfidence() == expectedConfidence) {

            System.out.println("Test passed: Horse's confidence decreased after falling.");

        } else {

            System.out.println("Test failed: Horse's confidence did not decrease after falling.");
        }
    }

}

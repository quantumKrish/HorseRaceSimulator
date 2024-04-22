package horseRacingSim.part1;

/**
 * Horse class contains the fields and methods for the horse object
 * 
 * @author Kirishanth Pirathapan 
 * @version (18/03/2024)
 */
public class Horse {

    // Fields of class Horse
    private String name;
    private char symbol;
    private int distance;
    private boolean fallen;
    private double confidence;

    // Constructor for objects of class Horse
    public Horse(char horseSymbol, String horseName, double horseConfidence) {

        setSymbol(horseSymbol);
        setName(horseName);
        setConfidence(horseConfidence);
        goBackToStart();
        fallen = false;

    }

    // methods of class Horse
    public boolean fall() {

        fallen = true;

        double newConfidence = confidence - 0.05;
        double roundedConfidence = Math.max(Math.round(newConfidence * 100.0) / 100.0, 0.01);
        confidence = roundedConfidence;
        
        return fallen;
    }

    public boolean hasFallen() {

        return fallen;
    }

    public double getConfidence() {

        return confidence;
    }

    public int getDistanceTravelled() {

        return distance;
    }

    public String getName() {

        return name;
    }

    public char getSymbol() {

        return symbol;
    }

    public void goBackToStart() {

        distance = 0;
    }

    public void moveForward() {

        distance++;
    }

    public void setConfidence(double horseConfidence) {

        // ensure confidence is within valid range (0 to 1 not inclusive of 0)
        if (horseConfidence > 0 && horseConfidence <= 1) {

            this.confidence = horseConfidence;

        } else {

            throw new IllegalArgumentException("Confidence must be between 0 and 1 not inclusive of 0");
        }
    }

    public void setSymbol(char horseSymbol) {

        this.symbol = horseSymbol;
    }
    

    public void setName(String horseName) {

        // ensure name is not null or empty
        if (horseName != null && !horseName.trim().isEmpty()) {

            this.name = horseName;
            
        } else {

            throw new IllegalArgumentException("Name cannot be null or empty");
        }
    }
}

package horseRacingSim.part2;

import java.util.concurrent.TimeUnit;
import java.lang.Math;

/**
 * A three-horse race, each horse running in its own lane
 * for a given distance
 * 
 * @author McFarewell
 * @version 1.0
 */
public class Race
{
    private int raceLength;
    private Horse lane1Horse;
    private Horse lane2Horse;
    private Horse lane3Horse;


    /**
     * Constructor for objects of class Race
     * Initially there are no horses in the lanes
     * 
     * @param distance the length of the racetrack (in metres/yards...)
     */
    public Race(int distance)
    {
        // initialise instance variables
        raceLength = distance;
        lane1Horse = null;
        lane2Horse = null;
        lane3Horse = null;
    }

    
    /**
     * Adds a horse to the race in a given lane
     * 
     * @param theHorse the horse to be added to the race
     * @param laneNumber the lane that the horse will be added to
     */
    public void addHorse(Horse theHorse, int laneNumber)
    {

        if (theHorse == null) {

            throw new IllegalArgumentException("Cannot add a null horse to a lane.");
        }


        if (laneNumber == 1)
        {
            lane1Horse = theHorse;
        }
        else if (laneNumber == 2)
        {
            lane2Horse = theHorse;
        }
        else if (laneNumber == 3)
        {
            lane3Horse = theHorse;
        }
        else {

            throw new IllegalArgumentException("Cannot add more than 3 horses.");
        }
    }
    
    /**
     * Start the race
     * The horse are brought to the start and
     * then repeatedly moved forward until the 
     * race is finished
     */
    public void startRace()
    {

        if (lane1Horse == null || lane2Horse == null || lane3Horse == null) {

            throw new IllegalArgumentException("Cannot add less than 3 horses");

        }

        //declare a local variable to tell us when the race is finished
        boolean finished = false;
        
        //reset all the lanes (all horses not fallen and back to 0). 
        lane1Horse.goBackToStart();
        lane2Horse.goBackToStart();
        lane3Horse.goBackToStart();
                      
        while (!finished)
        {
            //move each horse
            moveHorse(lane1Horse);
            moveHorse(lane2Horse);
            moveHorse(lane3Horse);
                        
            //print the race positions
            printRace();

            // Check if all horses have fallen
            if (lane1Horse.hasFallen() && lane2Horse.hasFallen() && lane3Horse.hasFallen()) {
                System.out.println("No one wins!");
                finished = true;
            }

            // Tracks if more than one horse has finished
            int finishedCount = 0;
            boolean draw = false;

            if (raceWonBy(lane1Horse)) {
                finishedCount++;
            }
            if (raceWonBy(lane2Horse)) {
                finishedCount++;
            }
            if (raceWonBy(lane3Horse)) {
                finishedCount++;
            }
            
            // If more than one horse has finished print draw and the names of the horses
            if (finishedCount > 1) {

                System.out.println("It's a draw between:");

                if (raceWonBy(lane1Horse)) {
                    System.out.println("- " + lane1Horse.getName());
                }
                if (raceWonBy(lane2Horse)) {
                    System.out.println("- " + lane2Horse.getName());
                }
                if (raceWonBy(lane3Horse)) {
                    System.out.println("- " + lane3Horse.getName());
                }

                finished = true;
                draw = true; // Set draw to true
            }
            

            //if any of the three horses has won the race is finished and its not a draw then print the winner's name
            if ( (raceWonBy(lane1Horse) || raceWonBy(lane2Horse) || raceWonBy(lane3Horse)) && !draw )
            {
                finished = true;
                if (raceWonBy(lane1Horse)) {
                    System.out.println("And the winner is: " + lane1Horse.getName());

                    increaseConfidence(lane1Horse);

                } else if (raceWonBy(lane2Horse)) {
                    System.out.println("And the winner is: " + lane2Horse.getName());

                    increaseConfidence(lane2Horse);

                } else {
                    System.out.println("And the winner is: " + lane3Horse.getName());

                    increaseConfidence(lane3Horse);
                }
            }
           
            //wait for 100 milliseconds
            try{ 
                TimeUnit.MILLISECONDS.sleep(100);
            }catch(Exception e){}
        }
    }
    
    /**
     * Randomly make a horse move forward or fall depending
     * on its confidence rating
     * A fallen horse cannot move
     * 
     * @param theHorse the horse to be moved
     */
    private void moveHorse(Horse theHorse)
    {
        //if the horse has fallen it cannot move, 
        //so only run if it has not fallen
        
        if  (!theHorse.hasFallen())
        {
            //the probability that the horse will move forward depends on the confidence;
            if (Math.random() < theHorse.getConfidence())
            {
               theHorse.moveForward();
            }
            
            // the probability that the horse will fall is very small (max is 0.1)
            // but will also will depends exponentially on confidence 
            // so if you double the confidence, the probability that it will fall is * 2
            if (Math.random() < (0.1*theHorse.getConfidence()*theHorse.getConfidence())) 
            {
                theHorse.fall();
            }
        }
    }
        
    /** 
     * Determines if a horse has won the race
     *
     * @param theHorse The horse we are testing
     * @return true if the horse has won, false otherwise.
     */
    private boolean raceWonBy(Horse theHorse)
    {
        if (theHorse.getDistanceTravelled() == raceLength)
        {
            return true;
        }
        else
        {
            return false;
        }
    }
    
    /***
     * Print the race on the terminal
     */
    private void printRace()
    {
        // Clear the screen

        multiplePrint('=',raceLength + 3); //top edge of track
        System.out.println();
        
        printLane(lane1Horse);
        System.out.println();
        
        printLane(lane2Horse);
        System.out.println();
        
        printLane(lane3Horse);
        System.out.println();
        
        multiplePrint('=',raceLength + 3); //bottom edge of track
        System.out.println();    
    }
    
    /**
     * print a horse's lane during the race
     * for example
     * |           X                      |
     * to show how far the horse has run
     */
    private void printLane(Horse theHorse)
    {
        //calculate how many spaces are needed before
        //and after the horse
        int spacesBefore = theHorse.getDistanceTravelled();
        int spacesAfter = raceLength - theHorse.getDistanceTravelled();
        
        //print a | for the beginning of the lane
        System.out.print('|');
        
        //print the spaces before the horse
        multiplePrint(' ',spacesBefore);
        
        //if the horse has fallen then print dead
        //else print the horse's symbol
        if(theHorse.hasFallen())
        {
            System.out.print('X');
        }
        else
        {
            System.out.print(theHorse.getSymbol());
        }
        
        //print the spaces after the horse
        multiplePrint(' ',spacesAfter);
        
        //print the | for the end of the track
        System.out.print('|');

        // Print the horse's name and confidence rating
        System.out.print(" " + theHorse.getName() + " (Current confidence: " + theHorse.getConfidence() + ")");
    }
        
    
    /***
     * print a character a given number of times.
     * e.g. printmany('x',5) will print: xxxxx
     * 
     * @param aChar the character to Print
     */
    private void multiplePrint(char aChar, int times)
    {
        int i = 0;
        while (i < times)
        {
            System.out.print(aChar);
            i = i + 1;
        }
    }

    private void increaseConfidence(Horse theHorse) {

        double newConfidence = theHorse.getConfidence() + 0.05;
        double roundedConfidence = Math.min(Math.round(newConfidence * 100.0) / 100.0, 0.99);
        theHorse.setConfidence(roundedConfidence);
        System.out.println(String.format("%s new confidence: %.2f", theHorse.getName(), theHorse.getConfidence()));
    }
}

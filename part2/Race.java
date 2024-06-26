
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import javax.swing.*;

/**
 * A multi-horse race, each horse running in its own lane
 * for a given distance
 * 
 * @author McFarewell
 * @version 3.0
 */
public class Race {
    private int raceLength;
    private Horse[] horses;
    private final int MAX_HORSES = 10;
    private int numHorses;
    private JTextArea raceTextArea;
    ArrayList oldHorseDetails = new ArrayList<>();


    /**
     * Constructor for objects of class Race
     * Initially there are no horses in the race
     * 
     * @param distance the length of the racetrack (in metres/yards...)
     */
    public Race(int distance) {
        raceLength = distance;
        horses = new Horse[MAX_HORSES];
        numHorses = 0;
    }

    /**
     * Adds a horse to the race
     * 
     * @param theHorse the horse to be added to the race
     */
    public void addHorse(Horse theHorse) {

        if (numHorses >= MAX_HORSES) {
            throw new IllegalArgumentException("Cannot add more than " + MAX_HORSES + " horses.");
        }

        if (theHorse == null) {
            throw new IllegalArgumentException("Cannot add a null horse to the race.");
        }

        horses[numHorses] = theHorse;
        numHorses++;
    }


    /**
     * Start the race
     * The horses are brought to the start and
     * then repeatedly moved forward until the 
     * race is finished
     */
    public void startRace(JTextArea raceTextArea) {
        this.raceTextArea = raceTextArea;

        if (numHorses < 2) {
            throw new IllegalArgumentException("Minimum of 2 horses required to start the race.");
        }

        // Add horse details to the oldHorseDetails ArrayList
        for (int i = 0; i < numHorses; i++) {
            String oldHorse = horses[i].getName() + "," + horses[i].getSymbol() + "," + horses[i].getConfidence() + "," +
                    horses[i].getColor() + "," + horses[i].getBreed() + "," + horses[i].getAccessories() + "," +
                    horses[i].getAvrgSpeed() + "," + horses[i].getWinRatio();

            oldHorseDetails.add(oldHorse);
        }

        for (int i = 0; i < numHorses; i++) {
            horses[i].goBackToStart();
        }

        new Thread(() -> {
            boolean finished = false;

            while (!finished) {
                for (int i = 0; i < numHorses; i++) {
                    moveHorse(horses[i]);
                }

                boolean allFallen = true;
                for (int i = 0; i < numHorses; i++) {
                    if (!horses[i].hasFallen()) {
                        allFallen = false;
                        break;
                    }
                }

                if (allFallen) {
                    appendToRaceWindow("No one wins!");
                    updateRaceAllLoseDetails(horses);
                    finished = true;
                }

                List<Horse> finishedHorses = new ArrayList<>();
                for (int i = 0; i < numHorses; i++) {

                    if (raceWonBy(horses[i])) {
                        finishedHorses.add(horses[i]);
                    }
                }

                // If there is only one horse that has won
                if (finishedHorses.size() == 1) {

                    finished = true;
                    Horse winner = finishedHorses.get(0);
                    appendToRaceWindow("And the winner is: " + winner.getName());
                    updateRaceWinDetails(finishedHorses, horses);
                    increaseConfidence(winner);
                    increaseWinRatio(winner);

                // If there are multiple that won
                } else if (finishedHorses.size() > 1) {
                    finished = true;
                    appendToRaceWindow("It's a draw between:");
                    updateRaceDrawDetails(finishedHorses, horses);
                    for (Horse horse : finishedHorses) {
                        appendToRaceWindow(horse.getName());
                    }
                }

                appendRacePositions();

                try {
                    TimeUnit.MILLISECONDS.sleep(300);
                } catch (InterruptedException e) {  
                    e.printStackTrace();
                }

                if (!finished) {

                    clearRaceWindow();
                } else {
                    
                    for (int i = 0; i < numHorses; i++) {

                        calculateAvrgSpeed(horses[i]);
                        String updatedHorse = horses[i].getName() + "," + horses[i].getSymbol() + "," + horses[i].getConfidence() + "," +
                        horses[i].getColor() + "," + horses[i].getBreed() + "," + horses[i].getAccessories() + "," +
                        Math.round(horses[i].getAvrgSpeed()*100.0)/100.0 + "," + Math.round(horses[i].getWinRatio()*100.0)/100.0;

                        updateHorseInFile((String) oldHorseDetails.get(i), updatedHorse);
                    }
                }
            }

        }).start();
    }

    public void updateRaceAllLoseDetails(Horse horses[]) {

        String directoryPath = "part2" + File.separator;
        File raceFile = new File(directoryPath + "raceDetails.txt");
    
        // Get the current date and time
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedDateTime = now.format(formatter);
    
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(raceFile, true))) {
            writer.newLine();

            writer.write("Race Details");
            writer.newLine();
            writer.write("Date: " + formattedDateTime);
            writer.newLine();
            writer.write("Race Status: All Lose");
            writer.newLine();
            writer.write("Race length: " + raceLength);
            writer.newLine();
            writer.write("Participants:");
            writer.newLine();

            for (int i = 0; i < numHorses; i++) {
                writer.write(horses[i].getName() + " Fallen: " + horses[i].hasFallen() + " Distance Travelled: " + horses[i].getDistanceTravelled());
                writer.newLine();
            }

            writer.write("-------------------------------------------------------");
            writer.newLine();
            writer.newLine();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    
    public void updateRaceDrawDetails(List<Horse> finishedHorses, Horse horses[]) {

        String directoryPath = "part2" + File.separator;
        File raceFile = new File(directoryPath + "raceDetails.txt");    

        // Get the current date and time
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedDateTime = now.format(formatter);
    
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(raceFile, true))) {

            writer.newLine();
            writer.write("Race Details");
            writer.newLine();
            writer.write("Date: " + formattedDateTime);
            writer.newLine();
            writer.write("Race Status: Draw");
            writer.newLine();
            writer.write("Race length: " + raceLength);
            writer.newLine();
            writer.write("Drawers: ");

            for (Horse horse : finishedHorses) {

                writer.write(horse.getName());
                writer.newLine();
            }

            writer.write("Participants:");
            writer.newLine();

            for (int i = 0; i < numHorses; i++) {
                writer.write(horses[i].getName() + " Fallen: " + horses[i].hasFallen() + " Distance Travelled: " + horses[i].getDistanceTravelled());
                writer.newLine();
            }

            writer.write("-------------------------------------------------------");
            writer.newLine();
            writer.newLine();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
            

    public void updateRaceWinDetails(List<Horse> finishedHorses, Horse horses[]) {

        String directoryPath = "part2" + File.separator;
        File raceFile = new File(directoryPath + "raceDetails.txt");    
        // Get the current date and time
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedDateTime = now.format(formatter);
    
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(raceFile, true))) {

            writer.newLine();
            writer.write("Race Details");
            writer.newLine();
            writer.write("Date: " + formattedDateTime);
            writer.newLine();
            writer.write("Race Status: Win");
            writer.newLine();
            writer.write("Race length: " + raceLength);
            writer.newLine();

            for (Horse horse : finishedHorses) {

                writer.write("Winner: " + horse.getName());
            }

            writer.newLine();
            writer.write("Participants:");
            writer.newLine();


            for (int i = 0; i < numHorses; i++) {
                writer.write(horses[i].getName() + " Fallen: " + horses[i].hasFallen() + " Distance Travelled: " + horses[i].getDistanceTravelled());
                writer.newLine();
            }

            writer.write("-------------------------------------------------------");

            writer.newLine();
            writer.newLine();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void updateHorseInFile(String oldHorse, String updatedHorse) {

        String directoryPath = "part2" + File.separator;
        File filePath = new File(directoryPath + "horseDetails.txt");
        File tempFile = new File(directoryPath + "tempHorseDetails.txt");
    
        try (BufferedReader br = new BufferedReader(new FileReader(filePath));
             BufferedWriter bw = new BufferedWriter(new FileWriter(tempFile))) {
    
            String line;

            while ((line = br.readLine()) != null) {
                if (line.equals(oldHorse)) {
                    bw.write(updatedHorse);
                } else {
                    bw.write(line);
                }
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    
        // Replace the original file with the updated one
        if (!filePath.delete()) {
            System.out.println("Could not delete original file.");
        }
        if (!tempFile.renameTo(filePath)) {
            System.out.println("Could not rename temp file to original file name.");
        }
    }


    // Method to calculate the average speed of the horse based on its confidence level
    private void calculateAvrgSpeed(Horse theHorse) {

        double confidence = theHorse.getConfidence();
        double minSpeed = 5.0;  // Minimum speed
        double maxSpeed = 45.0; // Maximum speed
        double speed = minSpeed + (maxSpeed - minSpeed) * confidence;
        
        // Ensure speed is within a reasonable range
        if (speed < minSpeed) {
            speed = minSpeed;
        } else if (speed > maxSpeed) {
            speed = maxSpeed;
        }

        speed = (theHorse.getAvrgSpeed() + speed) / 2;
        
        theHorse.setAvrgSpeed(speed);
    }


    // Method to increase the win ratio of a horse
    private void increaseWinRatio(Horse theHorse) {

        double currentWinRatio = theHorse.getWinRatio();
        
        // Increase win ratio
        currentWinRatio += 0.05;

        // Ensure win ratio stays within a reasonable range
        if (currentWinRatio > 1.0) {
            currentWinRatio = 1.0;
        }
        
        theHorse.setWinRatio(currentWinRatio);
    }

    // Method to decrease the win ratio of a horse
    private void decreaseWinRatio(Horse theHorse) {
        
        double currentWinRatio = theHorse.getWinRatio();

        // Decrease win ratio
        currentWinRatio -= 0.05;

        // Ensure win ratio stays within a reasonable range
        if (currentWinRatio < 0.0) {
            currentWinRatio = 0.0;
        }

        theHorse.setWinRatio(currentWinRatio);
    }

    // Method to increase the confidence of a horse
    private void increaseConfidence(Horse theHorse) {

        double newConfidence = theHorse.getConfidence() + 0.05;
        double roundedConfidence = Math.min(Math.round(newConfidence * 100.0) / 100.0, 0.99);
        theHorse.setConfidence(roundedConfidence);
    }

    private void appendRacePositions() {
        StringBuilder raceStatus = new StringBuilder();
        raceStatus.append("Race Status:\n");

        for (int i = 0; i < numHorses; i++) {

            raceStatus.append(horses[i].getName()).append(": ");
            for (int j = 0; j < raceLength; j++) {

                if (j == horses[i].getDistanceTravelled()) {

                    if (horses[i].hasFallen()) {
                        raceStatus.append("X");
                    } else {
                        raceStatus.append(horses[i].getSymbol());
                    }

                } else {
                    raceStatus.append("-");
                }
            }
            raceStatus.append("\n");
        }

        appendToRaceWindow(raceStatus.toString());
    }

    // Method to move the horse forward and check if it has fallen
    private void moveHorse(Horse theHorse) {
        if (!theHorse.hasFallen()) {
            if (Math.random() < theHorse.getConfidence()) {
                theHorse.moveForward();

            }

            if (Math.random() < (0.1 * theHorse.getConfidence() * theHorse.getConfidence())) {
                theHorse.fall();
                decreaseWinRatio(theHorse);
            }
        }
    }

    private boolean raceWonBy(Horse theHorse) {
        return theHorse.getDistanceTravelled() >= raceLength;
    }

    private void appendToRaceWindow(String text) {
        if (raceTextArea != null) {
            SwingUtilities.invokeLater(() -> {
                raceTextArea.append(text + "\n");
                raceTextArea.setCaretPosition(raceTextArea.getDocument().getLength());
            });
        }
    }

    private void clearRaceWindow() {
        if (raceTextArea != null) {
            SwingUtilities.invokeLater(() -> raceTextArea.setText(""));
        }
    }

    // Method to create a the racing window
    public static class RaceWindow extends JFrame {

        private static final long serialVersionUID = 1L;
        JTextArea raceTextArea;
        JDialog createRaceDialog;

        public RaceWindow(JDialog createRaceDialog) {
            
            this.createRaceDialog = createRaceDialog;            
            setTitle("Horse Race");
            setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            setSize(600, 400);
            setLocationRelativeTo(null);

            raceTextArea = new JTextArea();
            raceTextArea.setEditable(false);
            raceTextArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 14));

            JScrollPane scrollPane = new JScrollPane(raceTextArea);
            getContentPane().add(scrollPane, BorderLayout.CENTER);

            JButton closeButton = new JButton("Close Race");

            closeButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    dispose();
                    createRaceDialog.dispose(); // Close the Create Race dialog
                }
            });

            JPanel buttonPanel = new JPanel();
            buttonPanel.add(closeButton);
            getContentPane().add(buttonPanel, BorderLayout.SOUTH);
        }

        public void appendText(String text) {
            SwingUtilities.invokeLater(() -> {
                raceTextArea.append(text + "\n");
                raceTextArea.setCaretPosition(raceTextArea.getDocument().getLength());
            });
        }
    }
}

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class GUI extends JFrame {

    public GUI() {
        setTitle("Simulator");
        setSize(300, 200);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new FlowLayout());

        JButton startButton = new JButton("Start Simulator");
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (checkAndCreateFiles()) {
                    JOptionPane.showMessageDialog(GUI.this, "Files checked and created successfully.");
                    // Close current window and open a new one
                    dispose();
                    openMainMenu();
                } else {
                    JOptionPane.showMessageDialog(GUI.this, "Failed to check or create files.");
                }
            }
        });

        add(startButton);

        setLocationRelativeTo(null);
        setVisible(true);
    }

    private boolean checkAndCreateFiles() {
        String folderPath = "C:\\HorseRaceSimulator\\part2\\saveFiles";
        File horseFile = new File(folderPath + "\\horseDetails.txt");
        File raceFile = new File(folderPath + "\\raceDetails.txt");

        try {
            // Create the directory if it doesn't exist
            File folder = new File(folderPath);
            if (!folder.exists()) {
                folder.mkdirs(); // mkdirs() creates all parent directories if they don't exist
            }

            if (!horseFile.exists()) {
                horseFile.createNewFile();
            }
            if (!raceFile.exists()) {
                raceFile.createNewFile();
            }
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    private void openMainMenu() {
        JFrame newFrame = new JFrame("Horse Racing Simulator");
        newFrame.setSize(400, 300);
        newFrame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        newFrame.setLocationRelativeTo(null);
        newFrame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new GUI();
            }
        });
    }
}

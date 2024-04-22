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
        
        String directoryPath = "C:\\Users\\kiris\\OneDrive\\Documents\\GitHub\\HorseRaceSimulator\\part2\\";
        File horseFile = new File(directoryPath + "horseDetails.txt");
        File raceFile = new File(directoryPath + "raceDetails.txt");
    
        try {

    
                if (horseFile.createNewFile()) {
                    JOptionPane.showMessageDialog(GUI.this, "Horse file created: " + horseFile.getAbsolutePath());
                } else {
                    JOptionPane.showMessageDialog(GUI.this, "Horse file already exists.");
                }
            
    
                if (raceFile.createNewFile()) {
                    JOptionPane.showMessageDialog(GUI.this, "Race file created: " + raceFile.getAbsolutePath());
                } else {
                    JOptionPane.showMessageDialog(GUI.this, "Race file already exists.");
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
    
        JMenuBar menuBar = new JMenuBar();
    
        // Race menu
        JMenu raceMenu = new JMenu("Race");
        JMenuItem createRaceItem = new JMenuItem("Create Race");
        JMenuItem startRaceItem = new JMenuItem("Start Race");
        JMenuItem viewStatisticsItem = new JMenuItem("View Statistics");
        raceMenu.add(createRaceItem);
        raceMenu.add(startRaceItem);
        raceMenu.add(viewStatisticsItem);
    
        // Horse menu
        JMenu horseMenu = new JMenu("Horse");
        JMenuItem createHorseItem = new JMenuItem("Create Horse");
        JMenuItem editHorseItem = new JMenuItem("Edit Horse");
        horseMenu.add(createHorseItem);
        horseMenu.add(editHorseItem);
    
        // Add menus to menu bar
        menuBar.add(raceMenu);
        menuBar.add(horseMenu);
    
        // Set the menu bar for the frame
        newFrame.setJMenuBar(menuBar);
    
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

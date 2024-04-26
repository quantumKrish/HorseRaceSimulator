

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;


public class GUI extends JFrame {

    public GUI() {
        setTitle("Simulator");
        setSize(300, 200);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new FlowLayout());
        

        JButton startButton = new JButton("Start Simulator");
        startButton.addActionListener(new ActionListener() {

            
            public void actionPerformed(ActionEvent e) {
                if (checkAndCreateFiles()) {

                    JOptionPane.showMessageDialog(GUI.this, "Files checked and created successfully.");

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
        
        String directoryPath = "part2" + File.separator;
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
        JMenuItem viewRaceHistoryItem = new JMenuItem("View Race History");
        raceMenu.add(createRaceItem);
        raceMenu.add(viewRaceHistoryItem);
    
        // Horse menu
        JMenu horseMenu = new JMenu("Horse");
        JMenuItem createHorseItem = new JMenuItem("Create Horse");
        JMenuItem editHorseItem = new JMenuItem("Edit Horse");
        JMenuItem viewStatisticsItem = new JMenuItem("View Statistics");

        horseMenu.add(createHorseItem);
        horseMenu.add(editHorseItem);
        horseMenu.add(viewStatisticsItem);


        // Add menus to menu bar
        menuBar.add(raceMenu);
        menuBar.add(horseMenu);

    
        // Set the menu bar for the frame
        newFrame.setJMenuBar(menuBar);

        // Action listener for View Race History menu item
        viewRaceHistoryItem.addActionListener(new ActionListener() {
            
            public void actionPerformed(ActionEvent e) {
                openViewRaceHistoryDialog();
            }
        });
    
        // Action listener for Create Horse menu item
        createHorseItem.addActionListener(new ActionListener() {
            
            public void actionPerformed(ActionEvent e) {
                openCreateHorseDialog();
            }
        });

        // Action listener for Edit Horse menu item
        editHorseItem.addActionListener(new ActionListener() {
            
            public void actionPerformed(ActionEvent e) {
                openEditHorseDialog();
            }
        });

        // Action listener for View Statistics menu item
        viewStatisticsItem.addActionListener(new ActionListener() {
            
            public void actionPerformed(ActionEvent e) {
                viewHorseStatistics();
            }
        });

        // Action listener for Create Race menu item
        createRaceItem.addActionListener(new ActionListener() {
            
            public void actionPerformed(ActionEvent e) {
                openCreateRaceDialog();
            }
        });
    
        newFrame.setVisible(true);
    }

    private void openViewRaceHistoryDialog() {

        JFrame historyFrame = new JFrame("Race History");
        JTextArea historyTextArea = new JTextArea();
        JScrollPane scrollPane = new JScrollPane(historyTextArea);

        historyTextArea.setEditable(false);
        historyTextArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 14));

        historyFrame.getContentPane().add(scrollPane, BorderLayout.CENTER);
        historyFrame.setSize(600, 400);
        historyFrame.setLocationRelativeTo(null);

        // Read race details from file and display in text area
        String directoryPath = "part2" + File.separator;
        File raceFile = new File(directoryPath + "raceDetails.txt");
        try (BufferedReader reader = new BufferedReader(new FileReader(raceFile))) {

            String line;
            while ((line = reader.readLine()) != null) {
                historyTextArea.append(line + "\n");
            }

        } catch (IOException ex) {
            historyTextArea.setText("Error reading race history.");
        }

        historyFrame.setVisible(true);
    }

    
    private void openCreateHorseDialog() {

        JDialog createHorseDialog = new JDialog();
        createHorseDialog.setTitle("Create Horse");
        createHorseDialog.setSize(300, 400);
        createHorseDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        createHorseDialog.setLocationRelativeTo(null);
    
        JPanel panel = new JPanel(new GridLayout(10, 1));
    
        JLabel nameLabel = new JLabel("Name:");
        JTextField nameField = new JTextField();

        JLabel symbolLabel = new JLabel("Symbol:");
        JTextField symbolField = new JTextField();

        JLabel confidenceLabel = new JLabel("Confidence:");
        JSpinner confidenceSpinner = new JSpinner(new SpinnerNumberModel(0.5, 0.35,0.65 , 0.01));

        JLabel colorLabel = new JLabel("Color:");
        String[] colors = {"Black", "White", "Brown", "Gray", "Red", "Green", "Blue", "Yellow", "Purple", "Orange", "Pink", "Cyan", "Silver", "Gold"};
        JComboBox<String> colorComboBox = new JComboBox<>(colors);

        JLabel breedLabel = new JLabel("Breed:");
        String[] breeds = {"Bojack", "Arabian", "Thoroughbred", "Quarter Horse", "Appaloosa", "Connemara Pony", "Akhal-Teke", "Gypsy Vanner", "Mule"};        
        JComboBox<String> breedComboBox = new JComboBox<>(breeds);

        JLabel accessoriesLabel = new JLabel("Accessories:");
        String[] accessories = {"No Accessory", "Racing Saddle", "Lucky Horseshoe", "Golden Hooves", "Enchanted Saddle", "Pegasis Wings", "Enhanced Blinders"};
        JComboBox<String> accessoriesComboBox = new JComboBox<>(accessories);
    
        panel.add(nameLabel);
        panel.add(nameField);
        panel.add(symbolLabel);
        panel.add(symbolField);
        panel.add(confidenceLabel);
        panel.add(confidenceSpinner);
        panel.add(colorLabel);
        panel.add(colorComboBox);
        panel.add(breedLabel);
        panel.add(breedComboBox);
        panel.add(accessoriesLabel);
        panel.add(accessoriesComboBox);
    
        JButton createButton = new JButton("Create");
        createButton.addActionListener(new ActionListener() {

            
            public void actionPerformed(ActionEvent e) {

                // Retrieve values from fields
                String name = nameField.getText();
                String symbol = symbolField.getText();
                double confidence = (double) Math.max(Math.round(((Double) confidenceSpinner.getValue()) * 100.0) / 100.0, 0.01);
                String color = (String) colorComboBox.getSelectedItem();
                String breed = (String) breedComboBox.getSelectedItem();
                String accessories = (String) accessoriesComboBox.getSelectedItem();
    
                String directoryPath = "part2" + File.separator;
                File horseFile = new File(directoryPath + "horseDetails.txt");

                // Write horse details to file
                try (   

                    FileWriter writer = new FileWriter(horseFile, true);
                    BufferedWriter bw = new BufferedWriter(writer);
                    PrintWriter out = new PrintWriter(bw)) {
                    out.println(name + "," + symbol + "," + confidence + "," + color + "," + breed + "," + accessories + ",15.0" + ",0.5");
                    JOptionPane.showMessageDialog(GUI.this, "Horse created successfully!");

                } catch (IOException ex) {

                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(GUI.this, "Failed to create horse.");
                }
    
                createHorseDialog.dispose(); 
            }
        });

        panel.add(createButton);
    
        createHorseDialog.add(panel);
        createHorseDialog.setVisible(true);
    }

    private void openEditHorseDialog() {
        JFrame parentFrame = new JFrame();
        JDialog editHorseDialog = new JDialog(parentFrame, "Edit Horse Details", Dialog.ModalityType.APPLICATION_MODAL);
        editHorseDialog.setSize(400, 400);
        editHorseDialog.setLayout(new BorderLayout());

        JPanel horseListPanel = new JPanel();
        horseListPanel.setLayout(new BoxLayout(horseListPanel, BoxLayout.Y_AXIS));

        JScrollPane scrollPane = new JScrollPane(horseListPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        String[] horses = readHorsesFromFile();

        // Create clickable labels for each horse
        for (String horse : horses) {

            JLabel horseLabel = new JLabel(horse.split(",")[0]); // Display horse name
            horseLabel.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(Color.BLACK),
                    BorderFactory.createEmptyBorder(5, 5, 5, 5)
            ));

            horseLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            horseLabel.addMouseListener(new MouseAdapter() {
                
                public void mouseClicked(MouseEvent e) {
                    openEditHorseDetailsDialog(horse);
                    editHorseDialog.dispose(); // 
                }
            });
            
            horseListPanel.add(horseLabel);
        }

        editHorseDialog.add(scrollPane, BorderLayout.CENTER);

        editHorseDialog.setVisible(true);
    }

    private void openEditHorseDetailsDialog(String horseDetails) {
        JFrame parentFrame = new JFrame();
        JDialog editDetailsDialog = new JDialog(parentFrame, "Edit Horse Details", Dialog.ModalityType.APPLICATION_MODAL);
        editDetailsDialog.setTitle("Edit Horse Details");
        editDetailsDialog.setSize(300, 400);
        editDetailsDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        editDetailsDialog.setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(7, 2));

        // Split the horse details by comma
        String[] parts = horseDetails.split(",");
        String name = parts[0];
        String symbol = parts[1];
        double confidence = Double.parseDouble(parts[2]);
        String color = parts[3];
        String breed = parts[4];
        String accessories = parts[5];
        double avrgSpeed = Double.parseDouble(parts[6]);
        double winRatio = Double.parseDouble(parts[7]);

        JLabel nameLabel = new JLabel("Name:");
        JTextField nameField = new JTextField(name);
        JLabel symbolLabel = new JLabel("Symbol:");
        JTextField symbolField = new JTextField(symbol);
        JLabel confidenceLabel = new JLabel("Confidence:");
        JSpinner confidenceSpinner = new JSpinner(new SpinnerNumberModel(confidence, 0.35, 0.65, 0.01));
        JLabel colorLabel = new JLabel("Color:");
        String[] colors = {"Black", "White", "Brown", "Gray", "Red", "Green", "Blue", "Yellow", "Purple", "Orange", "Pink", "Cyan", "Silver", "Gold"};
        JComboBox<String> colorComboBox = new JComboBox<>(colors);
        colorComboBox.setSelectedItem(color);
        JLabel breedLabel = new JLabel("Breed:");
        String[] breeds = {"Bojack", "Arabian", "Thoroughbred", "Quarter Horse", "Appaloosa", "Connemara Pony", "Akhal-Teke", "Gypsy Vanner", "Mule"};
        JComboBox<String> breedComboBox = new JComboBox<>(breeds);
        breedComboBox.setSelectedItem(breed);
        JLabel accessoriesLabel = new JLabel("Accessories:");
        String[] accessoriesList = {"No Accessory", "Racing Saddle", "Lucky Horseshoe", "Golden Hooves", "Enchanted Saddle", "Pegasis Wings", "Enhanced Blinders"};
        JComboBox<String> accessoriesComboBox = new JComboBox<>(accessoriesList);
        accessoriesComboBox.setSelectedItem(accessories);

        panel.add(nameLabel);
        panel.add(nameField);
        panel.add(symbolLabel);
        panel.add(symbolField);
        panel.add(confidenceLabel);
        panel.add(confidenceSpinner);
        panel.add(colorLabel);
        panel.add(colorComboBox);
        panel.add(breedLabel);
        panel.add(breedComboBox);
        panel.add(accessoriesLabel);
        panel.add(accessoriesComboBox);

        JButton saveButton = new JButton("Save");
        saveButton.addActionListener(new ActionListener() {
            
            public void actionPerformed(ActionEvent e) {
                // Get the updated values
                String updatedName = nameField.getText();
                String updatedSymbol = symbolField.getText();
                double updatedConfidence = (double) confidenceSpinner.getValue();
                String updatedColor = (String) colorComboBox.getSelectedItem();
                String updatedBreed = (String) breedComboBox.getSelectedItem();
                String updatedAccessories = (String) accessoriesComboBox.getSelectedItem();

                // Construct updated horse details
                String updatedHorse = updatedName + "," + updatedSymbol + "," + updatedConfidence + "," +
                        updatedColor + "," + updatedBreed + "," + updatedAccessories + "," + avrgSpeed + "," + winRatio;

                // Update horse in the file
                updateHorseInFile(horseDetails, updatedHorse);

                JOptionPane.showMessageDialog(GUI.this, "Horse details updated!");
                editDetailsDialog.dispose();
            }
        });

        panel.add(saveButton);

        editDetailsDialog.add(panel);
        editDetailsDialog.setVisible(true);
    }

    private String[] readHorsesFromFile() {
        String directoryPath = "part2" + File.separator;
        File horseFile = new File(directoryPath + "horseDetails.txt");        
        String[] horses = new String[0];

        try (BufferedReader br = new BufferedReader(new FileReader(horseFile))) {

            String line;
            int count = 0;

            while ((line = br.readLine()) != null) {
                count++;
            }

            horses = new String[count];
            br.close();

            BufferedReader br2 = new BufferedReader(new FileReader(horseFile));
            int index = 0;
            while ((line = br2.readLine()) != null) {
                horses[index] = line;
                index++;
            }

            br2.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return horses;
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
    
        // Delete original file
        File oldFile = new File(filePath.getPath());

        if (!oldFile.delete()) {
            System.out.println("Could not delete original file.");
            return;
        }
    
        // Rename temp file to original file name
        File newFile = new File(tempFile.getPath());

        if (!newFile.renameTo(oldFile)) {
            System.out.println("Could not rename temp file to original file name.");
        }
    }

    private void viewHorseStatistics() {

        JFrame statisticsFrame = new JFrame("Horse Statistics");
        statisticsFrame.setSize(400, 300);
        statisticsFrame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        statisticsFrame.setLocationRelativeTo(null);

        JTextArea statsTextArea = new JTextArea();
        statsTextArea.setEditable(false);

        String directoryPath = "part2" + File.separator;
        File horseFile = new File(directoryPath + "horseDetails.txt");

        try (BufferedReader reader = new BufferedReader(new FileReader(horseFile))) {

            String line;

            while ((line = reader.readLine()) != null) {
                
                String[] horseDetails = line.split(",");
                String name = horseDetails[0];
                String symbol = horseDetails[1];
                double confidence = Double.parseDouble(horseDetails[2]);
                String color = horseDetails[3];
                String breed = horseDetails[4];
                String accessories = horseDetails[5];
                double avgSpeed = Double.parseDouble(horseDetails[6]);
                double winRatio = Double.parseDouble(horseDetails[7]);

                String stats = String.format("Name: %s\nSymbol: %s\nConfidence: %.2f\nColor: %s\nBreed: %s\nAccessories: %s\nAverage Speed: %.2f\nWin Ratio: %.2f\n\n",
                        name, symbol, confidence, color, breed, accessories, avgSpeed, winRatio);
                statsTextArea.append(stats);
            }
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(GUI.this, "Failed to read horse details.");
        }

        JScrollPane scrollPane = new JScrollPane(statsTextArea);
        statisticsFrame.add(scrollPane);

        statisticsFrame.setVisible(true);
    }
    
    private void openCreateRaceDialog() {

        JFrame parentFrame = new JFrame();
        JDialog createRaceDialog = new JDialog(parentFrame, "Create Race", Dialog.ModalityType.DOCUMENT_MODAL);
        createRaceDialog.setSize(400, 400);
        createRaceDialog.setLayout(new BorderLayout());
        ArrayList<String> selectedHorseDetail = new ArrayList<>();

        JPanel racePanel = new JPanel(new FlowLayout());

        // Slider for race length
        JLabel lengthLabel = new JLabel("Race Length:");
        JSlider raceLengthSlider = new JSlider(JSlider.HORIZONTAL, 1, 50, 1);
        raceLengthSlider.setMajorTickSpacing(5);
        raceLengthSlider.setPaintTicks(true);
        raceLengthSlider.setPaintLabels(true);
        
        // Label to display current slider value
        JLabel valueLabel = new JLabel("Race Length: " + raceLengthSlider.getValue());
        
        // ChangeListener to update valueLabel when slider value changes
        raceLengthSlider.addChangeListener(new ChangeListener() {
            
            public void stateChanged(ChangeEvent e) {
                valueLabel.setText("Race Length: " + raceLengthSlider.getValue());
            }
        });
        
        racePanel.add(lengthLabel);
        racePanel.add(raceLengthSlider);
        racePanel.add(valueLabel);

        // Button to add horse
        JButton addHorseButton = new JButton("Add Horse");
        racePanel.add(addHorseButton);

        // List to display added horses
        DefaultListModel<String> horseListModel = new DefaultListModel<>();
        JList<String> horseList = new JList<>(horseListModel);
        horseListModel.addElement("Horses added (minimum 2)");
        JScrollPane horseScrollPane = new JScrollPane(horseList);
        racePanel.add(horseScrollPane);

        // Action listener for add horse button
        addHorseButton.addActionListener(new ActionListener() {
            
            public void actionPerformed(ActionEvent e) {

                JFrame parentFrame = new JFrame();
                JDialog addHorseDialog = new JDialog(parentFrame, "Add Horse", Dialog.ModalityType.APPLICATION_MODAL);
                addHorseDialog.setSize(300, 300);
                addHorseDialog.setLayout(new BorderLayout());

                JPanel horseListPanel = new JPanel();
                horseListPanel.setLayout(new BoxLayout(horseListPanel, BoxLayout.Y_AXIS));

                String[] horses = readHorsesFromFile();

                // Create clickable labels for each horse
                for (String horse : horses) {

                    JLabel horseLabel = new JLabel(horse.split(",")[0]); // Display horse name
                    horseLabel.setBorder(BorderFactory.createCompoundBorder(
                            BorderFactory.createLineBorder(Color.BLACK),
                            BorderFactory.createEmptyBorder(5, 5, 5, 5)
                    ));

                    horseLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                    horseLabel.addMouseListener(new MouseAdapter() {
                        
                        public void mouseClicked(MouseEvent e) {
                            horseListModel.addElement(horseLabel.getText());

                            String horseDetail = horse.split(",")[0] + "," + horse.split(",")[1] + "," + horse.split(",")[2] + "," +
                                horse.split(",")[3] + "," + horse.split(",")[4] + "," + horse.split(",")[5] + "," + horse.split(",")[6] +
                                "," + horse.split(",")[7];

                            selectedHorseDetail.add(horseDetail);

                            addHorseDialog.dispose();
                        }
                    });

                    horseListPanel.add(horseLabel);
                }


                JScrollPane scrollPane = new JScrollPane(horseListPanel);
                addHorseDialog.add(scrollPane, BorderLayout.CENTER);

                addHorseDialog.setVisible(true);
            }
        });

        // Button to start race
        JButton startRaceButton = new JButton("Start Race");
        racePanel.add(startRaceButton);

        createRaceDialog.add(racePanel, BorderLayout.CENTER);

        startRaceButton.addActionListener(new ActionListener() {
            
            public void actionPerformed(ActionEvent e) {

                int raceLength = raceLengthSlider.getValue();

                // Check if there are at least 2 horses added
                if (selectedHorseDetail.size() < 2) {
                    JOptionPane.showMessageDialog(createRaceDialog, "Minimum 2 horses required to start the race.");
                    return;
                }

                try {

                // Create Race object and add selected horses
                Race race = new Race(raceLength);
                for (String horseDetail : selectedHorseDetail) {
                    String[] horseDetails = horseDetail.split(",");
                    Horse horse = new Horse(
                            horseDetails[0], // Name
                            horseDetails[1], // Symbol
                            Double.parseDouble(horseDetails[2]), // Confidence 
                            horseDetails[3], // Color
                            horseDetails[4], // Breed
                            horseDetails[5], // Accessories
                            Double.parseDouble(horseDetails[6]), // Speed
                            Double.parseDouble(horseDetails[7]) // winRate
                        );
                    race.addHorse(horse);
                }

                Race.RaceWindow raceWindow = new Race.RaceWindow(createRaceDialog);
                raceWindow.setVisible(true);
                race.startRace(raceWindow.raceTextArea);

                } catch (IllegalArgumentException ex) {
                    JOptionPane.showMessageDialog(createRaceDialog, ex.getMessage());
                }
            }
        });  

        createRaceDialog.setVisible(true);
        
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            
            public void run() {
                new GUI();
            }
        });
    }
}

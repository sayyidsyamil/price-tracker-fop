package com.mycompany.pricetracker;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;   
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;

public class PriceTracker {
    private static int clickCount = 0;
    private static JFrame mainFrame;
    private static JFrame loginFrame;
    private static String loggedInUsername;

    public static void main(String[] args) {
        createLoginWindow();
    }

    private static void createLoginWindow() {
        loginFrame = new JFrame("Login");
        loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        loginFrame.setSize(300, 150);
        
        // Load and set the icon image
        try {
            BufferedImage iconImage = ImageIO.read(new File("icon.png"));
            loginFrame.setIconImage(iconImage);
        } catch (IOException e) {
            // Handle image loading error
            e.printStackTrace();
        }
        
        // Center the loginFrame on the screen
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (dim.width - loginFrame.getWidth()) / 2;
        int y = (dim.height - loginFrame.getHeight()) / 2;
        loginFrame.setLocation(x, y);
        loginFrame.setLayout(null);

        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setBounds(10, 10, 80, 25);

        JTextField usernameField = new JTextField();
        usernameField.setBounds(100, 10, 160, 25);

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds(10, 40, 80, 25);

        JPasswordField passwordField = new JPasswordField();
        passwordField.setBounds(100, 40, 160, 25);

        JButton loginButton = new JButton("Login");
        loginButton.setBounds(160, 75,100, 25);
        loginButton.setBackground(new Color(26,115,232));
        loginButton.setForeground(new Color(255,255,255));
        

        JButton registerButton = new JButton("Register");
        registerButton.setBounds(40, 75, 100, 25);
        
        // Make the button background transparent
        registerButton.setOpaque(false);
        registerButton.setContentAreaFilled(false);
        registerButton.setBorderPainted(false);

        // Set the font color (text color)
        registerButton.setForeground(new Color(26,115,232)); 

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Check the username and password (for simplicity, you can hardcode the credentials)
                String enteredUsername = usernameField.getText();
                char[] enteredPassword = passwordField.getPassword();
                if (checkCredentials(enteredUsername, new String(enteredPassword))) {
                    loggedInUsername = enteredUsername;
                    loginFrame.dispose(); // Close the login window
                    createMainWindow();
                } else {
                    JOptionPane.showMessageDialog(loginFrame, "Invalid username or password");
                }
            }
        });

        loginFrame.add(usernameLabel);
        loginFrame.add(usernameField);
        loginFrame.add(passwordLabel);
        loginFrame.add(passwordField);
        loginFrame.add(loginButton);
        loginFrame.add(registerButton);

        loginFrame.setVisible(true);

        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createRegistrationWindow();
            }
        });
    }

    private static boolean checkCredentials(String enteredUsername, String enteredPassword) {
    try {
        String filePath = System.getProperty("user.dir") + "/data.txt";
        Scanner scanner = new Scanner(new FileReader(filePath));
        
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] parts = line.split(";");
            if (parts.length == 2) {
                String username = parts[0].trim();
                String password = parts[1].trim();
                if (username.equals(enteredUsername) && password.equals(enteredPassword)) {
                    scanner.close();
                    return true; // Credentials match
                }
            }
        }
        
        scanner.close();
    } catch (IOException e) {
        e.printStackTrace();
    }

    return false; // Credentials do not match or an error occurred
}
 
    private static void createMainWindow() {
        mainFrame = new JFrame("Price Tracker by Go Block");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setSize(600, 400);
        
        try {
            BufferedImage iconImage = ImageIO.read(new File("icon.png"));
            mainFrame.setIconImage(iconImage);
        } catch (IOException e) {
            // Handle image loading error
            e.printStackTrace();
        }
        
        
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (dim.width - mainFrame.getWidth()) / 2;
        int y = (dim.height - mainFrame.getHeight()) / 2;
        mainFrame.setLocation(x, y);
        
        mainFrame.setLayout(null);
        
        
        JLabel greetingLabel = new JLabel("Hi, welcome " + loggedInUsername + "!👽");
        greetingLabel.setBounds(x-110, -10, 220, 50);

        JButton button = new JButton("0");
        button.setBounds(150, 200, 220, 50);

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clickCount++;
                button.setText(String.valueOf(clickCount));
            }
        });

        mainFrame.add(button);
        mainFrame.add(greetingLabel);
        mainFrame.setVisible(true);
    }

    private static void createRegistrationWindow() {
        JFrame registrationFrame = new JFrame("Register");
        registrationFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        registrationFrame.setSize(300, 170);
        
        try {
            BufferedImage iconImage = ImageIO.read(new File("icon.png"));
            registrationFrame.setIconImage(iconImage);
        } catch (IOException e) {
            // Handle image loading error
            e.printStackTrace();
        }
        
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (dim.width - loginFrame.getWidth()) / 2;
        int y = (dim.height - loginFrame.getHeight()) / 2 - 170;
        registrationFrame.setLocation(x, y);
        
        registrationFrame.setLayout(null);

        JLabel registerLabel = new JLabel("Register a new account:");
        registerLabel.setBounds(10, 10, 180, 25);

        JLabel newUsernameLabel = new JLabel("New Username:");
        newUsernameLabel.setBounds(10, 40, 100, 25);

        JTextField newUsernameField = new JTextField();
        newUsernameField.setBounds(120, 40, 140, 25);

        JLabel newPasswordLabel = new JLabel("New Password:");
        newPasswordLabel.setBounds(10, 70, 100, 25);

        JPasswordField newPasswordField = new JPasswordField();
        newPasswordField.setBounds(120, 70, 140, 25);

        JButton registerAccountButton = new JButton("Register");
        registerAccountButton.setBounds(100, 100, 100, 25);

        registerAccountButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String newUsername = newUsernameField.getText();
                char[] newPassword = newPasswordField.getPassword();

                if (registerNewAccount(newUsername, new String(newPassword))) {
                    registrationFrame.dispose();
                    JOptionPane.showMessageDialog(loginFrame, "Registration successful.");
                } else {
                    JOptionPane.showMessageDialog(registrationFrame, "Registration failed. Please try again.");
                }
            }
        });

        registrationFrame.add(registerLabel);
        registrationFrame.add(newUsernameLabel);
        registrationFrame.add(newUsernameField);
        registrationFrame.add(newPasswordLabel);
        registrationFrame.add(newPasswordField);
        registrationFrame.add(registerAccountButton);

        registrationFrame.setVisible(true);
    }

    private static boolean registerNewAccount(String newUsername, String newPassword) {
        try {
            String filePath = System.getProperty("user.dir") + "/data.txt";
            // Check if the username is already taken
            if (isUsernameTaken(filePath, newUsername)) {
                JOptionPane.showMessageDialog(loginFrame, "Username is already taken.");
                return false;
            }

            FileWriter writer = new FileWriter(filePath, true); // Open the file for appending
            writer.write(newUsername + ";" + newPassword + "\n");
            writer.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
    private static boolean isUsernameTaken(String filePath, String newUsername) {
        try {
            Scanner scanner = new Scanner(new FileReader(filePath));
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split(";");
                if (parts.length > 0) {
                    String username = parts[0].trim();
                    if (username.equals(newUsername)) {
                        scanner.close();
                        return true; // Username is already taken
                    }
                }
            }
            scanner.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false; // Username is not taken
    }
}

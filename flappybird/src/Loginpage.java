import javax.swing.*;
import java.awt.*;
import java.awt.desktop.UserSessionEvent;
import java.awt.event.*;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class Loginpage extends JPanel {

    private MongoDB mongoDB = new MongoDB();
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JFrame parentFrame;
    private JButton loginButton;
    private JLabel messageLabel;
    private Image backgroundImage;
    private JPanel cardPanel;
    private CardLayout cardLayout;
    private HashMap<String, String> userDatabase = new HashMap<>(); // Simple in-memory storage

    public Loginpage(JFrame frame) {
        this.parentFrame = frame;
        setPreferredSize(new Dimension(360, 640));
        setLayout(new BorderLayout());

        // Load background image
        try {
            // Replace "path/to/your/image.jpg" with your actual image path
            backgroundImage = ImageIO.read(new File("C:\\Users\\tarun\\Desktop\\FlappyBird\\flappybird\\src\\flappybirdbg.png"));
        } catch (IOException e) {
            e.printStackTrace();
            // Fallback background color if image fails to load
            setBackground(new Color(135, 206, 235));
        }

        // Create card layout for switching between screens
        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);
        cardPanel.setOpaque(false);

        // Add different panels
        cardPanel.add(createChoicePanel(), "choice");
        cardPanel.add(createLoginPanel(), "login");
        cardPanel.add(createRegisterPanel(), "register");

        add(cardPanel, BorderLayout.CENTER);
    }

    private JPanel createChoicePanel() {
        JPanel choicePanel = new JPanel(null) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (backgroundImage != null) {
                    g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
                }
            }
        };

        // Title
        JLabel titleLabel = new JLabel("Flappy Bird");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 32));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setBounds(100, 100, 200, 40);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        choicePanel.add(titleLabel);

        // Login button
        JButton loginChoiceBtn = new JButton("Login");
        loginChoiceBtn.setBounds(80, 300, 200, 40);
        loginChoiceBtn.setBackground(new Color(34, 139, 34));
        loginChoiceBtn.setForeground(Color.WHITE);
        loginChoiceBtn.setFocusPainted(false);
        loginChoiceBtn.addActionListener(e -> cardLayout.show(cardPanel, "login"));
        choicePanel.add(loginChoiceBtn);

        // Register button
        JButton registerChoiceBtn = new JButton("Register");
        registerChoiceBtn.setBounds(80, 360, 200, 40);
        registerChoiceBtn.setBackground(new Color(0, 100, 200));
        registerChoiceBtn.setForeground(Color.WHITE);
        registerChoiceBtn.setFocusPainted(false);
        registerChoiceBtn.addActionListener(e -> cardLayout.show(cardPanel, "register"));
        choicePanel.add(registerChoiceBtn);

        return choicePanel;
    }

    private JPanel createLoginPanel() {
        JPanel loginPanel = new JPanel(null) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (backgroundImage != null) {
                    g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
                }
            }
        };

        // Title
        JLabel titleLabel = new JLabel("Login");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 28));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setBounds(80, 100, 200, 40);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        loginPanel.add(titleLabel);

        // Username
        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setBounds(70, 200, 200, 25);
        usernameLabel.setForeground(Color.WHITE);
        loginPanel.add(usernameLabel);

        usernameField = new JTextField();
        usernameField.setBounds(70, 230, 220, 25);
        loginPanel.add(usernameField);

        // Password
        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds(70, 270, 200, 25);
        passwordLabel.setForeground(Color.WHITE);
        loginPanel.add(passwordLabel);

        passwordField = new JPasswordField();
        passwordField.setBounds(70, 300, 220, 25);
        loginPanel.add(passwordField);

        // Login Button
        loginButton = new JButton("Login");
        loginButton.setBounds(70, 350, 220, 35);
        loginButton.setBackground(new Color(34, 139, 34));
        loginButton.setForeground(Color.WHITE);
        loginButton.setFocusPainted(false);
        loginPanel.add(loginButton);

        // Back Button
        JButton backButton = new JButton("Back");
        backButton.setBounds(70, 400, 220, 35);
        backButton.setBackground(new Color(150, 150, 150));
        backButton.setForeground(Color.WHITE);
        backButton.setFocusPainted(false);
        backButton.addActionListener(e -> cardLayout.show(cardPanel, "choice"));
        loginPanel.add(backButton);

        // Message Label
        messageLabel = new JLabel("");
        messageLabel.setBounds(70, 450, 220, 25);
        messageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        messageLabel.setForeground(Color.WHITE);
        loginPanel.add(messageLabel);

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());

                if (mongoDB.validateUser(username, password)) {
                    UserSession.currentUsername = username;
                    messageLabel.setForeground(Color.GREEN);
                    messageLabel.setText("Login Successful!");

                    Timer timer = new Timer(1000, new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            startGame();
                        }
                    });
                    timer.setRepeats(false);
                    timer.start();
                } else {
                    messageLabel.setForeground(Color.RED);
                    messageLabel.setText("Invalid username or password!");
                }
            }
        });

        return loginPanel;
    }

    private JPanel createRegisterPanel() {
        JPanel registerPanel = new JPanel(null) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (backgroundImage != null) {
                    g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
                }
            }
        };

        // Title
        JLabel titleLabel = new JLabel("Register");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 28));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setBounds(80, 100, 200, 40);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        registerPanel.add(titleLabel);

        // Username
        JLabel usernameLabel = new JLabel("Choose Username:");
        usernameLabel.setBounds(70, 200, 200, 25);
        usernameLabel.setForeground(Color.WHITE);
        registerPanel.add(usernameLabel);

        JTextField regUsernameField = new JTextField();
        regUsernameField.setBounds(70, 230, 220, 25);
        registerPanel.add(regUsernameField);

        // Password
        JLabel passwordLabel = new JLabel("Choose Password:");
        passwordLabel.setBounds(70, 270, 200, 25);
        passwordLabel.setForeground(Color.WHITE);
        registerPanel.add(passwordLabel);

        JPasswordField regPasswordField = new JPasswordField();
        regPasswordField.setBounds(70, 300, 220, 25);
        registerPanel.add(regPasswordField);

        // Register Button
        JButton registerButton = new JButton("Register");
        registerButton.setBounds(70, 350, 220, 35);
        registerButton.setBackground(new Color(0, 100, 200));
        registerButton.setForeground(Color.WHITE);
        registerButton.setFocusPainted(false);
        registerPanel.add(registerButton);

        // Back Button
        JButton backButton = new JButton("Back");
        backButton.setBounds(70, 400, 220, 35);
        backButton.setBackground(new Color(150, 150, 150));
        backButton.setForeground(Color.WHITE);
        backButton.setFocusPainted(false);
        backButton.addActionListener(e -> cardLayout.show(cardPanel, "choice"));
        registerPanel.add(backButton);

        // Message Label
        JLabel regMessageLabel = new JLabel("");
        regMessageLabel.setBounds(70, 450, 220, 25);
        regMessageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        regMessageLabel.setForeground(Color.WHITE);
        registerPanel.add(regMessageLabel);

        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = regUsernameField.getText();
                String password = new String(regPasswordField.getPassword());

                if (username.length() < 3 || password.length() < 3) {
                    regMessageLabel.setForeground(Color.RED);
                    regMessageLabel.setText("Username and password must be at least 3 characters!");
                    return;
                }

                if (!mongoDB.registerUser(username, password)) {
                    regMessageLabel.setForeground(Color.RED);
                    regMessageLabel.setText("Username already exists!");
                    return;
                }

                regMessageLabel.setForeground(Color.GREEN);
                regMessageLabel.setText("Registration successful! Please login.");

                Timer timer = new Timer(1500, new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        cardLayout.show(cardPanel, "login");
                    }
                });
                timer.setRepeats(false);
                timer.start();
            }
        });


        return registerPanel;
    }

    public class UserSession {
        public static String currentUsername = null;
    }

    private boolean validateLogin(String username, String password) {
        // Check against our simple database
        String storedPassword = userDatabase.get(username);
        return storedPassword != null && storedPassword.equals(password);
    }

    private void startGame() {
        parentFrame.getContentPane().removeAll();
        FlappyBird flappyBird = new FlappyBird(mongoDB);
        parentFrame.add(flappyBird);
        parentFrame.revalidate();
        parentFrame.repaint();
        flappyBird.requestFocus();
    }
}
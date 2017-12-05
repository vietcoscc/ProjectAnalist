package ui;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    public static final int WIDTH = 1280;
    public static final int HEIGHT = 720;

    public MainFrame() throws HeadlessException {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
            ex.printStackTrace();
        }
        setTitle("Project analist");
        setResizable(false);
        setSize(WIDTH, HEIGHT);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        add(new MainPanel());
    }
}

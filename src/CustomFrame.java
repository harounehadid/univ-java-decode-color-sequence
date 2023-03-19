import utils.GetBaseDirPath;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class CustomFrame extends JFrame {
    private static Font customFont;

    public CustomFrame(String frameTitle) {
        this.setCommonSettings(frameTitle);
    }

    public CustomFrame(String frameTitle, int width, int height) {
        this.setSize(width, height);
        this.setCommonSettings(frameTitle);
    }

    private void setCommonSettings(String frameTitle) {
        this.setTitle(frameTitle);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setLocationRelativeTo(null);

        // this.getContentPane().setBackground(new Color(0xf8f9f9));

        // Setting the font to be used
        try {
            String fontPath = GetBaseDirPath.root() + "/src/media/PressStart2P-Regular.ttf";
            customFont = Font.createFont(Font.TRUETYPE_FONT, new File(fontPath));
            customFont = customFont.deriveFont(Font.PLAIN, 12);
        } catch (IOException|FontFormatException e) {
            // System.out.println("(!) Font NOT detected (!)");
            customFont = this.getFont();
        }
    }

    public static Font geFont() {
        return customFont;
    }

    public void addItem(JPanel panel, String section) {
        if (section == "center") {
            this.add(panel, BorderLayout.CENTER);
        }
        else if (section == "north") {
            this.add(panel, BorderLayout.NORTH);
        }
        else if (section == "east") {
            this.add(panel, BorderLayout.EAST);
        }
        else if (section == "south") {
            this.add(panel, BorderLayout.SOUTH);
        }
        else if (section == "west") {
            this.add(panel, BorderLayout.WEST);
        }
        else {
            System.out.println("(!) Unknown frame section check item added to CustomFrame (!)");
        }
    }

    public void addItem(JLabel label, String section) {
        if (section == "center") {
            this.add(label, BorderLayout.CENTER);
        }
        else if (section == "north") {
            this.add(label, BorderLayout.NORTH);
        }
        else if (section == "east") {
            this.add(label, BorderLayout.EAST);
        }
        else if (section == "south") {
            this.add(label, BorderLayout.SOUTH);
        }
        else if (section == "west") {
            this.add(label, BorderLayout.WEST);
        }
        else {
            System.out.println("(!) Unknown frame section check item added to CustomFrame (!)");
        }
    }

    public void addItem(JButton btn, String section) {
        if (section == "center") {
            this.add(btn, BorderLayout.CENTER);
        }
        else if (section == "north") {
            this.add(btn, BorderLayout.NORTH);
        }
        else if (section == "east") {
            this.add(btn, BorderLayout.EAST);
        }
        else if (section == "south") {
            this.add(btn, BorderLayout.SOUTH);
        }
        else if (section == "west") {
            this.add(btn, BorderLayout.WEST);
        }
        else {
            System.out.println("(!) Unknown frame section check item added to CustomFrame (!)");
        }
    }

    public JLabel createLabel(String itemType, String data) {
        JLabel newLabel = new JLabel();

        newLabel.setHorizontalTextPosition(JLabel.CENTER);
        newLabel.setVerticalTextPosition(JLabel.CENTER);

        if (itemType == "image") {
            ImageIcon image = new ImageIcon(data);
            newLabel.setSize(image.getIconWidth(), image.getIconHeight());
            newLabel.setIcon(image);
        }
        else if (itemType == "text") {
            newLabel.setText(data);
            newLabel.setFont(customFont);
        }

        return newLabel;
    }

    public void updateLabel(JLabel label, String itemType, String data) {
        if (label == null) {
            System.out.println("(!) Label DO NOT exist (!)");
            return;
        }

        if (itemType == "image") {
            ImageIcon image = new ImageIcon(data);
            label.setSize(image.getIconWidth(), image.getIconHeight());
            label.setIcon(image);
        }
        else if (itemType == "text") {
            label.setText(data);
        }
    }

    public JButton createButton(String btnMessage) {
        JButton newBtn = new JButton(btnMessage);
        newBtn.setFont(customFont);
        // newBtn.setSize(100, 40);
        return newBtn;
    }

    public void finalizeFrameSetup() {
        // These two lines of code should be left at the very bottom
        this.pack();
        this.setVisible(true);
    } /* This function is created to avoid any problems that can occure with GUI */
}

import java.awt.Color;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

public abstract class ColorCode {
    private int length;
    private String title;
    private int cellsSize;
    private JPanel container;
    private JLabel titleLabel;
    private JPanel colorSeqWrapper;
    private ArrayList<JLabel> colorSeq;

    public ColorCode(int length, String title, int cellsSize) {
        this.length = length;
        this.title = title;
        this.cellsSize = cellsSize;
        this.container = new JPanel();
        this.titleLabel = new JLabel(title);
        this.colorSeqWrapper = new JPanel();
        this.colorSeq = new ArrayList<JLabel>();
        this.handleGUI();
    }

    public void handleGUI() {
        int width = this.cellsSize * length;
        int height = this.cellsSize;

        this.colorSeqWrapper.setLayout(new GridLayout(0, length, 0, 0));
        this.colorSeqWrapper.setSize(width, height);

        for (int i = 0; i < length; i++) {
            JLabel newLabel = new JLabel();
            newLabel.setOpaque(true);
            newLabel.setBackground(Color.lightGray);
            newLabel.setSize(this.cellsSize, this.cellsSize);
            newLabel.setText("                    ");
            newLabel.setBorder(BorderFactory.createLineBorder(Color.darkGray, 2));

            this.colorSeqWrapper.add(newLabel);
            this.colorSeq.add(newLabel);
        }

        this.container.add(this.titleLabel);
        this.container.add(this.colorSeqWrapper);
    }

    public void updateCellColor(int index, Color color) {
        this.colorSeq.get(index).setBackground(color);;
    }

    public void addToMainPanel(JLabel label) {
        this.container.add(label);
    }

    // Getters
    public int getLength() {
        return this.length;
    }

    public String getTitle() {
        return this.title;
    }

    public JPanel getGUI() {
        return this.container;
    }

    public ArrayList<JLabel> getColorSeq() {
        return this.colorSeq;
    }
}

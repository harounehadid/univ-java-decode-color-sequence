import java.awt.Color;
public class MissColor {
    private Color color;
    private int index;

    public MissColor(Color color, int index) {
        this.color = color;
        this.index = index;
    }

    // Getters
    public Color getColor() {
        return this.color;
    }

    public int getIndex() {
        return this.index;
    }
}

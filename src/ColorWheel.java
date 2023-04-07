import java.awt.Color;
import java.util.ArrayList;

public final class ColorWheel {
    private static ArrayList<Color> colors = new ArrayList<Color>();

    public static void setColors() {
        colors.add(Color.black);
        colors.add(Color.blue);
        colors.add(Color.cyan);
        colors.add(Color.green);
        colors.add(Color.magenta);
        colors.add(Color.orange);
        colors.add(Color.pink);
        colors.add(Color.red);
        colors.add(Color.white);
        colors.add(Color.yellow);
    }

    public static Color getRandColor() {
        if (colors.size() == 0) setColors();
        int randIndex = (int)Math.floor(Math.random() * colors.size());
        return colors.get(randIndex);
    }

    public static Color getColor(int index) {
        return colors.get(index);
    }

    public static int getColorIndex(Color color) {
        int index = -1;

        for (int i = 0; i < colors.size(); i++) {
            if (colors.get(i).equals(color)) {
                index = i;
                break;
            }
        }

        return index;
    }
}

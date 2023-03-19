public class CodeMaker extends ColorCode {
    public CodeMaker(int length, String title, int cellsSize) {
        super(length, title, cellsSize);
    }

    public void handleGUI() {
        super.handleGUI();
        
        for (int i = 0; i < this.getLength(); i++) {
            this.updateCellColor(i, ColorWheel.getRandColor());
        }
    }
}

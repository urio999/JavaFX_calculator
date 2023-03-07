package model;

public class HistoryLine {
    private StringBuilder text;
    private boolean isSet;

    public HistoryLine(StringBuilder text) {
        this.text = text;
        isSet = false;
    }

    public StringBuilder getText() {
        return text;
    }
}

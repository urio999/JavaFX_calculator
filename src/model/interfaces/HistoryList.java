package model.interfaces;

import javafx.collections.ObservableList;
import model.HistoryLine;

public interface HistoryList {
    public void addHistoryLine(HistoryLine line);

    public ObservableList<HistoryLine> getHistories();

}

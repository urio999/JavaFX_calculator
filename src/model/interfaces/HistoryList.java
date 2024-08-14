package model.interfaces;

import javafx.collections.ObservableList;
import model.HistoryLine;

public interface HistoryList {
    void addHistoryLine(HistoryLine line);

    ObservableList<HistoryLine> getHistories();
}

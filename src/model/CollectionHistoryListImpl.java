package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.interfaces.HistoryList;

public class CollectionHistoryListImpl implements HistoryList {
    ObservableList<HistoryLine> histories;

    public CollectionHistoryListImpl() {
        histories = FXCollections.observableArrayList();
    }

    @Override
    public void addHistoryLine(HistoryLine el) {
        histories.add(el);
    }

    @Override
    public ObservableList<HistoryLine> getHistories() {
        return histories;
    }
}

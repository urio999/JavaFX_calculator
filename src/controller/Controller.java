package controller;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import model.CollectionHistoryListImpl;
import model.HistoryLine;
import model.Model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;

public class Controller {
    CollectionHistoryListImpl historyList;

    @FXML
    private double num1 = 0;
    private double num2 = 0;
    private boolean switchTF;
    private String operation = "";
    private Model model = new Model();

    @FXML
    TextField outputNum1;

    @FXML
    TextField action;

    @FXML
    TextField outputNum2;

    @FXML
    TextField equality;

    @FXML
    TextField result;

    @FXML
    TableView tableView;

    @FXML
    TableColumn tableColumn;

    public Controller() {
        historyList = new CollectionHistoryListImpl();
    }

    @FXML
    private void onNumberClick(ActionEvent event) {
        String value = ((Button) event.getSource()).getText();
        if (!switchTF) {
            outputNum1.setText(outputNum1.getText() + value);
        } else {
            outputNum2.setText(outputNum2.getText() + value);
        }
    }

    @FXML
    private void onOperationClick(ActionEvent event) {
        String value = ((Button) event.getSource()).getText();
        switchTF = true;
        if (!"=".equals(value)) {
            if (!operation.isEmpty()) {
                return;
            }
            operation = value;
            action.setText(value);
        } else {
            if (operation.isEmpty()) {
                return;
            } else {
                switchTF = false;
                operation = "";
            }
        }
    }

    @FXML
    private void onEqualityClick(ActionEvent event) {
        String value = ((Button) event.getSource()).getText();
        equality.setText(value);
        resultCalc();
        createHistory();
    }

    public void onDELClick(ActionEvent event) {
        if (!switchTF && !outputNum1.getText().isEmpty()) {
            if (!outputNum1.getText().contains(".")) {
            }
            outputNum1.setText(outputNum1.getText(0, outputNum1.getText().length() - 1));
        } else if (switchTF && !outputNum2.getText().isEmpty()) {
            outputNum2.setText(outputNum2.getText(0, outputNum2.getText().length() - 1));
        }
    }

    @FXML
    private void resultCalc() {
        try {
            num1 = Double.parseDouble(outputNum1.getText());
        } catch (NumberFormatException e) {
            e.getMessage();
            outputNum1.setText("0");
            num1 = 0;
        }
        try {
            num2 = Double.parseDouble(outputNum2.getText());
        } catch (NumberFormatException e) {
            e.getMessage();
            outputNum2.setText("0");
            num2 = 0;
        }
        try {
            BigDecimal bigDecimal = new BigDecimal(model.calculation(num1, num2, operation));
            bigDecimal = bigDecimal.setScale(15, RoundingMode.HALF_UP);
            result.setText(String.valueOf(bigDecimal.doubleValue()));
        } catch (NumberFormatException e) {
            e.getMessage();
            if (num1 == 0 && num2 == 0) {
                result.setText(e.getMessage());
            } else {
                result.setText("Div by Zero");
            }
        }
    }

    @FXML
    private void createHistory() {
        StringBuilder sb = new StringBuilder();
        for (String s : Arrays.asList(" " + outputNum1.getText() + " ", action.getText() + " ",
                outputNum2.getText() + " ", equality.getText() + " ", result.getText() + " ")) {
            sb.append(s);
        }
        HistoryLine hl = new HistoryLine(sb);
        historyList.addHistoryLine(hl);
    }

    @FXML
    private void freezeTextFields() {
        for (TextField textField : Arrays.asList(outputNum1, outputNum2, action, result, equality)) {
            textField.setEditable(false);
        }
    }

    @FXML
    public void initialize() {
        freezeTextFields();
        tableColumn.setCellValueFactory(new PropertyValueFactory<HistoryLine, String>("text"));
        tableView.setItems(historyList.getHistories());

        outputNum1.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.matches("\\d*([.]\\d*)?")) {
                    outputNum1.setText(oldValue);
                }
            }
        });
        outputNum2.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.matches("\\d*([.]\\d*)?")) {
                    outputNum2.setText(oldValue);
                }
            }
        });
    }

    @FXML
    public void clear(ActionEvent event) {
        for (TextField textField : Arrays.asList(outputNum1, action, outputNum2, equality, result)) {
            textField.clear();
        }
        operation = "";
        switchTF = false;
        num1 = 0;
        num2 = 0;
    }
}
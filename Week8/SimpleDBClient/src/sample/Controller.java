package sample;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.util.Callback;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class Controller {

    @FXML
    private TextArea queryTextArea;

    @FXML
    private TableView tableView;

    public static final String BLANK = "";
    private ObservableList<ObservableList> tableData;

    public void buttonPressed(ActionEvent event) {
        String query = queryTextArea.getText();
        if (BLANK.equals(query)) {
            this.alert("Input Missing", "Please enter your query!!!", Alert.AlertType.ERROR);
        } else {
            Connection connection = null;
            PreparedStatement statement = null;
            tableData = FXCollections.observableArrayList();
            try {
                connection = Database.getDBConnection();
                connection.setAutoCommit(false);
                statement = connection.prepareStatement(query);
                ResultSet rs = statement.executeQuery();

                tableView.getColumns().clear();
                tableView.getItems().clear();

                for (int i = 0; i < rs.getMetaData().getColumnCount(); i++) {
                    final int j = i;
                    TableColumn column = new TableColumn(rs.getMetaData().getColumnName(i + 1));
                    column.setCellValueFactory(
                            new Callback<TableColumn.CellDataFeatures<ObservableList, String>, ObservableValue<String>>() {
                                public ObservableValue<String> call(
                                        TableColumn.CellDataFeatures<ObservableList, String> param) {
                                    return new SimpleStringProperty(param.getValue().get(j).toString());
                                }
                            });
                    tableView.getColumns().addAll(column);
                }

                while (rs.next()) {
                    ObservableList<String> row = FXCollections.observableArrayList();
                    for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
                        row.add(rs.getString(i));
                    }
                    tableData.add(row);
                }
                tableView.setItems(tableData);
            } catch (SQLException se) {
                this.alert("Error", "Invalid SQL or some other DB Error", Alert.AlertType.ERROR);
                se.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (null != statement) {
                    try {
                        statement.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
                if (null != connection) {
                    try {
                        connection.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }


    public void alert(String title, String message, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }


}

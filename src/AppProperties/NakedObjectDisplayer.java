package AppProperties;

import javafx.beans.property.StringProperty;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Function;

public class NakedObjectDisplayer {
    private VBox dialogVbox;
    private Stage dialog;

    public void display(NakedObject obj) {
        List<StringProperty> textPropertyList = new ArrayList<>();

        createDialog(obj.fieldNames().size() * 10);

        for (String fieldName : obj.fieldNames()) {
            String fieldValue = obj.getFieldValue(fieldName);
            textPropertyList.add(createDisplayForField(fieldName, fieldValue));
        }

        Button saveButton = new Button();
        saveButton.setText("Save");
        saveButton.setOnAction(value -> {
            for (int i = 0; i < obj.fieldNames().size(); i++) {
                obj.fieldChanged(obj.fieldNames().get(i), textPropertyList.get(i).getValue());
            }
            dialog.close();
        });

        this.dialogVbox.getChildren().add(saveButton);

        showDialog();
    }

    public void displayComboBox(NakedObjectDropDown obj, Function<Boolean, Void> listener) {
        createDialog(10);
        Text caption = new Text("Choose:");
        this.dialogVbox.getChildren().add(caption);

        ComboBox<String> comboBox = new ComboBox<>();
        comboBox.setItems(obj.getDropDownNames());

        this.dialogVbox.getChildren().add(comboBox);
        AtomicBoolean isChanged = new AtomicBoolean(false);
        Button saveButton = new Button();

        saveButton.setText("Set");
        saveButton.setOnAction(value -> {
            listener.apply(isChanged.get());
            dialog.close();
        });

        comboBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            isChanged.set(obj.setSelected(newValue));
        });

        this.dialogVbox.getChildren().add(saveButton);

        showDialog();
    }


    private void createDialog(int spacing) {
        dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        this.dialogVbox = new VBox(spacing);
        dialogVbox.setPadding(new Insets(20, 20, 20, 20));

    }

    private void showDialog() {
        Scene dialogScene = new Scene(dialogVbox);
        dialog.setScene(dialogScene);
        dialog.setAlwaysOnTop(true);
        dialog.setResizable(false);
        dialog.show();
    }

    private StringProperty createDisplayForField(String fieldName, String fieldValue) {
        Text caption = new Text(fieldName);
        this.dialogVbox.getChildren().add(caption);
        TextField textBox = new TextField(fieldValue);
        this.dialogVbox.getChildren().add(textBox);
        return textBox.textProperty();
    }
}
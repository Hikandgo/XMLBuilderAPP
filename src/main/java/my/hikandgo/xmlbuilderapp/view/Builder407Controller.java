package my.hikandgo.xmlbuilderapp.view;

import javafx.fxml.FXML;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.stage.DirectoryChooser;
import javafx.util.converter.IntegerStringConverter;
import my.hikandgo.xmlbuilderapp.MainApp;
import my.hikandgo.xmlbuilderapp.models.Builder407model;
import my.hikandgo.xmlbuilderapp.service.Builder407;
import my.hikandgo.xmlbuilderapp.service.TargetFolder;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.function.UnaryOperator;

public class Builder407Controller {

    @FXML
    private TextField path;
    @FXML
    private TextField textLog;
    @FXML
    private TextField DATE_MSG;
    @FXML
    private TextField DATE_REQ;
    @FXML
    private TextField DATE_GET_REQ;
    @FXML
    private TextField DATE_ORDER;
    @FXML
    private TextField DATE_PROLONG;

    public ProgressBar progressBar;
    @FXML
    private MainApp mainApp;
    public Builder407Controller() {

    }
    @FXML
    private void initialize() {
        UnaryOperator<TextFormatter.Change> filter = change -> {
            String newText = change.getControlNewText();
            if (newText.matches("-?([1-9][0-9]*)?")) {
                return change;
            }
            return null;
        };
        DATE_MSG.setTextFormatter(new TextFormatter<Integer>(new IntegerStringConverter(), 0, filter));
        DATE_REQ.setTextFormatter(new TextFormatter<Integer>(new IntegerStringConverter(), 0, filter));
        DATE_GET_REQ.setTextFormatter(new TextFormatter<Integer>(new IntegerStringConverter(), 0, filter));
        DATE_ORDER.setTextFormatter(new TextFormatter<Integer>(new IntegerStringConverter(), 0, filter));
        DATE_PROLONG.setTextFormatter(new TextFormatter<Integer>(new IntegerStringConverter(), 0, filter));
    }

    @FXML
    private void handleInputPath() {
        String inputString = path.getText();
        Path inputPath = Path.of(inputString);
        Builder407model model = new Builder407model(
                Integer.parseInt(DATE_MSG.getText()),
                Integer.parseInt(DATE_REQ.getText()),
                Integer.parseInt(DATE_GET_REQ.getText()),
                Integer.parseInt(DATE_ORDER.getText()),
                Integer.parseInt(DATE_PROLONG.getText())
        );
        try {
            if (Files.exists(inputPath)) {
                TargetFolder folder = new TargetFolder(inputPath);
                Builder407.buildActualDate(folder.getTargetFilesList(), folder.getSecondFilesList(), model);
                textLog.setText("Актуализация КИДов выполнена");
            } else if ((path.getText() == null) || (path.getText().length() == 0)) {
                textLog.setText("Введите абсолютный путь или используйте поиск");
            } else {
                textLog.setText("Введённого каталога не существует");
            }

        } catch (IOException e) {
            e.getMessage();
        }
    }

    @FXML
    private void folderChoise() {
        DirectoryChooser directory = new DirectoryChooser();
        this.path.setText(directory.showDialog(mainApp.getPrimaryStage()).getAbsolutePath());
    }
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }
}

package sample.ui;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.net.MalformedURLException;


public class Control {


    @FXML
    private TextField authorization;

    @FXML
    private TextField authuser;

    @FXML
    private TextField cookie;

    @FXML
    private TextField delegationCon;

    @FXML
    private TextField pageid;

    @FXML
    private TextField referer;

    @FXML
    public ChoiceBox<String> setUserProfail;

    @FXML
    public ChoiceBox<String> chooseData;

    @FXML
    public Button open;

    @FXML
    public Button saveData;

    @FXML
    public Button setData;

    @FXML
    public Button deleteData;

    @FXML
    public Button openSetChannel;

    @FXML
    public Button btnInfo;

    @FXML
    private Label result;

    @FXML
    private Label textSubscriber;

    @FXML
    private Label textVideo;

    @FXML
    private Label textView;

    @FXML
    private ImageView arro;

    public void clearText() {
        authorization.clear();
        authuser.clear();
        delegationCon.clear();
        cookie.clear();
        referer.clear();
        pageid.clear();
    }

    public String getAuthuser() {
       return  authuser.getText();
    }

    public String getAuthorization() {
        return authorization.getText();
    }

    public String getPageid() {
        return pageid.getText();
    }

    public String getDelegationCon() {
        return delegationCon.getText();
    }

    public String getCook() {
        return cookie.getText();
    }

    public String getRefer() {
        return referer.getText();
    }

    public String getIdCh() {
        return referer.getText().substring(referer.getText().lastIndexOf('/')+1);
    }

    public void setSubscriber(String text) {
        textSubscriber.setText(text);
    }

    public void setView(String text) {
        textView.setText(text);
    }

    public void setVideo(String text) {
        textVideo.setText(text);
    }

    public void setResult(String text) {
        result.setText(text);
    }

    public void setArro(Image image) {
        arro.setImage(image);
    }

    public void setStyleBtn() throws MalformedURLException {
        open.setStyle("-fx-background-radius: 5em; " +
                        "-fx-min-width: 22px; " +
                        "-fx-min-height: 22px; " +
                        "-fx-max-width: 22px; " +
                        "-fx-max-height: 22px;");

        Image loginImage = new Image("file:settings.png");
        open.setGraphic(new ImageView(loginImage));
    }

//    public void setTextInsruction() {
//        textData.setText("Зайдіть на сайт\n'studio.youtube'\nВашого каналу,\nта посеред сторінки\nнатисніть на праву\nкнопку мишки,і\nвиберіть переглянути код та\nвиконайте наступні пункти:\n" +
//                "З'явившогося меню\nвиберіть розділ\n\"Network\" або Мережа та\nнатисніть на \"Fetch/XHR\";\n" +
//                "Оновіть сторінку і в колонці \"Name\" Ім'я шукаємо \"get_channel_dashboar?alt=Json&key\" натискаємо на текст;\n" +
//                "І в розділі \"Headers\" Заголовки  шукаємо заголовок \"Request Headers\" Заголовки запитів і шукаємо пункти як в колонок зліва в програмі");
//    }
}

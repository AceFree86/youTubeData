package sample;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.jetbrains.annotations.NotNull;
import sample.ui.Control;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;


public class Main extends Application {

    private Control control;
    private DataModel dModel;

    @Override
    public void start(@NotNull Stage primaryStage) throws Exception{
        dModel = new DataModel();
        FXMLLoader firstLoader = new FXMLLoader(getClass().getResource("ui/sample.fxml"));
        Parent first = firstLoader.load();
        Image loginImage = new Image("file:analytics.png");
        primaryStage.getIcons().add(loginImage);
        primaryStage.setTitle(seTitle());
        Scene scene = new Scene(first, 300, 150);

        primaryStage.setScene(scene);
        primaryStage.setResizable(false);

        control = firstLoader.getController();
        control.setStyleBtn();
        control.open.setOnAction(event -> {
            primaryStage.setScene(scene);
            primaryStage.setWidth(316);
            if (primaryStage.getHeight() == (189)) {
                primaryStage.setHeight(335);
            } else {
                primaryStage.setHeight(189);
            }
        });
        control.setUserProfail.setValue(dModel.getDate("Key", "API_KEY"));
        control.setUserProfail.getItems().addAll(dModel.getName());
        control.setUserProfail.getSelectionModel()
                .selectedItemProperty()
                .addListener((v, oldValue, newValue) -> {
                    dModel.deleteRow("Key");
                    dModel.insertBox(newValue, "Key", "API_KEY");
                });

        control.setData.setOnAction(event3 -> {
            if (!dModel.checkTablet("Key")) {
                infoDialog("Виберіть канал");
            } else {
                try {
                    runWorker();
                    primaryStage.setTitle(seTitle());
                    infoDialog("Канал вибрано");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        control.chooseData.getItems().addAll(dModel.getName());

        control.deleteData.setOnAction(event4 -> {
            if (control.chooseData.getValue()!= null) {
                try {
                    dModel.deleteOneRow("UserChannel", "Name", control.chooseData.getValue());
                    dModel.deleteOneRow("Key", "API_KEY", control.chooseData.getValue());
                    control.chooseData.getItems().clear();
                    control.chooseData.getItems().addAll(dModel.getName());
                    control.setUserProfail.getItems().clear();
                    control.setUserProfail.getItems().addAll(dModel.getName());
                    control.chooseData.setValue(null);
                    control.setUserProfail.setValue(null);
                    infoDialog("Канал видалено");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                infoDialog("Виберіть канал");
            }

        });
        control.openSetChannel.setOnAction(event5 -> {
            primaryStage.setScene(scene);
            primaryStage.setWidth(316);
            if (primaryStage.getHeight() == 335) {
                primaryStage.setHeight(577);
            } else {
                primaryStage.setHeight(335);
            }
        });
        control.saveData.setOnAction(event2 -> {
            if(control.getIdCh().equals("") && control.getAuthorization().equals("") &&
                    control.getCook().equals("") && control.getRefer().equals("") &&
                    control.getAuthuser().equals("") && control.getPageid().equals("") &&
                    control.getDelegationCon().equals("")) {
                infoDialog("Заповніть всі колонки");
            } else {
                try {
                    dModel.insertUserCh(new ListChannel(
                            -1,
                            new GetName().getName(control.getIdCh()),
                            control.getAuthorization(),
                            control.getCook(), control.getRefer(),
                            control.getAuthuser(), control.getPageid(),
                            control.getDelegationCon(),
                            control.getIdCh()));
                    control.clearText();
                    control.chooseData.getItems().clear();
                    control.chooseData.getItems().addAll(dModel.getName());
                    control.setUserProfail.getItems().clear();
                    control.setUserProfail.getItems().addAll(dModel.getName());
                    infoDialog("Канал збережено");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        control.btnInfo.setOnAction(event6 -> {
            dialog();
        });
        runWorker();

        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    private void runWorker() {
        Timer myTimer = new Timer(true);
        TimerTask myTask = new TimerTask() {
            @Override
            public void run() {
                runTask();
                System.out.println("runWorker");
            }
        };

        myTimer.scheduleAtFixedRate(myTask, 0L, 5 * (60 * 1000)); // Runs every 5 mins
    }

    private void runTask() {
        if (checkingInternet() && dModel.checkTablet("Key")) {
            DateFormat dateFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());
            String date = dateFormat.format(new Date());
            ArrayList<ListChannel> listChannel = dModel.getAllUser(dModel.getDate("Key", "API_KEY"));
            for (ListChannel channel : listChannel) {
                GetTask getTask = new GetTask(
                        channel.getAuthorization(),
                        channel.getCookie(),
                        channel.getReferer(),
                        channel.getAuthuser(),
                        channel.getPageid(),
                        channel.getDelegatcontext(),
                        channel.getIdChanel());
                getTask.valueProperty().addListener(new ChangeListener<>() {
                    @Override
                    public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                        System.out.println("run");
                        String[] str = t1.split("-");
                        control.setSubscriber(str[0]);
                        control.setView(str[1]);
                        control.setVideo(str[2]);
                        dModel.insert(date, "Work_dun", "DATE");
                        saveInformation(str, date);
                        control.setResult(String.format("Оновлено %1s", dModel.getDate("Work_dun", "DATE")));
                        control.setArro(setImage(str[0], dModel.getSubscriber()));
                    }
                });
                Thread th = new Thread(getTask);
                th.setDaemon(true);
                th.start();
            }
        } else {
            System.out.println("no Internet");
        }
    }

    private void saveInformation(String[] str, String date) {
        CompletableFuture.runAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(20);
                System.out.println(dModel.checkRow(str[0]));
                if (!dModel.checkRow(str[0])) {
                    dModel.deleteRow("Subscriber");
                    dModel.insertDB(new ListData(-1, str[0], date), "Subscriber");
                }
                dModel.insertDB(new ListData(-1, str[1], date), "View");
            } catch (InterruptedException e) {
                throw new IllegalStateException(e);
            }
            System.out.println("saveInformation");
        });
    }

    private Image setImage(String subscriber, String sub_db){
        Image up = null;
        int subs = Integer.parseInt(subscriber);
        int db_subs = Integer.parseInt(sub_db);

        if (db_subs < subs) {
            up = new Image("file:up.png");
        } else if (db_subs > subs) {
            up = new Image("file:down.png");
        } else {
            System.out.println("===");
        }
        return up;
    }

    private void infoDialog(String info) {
        Dialog<String> dialog = new Dialog<>();
        dialog.setTitle(seTitle());
        ButtonType type = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
        dialog.setContentText(info);
        dialog.getDialogPane().setMaxSize(100,100);
        dialog.getDialogPane().getButtonTypes().add(type);
        dialog.showAndWait();
    }

    private String seTitle() {
        String title = "";
        if (dModel.checkTablet("Key")){
            title = "" + dModel.getDate("Key", "API_KEY");
        } else {
            title = "Youtube Аналитика";
        }
        return title;
    }

    private boolean checkingInternet() {
        try {
            URL url = new URL("http://www.google.com");
            URLConnection connection = url.openConnection();
            connection.connect();
        } catch (IOException e) {
            return false;
        }
        return true;
    }

    private void dialog() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Інструкція");
        alert.setHeaderText("Зайдіть на сайт \"studio.youtube\" Вашого каналу,\nта посеред сторінки " +
                "натисніть на праву кнопку мишки та\nвиберіть переглянути код і виконайте наступні пункти:");
        alert.setContentText("1. З'явившогося меню виберіть розділ \"Network\" або\nМережа та натисніть на \"Fetch/XHR\";\n\n" +
                "2. Оновіть сторінку і в колонці \"Name\" Ім'я шукаємо \"get_channel_dashboar?alt=Json&key\" натискаємо на текст;\n\n" +
                "3. І в розділі \"Headers\" Заголовки  шукаємо заголовок \"Request Headers\" Заголовки запитів і шукаємо назви пунктів як в колонок зліва в програмі.");
        alert.showAndWait();
    }
}

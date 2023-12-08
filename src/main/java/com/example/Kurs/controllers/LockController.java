package com.example.Kurs.controllers;

import com.example.Kurs.StartApplication;
import com.example.Kurs.util.UserAuthentication;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.*;

import static com.example.Kurs.util.UserAuthentication.authenticateUser;
import static com.example.Kurs.util.UserAuthentication.hashPassword;

public class LockController {

    @FXML
    private TextField loginTextArea;

    @FXML
    private TextField passwdTextArea;

    @FXML
    private Text status;
    @FXML
    private ImageView catImage;

    @FXML
    private Button loginButton;


    @FXML
    void loginAction(ActionEvent event) throws IOException {
        //postgres 5446
        //petr passwd
        if(authenticateUser(loginTextArea.getText(), passwdTextArea.getText())) {
            UserAuthentication.setUserHibernateConfig(loginTextArea.getText(), passwdTextArea.getText());

            FXMLLoader fxmlLoader = new FXMLLoader(StartApplication.class.getResource("MainWindow.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 519, 375);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("Таблицы");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setResizable(false);

            Stage currentStage = (Stage) loginTextArea.getScene().getWindow();
            currentStage.close();

            stage.show();
        } else {
            status.setVisible(true);
            status.setText("Неверный логин или пароль!");
            status.setFill(Color.RED);
        }
    }

    @FXML
    private void initialize() {
        Image i = new Image(new File("src/main/resources/gifs/gif.gif").toURI().toString());
        catImage.setImage(i);
        status.setFill(Color.RED);
        status.setVisible(false);
        String path = "src/main/resources/hashs.txt";
        File userHashFile = new File(path);

        if(!UserAuthentication.initHibernateProperties()) {
            status.setVisible(true);
            status.setText("Не удалось загрузить файл hibernate.properties");
            loginButton.setDisable(true);
        }

//        adding user into hashs.txt
//        try (FileWriter writer = new FileWriter(path)) {
//            String text = "postgres | " + hashPassword("5446") + "\n" + "petr | " + hashPassword("passwd");
//            writer.write(text);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        if(userHashFile.exists()){
            try (BufferedReader reader = new BufferedReader(new FileReader(userHashFile))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] parts = line.split("\s\\|\s");
                    UserAuthentication.adduser(parts[0], parts[1]);
                }
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        } else {
            status.setVisible(true);
            status.setText("Не возможно найти файл по пути:\n" + path);
        }
    }


}
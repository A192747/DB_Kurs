package com.example.Kurs.controllers;

import com.example.Kurs.models.Auto;
import com.example.Kurs.models.AutoPersonnel;
import com.example.Kurs.models.Journal;
import com.example.Kurs.models.Routes;
import com.example.Kurs.util.UserAuthentication;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.io.File;
import java.sql.Timestamp;

public class AddController {

    private Configuration configuration = null;
    private SessionFactory sessionFactory= null;

    @FXML
    private Text error;

    @FXML
    private Text name1;

    @FXML
    private Text name11;

    @FXML
    private Text name12;

    @FXML
    private Text name13;

    @FXML
    private TextField nameTextArea;

    @FXML
    private TextField nameTextArea1;

    @FXML
    private TextField nameTextArea2;

    @FXML
    private TextField nameTextArea3;

    @FXML
    private ComboBox<String> tablesBox;

    @FXML
    private ImageView catImage;

    @FXML
    void addAction(ActionEvent event) {
        sessionFactory = configuration.buildSessionFactory();
        Session session = sessionFactory.getCurrentSession();

        boolean exit = true;
        try {
            session.beginTransaction();
            switch (tablesBox.getValue()) {
                case "Журнал" -> {
                    Auto auto = session.get(Auto.class, Integer.valueOf(nameTextArea2.getText()));
                    Routes route = session.get(Routes.class, Integer.valueOf(nameTextArea3.getText()));
                    Journal journal = new Journal(
                            Timestamp.valueOf(nameTextArea.getText()),
                            Timestamp.valueOf(nameTextArea1.getText()),
                            auto,
                            route
                            );

                    auto.getJournal().add(journal);
                    route.getJournal().add(journal);

                    session.save(journal);
                }
                case "Авто" -> {
                    AutoPersonnel autoPersonnel = session.get(AutoPersonnel.class, Integer.valueOf(nameTextArea3.getText()));
                    Auto auto = new Auto(
                            nameTextArea.getText(),
                            nameTextArea1.getText(),
                            nameTextArea2.getText(),
                            autoPersonnel
                    );

                    autoPersonnel.getCars().add(auto);
                    session.save(auto);
                }
                case "Персоналии" -> {
                    AutoPersonnel  autoPersonnel = new AutoPersonnel(
                            nameTextArea.getText(),
                            nameTextArea1.getText(),
                            nameTextArea2.getText()
                    );

                    session.save(autoPersonnel);
                }
                case "Дороги" -> {
                    Routes route = new Routes(nameTextArea.getText());
                    session.save(route);
                }
            }
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
            error.setVisible(true);
            error.setText("Ошибка! Откат транзакции");
            error.setFill(Color.RED);
            exit = false;
        } finally {
            sessionFactory.close();
        }
        if(exit) {
            Stage currentStage = (Stage) tablesBox.getScene().getWindow();
            currentStage.close();
        }
    }
    void setAllVisible(){
        name1.setVisible(true);
        name11.setVisible(true);
        name12.setVisible(true);
        name13.setVisible(true);
        nameTextArea.setVisible(true);
        nameTextArea1.setVisible(true);
        nameTextArea2.setVisible(true);
        nameTextArea3.setVisible(true);
    }
    @FXML
    void tablesBoxAction(ActionEvent event) {
        setAllVisible();
        error.setVisible(false);
        switch (tablesBox.getValue()) {
            case "Журнал" -> {
                name1.setText("Время выезда");
                name11.setText("Время приезда");
                name12.setText("id Авто");
                name13.setText("id Дороги");
            }
            case "Авто" -> {
                name1.setText("Номер");
                name11.setText("Цвет");
                name12.setText("Марка");
                name13.setText("id Персоны");
            }
            case "Персоналии" -> {
                name1.setText("Фамилия");
                name11.setText("Имя");
                name12.setText("Отчество");
                name13.setVisible(false);
                nameTextArea3.setVisible(false);
            }
            case "Дороги" -> {
                name1.setText("Название дороги");
                name11.setVisible(false);
                name12.setVisible(false);
                name13.setVisible(false);
                nameTextArea1.setVisible(false);
                nameTextArea2.setVisible(false);
                nameTextArea3.setVisible(false);
            }
        }
    }

    @FXML
    private void initialize() {
        //hibernate init
        configuration = new Configuration()
                .addAnnotatedClass(Journal.class)
                .addAnnotatedClass(Auto.class)
                .addAnnotatedClass(AutoPersonnel.class)
                .addAnnotatedClass(Routes.class);
        sessionFactory = configuration.setProperties(UserAuthentication.getHibernateConfig())
                .buildSessionFactory();
        //hibernate init end

        ObservableList<String> items = FXCollections.observableArrayList(
                "Журнал",
                "Авто",
                "Персоналии",
                "Дороги"
        );
        error.setVisible(false);
        tablesBox.setItems(items);
        tablesBox.setValue("Журнал");
        tablesBoxAction(new ActionEvent());

        Image i = new Image(new File("src/main/resources/gifs/catEars.gif").toURI().toString());
        catImage.setImage(i);

    }

}

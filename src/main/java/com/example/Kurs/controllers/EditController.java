package com.example.Kurs.controllers;

import com.example.Kurs.models.Auto;
import com.example.Kurs.models.AutoPersonnel;
import com.example.Kurs.models.Journal;
import com.example.Kurs.models.Routes;
import com.example.Kurs.util.HQLCreator;
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
import org.hibernate.query.Query;

import java.io.File;
import java.sql.Timestamp;
import java.util.List;

public class EditController {

    private Configuration configuration = null;
    private SessionFactory sessionFactory= null;

    @FXML
    private Text error;

    @FXML
    private Text name1;

    @FXML
    private Text name11;

    @FXML
    private Text name2;

    @FXML
    private Text name21;

    @FXML
    private Text name3;

    @FXML
    private Text name31;

    @FXML
    private Text name4;

    @FXML
    private Text name41;

    @FXML
    private TextField nameTextArea;

    @FXML
    private TextField nameTextArea01;

    @FXML
    private TextField nameTextArea1;

    @FXML
    private TextField nameTextArea11;

    @FXML
    private TextField nameTextArea21;

    @FXML
    private TextField nameTextArea31;

    @FXML
    private TextField nameTextArea2;

    @FXML
    private TextField nameTextArea3;

    @FXML
    private ComboBox<String> tablesBox;

    @FXML
    private ImageView catImage;

    @FXML
    void editAction(ActionEvent event) {
        sessionFactory = configuration.buildSessionFactory();
        Session session = sessionFactory.getCurrentSession();
        boolean exit = true;
        try {
            session.beginTransaction();
            boolean constValue0 = !nameTextArea01.getText().isEmpty();
            boolean constValue1 = !nameTextArea11.getText().isEmpty();
            boolean constValue2 = !nameTextArea21.getText().isEmpty();
            boolean constValue3 = !nameTextArea31.getText().isEmpty();
            switch (tablesBox.getValue()) {
                case "Журнал" -> {

                    Query query = session.createQuery(HQLCreator.getHQLJournal(
                            nameTextArea.getText(),
                            nameTextArea1.getText(),
                            nameTextArea2.getText(),
                            nameTextArea3.getText()
                    ));
                    List<Journal> results = query.list();

                    for(Journal journal : results) {
                        if (constValue0){
                            journal.setTime_out(Timestamp.valueOf(nameTextArea01.getText()));
                        }
                        if (constValue1){
                            journal.setTime_in(Timestamp.valueOf(nameTextArea11.getText()));
                        }
                        if (constValue2){
                            Auto auto = session.get(Auto.class, Integer.valueOf(nameTextArea21.getText()));
                            journal.setAuto(auto);
                            auto.getJournal().add(journal);
                        }
                        if(constValue3) {
                            Routes route = session.get(Routes.class, Integer.valueOf(nameTextArea31.getText()));
                            journal.setRoute(route);
                            route.getJournal().add(journal);
                        }
                    }
                }
                case "Авто" -> {
                    Query query = session.createQuery(HQLCreator.getHQLAuto(
                            nameTextArea.getText(),
                            nameTextArea1.getText(),
                            nameTextArea2.getText(),
                            nameTextArea3.getText()
                    ));
                    List<Auto> results = query.list();

                    for(Auto auto : results) {
                        if (constValue0){
                            auto.setNum(nameTextArea01.getText());
                        }
                        if (constValue1){
                            auto.setColor(nameTextArea11.getText());
                        }
                        if (constValue2){
                            auto.setMark(nameTextArea21.getText());
                        }
                        if(constValue3) {
                            AutoPersonnel personnel = session.get(AutoPersonnel.class, Integer.valueOf(nameTextArea31.getText()));
                            auto.setPerson(personnel);
                            personnel.getCars().add(auto);
                        }
                    }
                }
                case "Персоналии" -> {
                    Query query = session.createQuery(HQLCreator.getHQLAutoPersonnel(
                            nameTextArea.getText(),
                            nameTextArea1.getText(),
                            nameTextArea2.getText()
                    ));
                    List<AutoPersonnel> results = query.list();

                    for(AutoPersonnel autoPersonnel : results) {
                        if (constValue0){
                            autoPersonnel.setLast_name(nameTextArea01.getText());
                        }
                        if (constValue1){
                            autoPersonnel.setFirst_name(nameTextArea11.getText());
                        }
                        if (constValue2){
                            autoPersonnel.setParther_name(nameTextArea21.getText());
                        }
                    }
                }
                case "Дороги" -> {
                    Query query = session.createQuery(HQLCreator.getHQLRoutes(
                            nameTextArea.getText()
                    ));
                    List<Routes> results = query.list();
                    System.out.println(results);
                    for(Routes route : results) {
                        if (constValue0){
                            route.setName(nameTextArea01.getText());
                        }
                    }
                }
            }
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
            error.setVisible(true);
            error.setText(e.getMessage() + "\nОшибка! Откат транзакции");
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
        name2.setVisible(true);
        name3.setVisible(true);
        name4.setVisible(true);
        name11.setVisible(true);
        name21.setVisible(true);
        name31.setVisible(true);
        name41.setVisible(true);
        nameTextArea.setVisible(true);
        nameTextArea1.setVisible(true);
        nameTextArea2.setVisible(true);
        nameTextArea3.setVisible(true);
        nameTextArea01.setVisible(true);
        nameTextArea11.setVisible(true);
        nameTextArea21.setVisible(true);
        nameTextArea31.setVisible(true);
    }
    @FXML
    void tablesBoxAction(ActionEvent event) {
        setAllVisible();
        error.setVisible(false);
        switch (tablesBox.getValue()) {
            case "Журнал" -> {
                name1.setText("Время выезда");
                name2.setText("Время приезда");
                name3.setText("id Авто");
                name4.setText("id Дороги");
                name11.setText("Время выезда");
                name21.setText("Время приезда");
                name31.setText("id Авто");
                name41.setText("id Дороги");
            }
            case "Авто" -> {
                name1.setText("Номер");
                name2.setText("Цвет");
                name3.setText("Марка");
                name4.setText("id Персоны");
                name11.setText("Номер");
                name21.setText("Цвет");
                name31.setText("Марка");
                name41.setText("id Персоны");
            }
            case "Персоналии" -> {
                name1.setText("Фамилия");
                name2.setText("Имя");
                name3.setText("Отчество");
                name4.setVisible(false);
                nameTextArea3.setVisible(false);
                name11.setText("Фамилия");
                name21.setText("Имя");
                name31.setText("Отчество");
                name41.setVisible(false);
                nameTextArea31.setVisible(false);
            }
            case "Дороги" -> {
                name1.setText("Название дороги");
                name2.setVisible(false);
                name3.setVisible(false);
                name4.setVisible(false);
                nameTextArea1.setVisible(false);
                nameTextArea2.setVisible(false);
                nameTextArea3.setVisible(false);
                name11.setText("Название дороги");
                name21.setVisible(false);
                name31.setVisible(false);
                name41.setVisible(false);
                nameTextArea11.setVisible(false);
                nameTextArea21.setVisible(false);
                nameTextArea31.setVisible(false);
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

        Image i = new Image(new File("src/main/resources/gifs/cats.gif").toURI().toString());
        catImage.setImage(i);

    }

}

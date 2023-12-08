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
import java.util.List;

public class DeleteController {
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
    void deleteAction(ActionEvent event) {
        sessionFactory = configuration.buildSessionFactory();
        Session session = sessionFactory.getCurrentSession();
        boolean exit = true;
        try {
            session.beginTransaction();
            switch (tablesBox.getValue()) {
                case "Журнал" -> {
                    Query query = session.createQuery(HQLCreator.getHQLJournal(
                            nameTextArea.getText(),
                            nameTextArea1.getText(),
                            nameTextArea2.getText(),
                            nameTextArea3.getText()
                    ));
                    List<Journal> results = query.list();

                    for (Journal result : results)
                        session.remove(result);
                }
                case "Авто" -> {
                    Query query = session.createQuery(HQLCreator.getHQLAuto(
                            nameTextArea.getText(),
                            nameTextArea1.getText(),
                            nameTextArea2.getText(),
                            nameTextArea3.getText()
                    ));
                    List<Auto> results = query.list();
                    for (Auto result : results)
                        session.remove(result);
                }
                case "Персоналии" -> {
                    Query query = session.createQuery(HQLCreator.getHQLAutoPersonnel(
                            nameTextArea.getText(),
                            nameTextArea1.getText(),
                            nameTextArea2.getText()
                    ));
                    List<AutoPersonnel> results = query.list();
                    for (AutoPersonnel result : results)
                        session.remove(result);
                }
                case "Дороги" -> {
                    Query query = session.createQuery(HQLCreator.getHQLRoutes(
                            nameTextArea.getText()
                    ));
                    List<Routes> results = query.list();
                    for (Routes result : results)
                        session.remove(result);
                }
            }
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
            error.setVisible(true);
            error.setText(e.getMessage()+"\nОшибка! Откат транзакции");
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

        Image i = new Image(new File("src/main/resources/gifs/sadCat.gif").toURI().toString());
        catImage.setImage(i);

    }

}

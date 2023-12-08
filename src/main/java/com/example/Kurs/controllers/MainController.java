package com.example.Kurs.controllers;

import com.example.Kurs.StartApplication;
import com.example.Kurs.models.*;
import com.example.Kurs.util.UserAuthentication;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Screen;
import javafx.stage.Stage;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.util.List;


public class MainController {
    private Configuration configuration = null;
    private SessionFactory sessionFactory= null;

    @FXML
    private Button addButton;


    @FXML
    private MenuButton reportsMenu;

    @FXML
    private Button deleteButton;

    @FXML
    private Button editButton;

    @FXML
    private MenuButton catalogMenu;
    @FXML
    private TableView<Journal> table = new TableView<>();

    @FXML
    private TableView<Auto> table1 = new TableView<>();

    @FXML
    private TableView<AutoPersonnel> table2 = new TableView<>();

    @FXML
    private TableView<Routes> table3 = new TableView<>();

    private void setButtonValues(boolean value) {
        addButton.setDisable(value);
        editButton.setDisable(value);
        deleteButton.setDisable(value);
    }

    private void setTablesVision(boolean value){
        table.setVisible(value);
        table1.setVisible(value);
        table2.setVisible(value);
        table3.setVisible(value);
    }
    private void setButtonVision(boolean value){
        addButton.setVisible(value);
        editButton.setVisible(value);
        deleteButton.setVisible(value);
    }
    @FXML
    void addAction(ActionEvent event) throws IOException {
        setButtonValues(true);
        FXMLLoader fxmlLoader = new FXMLLoader(StartApplication.class.getResource("AddWindow.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 365, 367);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Добавление");
        //stage.initModality(Modality.APPLICATION_MODAL);
        stage.setResizable(false);
        stage.showAndWait();
//        journalsBoxAction(new ActionEvent());
        setButtonValues(false);
    }

    @FXML
    void deleteAction(ActionEvent event) throws IOException {
        setButtonValues(true);
        FXMLLoader fxmlLoader = new FXMLLoader(StartApplication.class.getResource("DeleteWindow.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 365, 367);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Удаление");
        stage.setResizable(false);
        stage.showAndWait();
//        journalsBoxAction(new ActionEvent());
        setButtonValues(false);
    }

    @FXML
    void editAction(ActionEvent event) throws IOException {
        setButtonValues(true);
        FXMLLoader fxmlLoader = new FXMLLoader(StartApplication.class.getResource("EditWindow.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 648, 365);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Редактирование");
        stage.setResizable(false);
        stage.showAndWait();
//        journalsBoxAction(new ActionEvent());
        setButtonValues(false);
    }

    private void initTablesColumns() {
        table.getColumns().clear();
        table1.getColumns().clear();
        table2.getColumns().clear();
        table3.getColumns().clear();
        {
            TableColumn<Journal, Integer> col1 = new TableColumn<>("id");
            col1.setCellValueFactory(new PropertyValueFactory<>("id"));

            TableColumn<Journal, Timestamp> col2 = new TableColumn<>("time_out");
            col2.setCellValueFactory(new PropertyValueFactory<>("time_out"));

            TableColumn<Journal, Timestamp> col3 = new TableColumn<>("time_in");
            col3.setCellValueFactory(new PropertyValueFactory<>("time_in"));

            TableColumn<Journal, Integer> col4 = new TableColumn<>("auto_id");
            col4.setCellValueFactory(new PropertyValueFactory<>("auto_id"));

            TableColumn<Journal, Integer> col5 = new TableColumn<>("route_id");
            col5.setCellValueFactory(new PropertyValueFactory<>("route_id"));

            table.getColumns().addAll(col1, col2, col3, col4, col5);
        }
        {
            TableColumn<Auto, Integer> col1 = new TableColumn<>("id");
            col1.setCellValueFactory(new PropertyValueFactory<>("id"));

            TableColumn<Auto, String> col2 = new TableColumn<>("num");
            col2.setCellValueFactory(new PropertyValueFactory<>("num"));

            TableColumn<Auto, String> col3 = new TableColumn<>("color");
            col3.setCellValueFactory(new PropertyValueFactory<>("color"));

            TableColumn<Auto, String> col4 = new TableColumn<>("mark");
            col4.setCellValueFactory(new PropertyValueFactory<>("mark"));

            TableColumn<Auto, Integer> col5 = new TableColumn<>("personnel_id");
            col5.setCellValueFactory(new PropertyValueFactory<>("personnel_id"));

            table1.getColumns().addAll(col1, col2, col3, col4, col5);
        }
        {
            TableColumn<AutoPersonnel, Integer> col1 = new TableColumn<>("id");
            col1.setCellValueFactory(new PropertyValueFactory<>("id"));

            TableColumn<AutoPersonnel, String> col2 = new TableColumn<>("first_name");
            col2.setCellValueFactory(new PropertyValueFactory<>("first_name"));

            TableColumn<AutoPersonnel, String> col3  = new TableColumn<>("last_name");
            col3.setCellValueFactory(new PropertyValueFactory<>("last_name"));

            TableColumn<AutoPersonnel, String> col4  = new TableColumn<>("parther_name");
            col4.setCellValueFactory(new PropertyValueFactory<>("parther_name"));

            table2.getColumns().addAll(col1, col2, col3, col4);
        }
        {
            TableColumn<Routes, Integer> col1 = new TableColumn<>("id");
            col1.setCellValueFactory(new PropertyValueFactory<>("id"));

            TableColumn<Routes, String> col2 = new TableColumn<>("name");
            col2.setCellValueFactory(new PropertyValueFactory<>("name"));

            table3.getColumns().addAll(col1, col2);

        }
    }
    @FXML
    public void journalAction(ActionEvent actionEvent) {
        setTablesVision(false);
        table.setVisible(true);
        addJournalIntoTable();
    }
    @FXML
    private ImageView catImage;

    private void addJournalIntoTable(){
        try {
            sessionFactory = configuration.buildSessionFactory();
            Session session = sessionFactory.getCurrentSession();
            session.beginTransaction();

            List<Journal> journals = session.createQuery("SELECT j FROM Journal j", Journal.class).getResultList();
            System.out.println(journals);

            ObservableList<Journal> data = FXCollections.observableArrayList();
            for (Journal journal : journals){
                data.add(journal);
            }
            table.setItems(data);
            session.getTransaction().commit();
        } finally {
            sessionFactory.close();
        }
    }
    private void addAutoIntoTable(){
        try {
            sessionFactory = configuration.buildSessionFactory();
            Session session = sessionFactory.getCurrentSession();
            session.beginTransaction();

            List<Auto> autos = session.createQuery("SELECT j FROM Auto j", Auto.class).getResultList();

            ObservableList<Auto> data = FXCollections.observableArrayList();

            for (Auto auto : autos){
                data.add(auto);
            }
            table1.setItems(data);
            session.getTransaction().commit();
        } finally {
            sessionFactory.close();
        }
    }
    private void addRoutesIntoTable(){
        try {
            sessionFactory = configuration.buildSessionFactory();
            Session session = sessionFactory.getCurrentSession();
            session.beginTransaction();


            List<Routes> routes = session.createQuery("SELECT j FROM Routes j", Routes.class)
                    .getResultList();

            ObservableList<Routes> data = FXCollections.observableArrayList();
            for (Routes autoPersonnel : routes){
                data.add(autoPersonnel);
            }

            table3.setItems(data);
            session.getTransaction().commit();

        } finally {
            sessionFactory.close();
        }
    }
    private void addAutoPersonnelIntoTable(){
        try {
            sessionFactory = configuration.buildSessionFactory();
            Session session = sessionFactory.getCurrentSession();
            session.beginTransaction();


            List<AutoPersonnel> autoPersonnels = session.createQuery("SELECT j FROM AutoPersonnel j", AutoPersonnel.class).getResultList();

            ObservableList<AutoPersonnel> data = FXCollections.observableArrayList();
            // Iterate through the ResultSet and add the data to the ObservableList
            for (AutoPersonnel autoPersonnel : autoPersonnels){
                data.add(autoPersonnel);
            }

            table2.setItems(data);
            session.getTransaction().commit();
        } finally {
            sessionFactory.close();
        }
    }
    private void addAllInfoIntoTables(){
        addAutoIntoTable();
        addJournalIntoTable();
        addRoutesIntoTable();
        addAutoPersonnelIntoTable();
    }

    private void getReport(Object objects) {
        try {
            sessionFactory = configuration.buildSessionFactory();
            Session session = sessionFactory.getCurrentSession();
            session.beginTransaction();
            String hql = "select d from " + objects.getClass().getSimpleName() + " d";
            List<? extends Object> driverViews = session.createQuery(hql, objects.getClass())
                    .getResultList();
            System.out.println(driverViews);
            PrintWriter writer = new PrintWriter("src/main/resources/reports/"+ objects.getClass().getSimpleName() + ".txt");
            writer.println(driverViews);
            writer.close();
            session.getTransaction().commit();
        } catch (FileNotFoundException ex) {
            throw new RuntimeException(ex);
        } finally {
            sessionFactory.close();
        }
    }
    @FXML
    private void initialize() {
        //hibernate init
        configuration = new Configuration()
                .addAnnotatedClass(Journal.class)
                .addAnnotatedClass(Auto.class)
                .addAnnotatedClass(AutoPersonnel.class)
                .addAnnotatedClass(Routes.class)
                .addAnnotatedClass(DriverView.class)
                .addAnnotatedClass(RoutesCarsView.class);
        sessionFactory = configuration.setProperties(UserAuthentication.getHibernateConfig())
                .buildSessionFactory();
        //hibernate init ENDED

        //report menu init
        reportsMenu.setText("Отчеты");
        reportsMenu.getItems().removeAll();
        reportsMenu.getItems().addAll(
                new MenuItem("Все водители"),
                new MenuItem("Все маршруты и кол-во автомобилей")
        );


        reportsMenu.getItems().get(0).setOnAction(e -> {
            getReport(new DriverView());
        });
        reportsMenu.getItems().get(1).setOnAction(e -> {
            getReport(new RoutesCarsView());
        });
        //report menu init ENDED

        //catalog menu init
        catalogMenu.setText("Справочники");
        catalogMenu.getItems().removeAll();
        catalogMenu.getItems().addAll(
                new MenuItem("Авто"),
                new MenuItem("Персонал"),
                new MenuItem("Маршруты")
        );
        catalogMenu.getItems().get(0).setOnAction(e -> {
            setTablesVision(false);
            table1.setVisible(true);
            addAutoIntoTable();
        });
        catalogMenu.getItems().get(1).setOnAction(e -> {
            setTablesVision(false);
            table2.setVisible(true);
            addAutoPersonnelIntoTable();

        });
        catalogMenu.getItems().get(2).setOnAction(e -> {
            setTablesVision(false);
            table3.setVisible(true);
            addRoutesIntoTable();
        });
        //catalog menu init ENDED


        setTablesVision(false);
        setButtonVision(true);

        table.setVisible(true);

        //tables init
        initTablesColumns();
        addAllInfoIntoTables();
        //tables init ENDED

        Image i = new Image(new File("src/main/resources/gifs/catSitting.gif").toURI().toString());
        catImage.setImage(i);
    }

}

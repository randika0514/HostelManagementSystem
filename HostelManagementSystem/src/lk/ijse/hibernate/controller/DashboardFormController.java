package lk.ijse.hibernate.controller;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import lk.ijse.hibernate.bo.BOFactory;
import lk.ijse.hibernate.bo.custom.ReservationBO;
import lk.ijse.hibernate.bo.custom.RoomBO;
import lk.ijse.hibernate.bo.custom.StudentBO;
import lk.ijse.hibernate.dao.DAOFactory;
import lk.ijse.hibernate.dao.custom.ReservationDAO;
import lk.ijse.hibernate.dao.custom.RoomDAO;
import lk.ijse.hibernate.dao.custom.StudentDAO;
import lk.ijse.hibernate.dto.ReservationDTO;
import lk.ijse.hibernate.dto.RoomDTO;
import lk.ijse.hibernate.dto.StudentDTO;
import lk.ijse.hibernate.entity.Room;
import lk.ijse.hibernate.entity.Student;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class DashboardFormController {
    public AnchorPane roomReservationContext;
    public TextField txtResId;
    public TextField txtDate;
    public ComboBox<String> cmbStudentId;
    public ComboBox<String> cmbRoomTypeId;
    public TableView tblReservation;
    public TableColumn colResId;
    public TableColumn colDate;
    public TableColumn colStudentId;
    public TableColumn colRoomTypeId;
    public TableColumn colStatus;
    public TextField txtName;
    public TextField txtTelNo;
    public TextField txtType;
    public TextField txtQty;
    public TextField txtKeyMoney;
    public ComboBox<String> cmbStatus;

    private final RoomDAO roomDAO = (RoomDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.ROOM);
    private final StudentDAO studentDAO = (StudentDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.STUDENT);
    private final StudentBO studentBO = (StudentBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.STUDENT);
    private final RoomBO roomBO = (RoomBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.ROOM);
    private final ReservationBO reservationBO = (ReservationBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.RESERVATION);
    private final ReservationDAO reservationDAO = (ReservationDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.RESERVATION);

    public void initialize(){

        try {
            txtResId.setText(reservationBO.generateReservationId());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        cmbStatus.getItems().add("Paid");
        cmbStatus.getItems().add("Not Paid");

        try {
            loadDataToComboBox();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        setDate();

    }


    private void setDate() {

        Timeline clock = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            SimpleDateFormat formatter2 = new SimpleDateFormat("dd/MM/yyyy");
            java.util.Date date = new java.util.Date();
            txtDate.setText(formatter2.format(date));
        }), new KeyFrame(Duration.seconds(1)));
        clock.setCycleCount(Animation.INDEFINITE);
        clock.play();
    }

    private void loadDataToComboBox() throws SQLException, IOException, ClassNotFoundException {

        ArrayList<RoomDTO> allRoom = roomBO.getAllRoom();
        ArrayList<StudentDTO> allStudent = studentBO.getAllStudent();

        for (RoomDTO roomDTO:allRoom
        ) {
            cmbRoomTypeId.getItems().add(roomDTO.getRoom_type_id());
        }

        for (StudentDTO studentDTO:allStudent
        ) {
            cmbStudentId.getItems().add(studentDTO.getStudent_id());
        }

    }

    public void btnRoomOnAction(ActionEvent actionEvent) throws IOException {

        URL resource = getClass().getResource("../view/RoomManageForm.fxml");
        Parent load = FXMLLoader.load(resource);
        Scene scene=new Scene(load);
        Stage stage=new Stage();
        stage.setScene(scene);
        stage.show();

    }

    public void btnRegisterOnAction(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../view/StudentManageForm.fxml");
        Parent load = FXMLLoader.load(resource);
        Scene scene=new Scene(load);
        Stage stage=new Stage();
        stage.setScene(scene);
        stage.show();
    }

    public void btnRegistrationOnAction(MouseEvent mouseEvent) {
    }


    public void reserveOnAction(ActionEvent actionEvent) {

        try {
            if(reservationBO.save(new ReservationDTO(txtResId.getText(), LocalDate.now(), cmbStudentId.getValue(), cmbRoomTypeId.getValue(),cmbStatus.getValue()))) {
                new Alert(Alert.AlertType.CONFIRMATION, "Reserved!").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Something Happened").show();
            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Something Happened").show();
        }

    }

    public void setRoomDetailsOnAction(ActionEvent actionEvent) throws SQLException, IOException, ClassNotFoundException {
        Room search = roomDAO.search(cmbRoomTypeId.getValue());
        txtType.setText(search.getType());
        txtQty.setText(String.valueOf(search.getQty()));
        txtKeyMoney.setText(search.getKey_money());
    }

    public void setStudentDetailsOnAction(ActionEvent actionEvent) throws SQLException, IOException, ClassNotFoundException {
        Student search = studentDAO.search(cmbStudentId.getValue());
        txtName.setText(search.getName());
        txtTelNo.setText(search.getContact_no());
    }
}

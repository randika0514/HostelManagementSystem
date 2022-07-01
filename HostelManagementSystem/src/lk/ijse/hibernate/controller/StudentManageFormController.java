package lk.ijse.hibernate.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.hibernate.bo.BOFactory;
import lk.ijse.hibernate.bo.custom.impl.StudentBOImpl;
import lk.ijse.hibernate.dao.DAOFactory;
import lk.ijse.hibernate.dao.custom.ReservationDAO;
import lk.ijse.hibernate.dao.custom.StudentDAO;
import lk.ijse.hibernate.dto.StudentDTO;
import lk.ijse.hibernate.entity.Student;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;

public class StudentManageFormController {
    public TextField txtStudentId;
    public TextField txtStudentName;
    public TextField txtStudentAddress;
    public TextField txtStudentContact;
    public TextField txtDob;
    public TextField txtGender;
    public AnchorPane stRegistrationContext;
    public DatePicker dpDateOfBirth;


    private final StudentDAO studentDAO = (StudentDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.STUDENT);


    StudentBOImpl studentBOImpl = (StudentBOImpl) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.STUDENT);

    public void btnUpdateOnAction(ActionEvent actionEvent) throws IOException {
        String id = txtStudentId.getText();
        String name = txtStudentName.getText();
        String address = txtStudentAddress.getText();
        String contact_no = txtStudentContact.getText();
        Date date = Date.valueOf(dpDateOfBirth.getValue());
        String gender = txtGender.getText();
        try {
            if(studentBOImpl.update(new StudentDTO(id, name, address, contact_no, date, gender))) {
                new Alert(Alert.AlertType.CONFIRMATION, "Updated.!").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Something Happened").show();
            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Something Happened").show();
        }
        Stage window = (Stage) stRegistrationContext.getScene().getWindow();
        window.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/StudentManageForm.fxml"))));
    }

    public void btnDeleteOnAction(ActionEvent actionEvent) throws IOException {
        String id = txtStudentId.getText();
        try {
            if (studentBOImpl.delete(id)) {
                new Alert(Alert.AlertType.CONFIRMATION, "Deleted!").show();
            }
        } catch (Exception e) {
            System.out.println(e);
            new Alert(Alert.AlertType.ERROR, "Something Happened. try again carefully!").showAndWait();
        }
        Stage window = (Stage) stRegistrationContext.getScene().getWindow();
        window.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/StudentManageForm.fxml"))));
    }

    public void btnRegisterOnAction(ActionEvent actionEvent) throws IOException {
        String id = txtStudentId.getText();
        String name = txtStudentName.getText();
        String address = txtStudentAddress.getText();
        String contact_no = txtStudentContact.getText();
        Date date = Date.valueOf(dpDateOfBirth.getValue());
        String gender = txtGender.getText();

        try {
            if (studentBOImpl.save(new StudentDTO(id, name, address, contact_no, date, gender))) {
                new Alert(Alert.AlertType.CONFIRMATION, "Registered.!").show();
            }
        } catch (Exception e) {
            System.out.println(e);
            new Alert(Alert.AlertType.ERROR, "Something Happened. try again carefully!").showAndWait();
        }
        Stage window = (Stage) stRegistrationContext.getScene().getWindow();
        window.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/StudentManageForm.fxml"))));
    }

    public void backOnAction(ActionEvent actionEvent) throws IOException {
/*
        registrationContext.getChildren().clear();
        Parent parent = FXMLLoader.load(getClass().getResource("../view/DashboardForm.fxml"));
        registrationContext.getChildren().add(parent);*/

    }

    public void studentSearchOnAction(ActionEvent actionEvent) throws SQLException, IOException, ClassNotFoundException {
        Student search = studentDAO.search(txtStudentId.getText());

        txtStudentId.setText(search.getStudent_id());
        txtStudentName.setText(search.getName());
        txtStudentAddress.setText(search.getAddress());
        txtStudentContact.setText(search.getContact_no());
        txtDob.setText(String.valueOf(search.getDob()));
        txtGender.setText(search.getGender());


    }

    public void txtDobClearOnAction(ActionEvent actionEvent) {
        txtDob.clear();
    }
}

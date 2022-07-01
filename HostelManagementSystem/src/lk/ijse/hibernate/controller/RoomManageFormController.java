package lk.ijse.hibernate.controller;

import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import lk.ijse.hibernate.bo.BOFactory;
import lk.ijse.hibernate.bo.custom.impl.RoomBOImpl;
import lk.ijse.hibernate.dao.DAOFactory;
import lk.ijse.hibernate.dao.custom.RoomDAO;
import lk.ijse.hibernate.dto.RoomDTO;
import lk.ijse.hibernate.entity.Room;

import java.io.IOException;
import java.sql.SQLException;

public class RoomManageFormController {
    public TextField txtRoomTypeId;
    public TextField txtKeyMoney;
    public TextField txtRoomType;
    public TextField txtRoomQty;
    public AnchorPane roomContext;

    RoomBOImpl roomBOImpl = (RoomBOImpl) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.ROOM);
    private final RoomDAO roomDAO = (RoomDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.ROOM);

    public void btnSaveOnAction(ActionEvent actionEvent) {
        String id = txtRoomTypeId.getText();
        String type = txtRoomType.getText();
        String key_money = txtKeyMoney.getText();
        int qty = Integer.parseInt(txtRoomQty.getText());

        try {
            if (roomBOImpl.save(new RoomDTO(id, type, key_money, qty))) {
                new Alert(Alert.AlertType.CONFIRMATION, "Save.!").show();
            }
        } catch (Exception e) {
            System.out.println(e);
            new Alert(Alert.AlertType.ERROR, "Something Happened. try again carefully!").showAndWait();
        }
    }

    public void btnRoomUpdateOnAction(ActionEvent actionEvent) {
        String id = txtRoomTypeId.getText();
        String type = txtRoomType.getText();
        String key_money = txtKeyMoney.getText();
        int qty = Integer.parseInt(txtRoomQty.getText());

        try {
            if (roomBOImpl.update(new RoomDTO(id, type, key_money, qty))) {
                new Alert(Alert.AlertType.CONFIRMATION, "Update.!").show();
            }
        } catch (Exception e) {
            System.out.println(e);
            new Alert(Alert.AlertType.ERROR, "Something Happened. try again carefully!").showAndWait();
        }
    }

    public void btnRoomDeleteOnAction(ActionEvent actionEvent) {
    }

    public void backOnAction(ActionEvent actionEvent) {
    }

    public void roomSearchOnAction(ActionEvent actionEvent) throws SQLException, IOException, ClassNotFoundException {
        Room search = roomDAO.search(txtRoomTypeId.getText());

        txtRoomTypeId.setText(search.getRoom_type_id());
        txtRoomType.setText(search.getType());
        txtKeyMoney.setText(search.getKey_money());
        txtRoomQty.setText(String.valueOf(search.getQty()));
    }
}

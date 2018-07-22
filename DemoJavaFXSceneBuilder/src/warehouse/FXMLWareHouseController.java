/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package warehouse;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.KeyCode;
import javafx.util.Callback;
import javafx.util.StringConverter;
import javafx.util.converter.IntegerStringConverter;
import tqduy.bean.DVT;
import tqduy.bean.LoaiNX;
import tqduy.bean.Nhap;
import tqduy.bean.NhapTable;
import tqduy.bean.Xuat;
import tqduy.bean.XuatTable;
import tqduy.connect.DBUtils_DVT;
import tqduy.connect.DBUtils_LoaiNX;
import tqduy.connect.DBUtils_Nhap;
import tqduy.connect.DBUtils_Xuat;

/**
 * FXML Controller class
 *
 * @author QuangDuy
 */
public class FXMLWareHouseController implements Initializable {
    
    @FXML
    private TableView tbNhap, tbXuat;
    
    @FXML
    private TableColumn<NhapTable, String> tbTenLoaiNhapColumn, tbDVTNhapColumn, tbDVTXuatColumn, tbTenLoaiXuatColumn;
    
    @FXML
    private TableColumn<NhapTable, String> tbTenSpNhapColumn;
    
    @FXML
    private TableColumn<NhapTable, Integer> tbDonGiaNhapColumn, tbSoLuongNhapColumn;
    
    @FXML
    private TableColumn<NhapTable, Date> tbNgayNhapColumn;
    
    @FXML
    private TableColumn<XuatTable, String> tbTenSpXuatColumn;
    
    @FXML
    private TableColumn<XuatTable, Integer> tbSoLuongXuatColumn;
    
    @FXML
    private TableColumn<XuatTable, Date> tbNgayXuatColumn;
    
    @FXML
    private TextField txtTenSpNhap, txtSoLuongNhap, txtDonGiaNhap, txtTenSpXuat, txtSoLuongXuat, txtNgayXuat;
    
    @FXML
    private DatePicker dpNgayNhap, dpNgayXuat;
    
    @FXML
    private ComboBox cbTenLoaiNhap, cbTenLoaiXuat;
    
    @FXML
    private Button btnNhap, btnXuat;
    
    private ArrayList<LoaiNX> arrLoai = DBUtils_LoaiNX.getList();
    private ArrayList<DVT> arrDVT = DBUtils_DVT.getList();
    private ObservableList<String> listLoai;
    private LocalDate dateNhap = LocalDate.now();
    private LocalDate dateXuat = LocalDate.now();
    private int soLuongNhap = 1, donGiaNhap = 1, countNhap = 0, soLuongXuat = 1, countXuat = 0;
    private String tenLoaiNhap = "", tenLoaiXuat = "";
    
    // Combobox
    private void showCombobox() {
        listLoai = FXCollections.observableArrayList();
        ObservableList<String> listDVT = FXCollections.observableArrayList();
        for (LoaiNX loaiNX : arrLoai) {
            String tenLoai = loaiNX.getTenLoaiNX();
            listLoai.add(tenLoai);
        }
        
        for (DVT dvt : arrDVT) {
            String tenDV = dvt.getDvt();
            listDVT.add(tenDV);
        }
        cbTenLoaiNhap.setItems(listLoai);
        cbTenLoaiXuat.setItems(listLoai);
    }
    
    private void checkInput() {
        // Box Nhap
        txtSoLuongNhap.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.matches("\\d*") && !newValue.equals("0")) {
                try {
                    soLuongNhap = Integer.parseInt(newValue);
                } catch (Exception e) {
                    
                }
            } else {
                txtSoLuongNhap.setText(oldValue);
            }
        });
        
        txtDonGiaNhap.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.matches("\\d*") && !newValue.equals("0")) {
                try {
                    donGiaNhap = Integer.parseInt(newValue);
                } catch (Exception e) {
                    
                }
            } else {
                txtDonGiaNhap.setText(oldValue);
            }
        });
        // End Box Nhap
        
        // Box Xuat
        txtSoLuongXuat.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.matches("\\d*") && !newValue.equals("0")) {
                try {
                    soLuongXuat = Integer.parseInt(newValue);
                } catch (Exception e) {
                    
                }
            } else {
                txtSoLuongXuat.setText(oldValue);
            }
        });
        // End Box Xuat
    }
    
    private void eventDatePicker() {
        dpNgayNhap.setValue(LocalDate.now());
        dpNgayXuat.setValue(LocalDate.now());
        
        StringConverter<LocalDate> converter = new StringConverter<LocalDate>() {
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            
            @Override
            public String toString(LocalDate date) {
                if (date != null) {
                    return dateFormatter.format(date);
                } else {
                    return "";
                }
            }
            @Override
            public LocalDate fromString(String string) {
                if (string != null && !string.isEmpty()) {
                    return LocalDate.parse(string, dateFormatter);
                } else {
                    return null;
                }
            }
        };
        dpNgayNhap.setConverter(converter);
        dpNgayNhap.setPromptText("dd-MM-yyyy");
        
        dpNgayXuat.setConverter(converter);
        dpNgayXuat.setPromptText("dd-MM-yyyy");
        
        dpNgayNhap.setOnAction((event) -> {
            dateNhap = dpNgayNhap.getValue();
        });
        
        dpNgayXuat.setOnAction((event) -> {
            dateXuat = dpNgayXuat.getValue();
        });
    }
    
    private void boxNhap() {
        // Box Nhap
        cbTenLoaiNhap.setOnAction((event) -> {
            tenLoaiNhap = cbTenLoaiNhap.getSelectionModel().getSelectedItem().toString();
            countNhap = cbTenLoaiNhap.getSelectionModel().getSelectedIndex();
            System.out.println("tenLoai: " + tenLoaiNhap);
            System.out.println("Test: " + arrLoai.get(countNhap).getTenLoaiNX() + " - ID: " + arrLoai.get(countNhap).getIdLoaiNX());
        });
        
        btnNhap.setOnAction((event) -> {
            String ten = txtTenSpNhap.getText().toString().trim();
            if(ten.isEmpty() || txtSoLuongNhap.getText().isEmpty() || txtDonGiaNhap.getText().isEmpty() || tenLoaiNhap.isEmpty()) {
                createAlert("Xin Nhập đầy đủ thông tin !!!");
            } else {
                DBUtils_Nhap.insert(ten, countNhap, donGiaNhap, soLuongNhap, dateNhap);
                System.out.println("Ten: " + ten + " - Số lượng: " + soLuongNhap + " - Đơn giá: " + donGiaNhap + " - Date: " + dateNhap + " - Loai: " + tenLoaiNhap);
                txtTenSpNhap.setText("");
                txtSoLuongNhap.setText("");
                txtDonGiaNhap.setText("");
                
                ObservableList<NhapTable> listNhapDisplay = getListNhapDisplay(DBUtils_Nhap.getList());
                tbNhap.getItems().clear();
                tbNhap.setItems(listNhapDisplay);
            }
        });
        // End Box Nhap
    }
    
    private void boxXuat() {
        // Box Xuat
        cbTenLoaiXuat.setOnAction((event) -> {
            tenLoaiXuat = cbTenLoaiXuat.getSelectionModel().getSelectedItem().toString();
            countXuat = cbTenLoaiXuat.getSelectionModel().getSelectedIndex();
            System.out.println("tenLoai: " + tenLoaiXuat);
            System.out.println("Test: " + arrLoai.get(countXuat).getTenLoaiNX() + " - ID: " + arrLoai.get(countXuat).getIdLoaiNX());
        });
        
        btnXuat.setOnAction((event) -> {
            String ten = txtTenSpXuat.getText().toString().trim();
            if(ten.isEmpty() || txtSoLuongXuat.getText().isEmpty() || tenLoaiXuat.isEmpty()) {
                createAlert("Xin Nhập đầy đủ thông tin !!!");
            } else {
                DBUtils_Xuat.insert(ten, countXuat, soLuongXuat, dateXuat);
                System.out.println("Ten: " + ten + " - Số lượng: " + soLuongXuat + " - Date: " + dateXuat + " - Loai: " + tenLoaiXuat);
                txtTenSpXuat.setText("");
                txtSoLuongXuat.setText("");
                
                ObservableList<XuatTable> listXuatDisplay = getListXuatDisplay(DBUtils_Xuat.getList());
                tbXuat.getItems().clear();
                tbXuat.setItems(listXuatDisplay);
            }
        });
        // End Box Xuat
    }
    
    private void eventClickButtonADD() {
        checkInput();
        
        eventDatePicker();
        
        boxNhap();
        boxXuat();
    }
    
    private void createAlert(String content) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Infomation");
        alert.setContentText(content);

        alert.getButtonTypes().setAll(ButtonType.OK, ButtonType.CANCEL);

        Optional<ButtonType> result = alert.showAndWait();
        
        if(result.get() == ButtonType.OK) {
        }

        System.out.println(result.get().getText());
    }
    // END Combobox Nhap
    
    // Box Xuat
    
    // Table
    private void createNameColumn() {
        // Table Nhap
        tbTenSpNhapColumn.setText("Tên sản phẩm");
        tbTenLoaiNhapColumn.setText("Loại");
        tbDVTNhapColumn.setText("Đơn vị tính");
        tbDonGiaNhapColumn.setText("Đơn giá nhập");
        tbSoLuongNhapColumn.setText("Số lượng nhập");
        tbNgayNhapColumn.setText("Ngày nhập");
        
        // Table Xuat
        tbTenSpXuatColumn.setText("Tên sản phẩm");
        tbTenLoaiXuatColumn.setText("Loại");
        tbDVTXuatColumn.setText("Đơn vị tính");
        tbSoLuongXuatColumn.setText("Số lượng xuất");
        tbNgayXuatColumn.setText("Ngày xuất");
    }
    
    private void setDataTable() {
        // Set data for table Nhap
        tbTenSpNhapColumn.setCellValueFactory(new PropertyValueFactory<>("tenSp"));
//        tbTenLoaiNhapColumn.setCellValueFactory(new PropertyValueFactory<>("tenLoai"));
        tbDVTNhapColumn.setCellValueFactory(new PropertyValueFactory<>("dvt"));
        tbDonGiaNhapColumn.setCellValueFactory(new PropertyValueFactory<>("donGia"));
        tbSoLuongNhapColumn.setCellValueFactory(new PropertyValueFactory<>("soLuong"));
        tbNgayNhapColumn.setCellValueFactory(new PropertyValueFactory<>("ngayNhap"));
        
        // Set data for table Xuat
        tbTenSpXuatColumn.setCellValueFactory(new PropertyValueFactory<>("tenSp"));
        tbTenLoaiXuatColumn.setCellValueFactory(new PropertyValueFactory<>("tenLoai"));
        tbDVTXuatColumn.setCellValueFactory(new PropertyValueFactory<>("dvt"));
        tbSoLuongXuatColumn.setCellValueFactory(new PropertyValueFactory<>("soLuong"));
        tbNgayXuatColumn.setCellValueFactory(new PropertyValueFactory<>("ngayXuat"));
    }
    
    private void createTable() {
        createNameColumn();
        
        setDataTable();
        
        tbNgayNhapColumn.setCellFactory((column) -> {
            TableCell<NhapTable, Date> cell = new TableCell<NhapTable, Date>() {
                private SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");

                @Override
                protected void updateItem(Date item, boolean empty) {
                    super.updateItem(item, empty);
                    if(empty) {
                        setText(null);
                    }
                    else {
                        setText(format.format(item));
                    }
                }
            };
            return cell; //To change body of generated lambdas, choose Tools | Templates.
        });
    }
    
    private void setEvent() {
        tbNhap.getColumns().clear();
        tbXuat.getColumns().clear();
        tbNhap.setEditable(true);
        tbXuat.setEditable(true);
        
        createTable();
        
        ArrayList<Nhap> listNhap = DBUtils_Nhap.getList();
        ArrayList<Xuat> listXuat = DBUtils_Xuat.getList();
        
        ObservableList<NhapTable> listNhapDisplay = getListNhapDisplay(listNhap);
        ObservableList<XuatTable> listXuatDisplay = getListXuatDisplay(listXuat);
        
        if(!listXuatDisplay.isEmpty() || !listNhapDisplay.isEmpty()) {
            tbNhap.setItems(listNhapDisplay);
            tbNhap.getColumns().addAll(tbTenSpNhapColumn, tbTenLoaiNhapColumn, tbDVTNhapColumn, tbDonGiaNhapColumn, tbSoLuongNhapColumn, tbNgayNhapColumn);
            
            tbXuat.setItems(listXuatDisplay);
            tbXuat.getColumns().addAll(tbTenSpXuatColumn, tbTenLoaiXuatColumn, tbDVTXuatColumn, tbSoLuongXuatColumn, tbNgayXuatColumn);
        }
    }
    
    private void setOnKeyPress() {
        tbNhap.setOnKeyPressed((event) -> {
            if(event.getCode() == KeyCode.DELETE) {
                deleteRowTableNhap();
            }
        });
        
        tbXuat.setOnKeyPressed((event) -> {
            if(event.getCode() == KeyCode.DELETE) {
                deleteRowTableXuat();
            }
        });
    }
    
    private void deleteRowTableXuat() {
        ObservableList<XuatTable> list = tbXuat.getSelectionModel().getSelectedItems();
        ArrayList<XuatTable> rows = new ArrayList<>(list);
        rows.forEach((row) -> {
            System.out.println("row.getIdMon(): " + row.getId());
            DBUtils_Xuat.delete(row.getId());
            
            ArrayList<Xuat> listXuat = DBUtils_Xuat.getList();
            ObservableList<XuatTable> listXuatpDisplay = getListXuatDisplay(listXuat);
            
            tbXuat.getItems().clear();
            tbXuat.setItems(listXuatpDisplay);
        });
    }
    
    private void deleteRowTableNhap() {
        ObservableList<NhapTable> list = tbNhap.getSelectionModel().getSelectedItems();
        ArrayList<NhapTable> rows = new ArrayList<>(list);
        rows.forEach((row) -> {
            System.out.println("row.getIdMon(): " + row.getId());
            DBUtils_Nhap.delete(row.getId());
            
            ArrayList<Nhap> listNhap = DBUtils_Nhap.getList();
            ObservableList<NhapTable> listNhapDisplay = getListNhapDisplay(listNhap);
            
            tbNhap.getItems().clear();
            tbNhap.setItems(listNhapDisplay);
        });
    }
    
    private ObservableList<NhapTable> getListNhapDisplay(ArrayList<Nhap> listNhap) {
        ObservableList<NhapTable> list = FXCollections.observableArrayList();
        
        for (Nhap nhap : listNhap) {
            int id = nhap.getIdNhap();
            String tenSp = nhap.getTenSpNhap();
            int donGia = nhap.getDonGia();
            int soLuong = nhap.getSoLuong();
            Date ngayNhap = nhap.getNgayNhap();
            int idLoai = nhap.getIdLoaiNX();
            String tenLoai = "";
            String tenDV = "";
            for (LoaiNX loaiNX : arrLoai) {
                if(idLoai == loaiNX.getIdLoaiNX()) {
                    tenLoai = loaiNX.getTenLoaiNX();
                    int idDVT = loaiNX.getIdDVT();
                    
                    for(DVT dvt : arrDVT) {
                        if(idDVT == dvt.getIdDVT()) {
                            tenDV = dvt.getDvt();
                            break;
                        }
                    }
                    break;
                }
            }
            NhapTable nb = new NhapTable(id, tenSp, tenLoai, tenDV, donGia, soLuong, ngayNhap);
            System.out.println(nb);
            list.add(nb);
        }
        return list;
    }
    
    private ObservableList<XuatTable> getListXuatDisplay(ArrayList<Xuat> listXuat) {
        ObservableList<XuatTable> list = FXCollections.observableArrayList();
        
        for (Xuat xuat : listXuat) {
            int id = xuat.getIdXuat();
            String tenSp = xuat.getTenSpXuat();
            int soLuong = xuat.getSoLuong();
            Date ngayXuat = xuat.getNgayXuat();
            int idLoai = xuat.getIdLoaiNX();
            String tenLoai = "", tenDVT = "";
            for (LoaiNX loaiNX : arrLoai) {
                if(idLoai == loaiNX.getIdLoaiNX()) {
                    tenLoai = loaiNX.getTenLoaiNX();
                    int idDVT = loaiNX.getIdDVT();
                    
                    for(DVT dvt : arrDVT) {
                        if(idDVT == dvt.getIdDVT()) {
                            tenDVT = dvt.getDvt();
                            break;
                        }
                    }
                    break;
                }
            }
            XuatTable xb = new XuatTable(id, tenSp, tenLoai, tenDVT, soLuong, ngayXuat);
            System.out.println(xb);
            list.add(xb);
        }
        return list;
    }
    // End Table

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        tbNhap.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        tbXuat.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        setEvent();
        showCombobox();
        eventClickButtonADD();
        setOnKeyPress();
    }    
    
}

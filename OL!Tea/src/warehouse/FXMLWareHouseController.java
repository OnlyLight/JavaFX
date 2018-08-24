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
import java.util.Calendar;
import java.util.Date;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.Tab;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.util.StringConverter;
import tqduy.bean.DVT;
import tqduy.bean.LoaiNX;
import tqduy.bean.Nhap;
import tqduy.bean.NhapTable;
import tqduy.bean.TKTable;
import tqduy.bean.Xuat;
import tqduy.bean.XuatTable;
import tqduy.connect.DBUtils_DVT;
import tqduy.connect.DBUtils_LoaiNX;
import tqduy.connect.DBUtils_Nhap;
import tqduy.connect.DBUtils_TK;
import tqduy.connect.DBUtils_Xuat;

/**
 * FXML Controller class
 *
 * @author QuangDuy
 */
public class FXMLWareHouseController implements Initializable {
    
    @FXML private TableView tbNhap, tbXuat;
    @FXML private TableColumn<NhapTable, String> tbTenLoaiNhapColumn, tbDVTNhapColumn, tbDVTXuatColumn, tbTenLoaiXuatColumn;
    @FXML private TableColumn<NhapTable, String> tbTenSpNhapColumn;
    @FXML private TableColumn<NhapTable, Integer> tbDonGiaNhapColumn, tbSoLuongNhapColumn;
    @FXML private TableColumn<NhapTable, Date> tbNgayNhapColumn;
    @FXML private TableColumn<XuatTable, String> tbTenSpXuatColumn;
    @FXML private TableColumn<XuatTable, Integer> tbSoLuongXuatColumn;
    @FXML private TableColumn<XuatTable, Date> tbNgayXuatColumn;
    @FXML private TextField txtTenSpNhap, txtSoLuongNhap, txtDonGiaNhap, txtSoLuongXuat;
    @FXML private DatePicker dpNgayNhap, dpNgayXuat, dpDayStartNhap, dpDayFinishNhap, dpDayStartXuat, dpDayFinishXuat;
    @FXML private ComboBox<LoaiNX> cbTenLoaiNhap, cbTenLoaiXuat, cbLoaiThongKe;
    @FXML private ComboBox<String> cbTenSpXuat;
    @FXML private Button btnNhap, btnXuat, btnFind, btnPrintInfo, btnReset;
    @FXML private TableView<TKTable> tbThongKe;
    @FXML private TableColumn<TKTable, String> tbTenSpTKColumn, tbLoaiTKColumn;
    @FXML private TableColumn<TKTable, Integer> tbDonGiaTKColumn, tbSoLuongNhapTKColumn, tbSoLuongXuatTKColumn;
    @FXML private TableColumn<TKTable, Date> tbNgayNhapTKColumn, tbNgayXuatTKColumn;
    @FXML private Tab tabTK;
    @FXML private BarChart<String, Number> bcThongKe;
    @FXML private CategoryAxis xAxis;
    @FXML private NumberAxis yAxis;
    
    private ArrayList<LoaiNX> arrLoai = DBUtils_LoaiNX.getList();
    private ArrayList<DVT> arrDVT = DBUtils_DVT.getList();
    private ObservableList<String> listLoai;
    private LocalDate dateNhap = LocalDate.now();
    private LocalDate dateXuat = LocalDate.now();
    private LocalDate dayStartNhap, dayFinishNhap, dayStartXuat, dayFinishXuat;
    private int soLuongNhap = 1, donGiaNhap = 1, countNhap = 0, countTK = 0, soLuongXuat = 1, countXuat = 0;
    private LoaiNX tenLoaiNhap = new LoaiNX(), tenLoaiXuat = new LoaiNX(), loaiTK = new LoaiNX();
    private String tenSpXuat = "";
    
    private void displayBarChart() {
        int year = Calendar.getInstance().get(Calendar.YEAR); // GET CURRENT YEAR
        System.out.println("year: " +year);
        
        tabTK.setOnSelectionChanged((event) -> {
            ObservableList<TKTable> list = FXCollections.observableArrayList(DBUtils_TK.getList());
            if(!list.isEmpty()) {
                tbThongKe.getItems().clear();
                tbThongKe.setItems(list);
            }
        });
        
        xAxis.setLabel("Year");
        yAxis.setLabel("Data");
        
        XYChart.Series<String, Number> dataSeries1 = new XYChart.Series<>();
        dataSeries1.setName("Nhập");
        
        XYChart.Series<String, Number> dataSeries2 = new XYChart.Series<>();
        dataSeries2.setName("Xuất");

        for(int i = year - 2; i <= year; i++) {
            dataSeries1.getData().add(new XYChart.Data<String, Number>(""+i+"", DBUtils_Nhap.getNhap(i)));
            dataSeries2.getData().add(new XYChart.Data<String, Number>(""+i+"", DBUtils_Xuat.getXuat(i)));
        }
        
        bcThongKe.getData().add(dataSeries1);
        bcThongKe.getData().add(dataSeries2);
        
        bcThongKe.setTitle("Thống Kê Nhập Xuất");
        System.out.println("BarChart");
    }
    
    // Set event for Tab Thong ke
    private void eventClickTK() {
        btnReset.setOnAction((event) -> {
            ObservableList<TKTable> list = FXCollections.observableArrayList(DBUtils_TK.getList());
            if(!list.isEmpty()) {
                tbThongKe.getItems().clear();
                tbThongKe.setItems(list);
            }
        });
        
        btnPrintInfo.setOnAction((event) -> {
            createAlert("Printing....");
        });
    }
    
    private void eventSearch() {
        cbLoaiThongKe.setOnAction((event) -> {
            loaiTK = cbLoaiThongKe.getSelectionModel().getSelectedItem();
            System.out.println(loaiTK);
        });
        
        btnFind.setOnAction((event) -> {
            System.out.println("Result[dayStartNhap: " + dayStartNhap + " - dayFinishNhap: " + dayFinishNhap + " - dayStartXuat: " + dayStartXuat + " - dayFinishXuat: " + dayFinishXuat);
            ObservableList<TKTable> list = FXCollections.observableArrayList(DBUtils_TK.searchTK(dayStartNhap, dayFinishNhap, dayStartXuat, dayFinishXuat, loaiTK.getTenLoaiNX()));
            if(!list.isEmpty()) {
                tbThongKe.getItems().clear();
                tbThongKe.setItems(list);
            }
        });
    }
    // End Set event for Tab Thong ke
    
    // Combobox
    private void loadCombobox() {
        ArrayList<Nhap> arrNhap = DBUtils_Nhap.getList();
        ArrayList<String> copy = new ArrayList<>();
        ObservableList<String> list = FXCollections.observableArrayList();
        
        for (Nhap nhap : arrNhap) {
            String ten = nhap.getTenSpNhap();
            copy.add(ten);
        }
        System.out.println("arrCopy1: "+ copy.toString());
        
        for(int i = 0; i < arrNhap.size(); i++) {
            int count = 0;
            for(int j = 0; j < copy.size(); j++) {
                if(arrNhap.get(i).getTenSpNhap().equals(copy.get(j))) {
                    count++;
                    if(count >= 2) {
                        copy.remove(j);
                        break;
                    }
                }
            }
        }
        System.out.println("arrCopy2: "+ copy.toString());
        
        list.addAll(copy);
        if(!list.isEmpty()) cbTenSpXuat.setItems(list);
    }
    
    private void showComboboxTenXuat() {
        cbTenSpXuat.setOnMouseClicked((event) -> {
            cbTenSpXuat.getItems().clear();
            loadCombobox();
        });
        
        cbTenSpXuat.setOnAction((event) -> {
            tenSpXuat = cbTenSpXuat.getSelectionModel().getSelectedItem();
            System.out.println("Ten sp Xuat: " + tenSpXuat);
            
            if(tenSpXuat != null) {
                ObservableList<LoaiNX> loai = FXCollections.observableArrayList();
                for (LoaiNX loaiNX : DBUtils_LoaiNX.getListForName(tenSpXuat)) {
                    loai.add(loaiNX);
                }

                if(!loai.isEmpty()) {
                    cbTenLoaiXuat.getItems().clear();
                    cbTenLoaiXuat.setItems(loai);
                }
            }
            showBoxNhap();
        });
    }
    
    private void showBoxNhap() {
        ObservableList<LoaiNX> loai = FXCollections.observableArrayList();
        for (LoaiNX loaiNX : arrLoai) {
            loai.add(loaiNX);
            System.out.println(loaiNX);
        }
        if(!loai.isEmpty()) cbTenLoaiNhap.setItems(loai);
    }
    
    private void showCombobox() {
        ObservableList<LoaiNX> loai = FXCollections.observableArrayList();
        ObservableList<String> listDVT = FXCollections.observableArrayList();
        for (LoaiNX loaiNX : arrLoai) {
            loai.add(loaiNX);
        }
        
        for (DVT dvt : arrDVT) {
            String tenDV = dvt.getDvt();
            listDVT.add(tenDV);
        }
        
        showBoxNhap();
        cbTenLoaiXuat.setItems(loai);
        cbLoaiThongKe.setItems(loai);
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
        
        fomatterForDatePicker(dpNgayNhap);
        fomatterForDatePicker(dpNgayXuat);
        fomatterForDatePicker(dpDayStartNhap);
        fomatterForDatePicker(dpDayFinishNhap);
        fomatterForDatePicker(dpDayStartXuat);
        fomatterForDatePicker(dpDayFinishXuat);
        
        dpNgayNhap.setOnAction((event) -> {
            dateNhap = dpNgayNhap.getValue();
        });
        
        dpNgayXuat.setOnAction((event) -> {
            dateXuat = dpNgayXuat.getValue();
        });
        
        dpDayStartNhap.setOnAction((event) -> {
            dayStartNhap = dpDayStartNhap.getValue();
        });
        
        dpDayFinishNhap.setOnAction((event) -> {
            dayFinishNhap = dpDayFinishNhap.getValue();
        });
        
        dpDayStartXuat.setOnAction((event) -> {
            dayStartXuat = dpDayStartXuat.getValue();
        });
        
        dpDayFinishXuat.setOnAction((event) -> {
            dayFinishXuat = dpDayFinishXuat.getValue();
        });
    }
    
    private void fomatterForDatePicker(DatePicker datePicker) {
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
        
        datePicker.setConverter(converter);
        datePicker.setPromptText("dd-MM-yyyy");
    }
    
    private void boxNhap() {
        // Box Nhap
        cbTenLoaiNhap.setOnAction((event) -> {
            tenLoaiNhap = cbTenLoaiNhap.getSelectionModel().getSelectedItem();
            System.out.println(tenLoaiNhap);
        });
        
        btnNhap.setOnAction((event) -> {
            String ten = txtTenSpNhap.getText().toString().trim();
            if(ten.isEmpty() || txtSoLuongNhap.getText().isEmpty() || txtDonGiaNhap.getText().isEmpty() || tenLoaiNhap == null) {
                createAlert("Xin Nhập đầy đủ thông tin !!!");
            } else {
                DBUtils_Nhap.insert(ten, tenLoaiNhap.getIdLoaiNX(), donGiaNhap, soLuongNhap, dateNhap);
                System.out.println("Ten: " + ten + " - Số lượng: " + soLuongNhap + " - Đơn giá: " + donGiaNhap + " - Date: " + dateNhap + " - Loai: " + tenLoaiNhap);
                txtTenSpNhap.setText("");
                txtSoLuongNhap.setText("");
                txtDonGiaNhap.setText("");
                
                ObservableList<NhapTable> listNhapDisplay = getListNhapDisplay(DBUtils_Nhap.getList());
                if(!listNhapDisplay.isEmpty()) {
                    tbNhap.getItems().clear();
                    tbNhap.setItems(listNhapDisplay);
                }
            }
        });
        // End Box Nhap
    }
    
    private void boxXuat() {
        // Box Xuat
        cbTenLoaiXuat.setOnAction((event) -> {
            tenLoaiXuat = cbTenLoaiXuat.getSelectionModel().getSelectedItem();
            System.out.println(tenLoaiXuat);
        });
        
        btnXuat.setOnAction((event) -> {
            if(tenSpXuat.isEmpty() || txtSoLuongXuat.getText().toString().trim().isEmpty() || tenLoaiXuat == null) {
                createAlert("Xin Nhập đầy đủ thông tin !!!");
            } else {
                DBUtils_Xuat.insert(tenSpXuat, tenLoaiXuat.getIdLoaiNX(), soLuongXuat, dateXuat);
                txtSoLuongXuat.setText("");
                
                ObservableList<XuatTable> listXuatDisplay = getListXuatDisplay(DBUtils_Xuat.getList());
                if(!listXuatDisplay.isEmpty()) {
                    tbXuat.getItems().clear();
                    tbXuat.setItems(listXuatDisplay);
                }
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
        
        // Table Thong Ke
        tbTenSpTKColumn.setText("Tên SP");
        tbDonGiaTKColumn.setText("Đơn Giá");
        tbSoLuongNhapTKColumn.setText("Số lượng nhập");
        tbNgayNhapTKColumn.setText("Ngày Nhập");
        tbSoLuongXuatTKColumn.setText("Số lượng xuất");
        tbNgayXuatTKColumn.setText("Ngày xuất");
        tbLoaiTKColumn.setText("Loại");
    }
    
    private void setDataTable() {
        // Set data for table Nhap
        tbTenSpNhapColumn.setCellValueFactory(new PropertyValueFactory<>("tenSp"));
        tbTenLoaiNhapColumn.setCellValueFactory(new PropertyValueFactory<>("tenLoai"));
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
        
        // Set data for Table Thong Ke
        tbTenSpTKColumn.setCellValueFactory(new PropertyValueFactory<>("tenSp"));
        tbDonGiaTKColumn.setCellValueFactory(new PropertyValueFactory<>("donGia"));
        tbSoLuongNhapTKColumn.setCellValueFactory(new PropertyValueFactory<>("soLuongNhap"));
        tbNgayNhapTKColumn.setCellValueFactory(new PropertyValueFactory<>("ngayNhap"));
        tbSoLuongXuatTKColumn.setCellValueFactory(new PropertyValueFactory<>("soLuongXuat"));
        tbNgayXuatTKColumn.setCellValueFactory(new PropertyValueFactory<>("ngayXuat"));
        tbLoaiTKColumn.setCellValueFactory(new PropertyValueFactory<>("tenLoai"));
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
        
        tbNgayXuatColumn.setCellFactory((column) -> {
            TableCell<XuatTable, Date> cell = new TableCell<XuatTable, Date>() {
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
        
        tbNgayNhapTKColumn.setCellFactory((column) -> {
            TableCell<TKTable, Date> cell = new TableCell<TKTable, Date>() {
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
        
        tbNgayXuatTKColumn.setCellFactory((column) -> {
            TableCell<TKTable, Date> cell = new TableCell<TKTable, Date>() {
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
        tbThongKe.getColumns().clear();
        tbNhap.setEditable(true);
        tbXuat.setEditable(true);
        
        createTable();
        
        ArrayList<Nhap> listNhap = DBUtils_Nhap.getList();
        ArrayList<Xuat> listXuat = DBUtils_Xuat.getList();
        
        ObservableList<NhapTable> listNhapDisplay = getListNhapDisplay(listNhap);
        ObservableList<XuatTable> listXuatDisplay = getListXuatDisplay(listXuat);
        
        
        if(!listXuatDisplay.isEmpty() && !listNhapDisplay.isEmpty()) {
            tbNhap.setItems(listNhapDisplay);
            tbNhap.getColumns().addAll(tbTenSpNhapColumn, tbTenLoaiNhapColumn, tbDVTNhapColumn, tbDonGiaNhapColumn, tbSoLuongNhapColumn, tbNgayNhapColumn);
            
            tbXuat.setItems(listXuatDisplay);
            tbXuat.getColumns().addAll(tbTenSpXuatColumn, tbTenLoaiXuatColumn, tbDVTXuatColumn, tbSoLuongXuatColumn, tbNgayXuatColumn);
        }
        
        tbThongKe.getColumns().addAll(tbTenSpTKColumn, tbDonGiaTKColumn, tbSoLuongNhapTKColumn, tbNgayNhapTKColumn, tbSoLuongXuatTKColumn, tbNgayXuatTKColumn, tbLoaiTKColumn);
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
        eventSearch();
        eventClickTK();
        displayBarChart();
        showComboboxTenXuat();
    }    
    
}

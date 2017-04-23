package smartclimat;

import Model.DataBean;
import com.jfoenix.controls.JFXButton;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ArrayList;
import java.util.*;
import java.util.ResourceBundle;
import java.util.logging.*;
import javafx.collections.*;
import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import smartclimat.DonneeClasse.*;
import Model.Model;
import com.jfoenix.controls.*;
import com.sun.deploy.config.JREInfo;
import static com.sun.deploy.config.JREInfo.initialize;
import java.io.File;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.*;
import javafx.event.EventHandler;
import javafx.scene.chart.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javax.swing.*;

/**
 *
 * @author ferhat-mounir
 */
public class FXMLDocumentController implements Initializable {
    private Model m;
    String s = null;
    ObservableList<DataBean> listeTab;

    
    @FXML
    private JFXComboBox<String> comville;

    @FXML
    private JFXButton visualiser, comparer;

    @FXML
    private JFXDatePicker date, date2;

    @FXML
    private TableColumn<DataBean, String> temperature, nebulosite, Humidite;

    @FXML
    private LineChart<DataBean, String> humm, tempe, nubb;
    @FXML
    private TableView<DataBean> tablis;

    @FXML
    private ToggleGroup temps;

    @FXML
    private JFXRadioButton btnjour;

    @FXML
    private TreeView<String> jtreee;

    @FXML
    private Label nomStat;
    @FXML
    private Label dateR;

    @FXML
    void remplirGraphe(ActionEvent event) {
        if (comville.getSelectionModel().getSelectedItem() == null) {
            JOptionPane.showMessageDialog(null, "Séléctionner une Ville", null, JOptionPane.WARNING_MESSAGE);
        }
        m = new Model();
        String po = Integer.toString(m.getIdStationForName(comville.getSelectionModel().getSelectedItem()));
        String dat = date.getValue().toString();
        nomStat.setText(comville.getSelectionModel().getSelectedItem());
        dateR.setText(dat.toString());
        try {
            m.getDonneeAnnee(po, dat);
        } catch (IOException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
        remplirDateGrapheeAndTab();
    }

    private ObservableList<String> obs;
    private List<String> p = new ArrayList<>();

    private List<Float> rem = new ArrayList<>();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        remplirCom();
        m = new Model();
        try {
            m.getDonneeAnnee("07037", "2017-02-02");
        } catch (IOException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
        m.displayReleve("07037", "02/02/2017");

        //*****************************   Interface *******************//   
        btnjour.setToggleGroup(temps);
        btnjour.setSelected(true);

        date2.setDisable(true);
        visualiser.setDisable(true);
        comparer.setDisable(true);
        date.setOnMouseClicked(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent me) {
                visualiser.setDisable(false);
                date2.setDisable(false);
                comparer.setDisable(true);
            }
        });
        date2.setOnMouseClicked(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent me) {
                comparer.setDisable(false);
                visualiser.setDisable(true);
            }
        });
        //***************************************************************************// 

    }

    public void remplirCom() {

        Model m = new Model();
        for (Station mo : m.getListeStation().values()) {
            p.add(mo.getNom());
        }
        obs = FXCollections.observableArrayList(p);
        comville.setItems(obs);

    }

    public void remplirDateGrapheeAndTab() {

        tempe.setTitle("Température " + date.getValue().toString());
        humm.setTitle("Humidité " + date.getValue().toString());
        nubb.setTitle("Nébulosité " + date.getValue().toString());

        XYChart.Series series = new XYChart.Series();
        XYChart.Series seriesn = new XYChart.Series();
        XYChart.Series seriesh = new XYChart.Series();

        listeTab = FXCollections.observableArrayList();

        tempe.getData().addAll(series);
        humm.getData().addAll(seriesh);
        nubb.getData().addAll(seriesn);

        Humidite.setCellValueFactory(new PropertyValueFactory<DataBean, String>("humi"));
        nebulosite.setCellValueFactory(new PropertyValueFactory<DataBean, String>("nubi"));
        temperature.setCellValueFactory(new PropertyValueFactory<DataBean, String>("temp"));

        m.GrapheTemp(comville.getSelectionModel().getSelectedItem(), date.getValue().toString(), series,
                seriesh, seriesn, listeTab);

        tablis.getItems().addAll(listeTab);

    }

}

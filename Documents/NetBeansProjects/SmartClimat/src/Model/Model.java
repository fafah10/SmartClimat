package Model;

import com.jfoenix.controls.JFXRadioButton;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import javafx.scene.chart.XYChart;
import javax.swing.JOptionPane;
import smartclimat.DonneeClasse.Annee;
import smartclimat.DonneeClasse.Station;

public class Model {

    private Map<Integer, Station> listeStation;

    public Model() {
        this.listeStation = new HashMap<>();
        getIdNomStation();
    }

    public Map<Integer, Station> getListeStation() {
        return listeStation;
    }

    private void getIdNomStation() {

        FileReader reader = null;
        try {
            reader = new FileReader(new File("data/Stations/Station.csv"));
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Model.class.getName()).log(Level.SEVERE, null, ex);
        }

        BufferedReader buff = new BufferedReader(reader);

        try {

            String p = buff.readLine();
            p = buff.readLine();
            while (p != null) {

                String tab[] = p.split(";");
                int id = Integer.parseInt(tab[0]);
                Station s = new Station(id, tab[1]);
                listeStation.put(id, s);
                p = buff.readLine();
            }

            buff.close();

        } catch (IOException ex) {
            Logger.getLogger(Model.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public boolean getDonneeAnnee(String idStation, String date) throws FileNotFoundException, IOException {

        String month, years, chemin = null;
        String t[] = date.split("-");
        month = t[1];
        years = t[0];
        chemin = "data/" + years + "/" + month + "/";
        File f = new File(chemin + years + month + ".csv");
        
        if (!f.exists()) {
            JOptionPane.showMessageDialog(null, "Fichier inexistant, cliquez sur OK pour le telecharger ");
            if (Utiles.Utilitaire.verifierConection() == false) {
                JOptionPane.showMessageDialog(null, " aucune connexion internet");
            } else {
                Utiles.Utilitaire.telechargerFichier(Integer.valueOf(years), Integer.valueOf(month));
                JOptionPane.showMessageDialog(null, "fichier téléchargé avec succès");
            }

        }

        FileReader read = new FileReader(f);
        BufferedReader buff = new BufferedReader(read);

        String p = buff.readLine();
        p = buff.readLine();
        while (p != null) {

            String tab[] = p.split(";");
            int idStation1 = Integer.parseInt(tab[0]);
            if (Integer.parseInt(idStation) == idStation1) {
                int annee1 = Integer.parseInt(tab[1].substring(0, 4));
                int mois1 = Integer.parseInt(tab[1].substring(4, 6));
                int jour1 = Integer.parseInt(tab[1].substring(6, 8));
                int ordre1 = Integer.parseInt(tab[1].substring(8, 10)) / 3;

                float temperateur = (!tab[7].equals("mq")) ? Float.parseFloat(tab[7]) : 110;

                float nebulosite = (!tab[14].equals("mq")) ? Float.parseFloat(tab[14]) : 110;

                float humidite = (!tab[9].equals("mq")) ? Float.parseFloat(tab[9]) : 110;

                listeStation.
                        get(idStation1).
                        getCreateAnne(annee1).
                        getCreateMois(mois1).
                        getCreateJour(jour1).
                        getCreateReleve(ordre1, temperateur, nebulosite, humidite);

            }
            p = buff.readLine();
        }
        buff.close();
        return true;

    }

    public boolean displayReleve(String station, String date) {
        

        String tab[] = date.split("/");
        String nom = listeStation.get(Integer.parseInt(station)).getNom();
        float temp = listeStation.get(Integer.parseInt(station))
                .getCreateAnne(Integer.parseInt(tab[2])).CalculMoyenneAnnee().getTemperateur();
               

        
        System.out.println("la température moyenne anuelle est: " + temp + " dans la station:" + nom);

        
        return true;
    }

    /**
     *
     * @param station
     * @return Id de station sinon un nombre négative
     */
    public int getIdStationForName(String station) {

        for (Station s : listeStation.values()) {
            if (station.equals(s.getNom())) {
                return s.getId();
            }
        }
        return -1;

    }

    public boolean GrapheTemp(String nomVille, String date, XYChart.Series series,
            XYChart.Series seriesN, XYChart.Series seriesH, List<DataBean> listeTab) {

        List<Float> l = new ArrayList<>();

        int idStation = getIdStationForName(nomVille);
        String tab[] = date.split("-");
            for (int i = 0; i < 8; i++) {
                System.out.println(" annee: " + Integer.parseInt(tab[0]) + " mois: " + Integer.parseInt(tab[1]) + " jour: "
                        + Integer.parseInt(tab[2]) + " i: " + i);
                float temperature = listeStation.get(idStation)
                        .getCreateAnne(Integer.parseInt(tab[0]))
                        .getCreateMois(Integer.parseInt(tab[1]))
                        .getCreateJour(Integer.parseInt(tab[2]))
                        .getReleve()
                        .get(i)
                        .getTemperateur();
                System.out.println("la temperature est:" + temperature);

                float nub = listeStation.get(idStation)
                        .getCreateAnne(Integer.parseInt(tab[0]))
                        .getCreateMois(Integer.parseInt(tab[1]))
                        .getCreateJour(Integer.parseInt(tab[2])).
                        getReleve().
                        get(i).
                        getNebolosite();
                System.out.println("la nubilosité est:" + nub);

                float hum = listeStation.get(idStation)
                        .getCreateAnne(Integer.parseInt(tab[0]))
                        .getCreateMois(Integer.parseInt(tab[1]))
                        .getCreateJour(Integer.parseInt(tab[2])).
                        getReleve().
                        get(i).
                        getHumidite();

                System.out.println("hum" + hum);
                series.getData().add(new XYChart.Data<String, Float>(String.valueOf(i), temperature));
                seriesN.getData().add(new XYChart.Data<String, Float>(String.valueOf(i), nub));
                seriesH.getData().add(new XYChart.Data<String, Float>(String.valueOf(i), hum));
                listeTab.add(new DataBean(String.valueOf(idStation),
                        nomVille,
                        String.valueOf(temperature),
                        String.valueOf(nub),
                        String.valueOf(hum),
                        date));
              
            }
       
        return true;
}

}


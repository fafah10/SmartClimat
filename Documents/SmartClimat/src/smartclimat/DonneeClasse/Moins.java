package smartclimat.DonneeClasse;

import java.util.HashMap;
import java.util.Map;

public class Moins {

    private int moins;
    private Map<Integer, Jour> jours;

    public Moins(int moins) {
        this.moins = moins;
        this.jours = new HashMap<Integer, Jour>();
    }

    public int getMoins() {
        return moins;
    }

    public void setMoins(int moins) {
        this.moins = moins;
    }

    public Map<Integer, Jour> getJours() {
        return jours;
    }

    public void setJours(Map<Integer, Jour> jours) {
        this.jours = jours;
    }

    public Jour getSingleJour(int jour) {

        return this.jours.get(jour);
    }

    public Jour getCreateJour(int jour) {

        if (getSingleJour(jour) != null) {
            return getSingleJour(jour);
        } else {
            this.jours.put(jour, new Jour(jour));
            return this.jours.get(jour);
        }
    }

    public Releve CalculMoyenneMois() {
        float temp = 0;
        float neb = 0;
        float hum = 0;

        for (Jour r : jours.values()) {
            temp = temp + r.calculMoyenneJour().getTemperateur();
            neb = neb + r.calculMoyenneJour().getNebolosite();
            hum = hum + r.calculMoyenneJour().getHumidite();
        }

        temp = temp / jours.size();
        neb = neb / jours.size();
        hum = hum / jours.size();

        return new Releve(11, temp, neb, hum);
    }

}

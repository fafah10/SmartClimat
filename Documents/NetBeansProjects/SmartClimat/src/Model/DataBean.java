
package Model;

/**
 *
 * @author ferhat-mounir
 */
public class DataBean {
    
    private String  idStation;
    private String nomStation;
    private String temp;
    private String nubi;
    private String humi;
    private String date;
    
    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
    
    

    public DataBean(String idStation, String nomStation, String temp, String nubi, String humi, String date) {
        this.idStation = idStation;
        this.nomStation = nomStation;
        this.temp = temp;
        this.nubi = nubi;
        this.humi = humi;
        this.date=date;
    }

    
    
    
    public String getIdStation() {
        return idStation;
    }

    public void setIdStation(String idStation) {
        this.idStation = idStation;
    }

    public String getNomStation() {
        return nomStation;
    }

    public void setNomStation(String nomStation) {
        this.nomStation = nomStation;
    }

    public String getTemp() {
        return temp;
    }

    public void setTemp(String temp) {
        this.temp = temp;
    }

    public String getNubi() {
        return nubi;
    }

    public void setNubi(String nubi) {
        this.nubi = nubi;
    }

    public String getHumi() {
        return humi;
    }

    public void setHumi(String humi) {
        this.humi = humi;
    }
    
    
    
    
    
}

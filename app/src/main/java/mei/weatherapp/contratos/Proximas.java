package mei.weatherapp.contratos;

import java.io.Serializable;

/* praias proximas */
public class Proximas implements Serializable {
    private String praiaId;
    private String nome;
    private Double latitude;
    private Double longitude;

    public Proximas() {
    }

    public Proximas(Double latitude, Double longitude, String nome, String praiaId) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.nome = nome;
        this.praiaId = praiaId;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getPraiaId() {
        return praiaId;
    }

    public void setPraiaId(String praiaId) {
        this.praiaId = praiaId;
    }
}

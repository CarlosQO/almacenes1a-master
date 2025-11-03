package modelo.crudPQRS;

import java.sql.Timestamp;

public class Pqrs {
    private int idPqrs;
    private String correo;
    private String asunto;
    private String cuerpo;
    private int idUsuarioRemitente;
    private Timestamp fechaEnvio;
    private String nomUsuarioRemi;
    private String ApeUsuarioRemi;

    public Pqrs(int idPqrs, String correo, String asunto, Timestamp fechaEnvio, String cuerpo, int idUsuarioRemitente,
            String nomUsuarioRemi,
            String ApeUsuarioRemi) {
        this.idPqrs = idPqrs;
        this.correo = correo;
        this.asunto = asunto;
        this.cuerpo = cuerpo;
        this.idUsuarioRemitente = idUsuarioRemitente;
        this.nomUsuarioRemi = nomUsuarioRemi;
        this.ApeUsuarioRemi = ApeUsuarioRemi;
        this.fechaEnvio = fechaEnvio;
    }

    public Pqrs() {

    }

    public int getIdPqrs() {
        return idPqrs;
    }

    public void setIdPqrs(int idPqrs) {
        this.idPqrs = idPqrs;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getAsunto() {
        return asunto;
    }

    public void setAsunto(String asunto) {
        this.asunto = asunto;
    }

    public String getCuerpo() {
        return cuerpo;
    }

    public void setCuerpo(String cuerpo) {
        this.cuerpo = cuerpo;
    }

    public int getIdUsuarioRemitente() {
        return idUsuarioRemitente;
    }

    public void setIdUsuarioRemitente(int idUsuarioRemitente) {
        this.idUsuarioRemitente = idUsuarioRemitente;
    }

    public void setFechaEnvio(Timestamp fechaEnvio) {
        this.fechaEnvio = fechaEnvio;
    }

    public Timestamp getFechaEnvio() {
        return fechaEnvio;
    }

    public void setApeUsuarioRemi(String apeUsuarioRemi) {
        ApeUsuarioRemi = apeUsuarioRemi;
    }

    public void setNomUsuarioRemi(String nomUsuarioRemi) {
        this.nomUsuarioRemi = nomUsuarioRemi;
    }

    public String getApeUsuarioRemi() {
        return ApeUsuarioRemi;
    }

    public String getNomUsuarioRemi() {
        return nomUsuarioRemi;
    }
}

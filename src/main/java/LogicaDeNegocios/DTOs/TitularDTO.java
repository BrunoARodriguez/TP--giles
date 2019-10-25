package LogicaDeNegocios.DTOs;

import LogicaDeNegocios.Enumerations.TipoSangre;

public class TitularDTO {
    private Long dni;
    private String observaciones;
    private Boolean donante;
    private TipoSangre tipoSangre;

    public TitularDTO() {
    }

    public TitularDTO(Long dni, String observaciones, Boolean donante, TipoSangre tipoSangre) {
        this.dni = dni;
        this.observaciones = observaciones;
        this.donante = donante;
        this.tipoSangre = tipoSangre;
    }

    public Long getDni() {
        return dni;
    }

    public void setDni(Long dni) {
        this.dni = dni;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public Boolean getDonante() {
        return donante;
    }

    public void setDonante(Boolean donante) {
        this.donante = donante;
    }

    public TipoSangre getTipoSangre() {
        return tipoSangre;
    }

    public void setTipoSangre(TipoSangre tipoSangre) {
        this.tipoSangre = tipoSangre;
    }
}

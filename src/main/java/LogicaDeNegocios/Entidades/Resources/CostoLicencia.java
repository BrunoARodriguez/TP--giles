package LogicaDeNegocios.Entidades.Resources;

import LogicaDeNegocios.Enumerations.ClaseLicencia;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "Costo_Licencia")
public class CostoLicencia {

    private Atributos atributos;
    private Float costoLicencia;
    private static final Double COSTO_ADMINISTRATIVO = 8.0;

    public CostoLicencia() {
    }

    public CostoLicencia(ClaseLicencia claseLicencia, Integer vigenciaLicencia, Float costoLicencia) {
        this.atributos = new Atributos(claseLicencia,vigenciaLicencia);
        this.costoLicencia = costoLicencia;
    }

    @EmbeddedId
    public Atributos getAtributos() {
        return atributos;
    }

    public void setAtributos(Atributos atributos) {
        this.atributos = atributos;
    }

    @Column(name = "COSTO_LICENCIA")
    public Float getCostoLicencia() {
        return costoLicencia;
    }

    public void setCostoLicencia(Float costoLicencia) {
        this.costoLicencia = costoLicencia;
    }

    @Embeddable
    private static class Atributos implements Serializable{
        private ClaseLicencia claseLicencia;
        private Integer vigenciaLicencia;

        public Atributos() {
        }

        public Atributos(ClaseLicencia claseLicencia, Integer vigenciaLicencia) {
            this.claseLicencia = claseLicencia;
            this.vigenciaLicencia = vigenciaLicencia;
        }

        @Enumerated(EnumType.STRING)
        @Column(name = "CLASE_LICENCIA", length = 10)
        public ClaseLicencia getClaseLicencia() {
            return claseLicencia;
        }

        public void setClaseLicencia(ClaseLicencia claseLicencia) {
            this.claseLicencia = claseLicencia;
        }

        @Column(name = "VIGENCIA_LICENCIA")
        public Integer getVigenciaLicencia() {
            return vigenciaLicencia;
        }

        public void setVigenciaLicencia(Integer vigenciaLicencia) {
            this.vigenciaLicencia = vigenciaLicencia;
        }
    }
}

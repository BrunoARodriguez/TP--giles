package gestores;

import LogicaDeNegocios.DTOs.TitularDTO;
import LogicaDeNegocios.Entidades.Contribuyente;
import LogicaDeNegocios.Entidades.Licencia;
import LogicaDeNegocios.Entidades.Titular;

import java.util.ArrayList;

public abstract class GestorTitular {

    //TODO ver que hacemos con el retorno de este metodo: Boolean vs Titular
    public static Boolean validarTitularExistente(Long dni) {
        Titular titular = GestorBD.buscarTitular(dni);

        if (titular != null) {
            return true;
        } else {
            return false;
        }
    }//cierra validarTitularExistente

    public static int crearTitular(TitularDTO titularDTO) {
        Contribuyente contribuyente = GestorBD.buscarContribuyente(titularDTO.getDni());
        if (contribuyente != null) {
            Titular titular = new Titular(contribuyente, new ArrayList<Licencia>(), titularDTO.getObservaciones(), titularDTO.getDonante(), titularDTO.getTipoSangre());
            if (GestorBD.guardarTitular(titular)) {
                //Exitos prro
                return 0;
            } else {
                //Error al guardar el titular
                return -2;
            }
        } else {
            //Contribuyente no encontrado en base de datos
            return -1;
        }
    }//cierra crearTitular

    public static Titular buscarTitular(Long dni) {
        return GestorBD.buscarTitular(dni);
    }//cierra buscarTitular
}

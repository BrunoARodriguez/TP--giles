package Interfaces;

import LogicaDeNegocios.DTOs.ContribuyenteDTO;
import LogicaDeNegocios.DTOs.TitularDTO;
import LogicaDeNegocios.Entidades.Contribuyente;
import LogicaDeNegocios.Enumerations.TipoSangre;
import gestores.GestorTitular;

import javax.swing.*; import javax.swing.JPanel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class Interfaz_Alta_Titular {
    private JTextField tfNumeroDocumento;
    private JTextField tfApellido;
    private JTextField tfNombre;
    private JTextField tfFDeNac;
    private JTextField tfDomicilio;
    private JComboBox cbTipoSangre;
    private JTextArea taObservaciones;
    private JButton confirmarButton;
    private JButton cancelarButton;
    private JPanel rootPane;
    private JButton buscarDatosButton;
    private JCheckBox esDonanteCheckBox;

    public Interfaz_Alta_Titular( final MainFrame frame ) {

        TipoSangre[] tipoSangre = TipoSangre.values();
        for(TipoSangre t : tipoSangre){
            cbTipoSangre.addItem(t.getName());
        }

        buscarDatosButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String sNroDocumento = tfNumeroDocumento.getText().toString();
                Long nroDocumento;
                if (sNroDocumento != "") {
                    nroDocumento = Long.valueOf(sNroDocumento);
                    ContribuyenteDTO contribuyenteDTO = new ContribuyenteDTO();
                    contribuyenteDTO.setNroDocumento(nroDocumento);
                    contribuyenteDTO = GestorTitular.buscarContribuyente(contribuyenteDTO);

                    //TODO refactorizar el mensaje de error, a debatir con el gurpo la mejor opcion
                    if (contribuyenteDTO == null) {
                        //TODO cambiar por ventana emergente
                        System.out.println("Titular no encontrado");
                        tfNumeroDocumento.setText("");
                        tfApellido.setText("");
                        tfNombre.setText("");
                        //TODO refactorizar el formateo de fecha de nacimiento
                        tfFDeNac.setText("");
                        tfDomicilio.setText("");
                    } else {
                        tfApellido.setText(contribuyenteDTO.getApellido());
                        tfNombre.setText(contribuyenteDTO.getNombre());
                        //TODO refactorizar el formateo de fecha de nacimiento
                        tfFDeNac.setText(contribuyenteDTO.getFechaDeNacimiento().toString());
                        tfDomicilio.setText(contribuyenteDTO.getDomicilio());
                    }
                }
                else{
                    //TODO cambiar por ventana emergente.
                    System.out.println("Titular no encontrado");
                    tfNumeroDocumento.setText("");
                    tfApellido.setText("");
                    tfNombre.setText("");
                    //TODO refactorizar el formateo de fecha de nacimiento
                    tfFDeNac.setText("");
                    tfDomicilio.setText("");
                }
            }
        });

        confirmarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                TitularDTO titularDTO = new TitularDTO();
                titularDTO.setDni(Long.valueOf(tfNumeroDocumento.getText()));
                titularDTO.setDonante(esDonanteCheckBox.isSelected());
                titularDTO.setObservaciones(taObservaciones.getText());
                for(TipoSangre t : TipoSangre.values()){
                    if(t.getName().equals(cbTipoSangre.getSelectedItem())){
                        titularDTO.setTipoSangre(t);
                    }
                }
                ContribuyenteDTO contribuyenteDTO = new ContribuyenteDTO();
                contribuyenteDTO.setNroDocumento(titularDTO.getDni());
                titularDTO.setContribuyente(GestorTitular.buscarContribuyente(contribuyenteDTO));
                titularDTO.setTieneLicencias(false);
                GestorTitular.titularAux = titularDTO;
                if(GestorTitular.validarTitularExistente(titularDTO.getDni())){
                    //TODO hacer de esto una ventana emergente.
                    System.out.println("Ya existe titular.");
                }
                else{
                    frame.cambiarPanel(MainFrame.PANE_EMITIR_LICENCIA);
                }
            }
        });

        cancelarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                JDialogCancelar c = new JDialogCancelar(frame);
                if(c.fueCancelado()) {
                    GestorTitular.titularAux=null;
                    frame.backPreviousPane();
                }
            }
        });
    }

    public JPanel getPane(){
        return rootPane;
    }
}

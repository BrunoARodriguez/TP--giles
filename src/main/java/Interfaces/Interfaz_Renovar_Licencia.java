package Interfaces;

import LogicaDeNegocios.DTOs.CriteriosDTO;
import LogicaDeNegocios.DTOs.DatosTablaDTO;
import LogicaDeNegocios.DTOs.LicenciaDTO;
import LogicaDeNegocios.Enumerations.ClaseLicencia;
import com.toedter.calendar.JDateChooser;
import gestores.GestorLicencia;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Interfaz_Renovar_Licencia {
    private JPanel rootPane;
    private JTextField tf_dni;
    private JTextField tf_nombre;
    private JTextField tf_apellido;
    private JCheckBox aCheckBox;
    private JCheckBox bCheckBox;
    private JCheckBox cCheckBox;
    private JCheckBox dCheckBox;
    private JCheckBox eCheckBox;
    private JCheckBox fCheckBox;
    private JCheckBox gCheckBox;
    private JTable table_resultados;
    private JButton volverButton;
    private JButton buscarButton;
    private JButton renovarButton;
    private JScrollPane scrollPane;
    private JDateChooser tf_desde;
    private JDateChooser tf_hasta;
    private Long dni;
    private String nombre;
    private String apellido;
    private List<ClaseLicencia> claseLicenciaList;
    private LocalDateTime fechaDesde, fechaHasta;
    private List<DatosTablaDTO> datosTablaDTOS;

    public JPanel getPane() {
        return rootPane;
    }

    private void createUIComponents()
    {
        tf_desde = new JDateChooser();
        tf_desde.getDateEditor().setEnabled(false);
        ((JTextField)tf_desde.getDateEditor().getUiComponent()).setDisabledTextColor(Color.black);

        tf_hasta = new JDateChooser();
        tf_hasta.getDateEditor().setEnabled(false);
        ((JTextField)tf_hasta.getDateEditor().getUiComponent()).setDisabledTextColor(Color.black);
    }

    public Interfaz_Renovar_Licencia(final MainFrame frame) {
        String[] columns = {"DNI titular", "Nombre titular", "Apellido titular", "Clase(s)", "Fecha Alta"};
        datosTablaDTOS = new ArrayList<>();

        ModeloLicencias modeloLicencias = new ModeloLicencias(datosTablaDTOS, columns);
        table_resultados = new JTable(modeloLicencias);
        table_resultados.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        scrollPane.setViewportView(table_resultados);

        renovarButton.setEnabled(false);

        buscarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {

                dni = null;
                fechaDesde = null;
                fechaHasta = null;
                claseLicenciaList = new ArrayList<>();

                if(!tf_dni.getText().isEmpty()){
                    try {
                        dni = Long.valueOf(tf_dni.getText());
                    } catch (Exception e) {
                        e.printStackTrace();
                        JOptionPane.showMessageDialog(frame, "Documento Ingresado Invalido", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                }

                nombre = tf_nombre.getText();
                apellido = tf_apellido.getText();

                if (aCheckBox.isSelected()) {
                    claseLicenciaList.add(ClaseLicencia.CLASE_A);
                }
                if (bCheckBox.isSelected()) {
                    claseLicenciaList.add(ClaseLicencia.CLASE_B);
                }
                if (cCheckBox.isSelected()) {
                    claseLicenciaList.add(ClaseLicencia.CLASE_C);
                }
                if (dCheckBox.isSelected()) {
                    claseLicenciaList.add(ClaseLicencia.CLASE_D);
                }
                if (eCheckBox.isSelected()) {
                    claseLicenciaList.add(ClaseLicencia.CLASE_E);
                }
                if (fCheckBox.isSelected()) {
                    claseLicenciaList.add(ClaseLicencia.CLASE_F);
                }
                if (gCheckBox.isSelected()) {
                    claseLicenciaList.add(ClaseLicencia.CLASE_G);
                }

                if (tf_desde.getDate()==null && tf_hasta.getDate()==null) {
                    JOptionPane.showMessageDialog(frame, "Debe ingresar al menos una fecha.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (tf_desde.getDate()!=null) {
                    fechaDesde= tf_desde.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate().atTime(3,0,0);
                    Period periodo = Period.between(fechaDesde.toLocalDate(), LocalDateTime.now().toLocalDate());
                    int anios = periodo.getYears();
                    if (anios > 6) {
                        JOptionPane.showMessageDialog(frame, "La fecha de vencimiento no puede ser de más de 6 años en el futuro.", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                }

                if(tf_hasta.getDate()!=null){
                    fechaHasta= tf_hasta.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate().atTime(3,0,0);
                    Period periodo = Period.between(fechaHasta.toLocalDate(), LocalDateTime.now().toLocalDate());
                    int anios = periodo.getYears();
                    if (anios > 6) {
                        JOptionPane.showMessageDialog(frame, "La fecha de vencimiento no puede ser de más de 6 años en el futuro.", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                }

                CriteriosDTO criteriosDTO = new CriteriosDTO();
                if (dni == null) {
                    criteriosDTO.setDniTitular("");
                } else {
                    criteriosDTO.setDniTitular(dni.toString());
                }
                criteriosDTO.setNombreTitular(nombre);
                criteriosDTO.setApellidoTitular(apellido);
                criteriosDTO.setFechaVencimientoDesde(fechaDesde);
                criteriosDTO.setFechaVencimientoHasta(fechaHasta);
                criteriosDTO.setClaseLicencias(claseLicenciaList);

                datosTablaDTOS = GestorLicencia.listarLicencias(criteriosDTO);

                if(datosTablaDTOS.isEmpty()){
                    JOptionPane.showMessageDialog(frame, "No se encontraron resultados.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                } else {
                    JOptionPane.showMessageDialog(frame, "Busqueda completada.", "Consulta de licencias.", JOptionPane.INFORMATION_MESSAGE);
                }
                //TODO ver si hacemos que al fallar busquedas se vacie la tabla
                modeloLicencias.setDatosTablaDTOS(datosTablaDTOS);
                table_resultados.updateUI();

            }
        });

        volverButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                JDialogCancelar c = new JDialogCancelar(frame);
                if (c.fueCancelado()) {
                    frame.backPreviousPane();
                }
            }
        });

    }

}

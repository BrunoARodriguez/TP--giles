package Interfaces;

import javax.swing.*;
import java.awt.event.*;

public class JDialogCancelar extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JLabel LbDescripcionCancelar;
    private JLabel lbDescripcion;

    private boolean cancelado = false;

    public JDialogCancelar(final MainFrame frame) {
        setContentPane(contentPane);
        setTitle("TP AGILES");
        setLocationRelativeTo(frame);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });

        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

        //setResizable(false);

        this.pack();
        setVisible(true);
    }

    private void onOK() {
        // add your code here
        cancelado = true;
        dispose();
    }

    private void onCancel() {
        // add your code here if necessary
        cancelado = false;
        dispose();
    }

/*    public static void main(String[] args) {
        JDialogCancelar dialog = new JDialogCancelar();
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }*/

    public boolean fueCancelado() {
        return cancelado;
    }
}
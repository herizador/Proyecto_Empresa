package dialogs;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.List;

import javax.swing.*;

import dao.AccesoTrabajador;
import exception.BDException;
import exception.FicheroException;
import exception.TrabajadorException;
import gui.LeerValidaciones;
import modelo.Trabajador;

/**
 * @author usuario
 */
public class AltaDialog extends JDialog implements ActionListener, ItemListener {

    String rutaResouse = UtilsDialog.rutaResouse;

    /**
     * Imagen de check
     */
    ImageIcon iconoCheck = UtilsDialog.iconoCheck();

    /**
     * Elementos del JFrame
     */
    JLabel etiquetaDni;
    JTextField areaDni;
    JLabel etiquetaNombre;
    JTextField areaNombre;
    JLabel etiquetaApellidos;
    JTextField areaApellidos;
    JLabel etiquetaDireccion;
    JTextField areaDireccion;
    JLabel etiquetaTelefono;
    JTextField areaTelefono;
    JLabel etiquetaPuesto;
    JComboBox<String> comboPuesto;
    JButton aceptar;
    JButton cancelar;

    /**
     * Variables a las que se pasar� el contenido de los JTextField y del combo box
     */
    String dni = "";
    String nombre = "";
    String apellidos = "";
    String direccion = "";
    String telefono = "";
    String puesto = "";

    JPanel pDni;
    JPanel pNombre;
    JPanel pApellidos;
    JPanel pDireccion;
    JPanel pTelefono;
    JPanel pPuesto;
    JPanel pBotones;

    public AltaDialog() {
        String[] opciones = {"Manual", "Por fichero"};
        int opcion = JOptionPane.showOptionDialog(null, "¿Cómo desea insertar el trabajador?", "Alta de Trabajador", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, opciones, opciones[0]);

        switch (opcion) {
            case JOptionPane.YES_OPTION:
                altaManual();
                break;
            case JOptionPane.NO_OPTION:
                insertaPorFichero();
                break;
        }
    }

    public void insertaPorFichero() {
        try {
            UtilsDialog.importar(this);
        } catch (FicheroException e) {
            UtilsDialog.mensajeError(e);
        }
    }

    public void altaManual() {
        setResizable(false);
        // t�tulo del di�log
        setTitle("Alta Trabajador");
        setSize(300, 350);
        setLayout(new FlowLayout());

        setLocationRelativeTo(null);

        // una fila por JPanel
        pDni = new JPanel();
        pNombre = new JPanel();
        pApellidos = new JPanel();
        pDireccion = new JPanel();
        pTelefono = new JPanel();
        pPuesto = new JPanel();
        pBotones = new JPanel();

        // Se crean los elementos y se añaden
        etiquetaDni = new JLabel("DNI                 ");
        areaDni = new JTextField(15);
        // Se añaden al JPanel
        pDni.add(etiquetaDni);
        pDni.add(areaDni);

        // Se crean los elementos y se añaden
        etiquetaNombre = new JLabel("Nombre         ");
        areaNombre = new JTextField(15);
        // Se añaden al JPanel
        pNombre.add(etiquetaNombre);
        pNombre.add(areaNombre);

        // Se crean los elementos y se a�aden
        etiquetaApellidos = new JLabel("Apellidos      ");
        areaApellidos = new JTextField(15);
        // Se añaden al JPanel
        pApellidos.add(etiquetaApellidos);
        pApellidos.add(areaApellidos);

        // Se crean los elementos y se añaden
        etiquetaDireccion = new JLabel("Direccion      ");
        areaDireccion = new JTextField(15);
        // Se añaden al JPanel
        pDireccion.add(etiquetaDireccion);
        pDireccion.add(areaDireccion);

        // Se crean los elementos y se a�aden
        etiquetaTelefono = new JLabel("Telefono       ");
        areaTelefono = new JTextField(15);
        // Se añaden al JPanel
        pTelefono.add(etiquetaTelefono);
        pTelefono.add(areaTelefono);

        // Se crean los elementos y se añaden
        etiquetaPuesto = new JLabel("Puesto                         ");
        pPuesto.add(etiquetaPuesto);

        // lista desplegable
        comboPuesto = new JComboBox<>();
        comboPuesto.addItem("Elija Puesto");

        List<String> puestos;
        try {
            puestos = AccesoTrabajador.obtenerPuestos();

            for (String puesto : puestos) {
                comboPuesto.addItem(puesto);
            }
        } catch (BDException e) {
            UtilsDialog.mensajeError(e);
        }

        comboPuesto.addItemListener(this);
        pPuesto.add(comboPuesto);

        // Añadir al JDialog los JPanel
        add(pDni);
        add(pNombre);
        add(pApellidos);
        add(pDireccion);
        add(pTelefono);
        add(pPuesto);

        aceptar = new JButton("Alta");
        aceptar.addActionListener(this);
        pBotones.add(aceptar);

        cancelar = new JButton("Salir");
        cancelar.addActionListener(this);
        pBotones.add(cancelar);

        add(pBotones);

        // Visible
        setVisible(true);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        puesto = comboPuesto.getSelectedItem().toString();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == aceptar) {
            try {
                boolean error_validacion = false;

                dni = areaDni.getText();
                if (LeerValidaciones.validarDNI(dni)) {
                    areaDni.setForeground(Color.WHITE);
                    areaDni.setBackground(Color.RED);
                    error_validacion = true;
                }else{
                    areaDni.setForeground(Color.BLACK);
                    areaDni.setBackground(Color.WHITE);
                }

                nombre = areaNombre.getText();
                if (LeerValidaciones.validarNombre(nombre)) {
                    areaNombre.setForeground(Color.WHITE);
                    areaNombre.setBackground(Color.RED);
                    error_validacion = true;
                }else{
                    areaNombre.setForeground(Color.BLACK);
                    areaNombre.setBackground(Color.WHITE);
                }

                apellidos = areaApellidos.getText();
                if (LeerValidaciones.validarApellidos(apellidos)) {
                    areaApellidos.setForeground(Color.WHITE);
                    areaApellidos.setBackground(Color.RED);
                    error_validacion = true;
                }else{
                    areaApellidos.setForeground(Color.BLACK);
                    areaApellidos.setBackground(Color.WHITE);
                }

                direccion = areaDireccion.getText();
                if (LeerValidaciones.validarDireccion(direccion)) {
                    areaDireccion.setForeground(Color.WHITE);
                    areaDireccion.setBackground(Color.RED);
                    error_validacion = true;
                }else {
                    areaDireccion.setForeground(Color.BLACK);
                    areaDireccion.setBackground(Color.WHITE);
                }

                telefono = areaTelefono.getText();
                if (LeerValidaciones.validarTelefono(telefono)) {
                    areaTelefono.setForeground(Color.WHITE);
                    areaTelefono.setBackground(Color.RED);
                    error_validacion = true;
                }else{
                    areaTelefono.setForeground(Color.BLACK);
                    areaTelefono.setBackground(Color.WHITE);
                }

                if(LeerValidaciones.validarPuesto(puesto)){
                    comboPuesto.setForeground(Color.WHITE);
                    comboPuesto.setBackground(Color.RED);
                    error_validacion = true;
                }else{
                    comboPuesto.setForeground(Color.BLACK);
                    comboPuesto.setBackground(Color.WHITE);
                }

                if(!error_validacion){
                    Trabajador t = new Trabajador(dni, nombre, apellidos, direccion, telefono, puesto);
                    AccesoTrabajador.altaTrabajador(t);
                    JOptionPane.showMessageDialog(null, "Trabajador insertado exitosamente", "Exito", JOptionPane.PLAIN_MESSAGE, iconoCheck);
                    dispose();
                }
            } catch (TrabajadorException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "Actualizacion", JOptionPane.PLAIN_MESSAGE, iconoCheck);
            } catch (BDException e1) {
                UtilsDialog.mensajeError(e1);
            }
        } else if (e.getSource() == cancelar) {
            dispose();
        }
    }
}

package dialogs;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import dao.AccesoTrabajador;
import exception.BDException;
import modelo.Trabajador;

/**
 * @author usuario
 */
public class BajaDialog extends JDialog implements ActionListener {

    /**
     * Imagen de check
     */
    ImageIcon iconoOriginal = new ImageIcon(getClass().getResource("/images/check_verde.png"));
    Image imagenRedimensionada = iconoOriginal.getImage().getScaledInstance(48, 48, Image.SCALE_SMOOTH);
    ImageIcon iconoCheck = new ImageIcon(imagenRedimensionada);

    JButton aceptar;
    JButton cancelar;
    JTable tabla;
    DefaultTableModel modelo;
    String[][] datos;
    List<Trabajador> trajadores;

    public BajaDialog() {
        JOptionPane.showMessageDialog(null, "Seleccione un empleado y presione 'Borrar' para dar de baja.", "Mensaje", JOptionPane.INFORMATION_MESSAGE);

        setResizable(false);
        // t�tulo del di�log
        setTitle("Baja Trabajador");
        setSize(750, 700);
        setLayout(new FlowLayout());
        setLocationRelativeTo(null);

        // Crea un JTable, cada fila será un trabajador
        String[] columnas = {"DNI", "Nombre", "Apellidos", "Direccion", "Telefono", "Puesto"};
        trajadores = AccesoTrabajador.obtenerTrabajadores();
        datos = AccesoTrabajador.listarTrabajadores(trajadores);

        modelo = new DefaultTableModel(datos, columnas) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Bloquea TODAS las celdas
            }
        };

        tabla = new JTable(modelo);

        tabla.setAutoCreateRowSorter(true);

        tabla.setRowHeight(25);

        // Mete la tabla en un JCrollPane
        JScrollPane jsp = new JScrollPane(tabla);
        jsp.setPreferredSize(new Dimension(700, 600));
        add(jsp);

        aceptar = new JButton("Borrar");
        aceptar.addActionListener(this);
        add(aceptar);

        cancelar = new JButton("Salir");
        cancelar.addActionListener(this);
        add(cancelar);
        // Visible
        setVisible(true);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        int filaVisual = tabla.getSelectedRow();

        if (e.getSource() == aceptar) {
            if (filaVisual == -1) {
                JOptionPane.showMessageDialog(null, "No ha seleccionado ningun empleado", "ERROR", JOptionPane.ERROR_MESSAGE);
            } else {
                int filaModelo = tabla.convertRowIndexToModel(filaVisual);

                String dniTrabajorABorrar = tabla.getModel().getValueAt(filaModelo, 0).toString();
                int respuesta = JOptionPane.showConfirmDialog(null, "Esta seguro que quiere borrar al trabajador con DNI: " + dniTrabajorABorrar, "Borrar", JOptionPane.YES_NO_OPTION);
                switch (respuesta) {
                    case JOptionPane.YES_OPTION:
                        // Operaciones en caso afirmativo
                        try {
                            AccesoTrabajador.borrarTrabajador(dniTrabajorABorrar);
                            JOptionPane.showMessageDialog(null, "Trabajador eliminado exitosamente", "Exito", JOptionPane.PLAIN_MESSAGE, iconoCheck);
                            refrescarDatos();
                        } catch (BDException ex) {
                            JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                        }
                        break;
                    case JOptionPane.NO_OPTION:
                        // Operaciones en caso negativo
                        break;
                }
            }
        } else if (e.getSource() == cancelar) {
            dispose();
        }
    }

    public void refrescarDatos() {
        trajadores = AccesoTrabajador.obtenerTrabajadores();
        datos = AccesoTrabajador.listarTrabajadores(trajadores);
        String[] columnas = {"DNI", "Nombre", "Apellidos", "Direccion", "Telefono", "Puesto"};

        DefaultTableModel modelo = (DefaultTableModel) tabla.getModel();
        modelo.setDataVector(datos, columnas);
    }
}

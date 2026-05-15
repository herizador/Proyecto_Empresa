package dialogs;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;

import dao.AccesoTrabajador;
import exception.BDException;
import modelo.Trabajador;

/**
 * @author usuario
 */
public class BajaDialog extends JDialog implements ActionListener, TableModelListener {
    /**
     * Imagen de check
     */
    ImageIcon iconoCheck = UtilsDialog.iconoCheck();

    JButton aceptar;
    JButton cancelar;

    /**
     * Panel de busqueda
     */
    JPanel panelBusqueda;

    /**
     * tablas
     */
    JTable tabla;
    DefaultTableModel modelo;
    String[][] datos;
    String[] columnas = {"DNI", "Nombre", "Apellidos", "Direccion", "Telefono", "Puesto", "Borrar"};

    List<Trabajador> trabajadores;
    Set<Trabajador> trabajadoresAEliminar = new HashSet<>();

    public BajaDialog() {
        setResizable(false);
        // t�tulo del di�log
        setTitle("Baja Trabajador");
        setSize(750, 725);
        setLayout(new FlowLayout());
        setLocationRelativeTo(null);

        panelBusqueda = UtilsDialog.barraDeBusqueda(this);
        add(panelBusqueda);

        // Crea un JTable, cada fila será un trabajador
        try {
            trabajadores = AccesoTrabajador.obtenerTrabajadores();
            datos = AccesoTrabajador.listarTrabajadores(trabajadores);
        } catch (BDException e) {
            UtilsDialog.mensajeError(e);
        }

        modelo = new DefaultTableModel(datos, columnas) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 6; // Bloquea TODAS las celdas
            }

            @Override
            public Class<?> getColumnClass(int columnIndex) {
                // Al retornar Boolean.class, Swing usa automáticamente
                // un JCheckBox tanto para mostrar como para editar.
                if (columnIndex == 6) {
                    return Boolean.class;
                }
                return String.class;
            }
        };

        modelo.addTableModelListener(this);

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
        try {
            if (e.getSource() == aceptar) {
                if (trabajadoresAEliminar.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "No ha seleccionado ningun empleado", "ERROR", JOptionPane.ERROR_MESSAGE);
                } else {
                    int respuesta = JOptionPane.showConfirmDialog(null, "Esta seguro que quiere borrar a los trabajadores", "Borrar", JOptionPane.YES_NO_OPTION);
                    switch (respuesta) {
                        case JOptionPane.YES_OPTION:
                            // Operaciones en caso afirmativo
                                AccesoTrabajador.borrarTrabajador(trabajadoresAEliminar);
                                JOptionPane.showMessageDialog(null, "Trabajador/es eliminado exitosamente", "Exito", JOptionPane.PLAIN_MESSAGE, iconoCheck);
                                trabajadoresAEliminar.clear();
                                refrescarDatos();
                            break;
                        case JOptionPane.NO_OPTION:
                            break;
                    }
                }
            } else if (e.getSource() == cancelar) {
                dispose();
            } else if (e.getActionCommand().equals("Buscar")) {
                trabajadores = UtilsDialog.buscarPorFiltro(panelBusqueda);

                String[][] datos = AccesoTrabajador.listarTrabajadores(trabajadores);
                UtilsDialog.refrescarDatosFiltrados(datos, tabla, columnas);
            }
        } catch (BDException ex) {
            UtilsDialog.mensajeError(ex);
        }
    }

    public void refrescarDatos() {
        try {
            trabajadores = AccesoTrabajador.obtenerTrabajadores();
            datos = AccesoTrabajador.listarTrabajadores(trabajadores);
            DefaultTableModel modelo = (DefaultTableModel) tabla.getModel();
            modelo.setDataVector(datos, columnas);
        } catch (BDException e) {
            UtilsDialog.mensajeError(e);
        }
    }

    @Override
    public void tableChanged(TableModelEvent e) {
        if (e.getType() == TableModelEvent.UPDATE) {
            int fila = e.getFirstRow();
            int columna = e.getColumn();

            if (fila < 0 || columna < 0 || fila >= tabla.getRowCount()) {
                return;
            }

            String dni = modelo.getValueAt(fila, 0).toString();
            String nombre = modelo.getValueAt(fila, 1).toString();
            String apellido = modelo.getValueAt(fila, 2).toString();
            String direccion = modelo.getValueAt(fila, 3).toString();
            String telefono = modelo.getValueAt(fila, 4).toString();
            String puesto = modelo.getValueAt(fila, 5).toString();

            Trabajador trabajador = new Trabajador(dni, nombre, apellido, direccion, telefono, puesto);

            boolean checkBox = (Boolean) modelo.getValueAt(fila, 6);

            if (checkBox) {
                trabajadoresAEliminar.add(trabajador);
            } else {
                trabajadoresAEliminar.remove(trabajador);
            }
        }
    }
}
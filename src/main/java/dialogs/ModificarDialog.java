package dialogs;

import dao.AccesoTrabajador;
import exception.BDException;
import exception.TrabajadorException;
import modelo.Trabajador;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import java.util.List;

public class ModificarDialog extends JDialog implements ActionListener, TableModelListener {

    List<Trabajador> trabajadoresAModificar = new LinkedList<>();

    /**
     * Imagen de check
     */
    ImageIcon iconoOriginal = new ImageIcon(getClass().getResource("/check_verde.png"));
    Image imagenRedimensionada = iconoOriginal.getImage().getScaledInstance(48, 48, Image.SCALE_SMOOTH);
    ImageIcon iconoCheck = new ImageIcon(imagenRedimensionada);

    /**
     * Tabla
     */
    List<Trabajador> trajadores;
    String[][] datos;
    JTable tabla;
    DefaultTableModel modelo;
    JComboBox<String> comboPuesto;

    JButton btnCancelar;
    JButton btnModificar;

    public ModificarDialog() {
        JOptionPane.showMessageDialog(null, "Se guardarán los datos de las celdas modificadas. Presione 'Modificar' al terminar.", "Mensaje", JOptionPane.INFORMATION_MESSAGE);

        setResizable(false);
        // t�tulo del di�log
        setTitle("Modificar Trabajador");
        setSize(750, 700);
        setLayout(new FlowLayout());
        setLocationRelativeTo(null);

        String[] columnas = {"DNI", "Nombre", "Apellidos", "Direccion", "Telefono", "Puesto"};
        trajadores = AccesoTrabajador.obtenerTrabajadores();
        datos = AccesoTrabajador.listarTrabajadores(trajadores);

        modelo = new DefaultTableModel(datos, columnas) {
            @Override
            public boolean isCellEditable(int fila, int columna) {
                // Llamamos a tu método para centralizar la lógica
                return columna != 0;
            }
        };

        tabla = new JTable(modelo);

        tabla.setRowHeight(25);

        JScrollPane jsp = new JScrollPane(tabla);
        jsp.setPreferredSize(new Dimension(700, 600));
        add(jsp);

        List<String> puestos = AccesoTrabajador.obtenerPuestos();
        comboPuesto = new JComboBox<>();

        for (String p : puestos) {
            comboPuesto.addItem(p);
        }

        tabla.getModel().addTableModelListener(this);

        TableColumn columnaPuesto = tabla.getColumnModel().getColumn(5);
        columnaPuesto.setCellEditor(new DefaultCellEditor(comboPuesto));

        btnModificar = new JButton("Modificar");
        btnModificar.addActionListener(this);
        add(btnModificar);

        btnCancelar = new JButton("Cancelar");
        btnCancelar.addActionListener(this);
        add(btnCancelar);

        setVisible(true);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
    }

    public void refrescarDatos() {
        trajadores = AccesoTrabajador.obtenerTrabajadores();
        datos = AccesoTrabajador.listarTrabajadores(trajadores);
        String[] columnas = {"DNI", "Nombre", "Apellidos", "Direccion", "Telefono", "Puesto"};

        DefaultTableModel modelo = (DefaultTableModel) tabla.getModel();
        modelo.setDataVector(datos, columnas);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnCancelar) {
            dispose();
        } else if (e.getSource() == btnModificar) {
            if (!trabajadoresAModificar.isEmpty()) {
                if (tabla.isEditing()) {
                    tabla.getCellEditor().stopCellEditing();
                }

                try {
                    AccesoTrabajador.actualizarTrabajadorLista(trabajadoresAModificar);
                    JOptionPane.showMessageDialog(null, "Trabajador/es modificado exitosamente", "Exito", JOptionPane.PLAIN_MESSAGE, iconoCheck);
                    trabajadoresAModificar.clear();
                    refrescarDatos();
                } catch (TrabajadorException | BDException ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(null, "Modifica al menos un trabajador", "Error", JOptionPane.ERROR_MESSAGE);
            }
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

            String dni = tabla.getValueAt(fila, 0).toString();
            String nombre = tabla.getValueAt(fila, 1).toString();
            String apellido = tabla.getValueAt(fila, 2).toString();
            String direccion = tabla.getValueAt(fila, 3).toString();
            String telefono = tabla.getValueAt(fila, 4).toString();
            String puesto = tabla.getValueAt(fila, 5).toString();

            Trabajador trabajadorAux = new Trabajador(dni, nombre, apellido, direccion, telefono, puesto);

            trabajadoresAModificar.removeIf(t -> t.getDni().equals(dni));
            trabajadoresAModificar.add(trabajadorAux);
        }
    }
}

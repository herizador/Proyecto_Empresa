package dialogs;

import dao.AccesoTrabajador;
import exception.BDException;
import exception.TrabajadorException;
import modelo.Trabajador;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class ModificarDialog extends JDialog implements ActionListener {

    /**
     * Imagen de check
     */
    ImageIcon iconoOriginal = new ImageIcon(getClass().getResource("/check_verde.png"));
    Image imagenRedimensionada = iconoOriginal.getImage().getScaledInstance(48, 48, Image.SCALE_SMOOTH);
    ImageIcon iconoCheck = new ImageIcon(imagenRedimensionada);

    /**
     * Tabla
     */
    String[][] datos;
    JTable tabla;
    DefaultTableModel modelo;
    JComboBox<String> comboPuesto;

    JButton btnCancelar;
    JButton btnModificar;

    public ModificarDialog() {
        setResizable(false);
        // t�tulo del di�log
        setTitle("Modificar Trabajador");
        setSize(750, 700);
        setLayout(new FlowLayout());
        setLocationRelativeTo(null);

        String[] columnas = {"DNI", "Nombre", "Apellidos", "Direccion", "Telefono", "Puesto"};
        datos = AccesoTrabajador.listarTrabajadores();

        modelo = new DefaultTableModel(datos, columnas);
        tabla = new JTable(modelo);

        JScrollPane jsp = new JScrollPane(tabla);
        jsp.setPreferredSize(new Dimension(700, 600));
        add(jsp);

        List<String> puestos = AccesoTrabajador.obtenerPuestos();
        comboPuesto = new JComboBox<>();

        for (String p : puestos) {
            comboPuesto.addItem(p);
        }

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

    public void refrescarDatos(){
        datos = AccesoTrabajador.listarTrabajadores();
        String[] columnas = {"DNI", "Nombre", "Apellidos", "Direccion", "Telefono", "Puesto"};

        DefaultTableModel modelo = (DefaultTableModel) tabla.getModel();
        modelo.setDataVector(datos, columnas);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnCancelar) {
            dispose();
        }else if (e.getSource() == btnModificar) {
            int fila = tabla.getSelectedRow();

            if(fila != -1){
                if (tabla.isEditing()) {
                    tabla.getCellEditor().stopCellEditing();
                }

                String dni = tabla.getValueAt(fila, 0).toString();
                String nombre = tabla.getValueAt(fila, 1).toString();
                String apellido = tabla.getValueAt(fila, 2).toString();
                String direccion = tabla.getValueAt(fila, 3).toString();
                String telefono = tabla.getValueAt(fila, 4).toString();
                String puesto = tabla.getValueAt(fila, 5).toString();

                Trabajador trabajadorAux = new Trabajador(dni, nombre, apellido, direccion, telefono, puesto);

                try {
                    AccesoTrabajador.actualizarTrabajador(trabajadorAux);
                    JOptionPane.showMessageDialog(null, "Trabajador modificado exitosamente", "Exito", JOptionPane.PLAIN_MESSAGE, iconoCheck);
                    refrescarDatos();
                } catch (TrabajadorException | BDException ex) {
                    JOptionPane.showMessageDialog(null, "No se ha podido modificar", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }else{
                JOptionPane.showMessageDialog(null, "Selecciona una fila", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}

package dialogs;

import dao.AccesoTrabajador;
import exception.BDException;
import exception.TrabajadorException;
import gui.LeerValidaciones;
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
     * Panel de busqueda
     */
    JPanel panelBusqueda;

    /**
     * Imagen de check
     */
    ImageIcon iconoCheck = UtilsDialog.iconoCheck();

    /**
     * Tabla
     */
    List<Trabajador> trabajadores;
    String[][] datos;
    JTable tabla;
    DefaultTableModel modelo;
    JComboBox<String> comboPuesto = new JComboBox<>();
    String[] columnas = UtilsDialog.columnas();

    JButton btnCancelar;
    JButton btnModificar;
    JPanel pBotones;

    public ModificarDialog() {
        setResizable(false);
        // t�tulo del di�log
        setTitle("Modificar Trabajador");
        setSize(750, 725);
        setLayout(new FlowLayout());
        setLocationRelativeTo(null);

        panelBusqueda = UtilsDialog.barraDeBusqueda(this);
        add(panelBusqueda);

        try {
            trabajadores = AccesoTrabajador.obtenerTrabajadores();
            datos = AccesoTrabajador.listarTrabajadores(trabajadores);
        } catch (BDException e) {
            UtilsDialog.mensajeError(e);
        }

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

        List<String> puestos;
        try {
            puestos = AccesoTrabajador.obtenerPuestos();

            for (String puesto : puestos) {
                comboPuesto.addItem(puesto);
            }
        } catch (BDException e) {
            UtilsDialog.mensajeError(e);
        }

        tabla.getModel().addTableModelListener(this);

        TableColumn columnaPuesto = tabla.getColumnModel().getColumn(5);
        columnaPuesto.setCellEditor(new DefaultCellEditor(comboPuesto));

        pBotones = new JPanel();

        btnModificar = new JButton("Modificar");
        btnModificar.addActionListener(this);
        pBotones.add(btnModificar);

        btnCancelar = new JButton("Cancelar");
        btnCancelar.addActionListener(this);
        pBotones.add(btnCancelar);

        add(pBotones);

        setVisible(true);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
    }

    public void refrescarDatos() {
        try{
            trabajadores = AccesoTrabajador.obtenerTrabajadores();
            datos = AccesoTrabajador.listarTrabajadores(trabajadores);

            DefaultTableModel modelo = (DefaultTableModel) tabla.getModel();
            modelo.setDataVector(datos, columnas);

            TableColumn columnaPuesto = tabla.getColumnModel().getColumn(5);
            columnaPuesto.setCellEditor(new DefaultCellEditor(comboPuesto));
        }catch (BDException e){
            UtilsDialog.mensajeError(e);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            if (e.getSource() == btnCancelar) {
                dispose();
            } else if (e.getSource() == btnModificar) {
                if (tabla.isEditing()) {
                    tabla.getCellEditor().stopCellEditing();
                }

                if (!trabajadoresAModificar.isEmpty()) {
                        AccesoTrabajador.actualizarTrabajadorLista(trabajadoresAModificar);
                        JOptionPane.showMessageDialog(null, "Trabajador/es modificado exitosamente", "Exito", JOptionPane.PLAIN_MESSAGE, iconoCheck);
                        trabajadoresAModificar.clear();
                        refrescarDatos();
                } else {
                    JOptionPane.showMessageDialog(null, "Modifica al menos un trabajador", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else if (e.getActionCommand().equals("Buscar")) {
                trabajadores = UtilsDialog.buscarPorFiltro(panelBusqueda);

                String[][] datos = AccesoTrabajador.listarTrabajadores(trabajadores);
                UtilsDialog.refrescarDatosFiltrados(datos, tabla, columnas);
            }
        }catch (BDException | TrabajadorException ex) {
            UtilsDialog.mensajeError(ex);
        }
    }

    @Override
    public void tableChanged(TableModelEvent e) {
        if (e.getType() == TableModelEvent.UPDATE) {
            boolean error_validacion = false;
            int fila = e.getFirstRow();
            int columna = e.getColumn();


            if (fila < 0 || columna < 0 || fila >= tabla.getRowCount()) {
                return;
            }

            String valorNuevo = tabla.getValueAt(fila, columna).toString();
            String valorOriginal = datos[fila][columna];

            if (valorNuevo.equals(valorOriginal)) {
                return;
            }

            String dni = tabla.getValueAt(fila, 0).toString();
            String nombre = tabla.getValueAt(fila, 1).toString();
            String apellido = tabla.getValueAt(fila, 2).toString();
            String direccion = tabla.getValueAt(fila, 3).toString();
            String telefono = tabla.getValueAt(fila, 4).toString();
            String puesto = tabla.getValueAt(fila, 5).toString();

            if(LeerValidaciones.validarNombre(nombre) || LeerValidaciones.validarApellidos(apellido) || LeerValidaciones.validarDireccion(direccion) || LeerValidaciones.validarTelefono(telefono) || LeerValidaciones.validarPuesto(puesto)) {
                error_validacion = true;
            }

            if (!error_validacion) {
                Trabajador trabajadorAux = new Trabajador(dni, nombre, apellido, direccion, telefono, puesto);

                trabajadoresAModificar.removeIf(t -> t.getDni().equals(dni));
                trabajadoresAModificar.add(trabajadorAux);
            } else {
                refrescarDatos();
            }
        }
    }
}

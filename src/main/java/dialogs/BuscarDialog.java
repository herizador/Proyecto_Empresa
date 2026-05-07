package dialogs;

import dao.AccesoTrabajador;
import exception.BDException;
import exception.TrabajadorException;
import modelo.Trabajador;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class BuscarDialog extends JFrame implements ActionListener {

    /**
     * Filtado
     */
    JPanel pFiltado;
    JComboBox<String> opciones;
    JTextField filtrado;

    /**
     * Tabla
     */
    JTable tabla;
    List<Trabajador> trajadores;
    String[][] datos;
    String[] columnas = UtilsDialog.columnas();

    /**
     * Botones
     */
    JButton btnBuscar;
    JButton btnSalir;

    public BuscarDialog() {
        JOptionPane.showMessageDialog(null, """
                Buscador de trabajadores:
                - Seleccione un campo (DNI, Nombre...)
                - Escriba lo que desea buscar (puede ser una parte del texto)
                - Deje el campo vacío para ver a todos los trabajadores.""", "¿Cómo buscar?", JOptionPane.INFORMATION_MESSAGE);

        setResizable(false);
        setTitle("Buscar Trabajadores");
        setSize(750, 725);
        setLayout(new FlowLayout());
        setLocationRelativeTo(null);

        opciones = new JComboBox<>(columnas);

        filtrado = new JTextField(15);
        pFiltado = new JPanel();

        btnBuscar = new JButton("Buscar");
        btnBuscar.addActionListener(this);
        add(btnBuscar);

        pFiltado.add(opciones);
        pFiltado.add(filtrado);
        pFiltado.add(btnBuscar);
        add(pFiltado);

        trajadores = AccesoTrabajador.obtenerTrabajadores();
        datos = AccesoTrabajador.listarTrabajadores(trajadores);

        DefaultTableModel modelo = new DefaultTableModel(datos, columnas) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Bloquea TODAS las celdas
            }
        };
        tabla = new JTable(modelo);

        tabla.setAutoCreateRowSorter(true);

        tabla.setRowHeight(25);

        JScrollPane jsp = new JScrollPane(tabla);
        jsp.setPreferredSize(new Dimension(700, 600));
        add(jsp);

        setVisible(true);

        btnSalir = new JButton("Salir");
        btnSalir.addActionListener(this);
        add(btnSalir);

        setVisible(true);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnBuscar) {
            String columna = opciones.getSelectedItem().toString();
            String valor = filtrado.getText();

            try {
                trajadores = AccesoTrabajador.buscarPorFiltro(columna, valor);
                refrescarDatos(trajadores);
            } catch (TrabajadorException | BDException ex) {
                UtilsDialog.mensajeError(ex);
            }
        } else if (e.getSource() == btnSalir) {
            dispose();
        }
    }

    public void refrescarDatos(List<Trabajador> filtrado) {
        datos = AccesoTrabajador.listarTrabajadores(filtrado);

        DefaultTableModel modelo = (DefaultTableModel) tabla.getModel();
        modelo.setDataVector(datos, columnas);
    }
}

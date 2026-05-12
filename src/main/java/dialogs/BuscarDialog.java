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
    JPanel panelBusqueda;

    /**
     * Tabla
     */
    JTable tabla;
    List<Trabajador> trabajadores;
    String[][] datos;
    String[] columnas = UtilsDialog.columnas();

    /**
     * Botones
     */
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

        panelBusqueda = UtilsDialog.barraDeBusqueda(this);
        add(panelBusqueda);

        try {
            trabajadores = AccesoTrabajador.obtenerTrabajadores();
            datos = AccesoTrabajador.listarTrabajadores(trabajadores);
        } catch (BDException e) {
            UtilsDialog.mensajeError(e);
        }

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
        try {
            if (e.getActionCommand().equals("Buscar")) {
                trabajadores = UtilsDialog.buscarPorFiltro(this, panelBusqueda);

                String[][] datos = AccesoTrabajador.listarTrabajadores(trabajadores);
                UtilsDialog.refrescarDatosFiltrados(datos, tabla, columnas);
            } else if (e.getSource() == btnSalir) {
                dispose();
            }
        }catch (BDException ex) {
            UtilsDialog.mensajeError(ex);
        }
    }
}
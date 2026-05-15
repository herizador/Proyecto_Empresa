package dialogs;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import dao.AccesoTrabajador;
import exception.BDException;
import exception.FicheroException;
import modelo.Trabajador;

/**
 * @author usuario
 */
public class ListarDialog extends JDialog implements ActionListener {
    /**
     * tabla
     */
    JTable tabla;
    JButton cerrar;
    String[][] datos;
    String[] columnas = UtilsDialog.columnas();

    /**
     * check
     */
    ImageIcon iconoCheck = UtilsDialog.iconoCheck();

    /**
     * Panel de busqueda
     */
    JPanel panelBusqueda;

    /**
     * Botones
     */
    JPanel panelBotones;
    JButton botonExportar;

    List<Trabajador> trabajadores;

    public ListarDialog() {
        setResizable(false);
        // t�tulo del di�log
        setTitle("Listado Trabajadores en la Base de datos");
        // tama�o
        setSize(750, 725);
        setLayout(new FlowLayout());
        // colocaci�n en el centro de la pantalla
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

        DefaultTableModel modelo = new DefaultTableModel(datos, columnas) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Bloquea TODAS las celdas
            }
        };

        tabla = new JTable(modelo);

        tabla.setRowHeight(25);

        // Mete la tabla en un JCrollPane
        JScrollPane jsp = new JScrollPane(tabla);
        jsp.setPreferredSize(new Dimension(700, 600));
        add(jsp);

        tabla.setAutoCreateRowSorter(true);

        botonExportar = new JButton("Exportar");
        botonExportar.addActionListener(this);

        panelBotones = new JPanel();
        panelBotones.add(botonExportar);
        add(panelBotones);

        cerrar = new JButton("Salir");
        cerrar.addActionListener(this);
        add(cerrar);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            if (e.getSource() == cerrar) {
                dispose();
            } else if (e.getActionCommand().equals("Buscar")) {
                trabajadores = UtilsDialog.buscarPorFiltro(panelBusqueda);

                String[][] datos = AccesoTrabajador.listarTrabajadores(trabajadores);
                UtilsDialog.refrescarDatosFiltrados(datos, tabla, columnas);
            } else if (e.getSource() == botonExportar) {
                    UtilsDialog.exportar(this, trabajadores);
            }
        } catch (BDException | FicheroException ex) {
            UtilsDialog.mensajeError(ex);
        }
    }
}

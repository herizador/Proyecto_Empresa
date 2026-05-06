package dialogs;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import dao.AccesoTrabajador;
import modelo.Trabajador;

/**
 * @author usuario
 */
public class ListarDialog extends JDialog implements ActionListener {
    JTable tabla;
    JButton cerrar;
    String[][] datos;

    public ListarDialog() {
        JOptionPane.showMessageDialog(null, "Presione la cabecera para ordenar.", "Mensaje", JOptionPane.INFORMATION_MESSAGE);

        setResizable(false);
        // t�tulo del di�log
        setTitle("Listado Trabajadores en la Base de datos");
        // tama�o
        setSize(750, 700);
        setLayout(new FlowLayout());
        // colocaci�n en el centro de la pantalla
        setLocationRelativeTo(null);

        // Crea un JTable, cada fila será un trabajador
        String[] columnas = {"DNI", "Nombre", "Apellidos", "Direccion", "Telefono", "Puesto"};
        List<Trabajador> trajadores = AccesoTrabajador.obtenerTrabajadores();
        datos = AccesoTrabajador.listarTrabajadores(trajadores);

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

        cerrar = new JButton("Salir");
        cerrar.addActionListener(this);
        add(cerrar);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub
        if (e.getSource() == cerrar) {
            dispose();
        }
    }
}

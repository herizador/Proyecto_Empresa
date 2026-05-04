package dialogs;

import dao.AccesoTrabajador;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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

    JButton btnCancelar;
    JPanel panel;

    public ModificarDialog() {
        String[] columnas = {"DNI", "Nombre", "Apellidos", "Direccion", "Telefono", "Puesto"};
        datos = AccesoTrabajador.listarTrabajadores();
        DefaultTableModel modelo = new DefaultTableModel(datos, columnas);
        JTable tb = new JTable(modelo);
        JScrollPane jsp = new JScrollPane(tb);
        tb.setPreferredSize(new Dimension(700, 600));
        add(jsp);


    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}

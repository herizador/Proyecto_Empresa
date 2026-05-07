package dialogs;

import dao.AccesoTrabajador;
import modelo.Trabajador;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class UtilsDialog {
    public static String rutaResouse = "src/main/resources/";

    public static String[] columnas(){
        return new String[]{"DNI", "Nombre", "Apellidos", "Direccion", "Telefono", "Puesto"};
    }

    public static ImageIcon imagenCheck(){
        ImageIcon iconoOriginal = new ImageIcon(rutaResouse + "/images/check_verde.png");
        Image imagenRedimensionada = iconoOriginal.getImage().getScaledInstance(48, 48, Image.SCALE_SMOOTH);

        return new ImageIcon(imagenRedimensionada);
    }

    public static void mensajeError(Exception ex){
        JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    }
}

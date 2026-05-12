package dialogs;

import dao.AccesoTrabajador;
import exception.BDException;
import exception.TrabajadorException;
import modelo.Trabajador;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.LinkedList;
import java.util.List;

public class UtilsDialog {
    public static String rutaResouse = "src/main/resources/";

    public static String[] columnas() {
        return new String[]{"DNI", "Nombre", "Apellidos", "Direccion", "Telefono", "Puesto"};
    }

    public static ImageIcon imagenCheck() {
        ImageIcon iconoOriginal = new ImageIcon(rutaResouse + "/images/check_verde.png");
        Image imagenRedimensionada = iconoOriginal.getImage().getScaledInstance(48, 48, Image.SCALE_SMOOTH);

        return new ImageIcon(imagenRedimensionada);
    }

    public static void mensajeError(Exception ex) {
        JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    }

    public static JPanel barraDeBusqueda(ActionListener evento) {
        JButton buscar = new JButton("Buscar");
        JPanel pBotones = new JPanel();

        String[] columnas = UtilsDialog.columnas();
        JComboBox<String> combo = new JComboBox<>(columnas);

        JTextField filtro = new JTextField(15);

        //Para que busque con enter
        filtro.addActionListener(evento);

        buscar.addActionListener(evento);

        pBotones.add(combo);
        pBotones.add(filtro);
        pBotones.add(buscar);

        return pBotones;
    }

    public static List<Trabajador> buscarPorFiltro(JPanel pBotones) {
        List<Trabajador> trabajadores = new LinkedList<>();

        String filtro = "";
        String columna = "";

        for (Component comp : pBotones.getComponents()) {
            if (comp instanceof JTextField) {
                filtro = ((JTextField) comp).getText();
            }

            if (comp instanceof JComboBox) {
                columna = ((JComboBox<?>) comp).getSelectedItem().toString();
            }
        }

        try {
            trabajadores = AccesoTrabajador.buscarPorFiltro(columna, filtro);
        } catch (TrabajadorException | BDException ex) {
            UtilsDialog.mensajeError(ex);
        }

        return trabajadores;
    }

    public static void refrescarDatosFiltrados(String[][] datos, JTable tabla, String[] columnas) {
        DefaultTableModel modelo = (DefaultTableModel) tabla.getModel();
        modelo.setDataVector(datos, columnas);
    }

    public static String guardarFichero(Component parent){
        JFileChooser archivo = new JFileChooser();
        archivo.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

        int seleccion = archivo.showSaveDialog(parent);

        File archivoSeleccionado = archivo.getSelectedFile();
        if (seleccion == JFileChooser.APPROVE_OPTION && archivoSeleccionado != null) {
            return archivoSeleccionado.getAbsolutePath();
        }

        return null;
    }
}

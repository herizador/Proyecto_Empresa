package gui;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;

import dialogs.*;
//import dialogs.ModificaDialog;
//import dialogs.VerDialog;
import exception.FicheroException;
import ficheros.FicheroDatos;
import modelo.Empresa;
import modelo.Trabajador;

/**
 * @author usuario
 */
public class EmpresaGUI extends JFrame implements ActionListener {
    String rutaResouse = "src/main/resources/";
    JButton altaTrabajador;
    JButton bajaTrabajador;
    JButton modificaTrabajador;
    JButton buscaTrabajador;
    JButton listarTrabajadores;
    JButton salir;

    public EmpresaGUI() {
        super("Gestión de personal");
        // Tamaño JFrame
        setSize(800, 750);
        // Cerrar al salir
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new GridLayout(3, 2));
        setLocationRelativeTo(null);
        // Creación de los botones y se añaden al JFrame
        // Se añade una imagen para cada botón
        altaTrabajador = new JButton("Añadir Trabajador");
        altaTrabajador.addActionListener(this);
        altaTrabajador.setIcon(new ImageIcon(rutaResouse + "images/addUser.png"));
        add(altaTrabajador);

        bajaTrabajador = new JButton("Borrar Trabajador");
        bajaTrabajador.addActionListener(this);
        bajaTrabajador.setIcon(new ImageIcon(rutaResouse + "images/removeUser.png"));
        add(bajaTrabajador);

        modificaTrabajador = new JButton("Modificar Trabajador");
        modificaTrabajador.addActionListener(this);
        modificaTrabajador.setIcon(new ImageIcon(rutaResouse + "images/editUser.png"));
        add(modificaTrabajador);

        buscaTrabajador = new JButton("Buscar Trabajador");
        buscaTrabajador.addActionListener(this);
        buscaTrabajador.setIcon(new ImageIcon(rutaResouse + "images/searchUser.png"));
        add(buscaTrabajador);

        listarTrabajadores = new JButton("Listar Trabajadores");
        listarTrabajadores.addActionListener(this);
        listarTrabajadores.setIcon(new ImageIcon(rutaResouse + "images/listarUser.png"));
        add(listarTrabajadores);

        salir = new JButton("Salir");
        salir.addActionListener(this);
        salir.setIcon(new ImageIcon(rutaResouse + "images/exit.png"));
        add(salir);
        // Visible
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Para responder a los clicks del usuario en cada botón (ActionEvent)
        // nuestra clase hace de oyente de eventos por eso implementa ActionListener
        // e implementa el método actionPerformed() pasando como parámetro un
        // ActionEvent.
        if (e.getSource() == altaTrabajador) {
            new AltaDialog();
        } else if (e.getSource() == bajaTrabajador) {
            new BajaDialog();
        } else if (e.getSource() == modificaTrabajador) {
            new ModificarDialog();
        } else if (e.getSource() == buscaTrabajador) {
            new BuscarDialog();
        } else if (e.getSource() == listarTrabajadores) {
            new ListarDialog();
        }
        // Cuando se sale se vuelca a fichero.
        else if (e.getSource() == salir) {
            System.exit(0);
        }
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        new EmpresaGUI();
    }
}

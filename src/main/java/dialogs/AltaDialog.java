package dialogs;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.List;

import javax.swing.*;

import dao.AccesoTrabajador;
import exception.BDException;
import exception.FicheroException;
import exception.TrabajadorException;
import ficheros.FicheroDatos;
import gui.Validaciones;
import modelo.Trabajador;

/**
 * 
 * @author usuario
 *
 */
public class AltaDialog extends JDialog implements ActionListener, ItemListener {

	/**
	 * Imagen de check
	 */
	ImageIcon iconoOriginal = new ImageIcon(getClass().getResource("/images/check_verde.png"));
	Image imagenRedimensionada = iconoOriginal.getImage().getScaledInstance(48, 48, Image.SCALE_SMOOTH);
	ImageIcon iconoCheck = new ImageIcon(imagenRedimensionada);

	/**
	 * Elementos del JFrame
	 */
	JLabel etiquetaDni;
	JTextField areaDni;
	JLabel etiquetaNombre;
	JTextField areaNombre;
	JLabel etiquetaApellidos;
	JTextField areaApellidos;
	JLabel etiquetaDireccion;
	JTextField areaDireccion;
	JLabel etiquetaTelefono;
	JTextField areaTelefono;
	JLabel etiquetaPuesto;
	JComboBox<String> comboPuesto;
	JButton aceptar;
	JButton cancelar;

	/**
	 * Variables a las que se pasar� el contenido de los JTextField y del combo box
	 */
	String dni = "";
	String nombre = "";
	String apellidos = "";
	String direccion = "";
	String telefono = "";
	String puesto = "";

	JPanel pDni;
	JPanel pNombre;
	JPanel pApellidos;
	JPanel pDireccion;
	JPanel pTelefono;
	JPanel pPuesto;
	JPanel pBotones;

	public AltaDialog() {
		String[] opciones = {"Manual", "Por fichero CSV"};
		int opcion = JOptionPane.showOptionDialog(null, "¿Cómo desea insertar el trabajador?", "Alta de Trabajador", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, opciones, opciones[0]);

		switch (opcion) {
			case JOptionPane.YES_OPTION:
				altaManual();
				break;
			case JOptionPane.NO_OPTION:
				insertaPorFichero();
				break;
		}
	}

	public void insertaPorFichero() {
        try {
            List<Trabajador> trajadores = FicheroDatos.obtenerTrabajadores("ficheroDatos/empresa.csv");
			AccesoTrabajador.insertarTrabajadorLista(trajadores);

			JOptionPane.showMessageDialog(null, "Trabajadores insertados exitosamente", "Exito", JOptionPane.PLAIN_MESSAGE, iconoCheck);
        } catch (FicheroException | TrabajadorException | BDException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "ERROR",
					JOptionPane.ERROR_MESSAGE);
        }
    }

	public void altaManual() {
		setResizable(false);
		// t�tulo del di�log
		setTitle("Alta Trabajador");
		setSize(300, 350);
		setLayout(new FlowLayout());

		setLocationRelativeTo(null);

		// una fila por JPanel
		pDni = new JPanel();
		pNombre = new JPanel();
		pApellidos = new JPanel();
		pDireccion = new JPanel();
		pTelefono = new JPanel();
		pPuesto = new JPanel();
		pBotones = new JPanel();

		// Se crean los elementos y se añaden
		etiquetaDni = new JLabel("DNI                 ");
		areaDni = new JTextField(15);
		// Se añaden al JPanel
		pDni.add(etiquetaDni);
		pDni.add(areaDni);

		// Se crean los elementos y se añaden
		etiquetaNombre = new JLabel("Nombre         ");
		areaNombre = new JTextField(15);
		// Se añaden al JPanel
		pNombre.add(etiquetaNombre);
		pNombre.add(areaNombre);

		// Se crean los elementos y se a�aden
		etiquetaApellidos = new JLabel("Apellidos      ");
		areaApellidos = new JTextField(15);
		// Se añaden al JPanel
		pApellidos.add(etiquetaApellidos);
		pApellidos.add(areaApellidos);

		// Se crean los elementos y se añaden
		etiquetaDireccion = new JLabel("Direccion      ");
		areaDireccion = new JTextField(15);
		// Se añaden al JPanel
		pDireccion.add(etiquetaDireccion);
		pDireccion.add(areaDireccion);

		// Se crean los elementos y se a�aden
		etiquetaTelefono = new JLabel("Telefono       ");
		areaTelefono = new JTextField(15);
		// Se añaden al JPanel
		pTelefono.add(etiquetaTelefono);
		pTelefono.add(areaTelefono);

		// Se crean los elementos y se añaden
		etiquetaPuesto = new JLabel("Puesto                         ");
		pPuesto.add(etiquetaPuesto);

		// lista desplegable
		comboPuesto = new JComboBox<>();
		comboPuesto.addItem("Elija Puesto");

		List<String> puestos = AccesoTrabajador.obtenerPuestos();
		for(String puesto: puestos){
			comboPuesto.addItem(puesto);
		}

		comboPuesto.addItemListener(this);
		pPuesto.add(comboPuesto);

		// Añadir al JDialog los JPanel
		add(pDni);
		add(pNombre);
		add(pApellidos);
		add(pDireccion);
		add(pTelefono);
		add(pPuesto);

		aceptar = new JButton("Alta");
		aceptar.addActionListener(this);
		pBotones.add(aceptar);

		cancelar = new JButton("Salir");
		cancelar.addActionListener(this);
		pBotones.add(cancelar);

		add(pBotones);

		// Visible
		setVisible(true);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
	}

	@Override
	public void itemStateChanged(ItemEvent e) {
		puesto = comboPuesto.getSelectedItem().toString();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == aceptar) {
			try {
				dni = areaDni.getText();
				nombre = areaNombre.getText();
				apellidos = areaApellidos.getText();
				direccion = areaDireccion.getText();
				telefono = areaTelefono.getText();
				if (comprobarErrores()) {
					Trabajador t = new Trabajador(dni, nombre, apellidos, direccion, telefono, puesto);
					AccesoTrabajador.altaTrabajador(t);
					JOptionPane.showMessageDialog(null, "Trabajador insertado exitosamente", "Exito", JOptionPane.PLAIN_MESSAGE, iconoCheck);
				}
			} catch (TrabajadorException ex) {
				JOptionPane.showMessageDialog(null, ex.getMessage(), "Exito", JOptionPane.PLAIN_MESSAGE, iconoCheck);
			} catch (BDException e1) {
				JOptionPane.showMessageDialog(null, e1.getMessage(), "ERROR",
						JOptionPane.ERROR_MESSAGE);
			}
		} else if (e.getSource() == cancelar) {
			dispose();
		}
	}

	/**
	 * M�todo que comprueba si no hay ning�n campo vac�o o si la longitud de los
	 * campos es la correcta
	 * 
	 * @return verdadero si esta correcto los campos
	 */
	public boolean comprobarErrores() {
		if (dni.isEmpty() || Validaciones.validarDni(dni)) {
			JOptionPane.showMessageDialog(null, "El DNI no tiene formato valido (8 numeros y 1 letra)", "Error", JOptionPane.ERROR_MESSAGE);
			return false;
		} else if (nombre.isEmpty()) {
			JOptionPane.showMessageDialog(null, "Debe introducir el nombre del trabajador", "Error",
					JOptionPane.ERROR_MESSAGE);
			return false;
		} else if (apellidos.isEmpty()) {
			JOptionPane.showMessageDialog(null, "Debe introducir los apellidos del trabajador", "Error",
					JOptionPane.ERROR_MESSAGE);
			return false;
		} else if (direccion.isEmpty()) {
			JOptionPane.showMessageDialog(null, "Debe introducir la direcci�n del trabajador", "Error",
					JOptionPane.ERROR_MESSAGE);
			return false;
		} else if (telefono.isEmpty() || !Validaciones.validarTelefono(telefono)) {
			JOptionPane.showMessageDialog(null, "El telefono debe tener longitud 9 y debe comenzar con 6,7 o 9", "Error",
					JOptionPane.ERROR_MESSAGE);
			return false;
		} else if (puesto.isEmpty()) {
			JOptionPane.showMessageDialog(null, "Debe introducir el puesto del trabajador", "Error",
					JOptionPane.ERROR_MESSAGE);
			return false;
		}
		return true;
	}

}

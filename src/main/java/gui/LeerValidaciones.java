package gui;

import javax.swing.*;

public class LeerValidaciones {
    /**
     * M�todo que comprueba si no hay ning�n campo vac�o o si la longitud de los
     * campos es la correcta
     *
     * @return verdadero si esta correcto los campos
     */
    public static boolean comprobarErrores(String dni, String nombre, String apellidos, String direccion, String telefono, String puesto) {
        if (dni.isEmpty() || !Validaciones.validarDni(dni)) {
            JOptionPane.showMessageDialog(null, "El DNI no tiene formato valido (8 numeros y 1 letra)", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        } else if (nombre.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Debe introducir el nombre del trabajador", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        } else if (apellidos.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Debe introducir los apellidos del trabajador", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        } else if (direccion.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Debe introducir la direccion del trabajador", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        } else if (telefono.isEmpty() || !Validaciones.validarTelefono(telefono)) {
            JOptionPane.showMessageDialog(null, "El telefono debe tener longitud 9 y debe comenzar con 6,7 o 9", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        } else if (puesto.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Debe introducir el puesto del trabajador", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }
}

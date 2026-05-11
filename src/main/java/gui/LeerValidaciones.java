package gui;

import javax.swing.*;

public class LeerValidaciones {
    public static boolean validarDNI(String dni) {
        boolean error = !Validaciones.validarDni(dni) || dni.isEmpty();

        if(error){
            JOptionPane.showMessageDialog(null, "El DNI no tiene formato valido (8 numeros y 1 letra)", "Error", JOptionPane.ERROR_MESSAGE);
        }

        return error;
    }

    public static boolean validarNombre(String nombre) {
        boolean error = nombre.isEmpty();

        if(error){
            JOptionPane.showMessageDialog(null, "Debe introducir el nombre del trabajador", "Error", JOptionPane.ERROR_MESSAGE);
        }

        return error;
    }

    public static boolean validarApellidos(String apellidos) {
        boolean error = apellidos.isEmpty();

        if(error){
            JOptionPane.showMessageDialog(null, "Debe introducir los apellidos del trabajador", "Error", JOptionPane.ERROR_MESSAGE);
        }

        return error;
    }

    public static boolean validarDireccion(String direccion) {
        boolean error = direccion.isEmpty();

        if(error){
            JOptionPane.showMessageDialog(null, "Debe introducir la direccion del trabajador", "Error", JOptionPane.ERROR_MESSAGE);
        }

        return error;
    }

    public static boolean validarTelefono(String telefono) {
        boolean error = !Validaciones.validarTelefono(telefono) || telefono.isEmpty();

        if(error){
            JOptionPane.showMessageDialog(null, "El telefono debe tener longitud 9 y debe comenzar con 6,7 o 9", "Error", JOptionPane.ERROR_MESSAGE);
        }

        return error;
    }

    public static boolean validarPuesto(String puesto) {
        boolean error = puesto.isEmpty();

        if(error){
            JOptionPane.showMessageDialog(null, "Debe introducir el puesto del trabajador", "Error", JOptionPane.ERROR_MESSAGE);
        }

        return puesto.isEmpty();
    }
}

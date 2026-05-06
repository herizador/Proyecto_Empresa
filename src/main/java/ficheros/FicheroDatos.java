package ficheros;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import dao.AccesoTrabajador;
import exception.FicheroException;
import exception.TrabajadorException;
import modelo.Trabajador;

/**
 * @author alumno
 */
public class FicheroDatos {

    /**
     * Escribe un ArrayList en el fichero
     *
     * @param ruta
     * @param trabajadores
     */
    public static void escribirTrabajadores(String ruta, ArrayList<Trabajador> trabajadores) {

        DataOutputStream fichero = null;
        try {
            fichero = new DataOutputStream(new FileOutputStream(ruta));
            for (int i = 0; i < trabajadores.size(); i++) {
                fichero.writeUTF(trabajadores.get(i).getDni());
                fichero.writeUTF(trabajadores.get(i).getNombre());
                fichero.writeUTF(trabajadores.get(i).getApellidos());
                fichero.writeUTF(trabajadores.get(i).getDireccion());
                fichero.writeUTF(trabajadores.get(i).getTelefono());
                fichero.writeUTF(trabajadores.get(i).getPuesto());
            }
        } catch (FileNotFoundException e1) {
            System.out.printf("Error al abrir fichero para escritura");
        } catch (IOException e) {
            System.out.printf("Error al escribir en el fichero%n");
        } finally {
            try {
                fichero.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    /**
     * Devuelve un arraylist con los trabajadores del fichero
     *
     * @param rutaFichero
     * @return
     */
    public static List<Trabajador> obtenerTrabajadores(String rutaFichero) throws FicheroException {
        BufferedReader br = null;
        ArrayList<Trabajador> trabajadoresLeidos = new ArrayList<>();
        Trabajador t;

        try {
            File ficheroLectura = new File(rutaFichero);
            FileReader fr = new FileReader(ficheroLectura);
            br = new BufferedReader(fr);

            String linea = br.readLine();

            while (linea != null) {
                linea = linea.trim();

                if (!linea.isEmpty()) {
                    String[] datos = linea.split(";");

                    if (datos.length != 6) {
                        throw new FicheroException(FicheroException.DATOS_INCOMPLETOS);
                    }

                    String dni = datos[0];
                    String nombre = datos[1];
                    String apellidos = datos[2];
                    String direccion = datos[3];
                    String telefono = datos[4];
                    String puesto = datos[5];

                    t = new Trabajador(dni, nombre, apellidos, direccion, telefono, puesto);
                    trabajadoresLeidos.add(t);
                }

                linea = br.readLine();
            }
        } catch (FileNotFoundException e) {
            throw new FicheroException(FicheroException.ERROR_RUTA_INEXISTENTE);
        } catch (IOException e) {
            throw new FicheroException(FicheroException.ERROR_AL_LEER);
        } finally {
            try {
                if (br != null) {
                    br.close();
                }
            } catch (IOException e) {
                throw new FicheroException(FicheroException.ERROR_CERRAR_BUFFER);
            }
        }

        return trabajadoresLeidos;
    }

    public static void main(String[] args) {
        List<Trabajador> trabaj = null;

        try {
            trabaj = FicheroDatos.obtenerTrabajadores("ficheroDatos\\empresa.csv");
        } catch (FicheroException e) {
            System.out.println(e.getMessage());
        }

        try {
            if (trabaj != null) {
                AccesoTrabajador.insertarTrabajadorLista(trabaj);
            }
        } catch (TrabajadorException e) {
            System.out.println(e.getMessage());
        }
    }
}

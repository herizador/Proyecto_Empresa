package ficheros;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import com.google.gson.*;

import exception.FicheroException;
import modelo.Trabajador;

/**
 * @author alumno
 */
public class FicheroDatos {
    /**
     * Devuelve un arraylist con los trabajadores del fichero
     *
     * @param rutaFichero ruta
     * @return lista
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

    public static void exportarEmpleadosCSV(List<Trabajador> trabajadores, String ruta) throws FicheroException {
        BufferedWriter bw = null;

        try{
            File fichero = new File(ruta + "/Empleados.csv");
            FileWriter fw = new FileWriter(fichero,false);
            bw = new BufferedWriter(fw);

            for(Trabajador t : trabajadores){
                bw.write(t.toStringWithSeparators());
                bw.newLine();
            }
        }catch (FileNotFoundException e) {
            throw new FicheroException(FicheroException.ERROR_RUTA_INEXISTENTE);
        } catch (IOException e) {
            throw new FicheroException(FicheroException.ERROR_AL_LEER);
        } finally {
            try {
                if (bw != null) {
                    bw.close();
                }
            } catch (IOException e) {
                throw new FicheroException(FicheroException.ERROR_CERRAR_BUFFER);
            }
        }
    }

    public static void exportarEmpleadosJSON(List<Trabajador> trabajadores, String ruta) throws FicheroException{
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(trabajadores);

        BufferedWriter bw = null;
        try{
            File fichero = new File(ruta + File.separator + "Empleados.json");
            FileWriter fw = new FileWriter(fichero,false);
            bw = new BufferedWriter(fw);

            bw.write(json);
        }catch (FileNotFoundException e) {
            throw new FicheroException(FicheroException.ERROR_RUTA_INEXISTENTE);
        } catch (IOException e) {
            throw new FicheroException(FicheroException.ERROR_AL_LEER);
        } finally {
            try {
                if (bw != null) {
                    bw.close();
                }
            } catch (IOException e) {
                throw new FicheroException(FicheroException.ERROR_CERRAR_BUFFER);
            }
        }
    }
}

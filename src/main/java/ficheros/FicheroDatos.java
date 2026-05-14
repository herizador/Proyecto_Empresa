package ficheros;

import java.io.*;
import java.nio.file.OpenOption;
import java.util.ArrayList;
import java.util.List;
import com.google.gson.*;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.bean.util.OpencsvUtils;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
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
        File fichero = new File(ruta + "/Empleados.csv");
        FileWriter fw = null;

        try {
            fw = new FileWriter(fichero, false);
            StatefulBeanToCsv<Trabajador> beanToCsv = new StatefulBeanToCsvBuilder<Trabajador>(fw).withSeparator(';').withApplyQuotesToAll(false).build();
            beanToCsv.write(trabajadores);
        } catch (IOException e) {
            throw new FicheroException(FicheroException.ERROR_AL_LEER);
        } catch (CsvRequiredFieldEmptyException | CsvDataTypeMismatchException e) {
            throw new FicheroException(FicheroException.DATOS_INCOMPLETOS);
        }finally {
            try {
                if (fw != null) {
                    fw.close();
                }
            } catch (IOException e) {
                throw new FicheroException(FicheroException.ERROR_CERRAR_BUFFER);
            }
        }
    }

    public static void exportarEmpleadosJSON(List<Trabajador> trabajadores, String ruta) throws FicheroException{
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        File fichero = new File(ruta + File.separator + "Empleados.json");
        FileWriter fw = null;

        try {
            fw = new FileWriter(fichero, false);
            gson.toJson(trabajadores, fw);
        } catch (IOException e) {
            throw new FicheroException(FicheroException.ERROR_AL_LEER);
        }finally {
            try {
                if (fw != null) {
                    fw.close();
                }
            } catch (IOException e) {
                throw new FicheroException(FicheroException.ERROR_CERRAR_BUFFER);
            }
        }
    }

    public static List<Trabajador> importarEmpleadosCSV(String ruta) throws FicheroException {
        File fichero = new File(ruta);
        FileReader fr = null;

        try{
            fr = new FileReader(fichero);

            CsvToBean<Trabajador> csvToBean = new CsvToBeanBuilder<Trabajador>(fr).withType(Trabajador.class).withSeparator(';').withMappingStrategy(null).build();

            return csvToBean.parse();
        } catch (FileNotFoundException e) {
            throw new FicheroException(FicheroException.ERROR_AL_LEER);
        }finally {
            try {
                if (fr != null) {
                    fr.close();
                }
            } catch (IOException e) {
                throw new FicheroException(FicheroException.ERROR_CERRAR_BUFFER);
            }
        }
    }

    public static void importarEmpleadosJSON(String ruta) throws FicheroException{
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

    }
}

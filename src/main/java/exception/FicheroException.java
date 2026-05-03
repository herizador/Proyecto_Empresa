package exception;

public class FicheroException extends Exception{
    public static final String DATOS_INCOMPLETOS = "Alguna linea no cumple con el formato de datos esperado";
    public static final String ERROR_CERRAR_BUFFER = "Se ha producido un error al cerrar buffer";
    public static final String ERROR_AL_LEER = "Se ha producido un error al leer";
    public static final String ERROR_RUTA_INEXISTENTE = "No se encontro un fichero con esa ruta";

    /**
     *
     * @param mensaje mensaje
     */
    public FicheroException(String mensaje){
        super(mensaje);
    }
}

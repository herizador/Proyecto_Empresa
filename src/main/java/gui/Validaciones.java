package gui;

public class Validaciones {
    public static boolean validarDni(String dni) {
        if (dni == null || !dni.matches("^[0-9]{8}[A-Z]$")) {
            return false;
        }

        int numero = Integer.parseInt(dni.substring(0, 8));
        char letraProporcionada = dni.charAt(8);

        String letrasValidas = "TRWAGMYFPDXBNJZSQVHLCKE";
        char letraCorrecta = letrasValidas.charAt(numero % 23);

        return letraProporcionada == letraCorrecta;
    }

    public static boolean validarTelefono(String telefono) {
        return telefono.matches("^[679][0-9]{8}$");
    }
}

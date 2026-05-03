package dao;

import config.ConfigMySql;
import exception.BDException;
import exception.TrabajadorException;
import modelo.Trabajador;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class AccesoTrabajador {
    public static void altaTrabajador(Trabajador trabajador) throws BDException, TrabajadorException {
        Connection conexion = null;
        PreparedStatement ps;

        String dni = trabajador.getDni();
        String nombre = trabajador.getNombre();
        String apellidos = trabajador.getApellidos();
        String direccion = trabajador.getDireccion();
        String telefono = trabajador.getTelefono();
        String puesto = trabajador.getPuesto();

        try {
            conexion = ConfigMySql.abrirConexion();

            String queryInsertar = "INSERT INTO trabajador(dni, nombre, apellidos, direccion, telefono, puesto) VALUES(?,?,?,?,?,?)";
            ps = conexion.prepareStatement(queryInsertar);
            ps.setString(1, dni);
            ps.setString(2, nombre);
            ps.setString(3, apellidos);
            ps.setString(4, direccion);
            ps.setString(5, telefono);
            ps.setString(6, puesto);
            ps.executeUpdate();
        } catch (SQLException e) {
            actualizarTrabajador(trabajador);
            throw new TrabajadorException(TrabajadorException.TRABAJADOR_EXISTENTE);
        } catch (BDException e) {
            throw new BDException(BDException.ERROR_ABRIR_CONEXION + e.getMessage());
        } finally {
            if (conexion != null) {
                ConfigMySql.cerrarConexion(conexion);
            }
        }
    }

    public static void insertarTrabajadorLista(List<Trabajador> lista) throws TrabajadorException, BDException {
        Connection conexion = null;
        PreparedStatement ps;

        try {
            conexion = ConfigMySql.abrirConexion();

            conexion.setAutoCommit(false);

            String queryInsert = "INSERT INTO trabajador(dni, nombre, apellidos, direccion, telefono, puesto) VALUES(?,?,?,?,?,?)";
            ps = conexion.prepareStatement(queryInsert);

            for (Trabajador trabajador : lista) {
                ps.setString(1, trabajador.getDni());
                ps.setString(2, trabajador.getNombre());
                ps.setString(3, trabajador.getApellidos());
                ps.setString(4, trabajador.getDireccion());
                ps.setString(5, trabajador.getTelefono());
                ps.setString(6, trabajador.getPuesto());
                ps.addBatch();
            }

            ps.executeBatch();

            conexion.commit();
        } catch (SQLException e) {
            try {
                conexion.rollback();
            } catch (SQLException ex) {
                throw new BDException(BDException.ERROR_ROOLBACK);
            }
            throw new BDException(BDException.ERROR_QUERY + e.getMessage());
        } catch (BDException e) {
            throw new BDException(BDException.ERROR_ABRIR_CONEXION + e.getMessage());
        } finally {
            if (conexion != null) {
                ConfigMySql.cerrarConexion(conexion);
            }
        }
    }

    public static void actualizarTrabajador(Trabajador trabajador) throws BDException, TrabajadorException {
        Connection conexion = null;
        PreparedStatement ps;

        String dni = trabajador.getDni();
        String nombre = trabajador.getNombre();
        String apellidos = trabajador.getApellidos();
        String direccion = trabajador.getDireccion();
        String telefono = trabajador.getTelefono();
        String puesto = trabajador.getPuesto();

        try {
            conexion = ConfigMySql.abrirConexion();

            String queryActualizar = "UPDATE trabajador SET nombre = ?, apellidos = ?, direccion = ?, telefono = ?, puesto = ?  WHERE dni = ?";
            ps = conexion.prepareStatement(queryActualizar);
            ps.setString(1, nombre);
            ps.setString(2, apellidos);
            ps.setString(3, direccion);
            ps.setString(4, telefono);
            ps.setString(5, puesto);
            ps.setString(6, dni);
            int actualizado = ps.executeUpdate();

            if (actualizado == 0) {
                throw new TrabajadorException(TrabajadorException.TRABAJADOR_INEXISTENTE);
            }
        } catch (SQLException e) {
            throw new BDException(BDException.ERROR_QUERY + e.getMessage());
        } catch (BDException e) {
            throw new BDException(BDException.ERROR_ABRIR_CONEXION + e.getMessage());
        } finally {
            if (conexion != null) {
                ConfigMySql.cerrarConexion(conexion);
            }
        }
    }

    public static void borrarTrabajador(String dni) throws BDException {
        Connection conexion = null;
        PreparedStatement ps;

        try {
            conexion = ConfigMySql.abrirConexion();
            String queryDelete = "DELETE FROM trabajador WHERE dni = ?";
            ps = conexion.prepareStatement(queryDelete);
            ps.setString(1, dni);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new BDException(BDException.ERROR_QUERY + e.getMessage());
        } catch (BDException e) {
            throw new BDException(BDException.ERROR_ABRIR_CONEXION + e.getMessage());
        } finally {
            if (conexion != null) {
                ConfigMySql.cerrarConexion(conexion);
            }
        }
    }

    public static String[][] listarTrabajadores() throws BDException {
        List<Trabajador> trabajadores = obtenerTrabajadores();
        String[][] listado = new String[trabajadores.size()][6];

        for (int i = 0; i < trabajadores.size(); i++) {
            String[] fila = new String[6];
            Trabajador trabajador = trabajadores.get(i);

            fila[0] = trabajador.getDni();
            fila[1] = trabajador.getNombre();
            fila[2] = trabajador.getApellidos();
            fila[3] = trabajador.getDireccion();
            fila[4] = trabajador.getTelefono();
            fila[5] = trabajador.getPuesto();

            listado[i] = fila;
        }

        return listado;
    }

    public static List<Trabajador> obtenerTrabajadores() throws BDException {
        Connection conexion = null;
        PreparedStatement ps;
        List<Trabajador> trabajadores = new LinkedList<>();
        try {
            conexion = ConfigMySql.abrirConexion();

            String queryConsulta = "SELECT * FROM trabajador";
            ps = conexion.prepareStatement(queryConsulta);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                String dni = rs.getString("dni");
                String nombre = rs.getString("nombre");
                String apellidos = rs.getString("apellidos");
                String direccion = rs.getString("direccion");
                String telefono = rs.getString("telefono");
                String puesto = rs.getString("puesto");

                Trabajador trabajador = new Trabajador(dni, nombre, apellidos, direccion, telefono, puesto);
                trabajadores.add(trabajador);
            }
        } catch (SQLException e) {
            throw new BDException(BDException.ERROR_QUERY + e.getMessage());
        } catch (BDException e) {
            throw new BDException(BDException.ERROR_ABRIR_CONEXION + e.getMessage());
        } finally {
            if (conexion != null) {
                ConfigMySql.cerrarConexion(conexion);
            }
        }

        return trabajadores;
    }

    public static List<String> obtenerPuestos() throws BDException {
        List<String> puestos = new LinkedList<>();
        Connection conexion = null;
        PreparedStatement ps;
        try {
            conexion = ConfigMySql.abrirConexion();

            String queryConsulta = "SELECT DISTINCT (puesto) FROM trabajador";
            ps = conexion.prepareStatement(queryConsulta);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                String puesto = rs.getString("puesto");
                puestos.add(puesto);
            }
        } catch (SQLException e) {
            throw new BDException(BDException.ERROR_QUERY + e.getMessage());
        } catch (BDException e) {
            throw new BDException(BDException.ERROR_ABRIR_CONEXION + e.getMessage());
        } finally {
            if (conexion != null) {
                ConfigMySql.cerrarConexion(conexion);
            }
        }

        return puestos;
    }
}

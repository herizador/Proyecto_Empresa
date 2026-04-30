package dao;

import config.ConfigMySql;
import exception.BDException;
import exception.TrabajadorException;
import modelo.Trabajador;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AccesoTrabajador {
    public static void insertarTrabajador(Trabajador trabajador) throws BDException, TrabajadorException {
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
            int insertado = ps.executeUpdate();

            if(insertado == 0) {
                actualizarTrabajador(trabajador);
                throw new TrabajadorException(TrabajadorException.TRABAJADOR_EXISTENTE);
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

            String queryActualizar = "UPDATE FROM trabajador SET nombre = ?, apellidos = ?, direccion = ?, telefono = ?, puesto = ?  WHERE dni = ?";
            ps = conexion.prepareStatement(queryActualizar);
            ps.setString(1, nombre);
            ps.setString(2, apellidos);
            ps.setString(3, direccion);
            ps.setString(4, telefono);
            ps.setString(5, puesto);
            ps.setString(6, dni);
            int actualizado = ps.executeUpdate();
        }catch (SQLException e) {
            throw new BDException(BDException.ERROR_QUERY + e.getMessage());
        } catch (BDException e) {
            throw new BDException(BDException.ERROR_ABRIR_CONEXION + e.getMessage());
        } finally {
            if (conexion != null) {
                ConfigMySql.cerrarConexion(conexion);
            }
        }
    }
}

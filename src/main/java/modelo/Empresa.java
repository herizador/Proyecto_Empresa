/**
 * 
 */
package modelo;

import java.util.ArrayList;

/**
 * @author alumno
 *
 */
public class Empresa {
	
	ArrayList <Trabajador> trabajadores;
	

	/**
	 * @param trabajadores
	 */
	public Empresa(ArrayList<Trabajador> trabajadores) {
		this.trabajadores = trabajadores;
	}
	
	/**
	 * Comprueba si un trabajador est� en la lista
	 * @param t
	 * @return
	 */
	public boolean esta(Trabajador t){
		for(int i=0; i<trabajadores.size(); i++){
			if(trabajadores.get(i).getDni().equals(t.getDni())){
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Devuelve la posici�n en la que se encuentra un trabajador
	 * busc�ndolo por dni
	 * @param dni
	 * @return
	 */
	public int devolverPosicion(String dni){
		for(int i=0; i<trabajadores.size(); i++){
			if (trabajadores.get(i).getDni().equals(dni)){
				return i;
			}
		}
		return -1;
	}
	
	/**
	 * Si el trabajador no est� en la lista, lo a�ade
	 * @param t
	 */
	public boolean altaTrabajador(Trabajador t){
		if(!esta(t)){
			trabajadores.add(t);
			return true;
		}
		else return false;
	}
	/**
	 * Da de baja un trabajador busc�ndolo por c�digo
	 * @param dni
	 */
	public boolean bajaTrabajador(String dni){
		int posicion = devolverPosicion(dni);
		if(posicion > -1){
			trabajadores.remove(posicion);
			return true;
		}
		else return false;
	}
	/**
	 * Devuelve un trabajador
	 * @param dni
	 * @return
	 */
	public Trabajador buscarTrabajador(String dni){
		for(int i=0; i<trabajadores.size(); i++){
			if(trabajadores.get(i).getDni().equals(dni)){
				return( trabajadores.get(i));
			}
		}
		return null;
	}
	/**
	 * Permite modificar el valor de los atributos de un objeto Trabajador
	 * @param dni
	 * @return
	 */
	public void modificarTrabajador(String dni, String nombre, String apellidos, String direccion, String telefono, String puesto){
		int posicion = devolverPosicion(dni);
		trabajadores.get(posicion).setDni(dni);
		trabajadores.get(posicion).setNombre(nombre);
		trabajadores.get(posicion).setApellidos(apellidos);
		trabajadores.get(posicion).setDireccion(direccion);
		trabajadores.get(posicion).setTelefono(telefono);
		trabajadores.get(posicion).setPuesto(puesto);
	}
	
	/**
	 * Devuelve una matriz que se utilizar� para listar los trabajadores
	 * @return
	 */
	public String[][] listarTrabajadores(){
		String [][] datos = new String[trabajadores.size()][6];
		for (int i=0; i<trabajadores.size(); i++){
			String[] fila = new String [6];

			fila[0] = trabajadores.get(i).getDni();
			fila[1] = trabajadores.get(i).getNombre();
			fila[2] = trabajadores.get(i).getApellidos();
			fila[3] = trabajadores.get(i).getDireccion();
			fila[4] = trabajadores.get(i).getTelefono();
			fila[5] = trabajadores.get(i).getPuesto();
			
			datos[i] = fila;
		}
		return datos;
	}

	public ArrayList<Trabajador> getTrabajadores() {
		return trabajadores;
	}

	public void setTrabajadores(ArrayList<Trabajador> trabajadores) {
		this.trabajadores = trabajadores;
	}
}

package controlador;

import java.sql.SQLException;
import java.util.Scanner;

import com.mysql.cj.jdbc.DatabaseMetaData;

import modelo.DBManager;

/**
 * GestionClientes
 * 
 * @Version 1.1.1 26/05/2022
 * 
 * @author Daniel Ramirez Vaquero
 */

public class GestionClientes {

	private static final String ERROR_MSG = "Error :(";

	public static void main(String[] args) {

		DBManager.loadDriver();
		DBManager.connect();

		boolean salir = false;
		do {
			salir = menuPrincipal();
		} while (!salir);

		DBManager.close();

	}

	public static boolean menuPrincipal() {
		System.out.println("");
		System.out.println("MENU PRINCIPAL");
		System.out.println("1.  Listar clientes");
		System.out.println("2.  Nuevo cliente");
		System.out.println("3.  Modificar cliente");
		System.out.println("4.  Eliminar cliente");
		System.out.println("5.  Crear tabla nueva");
		System.out.println("6.  Filtrar por ciudad");
		System.out.println("7.  Volcar a un archivo");
		System.out.println("8.  Introducir datos desde archivo");
		System.out.println("9.  Actualizar datos desde fichero");
		System.out.println("10. Eliminar datos desde fichero");
		System.out.println("0. Salir");

		Scanner in = new Scanner(System.in);

		int opcion = pideInt("Elige una opción: ");

		switch (opcion) {
		case 1:
			opcionMostrarTabla();
			return false;
		case 2:
			opcionNuevaEntrada();
			return false;
		case 3:
			opcionModificarCliente();
			return false;
		case 4:
			opcionEliminarCliente();
			return false;
		case 5:
			opcionCrearNuevaTabla();
			return false;
		case 6:
			opcionFiltraCiudad();
			return false;
		case 7:
			opcionVolcarDatos();
			return false;
		case 8:
			opcionCargarDesdeArchivo();
			return false;
		case 9:
			opcionModificarDesdeFichero();
			return false;
		case 10: 
			opcionEliminarDesdeFichero();
			return false;
		case 0:
			return true;
		default:
			System.out.println("Opción elegida incorrecta");
			return false;
		}

	}

	/**
	 * 
	 * @param mensaje mensaje a mostrar por pantalla
	 * @return valor int ingresado por teclado
	 * @exception Exception
	 */
	public static int pideInt(String mensaje) {

		while (true) {
			try {
				System.out.print(mensaje);
				Scanner in = new Scanner(System.in);
				int valor = in.nextInt();
				return valor;
			} catch (Exception e) {
				System.out.println("No has introducido un número entero. Vuelve a intentarlo.");
			}
		}
	}

	/**
	 * 
	 * @param mensaje mensaje a motrar por pantalla
	 * @return linea String ingresado por teclado
	 * @exception Exception
	 */
	public static String pideLinea(String mensaje) {

		while (true) {
			try {
				System.out.print(mensaje);
				Scanner in = new Scanner(System.in);
				String linea = in.nextLine();
				return linea;
			} catch (Exception e) {
				System.out.println("No has introducido una cadena de texto. Vuelve a intentarlo.");
			}
		}
	}
	
	public static void opcionCargarDesdeArchivo () {
		boolean res = DBManager.insertarDesdeFichero();
		if (res) {
			System.out.println("Datos cargados con exito.");
		} else {
			System.out.println("Error al cargar los datos");
		}
	}
	
	public static void opcionModificarDesdeFichero() {
		boolean res = DBManager.actualizarDesdeFichero();
		if (res) {
			System.out.println("Datos actualizados con exito");
		} else {
			System.out.println("Error al actualizar los datos");
		}
	}
	
	public static void opcionEliminarDesdeFichero() {
		boolean res = DBManager.eliminarDesdeFichero();
		if (res) {
			System.out.println("Datos eliminados con exito");
		} else {
			System.out.println("Error al eliminar los datos");
		}
	}
	
	public static void opcionVolcarDatos() {
		
		String nombreArchivo = pideLinea("Indica el nombre del archivo donde volcar la BBDD (Tenga en cuenta que si indica un archivo ya existente este se sobreescribira): ");
		Boolean res = DBManager.volcarTabla(nombreArchivo);
		
		if (res) {
			System.out.println("Datos volcados con éxito en el archivo " + nombreArchivo);
		} else {
			System.out.println("Error al volcar los datos de la tabla");
		}
	}
	
	public static void opcionFiltraCiudad() {
		String ciudad = pideLinea("Ciudad a filtrar: ");
		DBManager.printTablaFiltrada(ciudad);
	}
	
	public static void opcionCrearNuevaTabla() {
		
		String nombreTabla = pideLinea("Indica el nombre de la nueva tabla: ");
		String nombreC1 = pideLinea("Indica el campo de la columna 1: ");
		String nombreC2 = pideLinea("Indica el campo de la columna 2: ");
		
		Boolean res = DBManager.nuevaTabla(nombreTabla, nombreC1, nombreC2);
		
		if (res) {
			System.out.println("Tabla " + nombreTabla + " creada con éxito.");
		} else {
			System.out.println("La tabla ya existe en la BBDD o ha ocurrido un error inesperado.");
		}
		
	}

	public static void opcionMostrarTabla() {
		System.out.println("Listado de Clientes:");
		DBManager.printTablaClientes();
	}

	public static void opcionNuevaEntrada() {
		Scanner in = new Scanner(System.in);

		System.out.println("Introduce los datos:");
		String nombre = pideLinea("Nombre: ");
		String ciudad = pideLinea("Ciudad: ");

		boolean res = DBManager.insertCliente(nombre, ciudad);

		if (res) {
			System.out.println("Cliente registrado correctamente");
		} else {
			System.out.println(ERROR_MSG);
		}
	}

	public static void opcionModificarCliente() {
		Scanner in = new Scanner(System.in);

		int id = pideInt("Indica el id del cliente a modificar: ");

		// Comprobamos si existe el cliente
		if (!DBManager.existsCliente(id)) {
			System.out.println("El cliente " + id + " no existe.");
			return;
		}

		// Mostramos datos del cliente a modificar
		DBManager.printCliente(id);

		// Solicitamos los nuevos datos
		String nombre = pideLinea("Nuevo nombre: ");
		String ciudad = pideLinea("Nueva ciudad: ");

		// Registramos los cambios
		boolean res = DBManager.updateCliente(id, nombre, ciudad);

		if (res) {
			System.out.println("Cliente modificado correctamente");
		} else {
			System.out.println(ERROR_MSG);
		}
	}

	public static void opcionEliminarCliente() {
		Scanner in = new Scanner(System.in);

		int id = pideInt("Indica el id del cliente a eliminar: ");

		// Comprobamos si existe el cliente
		if (!DBManager.existsCliente(id)) {
			System.out.println("El cliente " + id + " no existe.");
			return;
		}

		// Eliminamos el cliente
		boolean res = DBManager.deleteCliente(id);

		if (res) {
			System.out.println("Cliente eliminado correctamente");
		} else {
			System.out.println(ERROR_MSG);
		}
	}
}

package controlador;

import java.util.Scanner;
import modelo.DBManager;

/**
 * GestionClientes
 * 
 * @version 1.1.2 27/05/2022
 * 
 * @author Daniel Ramirez Vaquero
 */
public class GestionClientes {

	private static final String ERROR_MSG = "Error :(";

	/**
	 * Principal desde el cual cargarmos el driver JDBC, conectamos con la BBDD y entramos al menu.
	 * 
	 * @param args args
	 */
	public static void main(String[] args) {

		// Cargamos el driver JDBC y establecemos conexion con la BBDD
		DBManager.loadDriver();
		DBManager.connect();

		boolean salir = false;
		do {
			salir = menuPrincipal();
		} while (!salir);

		DBManager.close();

	}

	/**
	 * Menu principal de opciones
	 * 
	 * @return true si se elige la opcion salir, false de lo contrario
	 */
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

		int opcion = pideInt("Elige una opcion: ");

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
			System.out.println("Opcion elegida incorrecta");
			return false;
		}
	}

	/**
	 * LLama a la funcion printTablaClientes para mostrar la tabla
	 * @see modelo.DBManager#printTablaClientes()
	 * 
	 * @version 1.0
	 * @since 1.0.0
	 */
	public static void opcionMostrarTabla() {
		System.out.println("Listado de Clientes:");
		DBManager.printTablaClientes();
	}
	
	/**
	 * Solicita nombre y ciudad para crear un nuevo cliente, los manda a 
	 * insertCliente y devuelve un mensaje en funcion del resultado de la operacion
	 * @see modelo.DBManager#insertCliente(String, String)
	 * 
	 * @version 1.0
	 * @since 1.0.0
	 */
	public static void opcionNuevaEntrada() {

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
	
	/**
	 * Solicita la id del cliente a modificar, comprueba si existe cliente con dicha
	 * id existCliente y si existe pide el nuevo nombre y la nueva
	 * ciudad, estos datos los manda a updateCliente para modificar
	 * los datos
	 * @see modelo.DBManager#existsCliente(int)
	 * @see modelo.DBManager#updateCliente(int, String, String)
	 * 
	 * @version 1.0
	 * @since 1.0.0
	 */
	public static void opcionModificarCliente() {

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
	
	/**
	 * Solicita la id del cliente a eliminar, comprueba si existe cliente con dicha
	 * id existCliente y si existe manda el id a para eliminarlo de la BBDD
	 * @see modelo.DBManager#existsCliente(int)
	 * @see modelo.DBManager#updateCliente(int, String, String)
	 * 
	 * @version 1.0
	 * @since 1.1.1
	 */
	public static void opcionEliminarCliente() {

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
	
	/**
	 * Solicita el nombre de la nueva tabla y el nombre de sus dos columnas (aparte
	 * de la tercera que es el id), manda esta informacion a la funcion nuevaTabla. 
	 * Muestra un mensaje en funcion del resultado de la operacion
	 * @see modelo.DBManager#nuevaTabla(String, String, String)
	 * 
	 * @version 1.1
	 * @since 1.1.0
	 */
	public static void opcionCrearNuevaTabla() {

		String nombreTabla = pideLinea("Indica el nombre de la nueva tabla: ");
		String nombreC1 = pideLinea("Indica el campo de la columna 1: ");
		String nombreC2 = pideLinea("Indica el campo de la columna 2: ");

		Boolean res = DBManager.nuevaTabla(nombreTabla, nombreC1, nombreC2);

		if (res) {
			System.out.println("Tabla " + nombreTabla + " creada con exito.");
		} else {
			System.out.println("La tabla ya existe en la BBDD o ha ocurrido un error inesperado.");
		}
	}
	
	/**
	 * Pide la ciudad por la cual filtrar y la manda al a funcion printTablaFiltrada
	 * @see modelo.DBManager#printTablaFiltrada(String)
	 * 
	 * @version 1.0
	 * @since 1.1.0
	 */
	public static void opcionFiltraCiudad() {
		String ciudad = pideLinea("Ciudad a filtrar: ");
		DBManager.printTablaFiltrada(ciudad);
	}
	
	/**
	 * Pide el nombre del archivo en el cual volcar los datos y llama a la
	 * funcion volcarTabla y muestra un mensaje en funcion del
	 * resultado de la operacion
	 * @see modelo.DBManager#volcarTabla(String)
	 * 
	 * @version 1.0
	 * @since 1.1.0
	 */
	public static void opcionVolcarDatos() {

		String nombreArchivo = pideLinea(
				"Indica el nombre del archivo donde volcar la BBDD (Tenga en cuenta que si indica un archivo ya existente este se sobreescribira): ");
		Boolean res = DBManager.volcarTabla(nombreArchivo);

		if (res) {
			System.out.println("Datos volcados con exito en el archivo " + nombreArchivo);
		} else {
			System.out.println("Error al volcar los datos de la tabla");
		}
	}
	
	/**
	 * Llama a la funcion insertarDesdeFichero y muestra un mensaje
	 * en funcion del resultado de la operacion
	 * @see modelo.DBManager#insertarDesdeFichero()
	 * 
	 * @version 1.0
	 * @since 1.1.0
	 */
	public static void opcionCargarDesdeArchivo() {
		boolean res = DBManager.insertarDesdeFichero();
		if (res) {
			System.out.println("Datos cargados con exito.");
		} else {
			System.out.println("Error al cargar los datos");
		}
	}

	/**
	 * Llama a la funcion actualizarDesdeFichero y muestra un mensaje
	 * en funcion del resultado de la operacion
	 * @see modelo.DBManager#actualizarDesdeFichero()
	 * 
	 * @version 1.0
	 * @since 1.1.1
	 */
	public static void opcionModificarDesdeFichero() {
		boolean res = DBManager.actualizarDesdeFichero();
		if (res) {
			System.out.println("Datos actualizados con exito");
		} else {
			System.out.println("Error al actualizar los datos");
		}
	}

	/**
	 * Llama a la funcion eliminarDesdeFichero y muestra un mensaje
	 * en funcion del resultado de la operacion
	 * @see modelo.DBManager#eliminarDesdeFichero()
	 * 
	 * @version 1.0
	 * @since 1.1.1
	 */
	public static void opcionEliminarDesdeFichero() {
		boolean res = DBManager.eliminarDesdeFichero();
		if (res) {
			System.out.println("Datos eliminados con exito");
		} else {
			System.out.println("Error al eliminar los datos");
		}
	}
		
	/**
	 * Pide un int y controla las excepciones si se mete un tipo de dato no valido
	 * 
	 * @param mensaje mensaje a mostrar por pantalla
	 * @return valor int ingresado por teclado
	 * 
	 * @version 1.0
	 * @since 1.0.0
	 */
	public static int pideInt(String mensaje) {

		while (true) {
			try {
				System.out.print(mensaje);
				Scanner in = new Scanner(System.in);
				return in.nextInt();
			} catch (Exception e) {
				System.out.println("No has introducido un numero entero. Vuelve a intentarlo.");
			}
		}
	}

	/**
	 * Pide una linea y controla las excepciones si se mete un tipo de dato no
	 * valido
	 * 
	 * @param mensaje mensaje a motrar por pantalla
	 * @return linea String ingresado por teclado
	 * 
	 * @version 1.0
	 * @since 1.0.0
	 */
	public static String pideLinea(String mensaje) {

		while (true) {
			try {
				System.out.print(mensaje);
				Scanner in = new Scanner(System.in);
				return in.nextLine();
			} catch (Exception e) {
				System.out.println("No has introducido una cadena de texto. Vuelve a intentarlo.");
			}
		}
	}
}

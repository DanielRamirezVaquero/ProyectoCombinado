package modelo;

import java.sql.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.io.FileNotFoundException;

/**
 * DBManager
 * 
 * @version 1.1.2 27/05/2022
 * 
 * @author Daniel Ramirez Vaquero
 *
 */
public class DBManager {

	//Conexion a la base de datos
	private static Connection conn = null;

	//Configuracion de la conexion a la base de datos
	private static final String DB_HOST = "localhost";
	private static final String DB_PORT = "3306";
	private static final String DB_NAME = "tienda";
	private static final String DB_URL = "jdbc:mysql://" + DB_HOST + ":" + DB_PORT + "/" + DB_NAME + " ?serverTimezone=UTC";
	private static final String DB_USER = "root";
	private static final String DB_PASS = "";
	private static final String DB_MSQ_CONN_OK = "CONEXION CORRECTA";
	private static final String DB_MSQ_CONN_NO = "ERROR EN LA CONEXION";

	//ConfiguraciOn de la tabla
	private static final String DB_CLI = "clientes";
	private static final String DB_CLI_SELECT = "SELECT * FROM " + DB_CLI;
	private static final String DB_CLI_ID = "id";
	private static final String DB_CLI_NOM = "nombre";
	private static final String DB_CLI_CIU = "ciudad";
	
	//Ficheros
	private static final String RUTA_VOL = "Ficheros/Volcado de datos/";
	private static final String RUTA_INS = "Ficheros/Instrucciones/";
	private static final String FI_IN = "Insertar.txt";
	private static final String FI_MOD = "Actualizar.txt";
	private static final String FI_DEL = "Eliminar.txt";
	private static final String FI_MSQ_ERR = "No se ha encontrado el fichero ";

	//////////////////////////////////////////////////
	// METODOS DE CONEXION A LA BASE DE DATOS
	//////////////////////////////////////////////////

	/**
	 * Intenta cargar el JDBC driver.
	 * 
	 * @return true si pudo cargar el driver, false en caso contrario
	 * 
	 * @version 1.0
	 * @since 1.0.0
	 */
	public static boolean loadDriver() {
		try {
			System.out.print("Cargando Driver...");
			Class.forName("com.mysql.cj.jdbc.Driver");
			System.out.println("OK!");
			return true;
		} catch (ClassNotFoundException ex) {
			ex.printStackTrace();
			return false;
		}
	}

	/**
	 * Intenta conectar con la base de datos.
	 *
	 * @return true si pudo conectarse, false en caso contrario
	 * 
	 * @version 1.0
	 * @since 1.0.0
	 */
	public static boolean connect() {
		try {
			System.out.print("Conectando a la base de datos...");
			conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
			System.out.println("OK!");
			return true;
		} catch (SQLException ex) {
			ex.printStackTrace();
			return false;
		}
	}

	/**
	 * Comprueba la conexiOn y muestra su estado por pantalla
	 *
	 * @return true si la conexion existe y es valida, false en caso contrario
	 * 
	 * @version 1.0
	 * @since 1.0.0
	 */
	public static boolean isConnected() {
		// Comprobamos estado de la conexion
		try {
			if (conn != null && conn.isValid(0)) {
				System.out.println(DB_MSQ_CONN_OK);
				return true;
			} else {
				return false;
			}
		} catch (SQLException ex) {
			System.out.println(DB_MSQ_CONN_NO);
			ex.printStackTrace();
			return false;
		}
	}

	/**
	 * Cierra la conexion con la base de datos
	 * 
	 * @version 1.0
	 * @since 1.0.0
	 */
	public static void close() {
		try {
			System.out.print("Cerrando la conexion...");
			conn.close();
			System.out.println("OK!");
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * 
	 * Metodo en el que establecemos los parametros de la tabla.
	 * 
	 * @version 1.0
	 * @since 1.0.0
	 */
	public static void parametrosTabla() {

		try {
			java.sql.DatabaseMetaData metaDatos = conn.getMetaData();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		final String DB_TAB_NAME;
	}

	//////////////////////////////////////////////////
	// METODOS DE TABLA CLIENTES
	//////////////////////////////////////////////////

	/**
	 * Obtiene toda la tabla clientes de la base de datos
	 * 
	 * @param resultSetType			Parametros del ResultSet
	 * @param resultSetConcurrency 	Parametros del ResulSet
	 * @return ResultSet con la tabla, null en caso de error
	 * 
	 * @version 2.0
	 * @since 1.0.0
	 */
	public static ResultSet getTablaClientes(int resultSetType, int resultSetConcurrency) {
		try {
			PreparedStatement stmt = conn.prepareStatement(DB_CLI_SELECT, resultSetType, resultSetConcurrency);
			return stmt.executeQuery();
		} catch (SQLException | NullPointerException ex) {
			ex.printStackTrace();
			return null;
		}
	}

	/**
	 * Obtiene la tabla clientes filtrada por la ciudad de la base de datos
	 * 
	 * @param ciudad  				Ciudad por la que vamos a filtrar la BBDD
	 * @param resultSetType			Parametros del ResultSet
	 * @param resultSetConcurrency 	Parametros del ResulSet
	 * @return Resulset con la tabla filtrada, null si hay algUn error.
	 * 
	 * @version 1.0
	 * @since 1.1.0
	 */
	public static ResultSet getTablaFiltrada(String ciudad, int resultSetType, int resultSetConcurrency) {
		try {
			String sql = DB_CLI_SELECT + " WHERE " + DB_CLI_CIU + " ='" + ciudad + "';";
			PreparedStatement stmt = conn.prepareStatement(sql, resultSetType, resultSetConcurrency);
			return stmt.executeQuery();
		} catch (SQLException ex) {
			ex.printStackTrace();
			return null;
		}
	}

	/**
	 * Imprime por pantalla el contenido de la tabla clientes
	 * 
	 * @version 1.1
	 * @since 1.0.0
	 */
	public static void printTablaClientes() {
		try {
			ResultSet rs = getTablaClientes(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);

			//Si hay primer registro podemos imprimr la tabla, si no, salimos.
			if (rs.first()) {

				do {
					int id = rs.getInt(DB_CLI_ID);
					String n = rs.getString(DB_CLI_NOM);
					String c = rs.getString(DB_CLI_CIU);
					System.out.println(id + "\t" + n + "\t" + c);
				} while (rs.next());
			} else {
				return;
			}

			rs.close();
		} catch (SQLException | NullPointerException ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * Imprime por pantalla el contenido de la tabla clientes filtrada
	 * 
	 * @param ciudad ciudad por la que va a estar filtrada la tabla
	 * 
	 * @version 1.0
	 * @since 1.1.0
	 */
	public static void printTablaFiltrada(String ciudad) {
		try {
			ResultSet rs = getTablaFiltrada(ciudad, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);

			if (!rs.first()) {
				System.out.println("No hay ningUn cliente de " + ciudad + " registrado en la BBDD.");

			} else {

				do {
					int id = rs.getInt(DB_CLI_ID);
					String n = rs.getString(DB_CLI_NOM);
					String c = rs.getString(DB_CLI_CIU);
					System.out.println(id + "\t" + n + "\t" + c);
				} while (rs.next());
			}

		} catch (SQLException ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * Vuelta el contenido de la tabla en un archivo con nombre indicado por el usuario.
	 * 
	 * @param nombreArchivo Nombre del archivo dado por el usuario
	 * @return true si ha podido volcar los datos, false de lo contrario.
	 * 
	 * @version 1.0
	 * @since 1.1.0
	 */
	public static boolean volcarTabla(String nombreArchivo) {
		try {
			File archivoVolcado = new File(RUTA_VOL + nombreArchivo + ".txt");
			FileWriter printer = new FileWriter(archivoVolcado);

			//Imprimimos el nombre de la BBDD, de la tabla y de las columnas.
			printer.write("BBDD - " + DB_NAME + "\t" + "TABLA - " + DB_CLI + "\n");
			printer.write(DB_CLI_ID + "\t" + DB_CLI_NOM + "\t" + DB_CLI_CIU + "\n");

			//Obtenemos la tabla de clientes
			ResultSet rs = getTablaClientes(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
			
			//Si hay primer registro procedemos, si no devolvemos false
			if (rs.first()) {

				do {
					int id = rs.getInt(DB_CLI_ID);
					String n = rs.getString(DB_CLI_NOM);
					String c = rs.getString(DB_CLI_CIU);
					printer.write(id + "\t" + n + "\t" + c + "\n");
				} while (rs.next());
			} else {
				rs.close();
				printer.close();
				return false;
			}

			rs.close();
			printer.close();

			return true;
		} catch (SQLException ex) {
			ex.printStackTrace();
			return false;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}

	//////////////////////////////////////////////////
	// METODOS DE UN SOLO CLIENTE
	//////////////////////////////////////////////////

	/**
	 * Solicita a la BD el cliente con id indicado
	 * 
	 * @param id id del cliente
	 * @return ResultSet con el resultado de la consulta, null en caso de error
	 * 
	 * @version 2.0
	 * @since 1.0.0
	 */
	public static ResultSet getCliente(int id) {
		try {
			// Realizamos la consulta SQL
			String sql = DB_CLI_SELECT + " WHERE " + DB_CLI_ID + "='" + id + "';";
			PreparedStatement stmt = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_UPDATABLE);
			ResultSet rs = stmt.executeQuery();

			// Si no hay primer registro entonces no existe el cliente
			if (!rs.first()) {
				return null;
			}

			// Todo bien, devolvemos el cliente
			return rs;

		} catch (SQLException ex) {
			ex.printStackTrace();
			return null;

		}
	}

	/**
	 * Comprueba si en la BD existe el cliente con id indicado
	 *
	 * @param id id del cliente
	 * @return verdadero si existe, false en caso contrario
	 * 
	 * @version 1.0
	 * @since 1.0.0
	 */
	public static boolean existsCliente(int id) {
		try {
			// Obtenemos el cliente
			ResultSet rs = getCliente(id);

			// Si rs es null, se ha producido un error
			if (rs == null) {
				return false;
			}

			// Si no existe primer registro
			if (!rs.first()) {
				rs.close();
				return false;
			}

			// Todo bien, existe el cliente
			rs.close();
			return true;

		} catch (SQLException ex) {
			ex.printStackTrace();
			return false;
		}
	}

	/**
	 * Imprime los datos del cliente con id indicado
	 *
	 * @param id id del cliente
	 * 
	 * @version 1.0
	 * @since 1.0.0
	 */
	public static void printCliente(int id) {
		try {
			// Obtenemos el cliente
			ResultSet rs = getCliente(id);
			if (rs == null || !rs.first()) {
				System.out.println("Cliente " + id + " NO EXISTE");
				return;
			}

			// Imprimimos su informaciOn por pantalla
			int cliId = rs.getInt(DB_CLI_ID);
			String nombre = rs.getString(DB_CLI_NOM);
			String ciudad = rs.getString(DB_CLI_CIU);
			System.out.println("Cliente " + cliId + "\t" + nombre + "\t" + ciudad);

		} catch (SQLException ex) {
			System.out.println("Error al solicitar cliente " + id);
			ex.printStackTrace();
		}
	}

	/**
	 * Solicita a la BD insertar un nuevo registro cliente
	 *
	 * @param nombre nombre del cliente
	 * @param ciudad ciudad del cliente
	 * @return verdadero si pudo insertarlo, false en caso contrario
	 * 
	 * @version 1.0
	 * @since 1.0.0
	 */
	public static boolean insertCliente(String nombre, String ciudad) {
		try {
			// Obtenemos la tabla clientes
			System.out.print("Insertando cliente " + nombre + "...");
			ResultSet rs = getTablaClientes(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_UPDATABLE);

			// Insertamos el nuevo registro
			rs.moveToInsertRow();
			rs.updateString(DB_CLI_NOM, nombre);
			rs.updateString(DB_CLI_CIU, ciudad);
			rs.insertRow();

			// Todo bien, cerramos ResultSet y devolvemos true
			rs.close();
			System.out.println("OK!");
			return true;

		} catch (SQLException | NullPointerException ex) {
			ex.printStackTrace();
			return false;
		}
	}

	/**
	 * Solicita a la BD modificar los datos de un cliente
	 *
	 * @param id          id del cliente a modificar
	 * @param nuevoNombre nuevo nombre del cliente
	 * @param nuevaCiudad nueva ciudad del cliente
	 * @return true si pudo modificarlo, false en caso contrario
	 * 
	 * @version 1.0
	 * @since 1.0.0
	 */
	public static boolean updateCliente(int id, String nuevoNombre, String nuevaCiudad) {
		try {
			// Obtenemos el cliente
			System.out.print("Actualizando cliente " + id + "... ");
			ResultSet rs = getCliente(id);

			// Si no existe el Resultset
			if (rs == null) {
				System.out.println("Error. ResultSet null.");
				return false;
			}

			// Si tiene un primer registro, lo eliminamos
			if (rs.first()) {
				rs.updateString(DB_CLI_NOM, nuevoNombre);
				rs.updateString(DB_CLI_CIU, nuevaCiudad);
				rs.updateRow();
				rs.close();
				System.out.println("OK!");
				return true;
			} else {
				System.out.println("ERROR. ResultSet vacIo.");
				return false;
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
			return false;
		}
	}

	/**
	 * Solicita a la BD eliminar un cliente
	 *
	 * @param id id del cliente a eliminar
	 * @return verdadero si pudo eliminarlo, false en caso contrario
	 * 
	 * @version 1.0
	 * @since 1.0.0
	 */
	public static boolean deleteCliente(int id) {
		try {
			System.out.print("Eliminando cliente " + id + "... ");

			// Obtenemos el cliente
			ResultSet rs = getCliente(id);

			// Si no existe el Resultset
			if (rs == null) {
				System.out.println("ERROR. ResultSet null.");
				return false;
			}

			// Si existe y tiene primer registro, lo eliminamos
			if (rs.first()) {
				rs.deleteRow();
				rs.close();
				System.out.println("OK!");
				return true;
			} else {
				System.out.println("ERROR. ResultSet vacIo.");
				return false;
			}

		} catch (SQLException ex) {
			ex.printStackTrace();
			return false;
		}
	}

	//////////////////////////////////////////////////
	// OTROS METODOS
	//////////////////////////////////////////////////

	/**
	 * Crea una nueva tabla de 3 columnas id, columna1, columan2
	 * 
	 * @param nombreTabla 	Nombre de la tabla dado por el usuario
	 * @param columna1 		Nombre de la primera columna dada por el usuario
	 * @param columna2		Nombre de la segunda columan dada por el usuario
	 * @return true si ha podido crear la tabla, false de lo contrario.
	 * 
	 * @version 1.0
	 * @since 1.1.0
	 */
	public static boolean nuevaTabla(String nombreTabla, String columna1, String columna2) {
		System.out.println("Generando tabla " + nombreTabla + "...");
		try {
			//Sentencia SQL
			String sql = "CREATE TABLE " + nombreTabla + " (id INT PRIMARY KEY NOT NULL, " + columna1
					+ " TEXT NOT NULL, " + columna2 + " TEXT NOT NULL)";
			PreparedStatement stmt = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
			stmt.executeUpdate();
			stmt.close();
			System.out.println("OK!");
			return true;
		} catch (SQLException ex) {
			return false;
		}
	}

	/**
	 * Lee el fichero y procesa los datos para enviarlos a la funcion insertCliente
	 * @see modelo.DBManager#insertCliente(String, String)
	 * @return true si ha podido insertar los datos correctamente, false de lo contrario.
	 * 
	 * @version 1.1 
	 * @since 1.1.0
	 */
	public static boolean insertarDesdeFichero() {
		String[] lineaDividida;
		String linea;

		try {
			//Preparamos el archivo y el lector
			File insertar = new File(RUTA_INS + FI_IN);
			Scanner lector = new Scanner(insertar);

			String db_name = lector.nextLine();					//En la primera linea tenemos el nombre de la BBDD
			String tb_name = lector.nextLine();					//En la segunda el nombre de la tabla
			String column_names = lector.nextLine();			//En la tercera el nombbre de las comlumanas separadas por ","
													
			do {												//A patir de la cuarta linea tenemos los datos a insertar bajo el mismo criterio que el nombre de las columnas
						
				linea = lector.nextLine();						//Cojo la linea
				lineaDividida = linea.split(",");				//La divido por la coma
					
				String nombre = lineaDividida[0];				//La primera parte serA el nombre del cliente
				String ciudad = lineaDividida[1];				//La segunda el ciudad del cliente
				
				insertCliente(nombre, ciudad);					//Mandamos estos datos a insertar en la BBDD

			} while (lector.hasNext());							//Repetimos la operacion hasta no tener mas lineas
			
			lector.close();
			return true;
		} catch (FileNotFoundException ex) {
			System.out.println(FI_MSQ_ERR + FI_IN);
			return false;
		}
	}

	/**
	 * Lee el fichero y procesa los datos para enviarlos a la funcion updateCliente
	 * @see modelo.DBManager#updateCliente(int, String, String)
	 * 
	 * @return true se ha podido modificar los datos, false de lo contrario
	 * 
	 * @version 1.0
	 * @since 1.1.1
	 */
	public static boolean actualizarDesdeFichero() {
		String[] lineaDividida;
		String linea;
		
		try {
			//Preparamos el archivo y el lector
			File actualizar = new File (RUTA_INS + FI_MOD);
			Scanner lector = new Scanner(actualizar);
			
			String db_name = lector.nextLine();					//En la primera linea tenemos el nombre de la BBDD
			String tb_name = lector.nextLine();					//En la segunda el nombre de la tabla
			String column_names = lector.nextLine();			//En la tercera el nombbre de las comlumanas separadas por ","
			
			do {												//A patir de la cuarta linea tenemos los datos a modificar bajo el mismo criterio que el nombre de las columnas
				
				linea = lector.nextLine();						//Cojo la linea
				lineaDividida = linea.split(",");				//La divido por la coma
					
				int id = Integer.parseInt(lineaDividida[0]); 	//La primera parte sera el id del cliente
				String nuevoNombre = lineaDividida[1];			//La segunda parte sera el nuevo nombre del cliente
				String nuevaCiudad = lineaDividida[2];			//La tercera parte sera la nueva ciudad del cliete
				
				updateCliente(id, nuevoNombre, nuevaCiudad);	//Mandamos estos datos a modificar en la BBDD
				
			} while (lector.hasNext());							//Repetimos la operacion hasta no tener mas lineas
			
			lector.close();
			return true;
		} catch (FileNotFoundException ex){
			System.out.println(FI_MSQ_ERR + FI_MOD);
			return false;
		}
		
	}
	
	/**
	 * Lee el fichero y procesa los datos para enviarlos a la funcion deleteCliente
	 * @see modelo.DBManager#deleteCliente(int)
	 * 
	 * @return true se ha podido eliminar los datos correctamente, false de lo contrario
	 * @since 1.1.1
	 */
	public static boolean eliminarDesdeFichero() {
		String[] lineaDividida;
		String linea;
		
		try {
			//Preparo el archivo y el lector
			File eliminar = new File (RUTA_INS + FI_DEL);
			Scanner lector = new Scanner(eliminar);
			
			String db_name = lector.nextLine();					//En la primera linea tenemos el nombre de la BBDD
			String tb_name = lector.nextLine();					//El la segunda linea tenemos el nombre de la tabla
			
			linea = lector.nextLine();							//Cojo la linea
			lineaDividida = linea.split(",");					//La divido por la coma
			
			for (String id : lineaDividida) {					//Cada elemento de nuestra lineaDividida sera el id del cliente a eliminar
				
				int idInt = Integer.parseInt(id);				//Recogemos ese id y el hacemos parseInt
				deleteCliente(idInt);							//Mandamos a eliminar el cliente con esa id de la BBDD
			}
			
			return true;
		} catch (FileNotFoundException ex) {
			System.out.println(FI_MSQ_ERR + FI_DEL);
			return false;
		}
	}
	
}

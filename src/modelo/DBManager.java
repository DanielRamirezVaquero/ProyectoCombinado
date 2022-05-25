package modelo;
import java.sql.*;
import com.mysql.cj.jdbc.DatabaseMetaData;


/**
 * DBManager
 * 
 * @Version 1.0.4 14/05/2022
 * 
 * @author Daniel Ramirez Vaquero
 */

public class DBManager {

    // Conexi�n a la base de datos
    private static Connection conn = null;

    //Configuraci�n de la conexi�n a la base de datos
    private static final String DB_HOST = "localhost";
    private static final String DB_PORT = "3306";
    private static final String DB_NAME = "tienda";
    private static final String DB_URL = "jdbc:mysql://" + DB_HOST + ":" + DB_PORT + "/" + DB_NAME + "?serverTimezone=UTC";
    private static final String DB_USER = "root";
    private static final String DB_PASS = "";
    private static final String DB_MSQ_CONN_OK = "CONEXI�N CORRECTA";
    private static final String DB_MSQ_CONN_NO = "ERROR EN LA CONEXI�N";


    // Configuraci�n de la tabla
    private static final String DB_CLI = "clientes";
    private static final String DB_CLI_SELECT = "SELECT * FROM " + DB_CLI;
    private static final String DB_CLI_ID = "id";
    private static final String DB_CLI_NOM = "nombre";
    private static final String DB_CLI_CIU = "ciudad";
    
    
    //////////////////////////////////////////////////
    // M�TODOS DE CONEXI�N A LA BASE DE DATOS
    //////////////////////////////////////////////////
    
    
    /**
     * Intenta cargar el JDBC driver.
     * 
     * @return true si pudo cargar el driver, false en caso contrario
     * @exception ClassNotFoundException
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
     * @exception SQLException
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
     * Comprueba la conexi�n y muestra su estado por pantalla
     *
     * @return true si la conexi�n existe y es v�lida, false en caso contrario
     * @exception SQLException
     */
    public static boolean isConnected() {
        // Comprobamos estado de la conexi�n
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
     * Cierra la conexi�n con la base de datos
     * @exception SQLException
     */
    public static void close() {
        try {
            System.out.print("Cerrando la conexi�n...");
            conn.close();
            System.out.println("OK!");
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    
    /**
     * 
     * M�todo en el que establecemos los parametros de la tabla.
     */
    public static void parametrosTabla () {
    	
    	try {
			java.sql.DatabaseMetaData metaDatos = conn.getMetaData();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
    	final String DB_TAB_NAME;
    }
    
    //////////////////////////////////////////////////
    // M�TODOS DE TABLA CLIENTES
    //////////////////////////////////////////////////
        
    // Devuelve 
    // Los argumentos indican el tipo de ResultSet deseado
    /**
     * Obtiene toda la tabla clientes de la base de datos
     * @return ResultSet con la tabla, null en caso de error
     * @exception SQLException
     */
    public static ResultSet getTablaClientes() {
        try {
            PreparedStatement stmtClientes = conn.prepareStatement(DB_CLI_SELECT);
          return  stmtClientes.executeQuery();
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        } 
    }
    
    // Devuelve 
    // Los argumentos indican el tipo de ResultSet deseado
    /**
     * Obtiene toda la tabla clientes de la base de datos
     * @param resultSetType Tipo de ResultSet
     * @param resultSetConcurrency Concurrencia del ResultSet
     * @return ResultSet (del tipo indicado) con la tabla, null en caso de error
     * @exception SQLException
     */    
    public static ResultSet getTablaClientesUpdatable(int resultSetType, int resultSetConcurrency) {
        try {
        	Statement stmt = conn.createStatement(resultSetType, resultSetConcurrency);
            return stmt.executeQuery(DB_CLI_SELECT);
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }

    }

    /**
     * Imprime por pantalla el contenido de la tabla clientes
     * @exception SQLException, NullPointerException
     */
    public static void printTablaClientes() {
        try {
            ResultSet rs = getTablaClientes();
            while (rs.next()) {
                int id = rs.getInt(DB_CLI_ID);
                String n = rs.getString(DB_CLI_NOM);
                String c = rs.getString(DB_CLI_CIU);
                System.out.println(id + "\t" + n + "\t" + c);
            }
            rs.close();
        } catch (SQLException | NullPointerException ex) {
            ex.printStackTrace();
        } 
    }

    //////////////////////////////////////////////////
    // M�TODOS DE UN SOLO CLIENTE
    //////////////////////////////////////////////////
    
    /**
     * Solicita a la BD el cliente con id indicado
     * @param id id del cliente
     * @return ResultSet con el resultado de la consulta, null en caso de error
     * @exception SQLException
     */
    public static ResultSet getCliente(int id) {
        try {
            // Realizamos la consulta SQL
        	 Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
             String sql = DB_CLI_SELECT + " WHERE " + DB_CLI_ID + "='" + id + "';";
             ResultSet rs = stmt.executeQuery(sql);
             
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
     * @exception SQLException
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
     * @exception SQLException
     */
    public static void printCliente(int id) {
        try {
            // Obtenemos el cliente
            ResultSet rs = getCliente(id);
            if (rs == null || !rs.first()) {
                System.out.println("Cliente " + id + " NO EXISTE");
                return;
            }
            
            // Imprimimos su informaci�n por pantalla
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
     * @exception SQLException, NullPointerException
     */
    public static boolean insertCliente(String nombre, String direccion) {
        try {
            // Obtenemos la tabla clientes
            System.out.print("Insertando cliente " + nombre + "...");
            ResultSet rs = getTablaClientesUpdatable(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_UPDATABLE);

            // Insertamos el nuevo registro
            rs.moveToInsertRow();
            rs.updateString(DB_CLI_NOM, nombre);
            rs.updateString(DB_CLI_CIU, direccion);
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
     * @param id id del cliente a modificar
     * @param nuevoNombre nuevo nombre del cliente
     * @param nuevoCiudad nueva ciudad del cliente
     * @return verdadero si pudo modificarlo, false en caso contrario
     * @exception SQLException
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
                System.out.println("ERROR. ResultSet vac�o.");
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
     * @exception SQLException
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
                System.out.println("ERROR. ResultSet vac�o.");
                return false;
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }
}

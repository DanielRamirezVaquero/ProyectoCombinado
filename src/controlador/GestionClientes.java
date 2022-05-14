package controlador;

import java.sql.SQLException;
import java.util.Scanner;
import modelo.DBManager;

/**
 * GestionClientes
 * 
 * @Version 1.0.4 14/05/2022
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
        System.out.println("1. Listar clientes");
        System.out.println("2. Nuevo cliente");
        System.out.println("3. Modificar cliente");
        System.out.println("4. Eliminar cliente");
        System.out.println("5. Salir");
        
        Scanner in = new Scanner(System.in);
            
        int opcion = pideInt("Elige una opción: ");
        
        switch (opcion) {
            case 1:
                opcionMostrarClientes();
                return false;
            case 2:
                opcionNuevoCliente();
                return false;
            case 3:
                opcionModificarCliente();
                return false;
            case 4:
                opcionEliminarCliente();
                return false;
            case 5:
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
    public static int pideInt(String mensaje){
        
        while(true) {
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
    public static String pideLinea(String mensaje){
        
        while(true) {
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

    public static void opcionMostrarClientes() {
        System.out.println("Listado de Clientes:");
        DBManager.printTablaClientes();
    }

    public static void opcionNuevoCliente() {
        Scanner in = new Scanner(System.in);

        System.out.println("Introduce los datos del nuevo cliente:");
        String nombre = pideLinea("Nombre: ");
        String ciudad = pideLinea("Dirección: ");

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

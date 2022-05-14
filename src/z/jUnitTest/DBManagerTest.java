package z.jUnitTest;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import org.junit.Test;

import modelo.DBManager;

public class DBManagerTest {

		@Test
	public void testLoadDriver() {
		
		boolean resultado = DBManager.loadDriver();
		boolean esperado = true;
		assertEquals(resultado, esperado); //True, se ha cargado correctamente el driver
	}

	@Test
	public void testConnect() {
		
		boolean resultado = DBManager.connect();
		boolean esperado = true;
		assertEquals(resultado, esperado); //True, se ha conectado correctamente a la base.
	}

	@Test
	public void testIsConnected() {
		
		boolean resultado = DBManager.isConnected();
		boolean esperado = false;
		assertEquals(resultado, esperado); //False, comprueba correctamente el estado de la connexión con la BBDD.
	}


	@Test
	public void testGetTablaClientesParametros() {
			
	}

	@Test
	public void testGetTablaClientesPorDefecto() {
		
	}

	@Test
	public void testPrintTablaClientes() {
		
	}

	@Test
	public void testGetCliente() {
		
	}

	@Test
	public void testExistsCliente() {
		
	}

	@Test
	public void testPrintCliente() {
		
	}

	@Test
	public void testInsertCliente() {
		
	}

	@Test
	public void testUpdateCliente() {
		
	}

	@Test
	public void testDeleteCliente() {
		fail("Not yet implemented");
	}

}

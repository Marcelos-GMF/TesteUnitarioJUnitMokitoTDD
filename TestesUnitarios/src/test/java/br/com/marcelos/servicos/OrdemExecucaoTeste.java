package br.com.marcelos.servicos;

import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)//TODO ordena a execucao por ordem alfabetica.
public class OrdemExecucaoTeste {
	
	private static int contador = 0;
	
	@Test
	public void inicio() {
		contador = 1;
	}
	
	@Test
	public void verifica() {
		
		Assert.assertEquals(1, contador);
	}

}

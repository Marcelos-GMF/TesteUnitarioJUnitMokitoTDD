package br.com.marcelos.servicos;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import br.com.marcelos.entidades.Calculadora;
import br.com.marcelos.exceptions.lancarExcecaoDivisorZeroException;

public class CalculadoraTest {
	
	public static int RESULTADOSOMA = 10;
	public static int RESULTADOSUBTRACAO = 2;
	
	/* Instancia calculadora global */
	private Calculadora calcular;
	
	@Before
	public void iniciarGlobal() {
		System.out.println("iniciarGlobal");
		calcular = new Calculadora();
	}
	
	@Test
	public void deveSomarDoisValores() {
		
		int a = 5;
		int b = 5;
		
		//Cenario
		//Calculadora calcular = new Calculadora();
		
		//Acao
		int resultado = calcular.somar(a,b);
		//Verificacao
		Assert.assertEquals(RESULTADOSOMA, resultado);
		
	}
	
	@Test
	public void deveSubtrairDoisValores() {
		
		int a = 5;
		int b = 3;
		
		//Cenario
		//Calculadora calcular = new Calculadora();
		
		//Acao
		int resultado = calcular.subtrair(a,b);
		//Verificacao
		Assert.assertEquals(RESULTADOSUBTRACAO, resultado);
		
	}
	
	@Test
	public void deveDividirDoisValores() throws lancarExcecaoDivisorZeroException {
		
		int a = 10;
		int b = 5;
		
		//Cenario
		//Calculadora calcular = new Calculadora();
		
		//Acao
		int resultado = calcular.dividir(a,b);
		
		//Verificacao
		Assert.assertEquals(2, resultado);
	}
	
	@Test(expected = lancarExcecaoDivisorZeroException.class)
	public void lancarExcecaoDivisorZero() throws lancarExcecaoDivisorZeroException {
		
		int a = 10;
		int b = 0;
		
		Calculadora calcular = new Calculadora();
		
		calcular.dividir(a, b);
	}

}

package br.com.marcelos.suites;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import br.com.marcelos.servicos.CalculadoraTest;
import br.com.marcelos.servicos.CalculoValorLocacaoTest;
import br.com.marcelos.servicos.LocacaoServiceTest;

/*Classe muito interessante, nela podemos executar quantos teste for necessario*/
@RunWith(Suite.class)
//Aqui informamos todos os testes que são executados
@SuiteClasses({
	CalculadoraTest.class,
	CalculoValorLocacaoTest.class,
	LocacaoServiceTest.class
}) 

public class SuiteExecucao {
	
	/* Remover se puder!
	 * Não entendi muito bem, mas me parece que essa classe suite temos que ter uma
	 * declaracao de classe
	 */
	
	/*
	 * Caso for necessario, podemos criar os metodos before e after. que vai
	 * executar antes de todos os testes.
	 */

	@BeforeClass
	public static void antesDeTudo() {
		System.out.println("Antes de tudo!");
	}
	
	@AfterClass
	public static void depoisDeTudo() {
		System.out.println("Depois de tudo!");
	}
}

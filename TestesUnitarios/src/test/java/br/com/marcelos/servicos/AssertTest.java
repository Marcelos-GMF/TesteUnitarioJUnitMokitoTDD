package br.com.marcelos.servicos;

import org.junit.Assert;
import org.junit.Test;

import br.com.marcelos.entidades.Usuario;

public class AssertTest {
	
	@Test
	public void assertTest() {
		
		Assert.assertTrue(true);
		Assert.assertFalse(false);
		
		//Podemos trabalhar com valores:
		//Interge, short, long, e ate boolean. 
		/* A ordem importa 
		 * 1- parametro � o resultado esperado
		 * 2- � o valor atual 
		 * */
		Assert.assertEquals(1, 1);
		
		Assert.assertEquals("Erro no teste unitario",1, 1);
		
		/* Testando equals do tipo double
		 * lembrando que temos que incluir o
		 * um delta de compara��o conforme exemplo abaixo
		 * 
		 * Mas o que � esse delta?
		 * � uma vargem de erro de compara��o
		 * Por exemplo: Assert.assertEquals(0.51234,0.5123,0.0001);
		 * No caso: n�o tem erro, proque 
		 */
		Assert.assertEquals(0.51,0.51,0.01);
		
		/* Porque usar delta para casas decimais. porque tem valores infinitos
		 * por exemplo: Assert.assertEquals(Math.PI,3.14); 
		 * erro porque PI � infinito */
		Assert.assertEquals(Math.PI,3.14, 0.01);
		
		/* Comparando tipo primitivo 
		 * Assert.AssertEquals(i,i2); N�o tem como comparar
		 * Temos que fazer a convers�o 
		 * */
		int i = 5;
		Integer i2 = 5;
		Assert.assertEquals(Integer.valueOf(i),i2);
		//Transforma no valor primitivo
		Assert.assertEquals(i,i2.intValue());
		
		/* Comparacao de String */
		Assert.assertEquals("Marcelos","Marcelos");
		Assert.assertNotEquals("Marcelos","Paolla");
		
		Assert.assertTrue("Marcelos".equalsIgnoreCase("marcelos"));
		//Testando radical
		Assert.assertTrue("Marcelos".startsWith("Ma"));
		
		/* Comparando o mesmo objeto 
		 * N�o funciona sem os metodos
		 * hascode() e equals */
		Usuario user1 = new Usuario("Paolla1");
		Usuario user2 = new Usuario("Paolla1");	
		Usuario user3 = null;
		
		Assert.assertEquals(user1, user2);
		
		//Comparacao at� da mesma instancia
		Assert.assertSame(user2, user2);
		Assert.assertNotSame(user1, user2);
		
		//Verificando objeto nulo, e diferente de nulo
		Assert.assertNull(user3);
		Assert.assertNotNull(user1);
		
	}

}

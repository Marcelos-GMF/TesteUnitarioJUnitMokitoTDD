package br.com.marcelos.servicos;

import org.junit.Assert;
import org.junit.Test;

public class AssertTest {
	
	@Test
	public void assertTest() {
		
		Assert.assertTrue(true);
		Assert.assertFalse(false);
		
		//Podemos trabalhar com valores:
		//Interge, short, long, e ate boolean. 		
		Assert.assertEquals(1, 1);
	}

}

package br.com.marcelos.servicos;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.Date;

import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Test;

import br.com.marcelos.entidades.Filme;
import br.com.marcelos.entidades.Locacao;
import br.com.marcelos.entidades.Usuario;
import br.com.marcelos.utils.DataUtils;

public class LocacaoServiceTest {
	
	@Test
	public void marcelos() {
		
		//Cenario
		LocacaoService locacaoService = new LocacaoService();
		Usuario usuario = new Usuario("Marcelos teste unitario");
		Filme filme = new Filme("Marcelos Filme", 2, 5.0);
		
		//Acao
		Locacao locacao = locacaoService.alugarFilme(usuario, filme);
		
		//Verificacao
		//System.out.println(locacao.getValor() == 5.0);
		//System.out.println("Data locacao = " + DataUtils.isMesmaData(locacao.getDataLocacao(), new Date()) );
		//System.out.println("Data do retorno = " + DataUtils.isMesmaData(locacao.getDataRetorno(), DataUtils.obterDataComDiferencaDias(2)) );

		//Verifique que?
		//A ordem importa. 
		//1-Valor atual. 2-Valor do parametro/informado
		Assert.assertThat(locacao.getValor(), CoreMatchers.is(5.0));
		//Verifique que o valor da locação é igual a 5.0
		Assert.assertThat(locacao.getValor(), CoreMatchers.is(CoreMatchers.equalTo(5.0)));
		Assert.assertThat(locacao.getValor(), CoreMatchers.not(6.0));

		//Com importe statico. Botão direito source > add import
		assertThat(locacao.getValor(), is(5.0));
		//Com importe statico. Botão direito source > add import
		
		Assert.assertEquals(locacao.getValor(), 5.0, 0.01);
		Assert.assertTrue("Data locacao = ", DataUtils.isMesmaData(locacao.getDataLocacao(), new Date()));
		Assert.assertTrue("Data do retorno = ", DataUtils.isMesmaData(locacao.getDataRetorno(), DataUtils.obterDataComDiferencaDias(1)));
		
		
		Assert.assertThat("Data locacao = ", DataUtils.isMesmaData(locacao.getDataLocacao(), new Date()), is(true));
		Assert.assertThat("Data do retorno = ", DataUtils.isMesmaData(locacao.getDataRetorno(), DataUtils.obterDataComDiferencaDias(1)), is(true));
		
	}

}

package br.com.marcelos.servicos;

import static org.hamcrest.CoreMatchers.is;

import java.util.Date;

import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import org.junit.rules.ExpectedException;

import br.com.marcelos.entidades.Filme;
import br.com.marcelos.entidades.Locacao;
import br.com.marcelos.entidades.Usuario;
import br.com.marcelos.exceptions.FilmeSemEstoqueException;
import br.com.marcelos.exceptions.LocadoraException;
import br.com.marcelos.utils.DataUtils;

public class LocacaoServiceTest {

	/* Conseguimos agrupar os erros */
	@Rule
	public ErrorCollector error = new ErrorCollector();

	/* Outra forma de excecao */
	@Rule
	public ExpectedException excecaoEsperada = ExpectedException.none();

	@Test
	public void marcelosLocacao() throws Exception {

		// Cenario
		LocacaoService locacaoService = new LocacaoService();
		Usuario usuario = new Usuario("Marcelos teste unitario");
		Filme filme = new Filme("Marcelos Filme", 2, 5.0);

		// Acao
		Locacao locacao = locacaoService.alugarFilme(usuario, filme);

		error.checkThat(locacao.getValor(), CoreMatchers.is(CoreMatchers.equalTo(5.0)));
		error.checkThat("Data locacao = ", DataUtils.isMesmaData(locacao.getDataLocacao(), new Date()), is(true));
		error.checkThat("Data do retorno = ",
				DataUtils.isMesmaData(locacao.getDataRetorno(), DataUtils.obterDataComDiferencaDias(1)), is(true));

		// Verificacao
		// System.out.println(locacao.getValor() == 5.0);
		// System.out.println("Data locacao = " +
		// DataUtils.isMesmaData(locacao.getDataLocacao(), new Date()) );
		// System.out.println("Data do retorno = " +
		// DataUtils.isMesmaData(locacao.getDataRetorno(),
		// DataUtils.obterDataComDiferencaDias(2)) );

		// Verifique que?
		// A ordem importa.
		// 1-Valor atual. 2-Valor do parametro/informado
		// Assert.assertThat(locacao.getValor(), CoreMatchers.is(5.0));
		// Verifique que o valor da locação é igual a 5.0
		// Assert.assertThat(locacao.getValor(),
		// CoreMatchers.is(CoreMatchers.equalTo(5.0)));
		// Assert.assertThat(locacao.getValor(), CoreMatchers.not(6.0));

		// Com importe statico. Botão direito source > add import
		// assertThat(locacao.getValor(), is(5.0));
		// Com importe statico. Botão direito source > add import
		// Assert.assertEquals(locacao.getValor(), 5.0, 0.01);
		// Assert.assertTrue("Data locacao = ",
		// DataUtils.isMesmaData(locacao.getDataLocacao(), new Date()));
		// Assert.assertTrue("Data do retorno = ",
		// DataUtils.isMesmaData(locacao.getDataRetorno(),
		// DataUtils.obterDataComDiferencaDias(1)));
		// Assert.assertThat("Data locacao = ",
		// DataUtils.isMesmaData(locacao.getDataLocacao(), new Date()), is(true));
		// Assert.assertThat("Data do retorno = ",
		// DataUtils.isMesmaData(locacao.getDataRetorno(),
		// DataUtils.obterDataComDiferencaDias(1)), is(true));

	}

	/*
	 * Teste ELEGANTE, onde incluimos que estamos esperando exceções Onde existe uma
	 * exceção, mas não ocorre o erro
	 */
	// @Test(expected = Exception.class)
	@Test(expected = FilmeSemEstoqueException.class) // Minha exception especifica
	public void testeLocacao_semFilmeEstoque() throws Exception {

		// Cenario
		LocacaoService locacaoService = new LocacaoService();
		Usuario usuario = new Usuario("Marcelos 01");
		Filme filme = new Filme("Doutor estranho", 0, 5.0);

		// Ação
		locacaoService.alugarFilme(usuario, filme);

	}

	/* Exemplo eu controlando os erros, ao inves do JUinit */
	/*
	 * @Test public void testeLocacao_semFilmeEstoqueDev() {
	 * 
	 * // Cenario LocacaoService locacaoService = new LocacaoService(); Usuario
	 * usuario = new Usuario("Marcelos 01"); Filme filme = new
	 * Filme("Doutor estranho", 0, 5.0);
	 * 
	 * // Ação try { locacaoService.alugarFilme(usuario, filme); Forma robusta de
	 * tratar um erro, para não lancar um falso positivo Assert.
	 * fail("Erro: Tem filmes no estoque para o aluguel. tendo em vista verificar sem filmes no estoque!"
	 * ); } catch (Exception e) { Assert.assertThat(e.getMessage(),
	 * is("Sem filme no estoque!")); }
	 * 
	 * }
	 * 
	 * Terceira forma de excecao
	 * 
	 * @Test public void testeLocacao_semFilmeEstoqueExpectedException() throws
	 * Exception {
	 * 
	 * 
	 * // Cenario LocacaoService locacaoService = new LocacaoService(); Usuario
	 * usuario = new Usuario("Marcelos 01"); Filme filme = new
	 * Filme("Doutor estranho", 0, 5.0);
	 * 
	 * //Tem que colocar antes da chamada do metodo
	 * excecaoEsperada.expect(Exception.class);
	 * excecaoEsperada.expectMessage("Sem filme no estoque!");
	 * 
	 * // Ação locacaoService.alugarFilme(usuario, filme);
	 * 
	 * }
	 */
	
	  /* Exemplo eu controlando os erros, ao inves do JUinit */
	  @Test 
	  public void testeLocacao_UsuarioVazio() throws FilmeSemEstoqueException  {
	  
	    // Cenario 
	    LocacaoService locacaoService = new LocacaoService(); 
	    Filme filme = new Filme("Doutor estranho", 2, 5.0);
	    //Usuario usuario = new Usuario("Marcelos 01");
	  
	    // Ação 
		try {
			//Forma robusta de tratar um erro, para não lancar um falso positivo 
			locacaoService.alugarFilme(null, filme);
			 Assert.fail(); 
		} catch (LocadoraException e) {
			 Assert.assertThat(e.getMessage(),is("Usuario nao pode ser vazio!"));
		} 
		/*
		 * Forma Robusta consegui executar e segue o fluxo. 
		 * Diferente da forma nova
		 * Nao executa o sysout
		 * Aqui é executado o sysout no final da execucao
		 */
		System.out.println("Forma ROBUSTA");
	 }
	  
	 /* Forma nova */
	 @Test
	 public void testeLocacao_FilmeVazio() throws LocadoraException, FilmeSemEstoqueException {
		 
		// Cenario
		LocacaoService locacaoService = new LocacaoService();
		Usuario usuario = new Usuario("Marcelos 01");
		
		excecaoEsperada.expect(LocadoraException.class);
		excecaoEsperada.expectMessage("Filme não pode ser vazio!");
		
		// Ação
		locacaoService.alugarFilme(usuario, null);
		
		/*
		 * Forma nova nao executa nada depois da excecao. 
		 * Diferente da forma robusta
		 * Nao executa o sysout
		 */
		System.out.println("Forma nova");
		
	 }

}

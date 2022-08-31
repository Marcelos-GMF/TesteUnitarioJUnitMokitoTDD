package br.com.marcelos.servicos;

import static org.hamcrest.CoreMatchers.is;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.hamcrest.CoreMatchers;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Assume;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import org.junit.rules.ExpectedException;
import org.mockito.Mockito;

import br.com.marcelos.builder.FilmeBuilder;
import br.com.marcelos.builder.UsuarioBuilder;
import br.com.marcelos.daos.LocacaoDAO;
import br.com.marcelos.entidades.Filme;
import br.com.marcelos.entidades.Locacao;
import br.com.marcelos.entidades.Usuario;
import br.com.marcelos.exceptions.FilmeSemEstoqueException;
import br.com.marcelos.exceptions.LocadoraException;
import br.com.marcelos.matchers.MatchersProprios;
import br.com.marcelos.utils.DataUtils;

public class LocacaoServiceTest {
	
	private LocacaoService locacaoService;
	//private ArrayList<Filme> listarFilmes = new ArrayList<Filme>();
	
	//Contador
	//Variavel statica o JUnit nao reinicializa
	//private static int totalizador;

	/* Conseguimos agrupar os erros */
	@Rule
	public ErrorCollector error = new ErrorCollector();

	/* Outra forma de excecao */
	@Rule
	public ExpectedException excecaoEsperada = ExpectedException.none();
	
	@BeforeClass
	public static void executaAntesClass() {
	//	System.out.println("@BeforeClass");		
	}
	
	@AfterClass
	public static void executaDepoisClass() {
	//	System.out.println("@AfterClass");
	}
	
	@Before
	public void executaAntes() {
		//System.out.println("Antes da execucao");
		locacaoService = new LocacaoService();
		//Variavel statica o JUnit nao reinicializa
		//totalizador++;
		//System.out.println("Totalizador = "+totalizador);
		
		//LocacaoDAO dao = new LocacaoDAOFake();
		//Injecao de dependencia
		/** Nesse momento estamos criando um objeto mockado*/
		LocacaoDAO dao = Mockito.mock(LocacaoDAO.class);
		locacaoService.setLocacaoDAO(dao);
	}
	
	@After
	public void executaDepois() {
	//	System.out.println("Depois da execucao");
	}

	
	@Test
	public void deveAlugarFilme() throws Exception {
		
		 /* Função para executar um teste em um determinado dia da semana
		  * No caso abaixo todos os dias <> de SABADO */
		 Assume.assumeFalse(DataUtils.verificarDiaSemana(new Date(), Calendar.SATURDAY));
		

		// Cenario
		Usuario usuario = UsuarioBuilder.umUsuario().agora(); // new Usuario("Marcelos teste unitario");
		/* Cria e inclui na lista de filmes direto */
		//List<Filme> listarFilmes = Arrays.asList(new Filme("Marcelos Filme", 2, 5.0));
		List<Filme> listarFilmes = Arrays.asList(FilmeBuilder.umFilme().comValor(5.0).agora());

		// Acao
		Locacao locacao = locacaoService.alugarFilme(usuario, listarFilmes);

		error.checkThat(locacao.getValor(), CoreMatchers.is(CoreMatchers.equalTo(5.0)));
		error.checkThat("Data locacao = ", DataUtils.isMesmaData(locacao.getDataLocacao(), new Date()), is(true));
		//error.checkThat("Data do retorno = ",DataUtils.isMesmaData(locacao.getDataRetorno(), DataUtils.obterDataComDiferencaDias(1)), is(true));

		//Meu proprio Matcher
		error.checkThat(locacao.getDataRetorno(), MatchersProprios.ehHojeComDiferencaDias(1));
		//error.checkThat(locacao.getDataRetorno(), MatchersProprios.ehHoje());
		

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
	public void naoDeveAlugarFilmeSemEstoque() throws Exception {

		// Cenario
		Usuario usuario = UsuarioBuilder.umUsuario().agora(); //new Usuario("Marcelos 01");
		//List<Filme> listarFilmes = Arrays.asList(new Filme("Doutor estranho", 0, 5.0));
		List<Filme> listarFilmes = Arrays.asList(FilmeBuilder.umFilmeSemEstoque().agora());


		// Ação
		locacaoService.alugarFilme(usuario, listarFilmes);

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
	  public void naoDeveAlugarFilmeSemUsuario() throws FilmeSemEstoqueException  {
	  
	    // Cenario 
	    //List<Filme> listarFilmes = Arrays.asList(new Filme("Doutor estranho", 2, 5.0));
	    List<Filme> listarFilmes = Arrays.asList(FilmeBuilder.umFilme().agora());
	    //Usuario usuario = new Usuario("Marcelos 01");
	    
	  
	    // Ação 
		try {
			//Forma robusta de tratar um erro, para não lancar um falso positivo 
			locacaoService.alugarFilme(null, listarFilmes);
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
	 public void naoDeveAlugarFilmeSemFilme() throws LocadoraException, FilmeSemEstoqueException {
		 
		// Cenario
		Usuario usuario = UsuarioBuilder.umUsuario().agora(); //new Usuario("Marcelos 01");
		
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
	 
	 @Test
	 public void devePagar75PorcentoNoFilme3() throws LocadoraException, FilmeSemEstoqueException {
		 
		 //Cenario
		 Usuario usuario = new Usuario();
			/*
			 * List<Filme> listaFilmes = Arrays.asList( new Filme("Filme1", 2, 4.0), new
			 * Filme("Filme2", 2, 4.0), new Filme("Filme3", 2, 4.0), new Filme("Filme4", 2,
			 * 4.0), new Filme("Filme5", 2, 4.0));
			 */
		 List<Filme> listaFilmes = Arrays.asList(
				 FilmeBuilder.umFilme().agora(),
				 FilmeBuilder.umFilme().agora(),
				 FilmeBuilder.umFilme().agora(),
				 FilmeBuilder.umFilme().agora(),
				 FilmeBuilder.umFilme().agora());
		 
		 //Acao
		 Locacao resultado = locacaoService.alugarFilme(usuario, listaFilmes);
		 
		 //Verificacao
		 //Resultados esperados, de acordo com cada desconto
		 //4+4+3=11 com desconto de 25% no 3º filme
		 //Assert.assertThat(resultado.getValor(), is(11.0));
		 //4+4+3+2=13 com desconto de 50% no 4º filme
		 //Assert.assertThat(resultado.getValor(), is(13.0));
		 //4+4+3+2+1=14 com desconto de 75% no 5º filme
		 //Assert.assertThat(resultado.getValor(), is(14.0));		 
	 }
	 
	 @Test
	 public void devePagar50PorcentoNoFilme4() throws LocadoraException, FilmeSemEstoqueException {
		 
		 //Cenario
		 Usuario usuario = new Usuario();
		 List<Filme> listaFilmes = Arrays.asList(
				 new Filme("Filme1", 2, 4.0),
				 new Filme("Filme2", 2, 4.0),
				 new Filme("Filme3", 2, 4.0),
				 new Filme("Filme4", 2, 4.0));
		 //Acao
		 Locacao resultado = locacaoService.alugarFilme(usuario, listaFilmes);
		 
		 //Verificacao
		 //Resultados esperados, de acordo com cada desconto
		 //4+4+3+2=13 com desconto de 50% no 4º filme
		 Assert.assertThat(resultado.getValor(), is(13.0));	 
	 }
	 
	 @Test
	 public void devePagar25PorcentoNoFilme5() throws LocadoraException, FilmeSemEstoqueException {
		 
		 //Cenario
		 Usuario usuario = new Usuario();
		 List<Filme> listaFilmes = Arrays.asList(
				 new Filme("Filme1", 2, 4.0),
				 new Filme("Filme2", 2, 4.0),
				 new Filme("Filme3", 2, 4.0),
				 new Filme("Filme4", 2, 4.0),
				 new Filme("Filme5", 2, 4.0));
		 //Acao
		 Locacao resultado = locacaoService.alugarFilme(usuario, listaFilmes);
		 
		 //Verificacao
		 //Resultados esperados, de acordo com cada desconto
		 //4+4+3=11 com desconto de 25% no 3º filme
		 //Assert.assertThat(resultado.getValor(), is(11.0));
		 //4+4+3+2=13 com desconto de 50% no 4º filme
		 //Assert.assertThat(resultado.getValor(), is(13.0));
		 //4+4+3+2+1=14 com desconto de 75% no 5º filme
		 Assert.assertThat(resultado.getValor(), is(14.0));		 
	 }
	 
	 @Test
	 public void devePagar0PorcentoNoFilme6() throws LocadoraException, FilmeSemEstoqueException {
		 
		 //Cenario
		 Usuario usuario = new Usuario();
		 List<Filme> listaFilmes = Arrays.asList(
				 new Filme("Filme1", 2, 4.0),
				 new Filme("Filme2", 2, 4.0),
				 new Filme("Filme3", 2, 4.0),
				 new Filme("Filme4", 2, 4.0),
				 new Filme("Filme5", 2, 4.0),
				 new Filme("Filme6", 2, 4.0));
		 //Acao
		 Locacao resultado = locacaoService.alugarFilme(usuario, listaFilmes);
		 
		 //Verificacao
		 //Resultados esperados, de acordo com cada desconto
		 //4+4+3=11 com desconto de 25% no 3º filme
		 //Assert.assertThat(resultado.getValor(), is(11.0));
		 //4+4+3+2=13 com desconto de 50% no 4º filme
		 //Assert.assertThat(resultado.getValor(), is(13.0));
		 //4+4+3+2+1=14 com desconto de 75% no 5º filme
		 Assert.assertThat(resultado.getValor(), is(14.0));		 
	 }
	 
	 @Test
	 public void deveDevolverNaSegundaAoPegarNoSabado() throws LocadoraException, FilmeSemEstoqueException {
		 
		 /* Função para executar um teste em um determinado dia da semana */
		 Assume.assumeTrue(DataUtils.verificarDiaSemana(new Date(), Calendar.SATURDAY));
		 
		 //Cenario
		 Usuario usuario = UsuarioBuilder.umUsuario().agora(); //new Usuario("Marcelos");
		 List<Filme> listaFilmes = Arrays.asList(new Filme("Filme", 1, 5.0));
		 
		 //Acao
		 Locacao retorno = locacaoService.alugarFilme(usuario, listaFilmes);
		 
		 //Verificacao
		 //Antes da criacao do Matcher
		 //boolean segunda = DataUtils.verificarDiaSemana(retorno.getDataRetorno(), Calendar.MONDAY);		 
		 //Assert.assertTrue(segunda);
		 
		 //Depois do Matcher criado
		 //Assert.assertThat(retorno.getDataRetorno(), new DiaSemanaMatcher(Calendar.MONDAY));
		 //Assert.assertThat(retorno.getDataRetorno(), MatchersProprios.caiEm(Calendar.SUNDAY)); //Testar mensagem de erro
		 Assert.assertThat(retorno.getDataRetorno(), MatchersProprios.caiNumaSegunda());
		 
	 }

}

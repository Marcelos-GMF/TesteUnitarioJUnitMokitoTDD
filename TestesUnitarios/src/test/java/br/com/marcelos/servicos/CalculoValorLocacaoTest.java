 package br.com.marcelos.servicos;

import static org.hamcrest.CoreMatchers.is;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;

import br.com.marcelos.builder.FilmeBuilder;
import br.com.marcelos.daos.LocacaoDAO;
import br.com.marcelos.daos.LocacaoDAOFake;
import br.com.marcelos.entidades.Filme;
import br.com.marcelos.entidades.Locacao;
import br.com.marcelos.entidades.Usuario;
import br.com.marcelos.exceptions.FilmeSemEstoqueException;
import br.com.marcelos.exceptions.LocadoraException;


@RunWith(Parameterized.class)
public class CalculoValorLocacaoTest {
	
	@Parameter //Ordem de execucao dos parametros
	public List<Filme> listarFilmes;
	
	@Parameter(value = 1) //Porque o valor do numero é 1: porque no vetor ele é o segundo campo parametro
	public Double valorLocacao;
	
	@Parameter(value = 2) //Porque o valor do numero é 2: porque no vetor/lista é o terceiro parametro
	public String cenario;
	
	private LocacaoService locacaoService;
	
	@Before
	public void iniciarCicloVida() {
		locacaoService = new LocacaoService();
		LocacaoDAO dao = new LocacaoDAOFake();
		//Injecao de dependencia
		locacaoService.setLocacaoDAO(dao);
	}
	
	private static Filme filme1 = FilmeBuilder.umFilme().agora(); //new Filme("Filme1", 2, 4.0);	
	private static Filme filme2 = FilmeBuilder.umFilme().agora(); //new Filme("Filme2", 2, 4.0);
	private static Filme filme3 = FilmeBuilder.umFilme().agora(); //new Filme("Filme3", 2, 4.0);
	private static Filme filme4 = FilmeBuilder.umFilme().agora(); //new Filme("Filme4", 2, 4.0);
	private static Filme filme5 = FilmeBuilder.umFilme().agora(); //new Filme("Filme5", 2, 4.0);
	private static Filme filme6 = FilmeBuilder.umFilme().agora(); //new Filme("Filme6", 2, 4.0);
			
	/* Criando metodo de objetos para as colecoes */
	@Parameters(name = "Indece: {index} {2}")//Valor que vai ser impresso no log do teste.
	public static Collection<Object[]> getParametros(){
		
		return Arrays.asList(new Object[][] {
			{Arrays.asList(filme1, filme2, filme3),11.0, " 3 Filme: 25% desconto"},
			{Arrays.asList(filme1, filme2, filme3, filme4),13.0, " 4 Filme: 50% desconto"},
			{Arrays.asList(filme1, filme2, filme3, filme4, filme5),14.0, " 5 Filme: 75% desconto"},
			{Arrays.asList(filme1, filme2, filme3, filme4, filme5, filme6),14.0, " 6 Filme: 100% desconto"}
		});
	}
	
	@Test
	 public void deveCalcularValorLocacaConsiderandoDescontos() throws LocadoraException, FilmeSemEstoqueException {
		 
		 //Cenario
		 Usuario usuario = new Usuario();

		 //Acao
		 Locacao resultado = locacaoService.alugarFilme(usuario, listarFilmes);
		 
		 //Verificacao
		 //4+4+3+2+1=14 com desconto de 75% no 5º filme
		 Assert.assertThat(resultado.getValor(), is(valorLocacao));		 
	 }

}

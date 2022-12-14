package br.com.marcelos.servicos;

import java.util.Date;

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
		Usuario usuario = new Usuario("Marcelos 1");
		Filme filme = new Filme("Marcelos Filme", 2, 5.0);
		
		//Acao
		Locacao locacao = locacaoService.alugarFilme(usuario, filme);
		
		//Verificacao
		System.out.println(locacao.getValor() == 5.0);
		System.out.println("Data locacao = " + DataUtils.isMesmaData(locacao.getDataLocacao(), new Date()) );
		System.out.println("Data do retorno = " + DataUtils.isMesmaData(locacao.getDataRetorno(), DataUtils.obterDataComDiferencaDias(2)) );
	}

}

package br.com.marcelos.servicos;

import static br.com.marcelos.utils.DataUtils.adicionarDias;

import java.util.Date;
import java.util.List;

import br.com.marcelos.entidades.Filme;
import br.com.marcelos.entidades.Locacao;
import br.com.marcelos.entidades.Usuario;
import br.com.marcelos.exceptions.FilmeSemEstoqueException;
import br.com.marcelos.exceptions.LocadoraException;

public class LocacaoService {
	
	/**
	 * @param usuario
	 * @param listarFilmes
	 * @return
	 * @throws LocadoraException 
	 * @throws FilmeSemEstoqueException 
	 * @throws Exception 
	 */
	public Locacao alugarFilme(Usuario usuario, List<Filme> listarFilmes) throws LocadoraException, FilmeSemEstoqueException  {
		

		if(usuario == null) {
			throw new LocadoraException("Usuario nao pode ser vazio!");
		}
		if(listarFilmes == null || listarFilmes.isEmpty()) {
			throw new LocadoraException("Filme não pode ser vazio!");
		}
		
		for(Filme filme : listarFilmes) {
			
			if(filme.getEstoque() == 0) {
				throw new FilmeSemEstoqueException();
			}
			
		}
		
		Locacao locacao = new Locacao();
		locacao.setListaFilmes(listarFilmes);
		locacao.setUsuario(usuario);
		locacao.setDataLocacao(new Date());
		Double valorTotal = 0d;
		//Para pegar o valor total dos filmes alugados
		for(Filme filme : listarFilmes) {
			valorTotal += filme.getPrecoLocacao();
		}
		locacao.setValor(valorTotal);

		//Entrega no dia seguinte
		Date dataEntrega = new Date();
		dataEntrega = adicionarDias(dataEntrega, 1);
		locacao.setDataRetorno(dataEntrega);
		
		//Salvando a locacao...	
		//TODO adicionar metodo para salvar
		
		return locacao;
	}

}
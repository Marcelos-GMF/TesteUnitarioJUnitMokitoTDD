package br.com.marcelos.servicos;

import static br.com.marcelos.utils.DataUtils.adicionarDias;

import java.util.Date;

import br.com.marcelos.entidades.Filme;
import br.com.marcelos.entidades.Locacao;
import br.com.marcelos.entidades.Usuario;
import br.com.marcelos.exceptions.FilmeSemEstoqueException;
import br.com.marcelos.exceptions.LocadoraException;

public class LocacaoService {
	
	/**
	 * @param usuario
	 * @param filme
	 * @return
	 * @throws LocadoraException 
	 * @throws FilmeSemEstoqueException 
	 * @throws Exception 
	 */
	public Locacao alugarFilme(Usuario usuario, Filme filme) throws LocadoraException, FilmeSemEstoqueException  {
		

		if(usuario == null) {
			throw new LocadoraException("Usuario nao pode ser vazio!");
		}
		if(filme == null) {
			throw new LocadoraException("Filme não pode ser vazio!");
		}
		if(filme.getEstoque() == 0) {
			throw new FilmeSemEstoqueException();
		}
		
		Locacao locacao = new Locacao();
		locacao.setFilme(filme);
		locacao.setUsuario(usuario);
		locacao.setDataLocacao(new Date());
		locacao.setValor(filme.getPrecoLocacao());

		//Entrega no dia seguinte
		Date dataEntrega = new Date();
		dataEntrega = adicionarDias(dataEntrega, 1);
		locacao.setDataRetorno(dataEntrega);
		
		//Salvando a locacao...	
		//TODO adicionar metodo para salvar
		
		return locacao;
	}

}
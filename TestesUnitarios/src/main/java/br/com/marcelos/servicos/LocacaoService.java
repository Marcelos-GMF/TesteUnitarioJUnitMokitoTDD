package br.com.marcelos.servicos;

import static br.com.marcelos.utils.DataUtils.adicionarDias;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import br.com.marcelos.entidades.Filme;
import br.com.marcelos.entidades.Locacao;
import br.com.marcelos.entidades.Usuario;
import br.com.marcelos.exceptions.FilmeSemEstoqueException;
import br.com.marcelos.exceptions.LocadoraException;
import br.com.marcelos.utils.DataUtils;

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
		for(int i=0; i < listarFilmes.size(); i++) {
			Filme filme = listarFilmes.get(i);
			Double valorFilme = filme.getPrecoLocacao();
			
			switch (i) {
			/** Laço para calcular um desconto de 75% no 3º Filme */
			case 2: valorFilme = valorFilme * 0.75;
				break;
			/** Laço para calcular um desconto de 50% no 4º Filme */
			case 3: valorFilme = valorFilme * 0.5;
				break;
			/** Laço para calcular um desconto de 75% no 5º Filme */	
			case 4: valorFilme = valorFilme * 0.25;
				break;
			/** Laço para calcular um desconto de 100% no 6º Filme */				
			case 5: valorFilme = 0d;
				break;			
			}
			valorTotal += valorFilme;
			
			/** Laço para calcular um desconto de 75% no 3º Filme 
			if(i == 2) {
				valorFilme = valorFilme * 0.75;
			}
			/** Laço para calcular um desconto de 50% no 4º Filme 
			if(i == 3) {
				valorFilme = valorFilme * 0.5;
			}
			/** Laço para calcular um desconto de 75% no 5º Filme 
			if(i == 4) {
				valorFilme = valorFilme * 0.25;
			}
			/** Laço para calcular um desconto de 100% no 6º Filme 
			if(i == 5) {
				valorFilme = 0d;
			}
			valorTotal += valorFilme;*/
		}
		locacao.setValor(valorTotal);

		//Entrega no dia seguinte
		Date dataEntrega = new Date();
		dataEntrega = adicionarDias(dataEntrega, 1);
		if(DataUtils.verificarDiaSemana(dataEntrega, Calendar.SUNDAY)) {
			dataEntrega = adicionarDias(dataEntrega, 1);
		}
		locacao.setDataRetorno(dataEntrega);
		
		//Salvando a locacao...	
		//TODO adicionar metodo para salvar
		
		return locacao;
	}

}
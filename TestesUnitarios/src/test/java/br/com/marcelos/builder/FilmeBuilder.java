package br.com.marcelos.builder;

import br.com.marcelos.entidades.Filme;

public class FilmeBuilder {
	
	private Filme filme;
	
	private FilmeBuilder() {}
	
	/* Apenas o metodo de entreda é statico. O resto é complemento*/
	public static FilmeBuilder umFilme() {
		FilmeBuilder builder = new FilmeBuilder();
		builder.filme = new Filme();
		
		builder.filme.setEstoque(2);
		builder.filme.setNome("Filme1");
		builder.filme.setPrecoLocacao(4.0);		
		return builder;
	}
	
	/*
	 * Configuração de outra nova instancia do build, caso fique criando muitos
	 * metodos especificos
	 */
	/* Apenas o metodo de entreda é statico. O resto é complemento*/
	public static FilmeBuilder umFilmeSemEstoque() {
		FilmeBuilder builder = new FilmeBuilder();
		builder.filme = new Filme();
		
		builder.filme.setEstoque(0);
		builder.filme.setNome("Filme1");
		builder.filme.setPrecoLocacao(4.0);		
		return builder;
	}
	public FilmeBuilder semEstoque() {
		filme.setEstoque(0);
		return this;
	}
	
	public FilmeBuilder comValor(Double valor) {
		filme.setPrecoLocacao(valor);
		return this;
	}

	public Filme agora() {
		return filme; 
	}
}

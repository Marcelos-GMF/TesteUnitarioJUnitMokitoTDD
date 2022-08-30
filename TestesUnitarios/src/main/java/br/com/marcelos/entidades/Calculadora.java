package br.com.marcelos.entidades;

import br.com.marcelos.exceptions.lancarExcecaoDivisorZeroException;

public class Calculadora {

	public int somar(int a, int b) {
		return a + b;
	}

	public int subtrair(int a, int b) {
		return a - b;
	}

	public int dividir(int a, int b) throws lancarExcecaoDivisorZeroException {
		
		if(a == 0 || b == 0) {
			throw new lancarExcecaoDivisorZeroException();
		}
		
		return a / b;
	}

}

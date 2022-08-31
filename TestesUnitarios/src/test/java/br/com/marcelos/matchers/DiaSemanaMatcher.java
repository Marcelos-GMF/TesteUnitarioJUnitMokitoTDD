package br.com.marcelos.matchers;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

import br.com.marcelos.utils.DataUtils;

//TypeSafeMatcher<Date> é o valor que vamos passar como primeiro parametro
public class DiaSemanaMatcher extends TypeSafeMatcher<Date> {

	private Integer diaSemana;
	
	
	
	public DiaSemanaMatcher(Integer diaSemana) {
		this.diaSemana = diaSemana;
	}

	//Usamos para melhorar as mensagens de erro, e outras coisas afins
	public void describeTo(Description descricao) {

        Calendar data = Calendar.getInstance();
        data.set(Calendar.DAY_OF_WEEK, diaSemana);
        String dataExtenso = data.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, new Locale("pt", "BR"));
        descricao.appendText(dataExtenso);

	}

	//Este é o metodo que a comparacao será realizada.
	@Override
	protected boolean matchesSafely(Date dataArgumento) {
		return DataUtils.verificarDiaSemana(dataArgumento, diaSemana);
	}

}

package org.hinario.dao.filtro;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Condicao {

	private Campo campo;
	private Operador operador;
	private Object valor;

	public List<Operador> getOperadoresValidos() {

		ArrayList<Operador> operadoresValidos = new ArrayList<>();
		if (this.getCampo() != null) {
			if (this.getCampo().getTipo().isEnum()) {
				operadoresValidos.add(Operador.IGUAL);
				operadoresValidos.add(Operador.DIFERENTE);
			} else if (this.getCampo().getTipo().equals(Date.class)) {
				operadoresValidos.add(Operador.IGUAL);
				operadoresValidos.add(Operador.MAIOR);
				operadoresValidos.add(Operador.MAIORIGUAL);
				operadoresValidos.add(Operador.MENOR);
				operadoresValidos.add(Operador.MENORIGUAL);
				operadoresValidos.add(Operador.DIFERENTE);
			} else {
				operadoresValidos.add(Operador.IGUAL);
				operadoresValidos.add(Operador.CONTEM);
				operadoresValidos.add(Operador.NAOCONTEM);
				operadoresValidos.add(Operador.COMECACOM);
				operadoresValidos.add(Operador.TERMINACOM);
				operadoresValidos.add(Operador.CONTEMPALAVRAS);
				operadoresValidos.add(Operador.MAIOR);
				operadoresValidos.add(Operador.MAIORIGUAL);
				operadoresValidos.add(Operador.MENOR);
				operadoresValidos.add(Operador.MENORIGUAL);
				operadoresValidos.add(Operador.DIFERENTE);
			}
		}
		return operadoresValidos;
	}

	public Campo getCampo() {
		return campo;
	}

	public void setCampo(Campo campo) {
		System.out.println("Setando campo: " + campo);
		this.campo = campo;
	}

	public Operador getOperador() {
		return operador;
	}

	public void setOperador(Operador operador) {
		this.operador = operador;
	}

	public Object getValor() {
		return valor;
	}

	public void setValor(Object valor) {
		this.valor = valor;
	}

	public StatusCondicao getStatusCondicao() {
		if (this.getCampo() == null || this.getOperador() == null || this.getValor() == null || this.getValor().toString().isEmpty()) {
			return StatusCondicao.VALOR_NULO;
		}
		if (this.getCampo().getTipo().equals(Date.class) && (this.getOperador().equals(Operador.CONTEM) || this.getOperador().equals(Operador.NAOCONTEM) || this.getOperador().equals(Operador.CONTEMPALAVRAS) || this.getOperador().equals(Operador.COMECACOM) || this.getOperador().equals(Operador.TERMINACOM))) {
			return StatusCondicao.CAMPO_OPERADOR_IMCOMPATIVEIS;
		}
		if (isValorValido()) {
			return StatusCondicao.VALOR_INVALIDO;
		}

		return StatusCondicao.SUCESSO;
	}

	private boolean isValorValido() {
		return (this.valor.toString().toLowerCase().contains("drop ") || this.valor.toString().toLowerCase().contains("delete ") || this.valor.toString().toLowerCase().contains("update ") || this.valor.toString().toLowerCase().contains("insert ") || (this.valor.toString().contains("<") && this.valor.toString().contains(">")));
	}

	public boolean isValorNumerico() {
		System.out.print("Verificando Numerico ");
		boolean returN = this.getCampo() != null && this.getCampo().getTipo().isInstance(Number.class);
		System.out.println(returN);
		return returN;
	}

	public boolean isValorAlfanumerico() {
		System.out.print("Verificando Alfanumerico ");
		boolean returN = this.getCampo() == null || this.getCampo().getTipo().isInstance(String.class);
		System.out.println(returN);
		return returN;

	}

	public boolean isValorTemporal() {
		System.out.print("Verificando Temporal ");
		boolean returN = this.getCampo() != null && this.getCampo().getTipo().isInstance(Date.class);
		System.out.println(returN);
		return returN;

	}

	public boolean isValorEnumerado() {
		System.out.print("Verificando Enumerado ");
		boolean returN = this.getCampo() != null && this.getCampo().getTipo().isEnum();
		System.out.println(returN);
		return returN;
	}

}

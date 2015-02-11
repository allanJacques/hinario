package org.hinario.dao.filtro;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hinario.util.ReflectionUtil;

public class Condicao {

	private final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
	private final ReflectionUtil reflectionUtil = new ReflectionUtil();
	private Campo campo;
	private Operador operador;
	private Object valor;

	public Condicao() {
		this.sdf.setLenient(true);
	}

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

	public List<String> getValoresEnumerados() {
		ArrayList<String> returN = new ArrayList<String>();
		if (this.getCampo() != null && this.isValorEnumerado())
			for (Object oTemp : reflectionUtil.getDescricoesDoEnum(this.getCampo().getTipo())) {
				returN.add(oTemp.toString());
			}
		return returN;
	}

	public Campo getCampo() {
		return campo;
	}

	public void setCampo(Campo campo) {
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
		return this.getCampo() != null && (this.getCampo().getTipo().equals(Number.class) || this.getCampo().getTipo().equals(Long.class) || this.getCampo().getTipo().equals(Integer.class) || this.getCampo().getTipo().equals(Double.class));
	}

	public boolean isValorAlfanumerico() {
		return this.getCampo() == null || this.getCampo().getTipo().equals(String.class);

	}

	public boolean isValorTemporal() {
		return this.getCampo() != null && this.getCampo().getTipo().equals(Date.class);

	}

	public boolean isValorEnumerado() {
		return this.getCampo() != null && this.getCampo().getTipo().isEnum();
	}

	public String getValorFormatado() {
		if (isValorTemporal())
			return this.sdf.format(this.getValor());
		else
			return this.getValor().toString();
	}
}

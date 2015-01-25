package org.hinario.util.filtro;

import org.hinario.app.AppMessage;

public class Campo {

	private String chave;
	private String descricao;
	private final AppMessage appMessage = new AppMessage();

	public Campo(String chave) {
		this.chave = chave;
		this.descricao = this.appMessage.getString(chave);
	}

	public String getChave() {
		return chave;
	}

	public void setChave(String chave) {
		this.chave = chave;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((chave == null) ? 0 : chave.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Campo outro = (Campo) obj;
		if (chave == null) {
			if (outro.chave != null)
				return false;
		} else if (!chave.equals(outro.chave))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Campo [chave=" + chave + ", descricao=" + descricao + ", appMessage=" + appMessage + "]";
	}

}

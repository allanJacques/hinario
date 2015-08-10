package org.hinario.dao.filtro;

import org.hinario.app.AppMessage;

public class Campo {

	private String chave;
	private String descricao;
	private final AppMessage appMessage = new AppMessage();

	public Campo(final String chave) {
		this.chave = chave;
		this.descricao = this.appMessage.getString(chave);
	}

	public String getNome() {
		return this.chave.split("[*]")[0];
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

	public Class<? extends Object> getTipo() {
		Class<? extends Object> classe;
		try {
			classe = Class.forName(this.getClassName());
			String[] campos = this.chave.split("[*]")[0].split("[.]");
			for (int i = campos.length > 1 ? 1 : 0; i < campos.length; i++) {
				try {
					classe = classe.getDeclaredField(campos[i]).getType();
				} catch (NoSuchFieldException nsf) {
				}
			}
			return classe;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return String.class;
	}

	private String getClassName() {
		String atributoAbsoluto = this.chave.split("[*]")[1];
		String[] paths = atributoAbsoluto.split("[.]");
		String className = "";
		for (int i = 0; i < paths.length - 1; i++) {
			className += paths[i];
			if ((paths.length - i) > 2) {
				className += ".";
			}
		}

		return className;
	}

	public boolean isDeColecao() {
		return this.chave.contains("deColecao");
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
		Campo other = (Campo) obj;
		if (chave == null) {
			if (other.chave != null)
				return false;
		} else if (!chave.equals(other.chave))
			return false;
		return true;
	}

}

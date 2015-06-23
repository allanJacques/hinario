package org.hinario.negocio.managedbean;

import java.io.Serializable;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

import org.hinario.app.AppConfig;
import org.hinario.app.AppMessage;

@ManagedBean
@ApplicationScoped
public class ConfiguracaoBean implements Serializable {

	private static final long serialVersionUID = 1L;
	private final AppMessage appMessage = new AppMessage();
	private final AppConfig appConfig = AppConfig.getInstancia();

	private String email;
	private boolean email_aoEditar;
	private boolean email_aoInserir;
	private boolean email_debug;
	private String email_nome;
	private String email_senha;
	private boolean email_servico;
	private int email_servico_frequenciaEmMinutos;
	private String email_smtp_host;
	private int email_smtp_porta;
	private String email_tipoSeguranca;

	public ConfiguracaoBean() {
		carregar();
	}

	private void carregar() {

	}

	private void salvar() {

	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public boolean isEmail_aoEditar() {
		return email_aoEditar;
	}

	public void setEmail_aoEditar(boolean email_aoEditar) {
		this.email_aoEditar = email_aoEditar;
	}

	public boolean isEmail_aoInserir() {
		return email_aoInserir;
	}

	public void setEmail_aoInserir(boolean email_aoInserir) {
		this.email_aoInserir = email_aoInserir;
	}

	public boolean isEmail_debug() {
		return email_debug;
	}

	public void setEmail_debug(boolean email_debug) {
		this.email_debug = email_debug;
	}

	public String getEmail_nome() {
		return email_nome;
	}

	public void setEmail_nome(String email_nome) {
		this.email_nome = email_nome;
	}

	public String getEmail_senha() {
		return email_senha;
	}

	public void setEmail_senha(String email_senha) {
		this.email_senha = email_senha;
	}

	public boolean isEmail_servico() {
		return email_servico;
	}

	public void setEmail_servico(boolean email_servico) {
		this.email_servico = email_servico;
	}

	public int getEmail_servico_frequenciaEmMinutos() {
		return email_servico_frequenciaEmMinutos;
	}

	public void setEmail_servico_frequenciaEmMinutos(int email_servico_frequenciaEmMinutos) {
		this.email_servico_frequenciaEmMinutos = email_servico_frequenciaEmMinutos;
	}

	public String getEmail_smtp_host() {
		return email_smtp_host;
	}

	public void setEmail_smtp_host(String email_smtp_host) {
		this.email_smtp_host = email_smtp_host;
	}

	public int getEmail_smtp_porta() {
		return email_smtp_porta;
	}

	public void setEmail_smtp_porta(int email_smtp_porta) {
		this.email_smtp_porta = email_smtp_porta;
	}

	public String getEmail_tipoSeguranca() {
		return email_tipoSeguranca;
	}

	public void setEmail_tipoSeguranca(String email_tipoSeguranca) {
		this.email_tipoSeguranca = email_tipoSeguranca;
	}

}

package org.hinario.negocio.managedbean;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.NoneScoped;

@ManagedBean
@NoneScoped
public class FormatsBean {

	private final SimpleDateFormat sdfTimestamp = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
	private final SimpleDateFormat sdfData = new SimpleDateFormat("dd/MM/yyyy");

	public String getTimestamp(final Date date) {
		if (date != null)
			return sdfTimestamp.format(date);
		return null;
	}

	public String getData(final Date date) {
		if (date != null)
			return sdfData.format(date);
		return null;
	}

	public long getCurrentTimeMillis() {
		return System.currentTimeMillis();
	}
}

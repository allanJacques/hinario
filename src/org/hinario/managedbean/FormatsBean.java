package org.hinario.managedbean;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.NoneScoped;

@ManagedBean
@NoneScoped
public class FormatsBean {

	private final SimpleDateFormat sdfTimestamp = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
	private final SimpleDateFormat sdfData = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

	public String getTimestamp(final Date date) {
		return sdfTimestamp.format(date);
	}

	public String getData(final Date date) {
		return sdfData.format(date);
	}

	public long getCurrentTimeMillis() {
		return System.currentTimeMillis();
	}
}

package org.hinario.negocio.managedbean;

import java.util.List;
import java.util.Map;

import org.hinario.app.ModoEditor;
import org.hinario.dao.DAOBase;
import org.hinario.model.EntidadeBase;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortMeta;
import org.primefaces.model.SortOrder;

public class EntidadeDataModel extends LazyDataModel<EntidadeBase> {

	private static final long serialVersionUID = 1L;
	private DAOBase dao;
	private ManagedBeanBase managedBean;

	public EntidadeDataModel(final ManagedBeanBase managedBean, DAOBase dao) {
		this.managedBean = managedBean;
		this.dao = dao;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<EntidadeBase> load(int first, int pageSize, List<SortMeta> multiSortMeta, Map<String, Object> filters) {
		setRowCount(dao.count(managedBean.getFiltro()).intValue());
		List<EntidadeBase> returN = (List<EntidadeBase>) dao.getLista(first, pageSize, multiSortMeta, this.managedBean.getFiltro());
		return returN;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<EntidadeBase> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
		setRowCount(dao.count(this.managedBean.getFiltro()).intValue());
		List<EntidadeBase> returN = (List<EntidadeBase>) dao.getLista(first, pageSize, null, this.managedBean.getFiltro());
		return returN;
	}

	@Override
	public EntidadeBase getRowData(String rowKey) {
		try {
			this.managedBean.setEntidade(dao.getEntidadePorId(Long.parseLong(rowKey)));
			this.managedBean.setModoEditor(ModoEditor.EDICAO);
		} catch (NumberFormatException nfe) {
			nfe.printStackTrace();
		}
		return this.managedBean.getEntidade();
	}

	@Override
	public Object getRowKey(final EntidadeBase entidadeBase) {
		return entidadeBase.getId();
	}
}
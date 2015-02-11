package org.hinario.util;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public class ReflectionUtil {

	public List<String> getDescricoesDoEnum(final Class<? extends Object> classeDoEnum) {
		ArrayList<String> returN = new ArrayList<>();
		try {
			for (Object oTemp : (Object[]) classeDoEnum.getMethod("values", null).invoke(null, null)) {
				returN.add(oTemp.toString());
			}
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
		}
		return returN;
	}

	public Object getEnumPorDescricao(final Class<? extends Object> classeDoEnum, final String descricao) {
		try {
			for (Object oTemp : (Object[]) classeDoEnum.getMethod("values", null).invoke(null, null)) {
				String descricaoTemp = oTemp.getClass().getMethod("getDescricao").invoke(oTemp).toString();
				if (descricao.equals(descricaoTemp))
					return oTemp;
			}
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
		}
		return null;
	}
}

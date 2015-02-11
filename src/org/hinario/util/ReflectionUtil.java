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
		System.out.println("classe do Enum: " + classeDoEnum);
		System.out.println("Descrição: " + descricao);
		try {
			Object[] valores = (Object[]) classeDoEnum.getMethod("values", null).invoke(null, null);
			for (Object oTemp : valores) {
				System.out.println(oTemp.getClass() + oTemp.toString());
				String descricaoTemp = oTemp.getClass().getMethod("getDescricao").invoke(oTemp).toString();
				System.out.println(descricaoTemp);
				System.out.println("Chegou no if");
				if (descricao.equals(descricaoTemp)) {
					System.out.println("=====================================================================================================Passou no if, retornado " + oTemp);
					return oTemp;
				}
			}
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
		}
		return null;
	}
}

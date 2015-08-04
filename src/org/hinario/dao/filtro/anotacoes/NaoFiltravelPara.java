package org.hinario.dao.filtro.anotacoes;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.hinario.model.EntidadeBase;

@Target(value = { ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface NaoFiltravelPara {
	Class<? extends EntidadeBase>[] value();
}

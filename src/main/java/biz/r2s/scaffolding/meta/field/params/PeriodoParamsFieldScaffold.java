package biz.r2s.scaffolding.meta.field.params;

import biz.r2s.scaffolding.meta.field.TypeFieldScaffold;

/**
 * Created by raphael on 24/07/15.
 */
public class PeriodoParamsFieldScaffold  extends InputParamsFieldScaffold {

    String campoInicio;
    String campoFim;

    public PeriodoParamsFieldScaffold(){
        this.setCampos("campoInicio");
        this.setCampos("campoFim");
    }

    @Override
    public TypeFieldScaffold getType() {
        return TypeFieldScaffold.PERIODO;
    }

	public String getCampoInicio() {
		return campoInicio;
	}

	public void setCampoInicio(String campoInicio) {
		this.campoInicio = campoInicio;
	}

	public String getCampoFim() {
		return campoFim;
	}

	public void setCampoFim(String campoFim) {
		this.campoFim = campoFim;
	}
    
    
}

package biz.r2s.scaffolding.meta.field.params;

import java.util.Locale;

import biz.r2s.scaffolding.meta.field.TypeFieldScaffold;

/**
 * Created by raphael on 24/07/15.
 */
public class ReaisParamsFieldScaffold extends InputParamsFieldScaffold {

    Locale localMoeda;

    public ReaisParamsFieldScaffold(){
        this.setCampos("localMoeda");
    }

    @Override
    public TypeFieldScaffold getType() {
        return TypeFieldScaffold.REAIS;
    }

	public Locale getLocalMoeda() {
		return localMoeda;
	}

	public void setLocalMoeda(Locale localMoeda) {
		this.localMoeda = localMoeda;
	}
    
    
}

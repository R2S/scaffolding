package biz.r2s.scaffolding.extractor.scaffolding;

import java.lang.reflect.Field;
import java.util.Map;

import biz.r2s.core.util.ObjectUtil;
import biz.r2s.scaffolding.meta.ResourceUrlScaffold;
import biz.r2s.scaffolding.meta.field.FieldScaffold;
import biz.r2s.scaffolding.meta.field.TypeFieldScaffold;
import biz.r2s.scaffolding.meta.field.params.DataTableParamsFieldScaffold;
import biz.r2s.scaffolding.meta.field.params.ParamsFactory;
import biz.r2s.scaffolding.meta.field.params.ParamsFieldScaffold;
import biz.r2s.scaffolding.meta.field.params.Select2AjaxParamsFieldScaffold;

/**
 * Created by raphael on 06/08/15.
 */
public class ParamsScaffoldingExtrator {

	DataTableScaffoldingExtrator dataTableScaffoldingExtrator;
	ActionsScaffoldingExtrator actionsScaffoldingExtrator;

	public ParamsScaffoldingExtrator() {
        dataTableScaffoldingExtrator = new DataTableScaffoldingExtrator();
        actionsScaffoldingExtrator = new ActionsScaffoldingExtrator();
    }
	
	void copyParams(ParamsFieldScaffold origem, ParamsFieldScaffold destino){
		//TODO:FAZER
	}

	public void changeTypeAndParamsFields(Map fieldScaffolding, FieldScaffold fieldScaffold) throws Exception {
        TypeFieldScaffold type = this.getType(fieldScaffolding);
        Map<String, Object> paramsConfig = this.getParams(fieldScaffolding);
        if (type != null) {
            fieldScaffold.setType(type);
            ParamsFieldScaffold params = ParamsFactory.factory(type);
            if (fieldScaffold.getType() == TypeFieldScaffold.SELECT2_AJAX) {
                Class domain = fieldScaffold.getParent().getClazz();
                Field field = ObjectUtil.getField(fieldScaffold.getKey(), domain);
                		if(field!=null){
                ((Select2AjaxParamsFieldScaffold)params).setResourceUrl(ResourceUrlScaffold.builder(field.getType(),null));
                		}
            }
            if (!fieldScaffold.getParams().getClass().equals(params.getClass())) {
            	copyParams(params, fieldScaffold.getParams());
            }
        }

        if (paramsConfig!=null) {
            if (fieldScaffold.getType() == TypeFieldScaffold.DATATABLE) {
                DataTableParamsFieldScaffold dataTableParamsFieldScaffold = (DataTableParamsFieldScaffold) fieldScaffold.getParams();

                actionsScaffoldingExtrator.changeActions(paramsConfig, dataTableParamsFieldScaffold.getActions());
                dataTableScaffoldingExtrator.changeDatatable(paramsConfig, dataTableParamsFieldScaffold);
                //dataTableParamsFieldScaffold.aplicarActions();
            } else if (fieldScaffold.getParams().validate(paramsConfig)) {
            	for(String key: paramsConfig.keySet()){
            		Object value = paramsConfig.get(key);
            		ObjectUtil.copyFieldValue(value, fieldScaffold.getParams(), ObjectUtil.getField(key, fieldScaffold.getParams().getClass()));
            	}            	
            } else {
                throw new Exception("Parametros invalidos " + fieldScaffold.getKey());
            }

        }
    }

	TypeFieldScaffold getType(Map fieldScaffolding) throws Exception {
		String type = (String) fieldScaffolding.get("type");
		TypeFieldScaffold typeFieldScaffold = null;
		if (type != null) {
			typeFieldScaffold = this.converterParaEnum(type);
		}
		return typeFieldScaffold;
	}

	Map getParams(Map fieldScaffolding) {
		return (Map) fieldScaffolding.get("params");
	}

	TypeFieldScaffold converterParaEnum(Object type) throws Exception {
		if (!(type instanceof TypeFieldScaffold)) {
			try {
				if (type instanceof String) {
					return TypeFieldScaffold.valueOf(((String) type).toUpperCase());
				} else {
					throw new Exception("TypeFieldScaffold invalido");
				}
			} catch (Exception e) {
				throw new Exception("TypeFieldScaffold invalido");
			}
		}
		return null;
	}

}

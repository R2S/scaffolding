package biz.r2s.scaffolding.meta.field.params;

import java.util.Collections;
import java.util.List;

import biz.r2s.scaffolding.meta.field.TypeFieldScaffold;

/**
 * Created by raphael on 24/07/15.
 */
public class Select2ParamsFieldScaffold extends InputParamsFieldScaffold {

	List<Object> options = Collections.emptyList();
	String dataTextField = "text";
	String dataValueField = "id";
	boolean multiple = false;

	public Select2ParamsFieldScaffold() {
		this.setCampos("options");
		this.setCampos("dataTextField");
		this.setCampos("dataValueField");
		this.setCampos("multiple");
	}

	@Override
	public TypeFieldScaffold getType() {
		return TypeFieldScaffold.SELECT2;
	}

	public List<Object> getOptions() {
		return options;
	}

	public void setOptions(List<Object> options) {
		this.options = options;
	}

	public String getDataTextField() {
		return dataTextField;
	}

	public void setDataTextField(String dataTextField) {
		this.dataTextField = dataTextField;
	}

	public String getDataValueField() {
		return dataValueField;
	}

	public void setDataValueField(String dataValueField) {
		this.dataValueField = dataValueField;
	}

	public boolean isMultiple() {
		return multiple;
	}

	public void setMultiple(boolean multiple) {
		this.multiple = multiple;
	}

}

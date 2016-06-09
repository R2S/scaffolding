package biz.r2s.scaffolding.meta.button;

import java.util.List;

/**
 * Created by raphael on 30/03/16.
 */
public class ButtonInternal extends Button{
    List<String> params;
    String function;

    @Override
    public ButtonType getType() {
        return ButtonType.INTERNAL;
    }

	public List<String> getParams() {
		return params;
	}

	public void setParams(List<String> params) {
		this.params = params;
	}

	public String getFunction() {
		return function;
	}

	public void setFunction(String function) {
		this.function = function;
	}
}

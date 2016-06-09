package biz.r2s.scaffolding.meta.button;

/**
 * Created by raphael on 30/03/16.
 */
public class ButtonRedirect extends Button{
    String rota;

    @Override
    public ButtonType getType() {
        return ButtonType.REDIRECT;
    }

	public String getRota() {
		return rota;
	}

	public void setRota(String rota) {
		this.rota = rota;
	}
    
}

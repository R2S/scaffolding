package biz.r2s.scaffolding.meta.button;

/**
 * Created by raphael on 30/03/16.
 */
public class ButtonHasManyEdit extends Button{
    @Override
    public ButtonType getType() {
        return ButtonType.HASMANY_EDIT;
    }
}

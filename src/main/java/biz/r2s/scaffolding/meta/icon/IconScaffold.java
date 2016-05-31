package biz.r2s.scaffolding.meta.icon;

/**
 * Created by raphael on 27/07/15.
 */
public class IconScaffold {
    String name;
    TypeIcon type;
    PositionIcon position;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public TypeIcon getType() {
		return type;
	}
	public void setType(TypeIcon type) {
		this.type = type;
	}
	public PositionIcon getPosition() {
		return position;
	}
	public void setPosition(PositionIcon position) {
		this.position = position;
	}    
}

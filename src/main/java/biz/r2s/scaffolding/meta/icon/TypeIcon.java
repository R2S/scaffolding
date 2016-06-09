package biz.r2s.scaffolding.meta.icon;

/**
 * Created by raphael on 27/07/15.
 */
public enum TypeIcon {
    FA, GLYPHICON, SLI;

    public static String format(TypeIcon typeIcon, String name){
        String iconClass = "";
        if(typeIcon == FA){
            iconClass = "fa fa-".concat(name);
        }else if(typeIcon==GLYPHICON){
            iconClass = "glyphicon glyphicon-".concat(name);
        }else if(typeIcon == SLI){
            if(!name.startsWith("icon-")){
                iconClass = "icon-".concat(name);
            }else{
                iconClass = name;
            }
        }
        return iconClass;
    }
}
package biz.r2s.scaffolding.annotation;

import biz.r2s.scaffolding.meta.icon.PositionIcon;
import biz.r2s.scaffolding.meta.icon.TypeIcon;

public @interface IconScaffold {
	String name() default "";
    TypeIcon type() default TypeIcon.SLI;
    PositionIcon position() default PositionIcon.LEFT;
}

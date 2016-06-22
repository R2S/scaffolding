package biz.r2s.scaffolding.annotation;

public @interface Scaffolding {
	String name() default "";
	String service() default "";
	String controller() default "";
	TitleScaffold title() default @TitleScaffold;	
}

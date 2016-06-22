package biz.r2s.scaffolding.annotation;

public @interface MenuScaffold {
	String key() default "";

	String root() default "";

	TitleScaffold title() default @TitleScaffold;

	IconScaffold icon() default @IconScaffold;

	String url() default "";

	boolean enabled() default true;
}

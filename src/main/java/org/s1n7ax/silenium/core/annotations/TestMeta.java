package org.s1n7ax.silenium.core.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * TestMeta
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.TYPE })
public @interface TestMeta {
	public String browser() default "chrome";

	public String baseURL();

	public long pageloadTimeout() default 0;

	public long implicitTimeout() default 0;

	public long scriptTimeout() default 0;
}

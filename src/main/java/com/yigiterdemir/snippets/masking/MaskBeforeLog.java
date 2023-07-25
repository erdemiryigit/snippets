package com.yigiterdemir.snippets.masking;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface MaskBeforeLog {

    int leftVisible() default 0;

    int rightVisible() default 0;

}
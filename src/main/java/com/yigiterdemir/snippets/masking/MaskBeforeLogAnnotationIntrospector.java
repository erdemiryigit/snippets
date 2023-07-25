package com.yigiterdemir.snippets.masking;

import com.fasterxml.jackson.databind.introspect.Annotated;
import com.fasterxml.jackson.databind.introspect.NopAnnotationIntrospector;

public class MaskBeforeLogAnnotationIntrospector extends NopAnnotationIntrospector {

	private static final long serialVersionUID = 2345739318451076683L;

	@Override
	public Object findSerializer(Annotated annotated) {
		MaskBeforeLog annotation = annotated.getAnnotation(MaskBeforeLog.class);
		if (annotation != null) {
			return new MaskingSerializer(annotation.leftVisible(), annotation.rightVisible());
		}
		return null;
	}

}
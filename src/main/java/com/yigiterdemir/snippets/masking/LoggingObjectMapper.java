package com.yigiterdemir.snippets.masking;

import com.fasterxml.jackson.databind.AnnotationIntrospector;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.introspect.AnnotationIntrospectorPair;

public final class LoggingObjectMapper extends ObjectMapper {

	private static final long serialVersionUID = -1620079742091329228L;

	public LoggingObjectMapper() {
		super();

		MaskBeforeLogAnnotationIntrospector maskBeforeLogAnnotationIntrospector = new MaskBeforeLogAnnotationIntrospector();
		AnnotationIntrospector pair = AnnotationIntrospectorPair
				.pair(this.getSerializationConfig().getAnnotationIntrospector(), maskBeforeLogAnnotationIntrospector);
		this.setAnnotationIntrospector(pair);
	}

	public String toJsonString(Object value) {
		if (value == null) {
			return null;
		}

		try { 
			return writeValueAsString(value);
		} catch (Exception e) {
			return "Object couldn't be converted to JSON: " + e.getMessage();
		}
	} 

}
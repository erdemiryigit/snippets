package com.yigiterdemir.snippets.logbackmasking;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Marker;
import org.slf4j.MarkerFactory;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.pattern.CompositeConverter;

/**
 * @author yigit.erdemir
 */

public class MaskingConverter<E extends ILoggingEvent> extends CompositeConverter<E> {

	public static final Marker CONFIDENTIAL = MarkerFactory.getMarker("CONFIDENTIAL");
	private static final String PATTERN_KEY = "maskingPattern-";
	private static final String REPLACEMENT_KEY = "maskingReplacement-";
	private Map<Pattern, String> replacementMap = null;

	@Override
	public void start() {
		parsePatterns();
		super.start();
	}

	private void parsePatterns() {
		Map<String, String> propertyMap = getContext().getCopyOfPropertyMap();

		for (String k : propertyMap.keySet()) {
			if (k.startsWith(PATTERN_KEY)) {
				Pattern pattern = Pattern.compile(propertyMap.get(k));
				String[] patternArr = k.split(PATTERN_KEY);
				String patternId = "";
				if (patternArr.length == 2) {
					patternId = patternArr[1];
				}
				String replacementId = REPLACEMENT_KEY + patternId;
				String replacement = propertyMap.containsKey(replacementId) ? propertyMap.get(replacementId) : "***";

				if (replacementMap == null) {
					replacementMap = new HashMap<Pattern, String>();
				}
				replacementMap.put(pattern, replacement);

			}
		}
	}

	@Override
	protected String transform(E event, String in) {
		Marker marker = event.getMarker();
		if (replacementMap == null || !started || marker == null || !CONFIDENTIAL.getName().equals(marker.getName())) {
			return in;
		}

		for (Pattern p : replacementMap.keySet()) {
			Matcher matcher = p.matcher(in);
			if (matcher.find()) {
				in = matcher.replaceAll(replacementMap.get(p));
			}
		}

		return in;
	}
}

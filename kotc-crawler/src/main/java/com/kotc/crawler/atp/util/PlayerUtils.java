package com.kotc.crawler.atp.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PlayerUtils {

	private static final Logger LOGGER = LoggerFactory.getLogger(PlayerUtils.class);

	/*
	 * pattern: /en/players/<player-name>/<player-code>/overview
	 */
	private static final Pattern PLAYER_OVERVIEW_PATTERN = Pattern.compile("/en/players/([^/]+)/([\\w]+)/overview");

	private PlayerUtils() {
		assert false : "not instantiable";
	}

	public static final String extractPlayerCode(final String link) {

		try {
			final String url = URLDecoder.decode(link, StandardCharsets.UTF_8.name());
			final Matcher matcher = PLAYER_OVERVIEW_PATTERN.matcher(url);
			if (matcher.find()) {
				return StringUtils.upperCase(matcher.group(2));
			}
		} catch (final UnsupportedEncodingException e) {
			LOGGER.error(e.getMessage(), e);
		}

		assert false : "code could not be extracted from " + link;
		return null;
	}
}

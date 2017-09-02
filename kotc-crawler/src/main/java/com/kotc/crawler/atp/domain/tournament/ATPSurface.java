package com.kotc.crawler.atp.domain.tournament;

public enum ATPSurface {
	HARD("Hard"), CLAY("Clay"), GRASS("Grass"), CARPET("Carpet"), UNKNOWN("");

	private final String value;

	private ATPSurface(final String value) {
		this.value = value;
	}

	public static ATPSurface fromValue(final String value) {
		for (final ATPSurface court : values()) {
			if (court.getValue().equalsIgnoreCase(value)) {
				return court;
			}
		}

		return UNKNOWN;
	}

	public String getValue() {
		return value;
	}
}
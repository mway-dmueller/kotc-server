package com.kotc.circuit.player.service;

import java.util.Date;
import java.util.List;

import com.kotc.circuit.player.domain.AtpRankDate;

public interface AtpRankingService {

	AtpRankDate getRankDate(Date date);

	AtpRankDate createRankDate(Date date);

	boolean deleteRankDate(Date date);

	List<Date> getRankDates();

	boolean isDatePresent(Date date);
}

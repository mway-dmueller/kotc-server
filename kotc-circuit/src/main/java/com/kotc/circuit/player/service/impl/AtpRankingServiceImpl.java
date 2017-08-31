package com.kotc.circuit.player.service.impl;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.common.collect.Streams;
import com.kotc.circuit.dao.impl.AtpRankDateRepository;
import com.kotc.circuit.player.domain.AtpRankDate;
import com.kotc.circuit.player.service.AtpRankingService;

@Component
public class AtpRankingServiceImpl implements AtpRankingService {

	@Autowired
	private AtpRankDateRepository repository;

	@Override
	public AtpRankDate getRankDate(final Date date) {
		return repository.findOne(date);
	}

	@Override
	public AtpRankDate createRankDate(final Date date) {
		final AtpRankDate rankDate = new AtpRankDate();
		rankDate.setDate(date);

		return repository.save(rankDate);
	}

	@Override
	public boolean deleteRankDate(final Date date) {
		repository.delete(date);
		return true;
	}

	@Override
	public List<Date> getRankDates() {
		final Iterable<AtpRankDate> rankDates = repository.findAll();

		return Streams.stream(rankDates).map((rankDate) -> rankDate.getDate()).collect(Collectors.toList());
	}

	@Override
	public boolean isDatePresent(final Date date) {
		return repository.exists(date);
	}
}

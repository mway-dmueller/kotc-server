package com.kotc.circuit.enumerable.service;

import java.util.concurrent.ConcurrentHashMap;

import org.springframework.data.domain.Page;

import com.kotc.circuit.enumerable.domain.KeyValuePair;
import com.kotc.db.util.Query;

public interface EnumerableService {

	ConcurrentHashMap<String, Enumerable> getEnumerables();

	Page<KeyValuePair> getKeyValuePairs(String enumerable, Query query);
}

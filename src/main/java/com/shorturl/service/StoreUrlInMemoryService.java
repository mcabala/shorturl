package com.shorturl.service;

import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 
 * @author Bala
 *
 */
@Service
public class StoreUrlInMemoryService implements ShortUrlService {
	private Map<String, String> urlById = new ConcurrentHashMap<>();

	@Override
	public String findUrlById(String id) {
		return urlById.get(id);
	}

	@Override
	public void saveUrl(String id, String url) {
		urlById.put(id, url);
	}
}

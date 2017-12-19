package com.shorturl.service;

/**
 * 
 * @author Bala
 *
 */
public interface ShortUrlService {
	String findUrlById(String id);

	void saveUrl(String id, String url);
}

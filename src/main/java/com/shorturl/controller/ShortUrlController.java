package com.shorturl.controller;

import com.google.common.hash.Hashing;
import com.shorturl.constant.ShortUrlConst;
import com.shorturl.model.ShortUrlRequest;
import com.shorturl.service.ShortUrlService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;

/**
 * 
 * @author Bala
 *
 */
@Controller
public class ShortUrlController {
	@Autowired
	private ShortUrlService urlService;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String showForm(ShortUrlRequest request) {
		return ShortUrlConst.template;
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public void redirectToUrl(@PathVariable String id, HttpServletResponse resp) throws Exception {
		final String url = urlService.findUrlById(id);
		if (url != null) {
			resp.addHeader(ShortUrlConst.Location, url);
			resp.setStatus(HttpServletResponse.SC_MOVED_PERMANENTLY);
		} else {
			resp.sendError(HttpServletResponse.SC_NOT_FOUND);
		}
	}

	@RequestMapping(value = "/", method = RequestMethod.POST)
	public ModelAndView shortenUrl(HttpServletRequest httpRequest, ShortUrlRequest request,
			BindingResult bindingResult) {
		String url = request.getUrl();
		if (!isUrlValid(url)) {
			bindingResult.addError(new ObjectError(ShortUrlConst.url, ShortUrlConst.Invalid + url));
		}

		ModelAndView modelAndView = new ModelAndView(ShortUrlConst.template);
		if (!bindingResult.hasErrors()) {
			final String id = Hashing.murmur3_32().hashString(url, StandardCharsets.UTF_8).toString();
			urlService.saveUrl(id, url);
			String requestUrl = httpRequest.getRequestURL().toString();
			String prefix = requestUrl.substring(0,
					requestUrl.indexOf(httpRequest.getRequestURI(), ShortUrlConst.http.length()));

			modelAndView.addObject(ShortUrlConst.ShortUrl, prefix + "/" + id);
		}
		return modelAndView;
	}

	private boolean isUrlValid(String url) {
		boolean valid = true;
		try {
			new URL(url);
		} catch (MalformedURLException e) {
			valid = false;
		}
		return valid;
	}
}


package org.springframework.samples.petclinic.configuration;

import java.util.concurrent.TimeUnit;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleContextResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;

@Configuration
public class LocaleConfiguration implements WebMvcConfigurer {

	// WebMvcConfigurer interface ---------------------------------------------

	@Override
	public void addInterceptors(final InterceptorRegistry registry) {
		LocaleChangeInterceptor interceptor;

		interceptor = this.localeChangeInterceptor();
		registry.addInterceptor(interceptor);
	}

	// Beans ------------------------------------------------------------------

	@Bean
	public LocaleContextResolver localeResolver() {
		final CookieLocaleResolver

		result = new CookieLocaleResolver();

		result.setCookieName("lang");
		result.setCookieMaxAge((int) TimeUnit.DAYS.toSeconds(31));

		return result;
	}

	@Bean
	public LocaleChangeInterceptor localeChangeInterceptor() {
		LocaleChangeInterceptor result;

		result = new LocaleChangeInterceptor();
		result.setParamName("lang");

		return result;
	}

}

package com.votacao.infra.cors;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("votacao")
public class ApiProperty {

	/**
	 * Habilita origiem permitida
	 */
	private String originPermitida = "http://localhost:3000";
	
	private final Seguranca seguranca = new Seguranca();

	public Seguranca getSeguranca() {
		return seguranca;
	}

	public String getOriginPermitida() {
		return originPermitida;
	}

	public void setOriginPermitida(String originPermitida) {
		this.originPermitida = originPermitida;
	}

	public static class Seguranca {

		private boolean enableHttps;

		public boolean isEnableHttps() {
			return enableHttps;
		}

		public void setEnableHttps(boolean enableHttps) {
			this.enableHttps = enableHttps;
		}

	}

}

/*
*SPDX-FileCopyrightText: Copyright 2020 | CSI Piemonte
*SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.siac.siacbatch.batch.oil.uniit;

public abstract class BaseOilUniItBatchConfig {
	private String keyStorePath;
	private String keyStorePassword;
	private String certPassword;

	public String getCertPassword()
	{
		return certPassword;
	}

	public void setCertPassword(String certPassword)
	{
		this.certPassword = certPassword;
	}

	public String getKeyStorePath()
	{
		return keyStorePath;
	}

	public void setKeyStorePath(String keyStorePath)
	{
		this.keyStorePath = keyStorePath;
	}

	public String getKeyStorePassword()
	{
		return keyStorePassword;
	}

	public void setKeyStorePassword(String keyStorePassword)
	{
		this.keyStorePassword = keyStorePassword;
	}
}

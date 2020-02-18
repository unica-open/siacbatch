/*
*SPDX-FileCopyrightText: Copyright 2020 | CSI Piemonte
*SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.siac.siacbatch.batch.oil.uniit.ricevute;

import org.springframework.stereotype.Component;

import it.csi.siac.siacbatch.batch.oil.uniit.BaseOilUniItBatchConfig;

@Component
public class OilUniItRicevuteBatchConfig extends BaseOilUniItBatchConfig
{
	private String urlPattern;
	private String ricevuteFilePath;

	public String getUrlPattern()
	{
		return urlPattern;
	}

	public void setUrlPattern(String urlPattern)
	{
		this.urlPattern = urlPattern;
	}

	public String getRicevuteFilePath()
	{
		return ricevuteFilePath;
	}

	public void setRicevuteFilePath(String ricevuteFilePath)
	{
		this.ricevuteFilePath = ricevuteFilePath;
	}
}

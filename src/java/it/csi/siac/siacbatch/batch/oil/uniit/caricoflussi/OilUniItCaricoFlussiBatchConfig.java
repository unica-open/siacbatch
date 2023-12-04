/*
*SPDX-FileCopyrightText: Copyright 2020 | CSI Piemonte
*SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.siac.siacbatch.batch.oil.uniit.caricoflussi;

import org.springframework.stereotype.Component;

import it.csi.siac.siacbatch.batch.oil.uniit.BaseOilUniItBatchConfig;

@Component
public class OilUniItCaricoFlussiBatchConfig extends BaseOilUniItBatchConfig
{
	private String url;

	public String getUrl()
	{
		return url;
	}

	public void setUrl(String url)
	{
		this.url = url;
	}
}

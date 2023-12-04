/*
*SPDX-FileCopyrightText: Copyright 2020 | CSI Piemonte
*SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.siac.siacbatch.batch;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import it.csi.siac.siaccorser.frontend.webservice.CoreService;
import it.csi.siac.siaccorser.frontend.webservice.msg.GetRichiedente;
import it.csi.siac.siaccorser.frontend.webservice.msg.GetRichiedenteResponse;

@Component
public class CoreServiceInvoker extends BaseServiceInvoker
{
	@Autowired
	protected CoreService coreService;

	public GetRichiedenteResponse getRichiedente(GetRichiedente getRichiedente) throws Exception
	{
		GetRichiedenteResponse getrRichiedenteResponse = coreService.getRichiedente(getRichiedente);

		checkServiceResponse(getrRichiedenteResponse);

		return getrRichiedenteResponse;
	}
}

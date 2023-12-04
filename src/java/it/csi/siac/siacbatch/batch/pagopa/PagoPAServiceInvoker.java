/*
*SPDX-FileCopyrightText: Copyright 2020 | CSI Piemonte
*SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.siac.siacbatch.batch.pagopa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import it.csi.siac.pagopa.frontend.webservice.PagoPAService;
import it.csi.siac.pagopa.frontend.webservice.msg.ElaboraFlussoRiconciliazione;
import it.csi.siac.pagopa.frontend.webservice.msg.ElaboraFlussoRiconciliazioneResponse;
import it.csi.siac.siacbatch.batch.BaseServiceInvoker;

@Component
public class PagoPAServiceInvoker extends BaseServiceInvoker
{
	@Autowired
	protected PagoPAService pagoPAService;

	public ElaboraFlussoRiconciliazioneResponse elaboraFlussoRiconciliazione(ElaboraFlussoRiconciliazione request) throws Exception
	{
		ElaboraFlussoRiconciliazioneResponse response = pagoPAService.elaboraFlussoRiconciliazione(request);
		checkServiceResponse(response);

		return response;
	}

}

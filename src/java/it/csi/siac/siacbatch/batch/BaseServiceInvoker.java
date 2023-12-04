/*
*SPDX-FileCopyrightText: Copyright 2020 | CSI Piemonte
*SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.siac.siacbatch.batch;

import it.csi.siac.siacbatch.util.LogHandler;
import it.csi.siac.siaccorser.frontend.webservice.msg.AsyncServiceRequestWrapper;
import it.csi.siac.siaccorser.model.AzioneRichiesta;
import it.csi.siac.siaccorser.model.ServiceRequest;
import it.csi.siac.siaccorser.model.ServiceResponse;

public abstract class BaseServiceInvoker 
{
	protected void checkServiceResponse(ServiceResponse sr) throws Exception
	{
		LogHandler.logInfo(String.format("Esito servizio (%s): %s", sr.getClass().getName(), sr.getEsito()));

		if (sr.isFallimento())
			throw new Exception(sr.getDescrizioneErrori());
	}
	
	protected <REQ extends ServiceRequest> AsyncServiceRequestWrapper<REQ> wrapRequestToAsync(REQ request, AzioneRichiesta azioneRichiesta) {
		AsyncServiceRequestWrapper<REQ> result = new AsyncServiceRequestWrapper<REQ>();
		
		result.setAzioneRichiesta(azioneRichiesta);
		result.setDataOra(request.getDataOra());
		result.setRichiedente(request.getRichiedente());
		result.setRequest(request);
		
		return result;
	}
	
	
}

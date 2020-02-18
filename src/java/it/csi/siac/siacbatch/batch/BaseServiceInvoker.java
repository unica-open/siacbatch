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
//	@Autowired
//	protected OperazioneAsincronaService operazioneAsincronaService;

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
		result.setEnte(request.getRichiedente().getAccount().getEnte());
		result.setRequest(request);
		result.setAccount(request.getRichiedente().getAccount());
		
		return result;
	}
	
	
//	TODO
//	
//	protected InserisciOperazioneAsincResponse inserisciOperazioneAsinc(){
//		
//		InserisciOperazioneAsinc reqIOS = new InserisciOperazioneAsinc();
//		
//		reqIOS.setAccount(req.getAccount());
//		reqIOS.setDataOra(new Date());
//		reqIOS.setEnte(req.getEnte());
//		reqIOS.setRichiedente(originalRequest.getRichiedente());
//		
//		if(getNomeAzione() == null) { 
//			//XXX fallback temporaneo, in attesa che tutte le classi che estendono AsyncBaseService specifichino il nomeAzione.
//			reqIOS.setAzioneRichiesta(req.getAzioneRichiesta());
//		} else {
//			AzioneRichiesta azioneRichiesta = creaAzioneRichiesta();
//			reqIOS.setAzioneRichiesta(azioneRichiesta);
//		}
//		
//		InserisciOperazioneAsincResponse resAgg = operazioneAsincronaService.inserisciOperazioneAsinc(reqIOS);
//		checkServiceResponse(resAgg);
//		return resAgg;
//	}
//	
//
//	protected void aggiornaOperazioneAsinc(Integer idOperazioneAsincrona, StatoOperazioneAsincronaEnum stato, Integer idEnte, Richiedente richiedente) throws Exception {
//		AggiornaOperazioneAsinc reqAgg = new AggiornaOperazioneAsinc();
//		reqAgg.setRichiedente(richiedente);
//		reqAgg.setIdOperazioneAsinc(idOperazioneAsincrona);
//		reqAgg.setIdEnte(idEnte);
//
//		reqAgg.setStato(stato);
//
//		AggiornaOperazioneAsincResponse resAgg = operazioneAsincronaService.aggiornaOperazioneAsinc(reqAgg);
//		checkServiceResponse(resAgg);
//	}
	
}

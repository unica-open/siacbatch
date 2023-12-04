/*
*SPDX-FileCopyrightText: Copyright 2020 | CSI Piemonte
*SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.siac.siacbatch.batch.durc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import it.csi.siac.siacbatch.batch.BaseServiceInvoker;
import it.csi.siac.siacfinser.frontend.webservice.SoggettoService;
import it.csi.siac.siacfinser.frontend.webservice.msg.AggiornaDatiDurcSoggetto;
import it.csi.siac.siacfinser.frontend.webservice.msg.AggiornaDatiDurcSoggettoResponse;
import it.csi.siac.siacfinser.frontend.webservice.msg.RicercaSoggettiOttimizzato;
import it.csi.siac.siacfinser.frontend.webservice.msg.RicercaSoggettiOttimizzatoResponse;

@Component
public class DurcServiceInvoker extends BaseServiceInvoker
{
	@Autowired
	private SoggettoService soggettoService;

	public RicercaSoggettiOttimizzatoResponse ricercaSoggettiOttimizzato(RicercaSoggettiOttimizzato request) throws Exception {

		RicercaSoggettiOttimizzatoResponse response = soggettoService.ricercaSoggettiOttimizzato(request);
		checkServiceResponse(response);
		
		return response;
	}
	
	public AggiornaDatiDurcSoggettoResponse aggiornaDatiDurcSoggetto(AggiornaDatiDurcSoggetto request) throws Exception {

		AggiornaDatiDurcSoggettoResponse response = soggettoService.aggiornaDatiDurcSoggetto(request);
		checkServiceResponse(response);
		
		return response;
	}
	

}

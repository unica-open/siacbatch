/*
*SPDX-FileCopyrightText: Copyright 2020 | CSI Piemonte
*SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.siac.siacbatch.batch.pagopa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import it.csi.siac.pagopa.frontend.webservice.msg.ElaboraFlussoRiconciliazione;
import it.csi.siac.pagopa.frontend.webservice.msg.ElaboraFlussoRiconciliazioneResponse;
import it.csi.siac.siacbatch.batch.BaseBatch;
import it.csi.siac.siacbatch.batch.CmdLineOptions;
import it.csi.siac.siacbatch.util.LogHandler;
import it.csi.siac.siaccorser.model.Errore;
import it.csi.siac.siaccorser.model.Messaggio;
import it.csi.siac.siaccorser.model.Richiedente;

@Component
public class PagoPABatch extends BaseBatch
{
	@Autowired
	private PagoPAServiceInvoker pagoPAServiceInvoker;
	
	@Override
	public void execute() throws Exception
	{
		String codiceEnte = getOptionValue(CmdLineOptions.CODICE_ENTE);

		LogHandler.logInfo("codiceEnte: " + codiceEnte);

		Richiedente richiedente = readRichiedente(codiceEnte);

		ElaboraFlussoRiconciliazione request = new ElaboraFlussoRiconciliazione();
		request.setRichiedente(richiedente);

		LogHandler.logInfo("Chiamata Pago PA Service");
		
		ElaboraFlussoRiconciliazioneResponse response = pagoPAServiceInvoker.elaboraFlussoRiconciliazione(request);
		
		for (Messaggio m : response.getMessaggi()) {
			LogHandler.logInfo(String.format("Messaggio dal servizio: %s - %s", m.getCodice(), m.getDescrizione()));
		}
		
		for (Errore e : response.getErrori()) {
			LogHandler.logInfo(String.format("Errore dal servizio: %s - %s", e.getCodice(), e.getDescrizione()));
		}
	}
}

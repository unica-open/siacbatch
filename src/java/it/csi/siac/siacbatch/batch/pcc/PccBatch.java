/*
*SPDX-FileCopyrightText: Copyright 2020 | CSI Piemonte
*SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.siac.siacbatch.batch.pcc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import it.csi.siac.pcc.frontend.webservice.msg.InviaOperazioniPCC;
import it.csi.siac.pcc.frontend.webservice.msg.InviaOperazioniPCCResponse;
import it.csi.siac.siacbatch.batch.BaseBatch;
import it.csi.siac.siacbatch.batch.CmdLineOptions;
import it.csi.siac.siacbatch.util.LogHandler;
import it.csi.siac.siaccorser.model.Messaggio;
import it.csi.siac.siaccorser.model.Richiedente;

@Component
public class PccBatch extends BaseBatch
{
	@Autowired
	private PccServiceInvoker pccServiceInvoker;
	
	@Override
	public void execute() throws Exception
	{
		String codiceEnte = getOptionValue(CmdLineOptions.CODICE_ENTE);

		LogHandler.logInfo("codiceEnte: " + codiceEnte);

		Richiedente richiedente = readRichiedente(codiceEnte);

		InviaOperazioniPCC inviaOperazioniPCC = new InviaOperazioniPCC();
		inviaOperazioniPCC.setRichiedente(richiedente);

		LogHandler.logInfo("Chiamata pccService.inviaOperazioniPCC()");

		 InviaOperazioniPCCResponse inviaOperazioniPCCResponse = pccServiceInvoker.inviaOperazioniPCC(inviaOperazioniPCC);
		
		for (Messaggio m : inviaOperazioniPCCResponse .getMessaggi())
			LogHandler.logInfo(String.format("Messaggio dal servizio: %s - %s", m.getCodice(), m.getDescrizione()));
	}
}

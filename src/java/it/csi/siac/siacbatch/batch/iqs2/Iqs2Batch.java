/*
*SPDX-FileCopyrightText: Copyright 2020 | CSI Piemonte
*SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.siac.siacbatch.batch.iqs2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import it.csi.siac.siacbatch.batch.BaseBatch;
import it.csi.siac.siacbatch.batch.CmdLineOptions;
import it.csi.siac.siacbatch.util.LogHandler;
import it.csi.siac.siaccommon.util.MimeType;
import it.csi.siac.siaccorser.model.Errore;
import it.csi.siac.siaccorser.model.Messaggio;
import it.csi.siac.siaccorser.model.Richiedente;
import it.csi.siac.siaccorser.model.file.File;
import it.csi.siac.siacintegser.frontend.webservice.msg.ElaboraFile;
import it.csi.siac.siacintegser.frontend.webservice.msg.ElaboraFileResponse;

@Component
public class Iqs2Batch extends BaseBatch
{
	@Autowired
	private Iqs2ServiceInvoker iqs2ServiceInvoker;
	
	@Override
	public void execute() throws Exception
	{
		String codiceEnte = getOptionValue(CmdLineOptions.CODICE_ENTE);
		File file = new File(getOptionValue(Iqs2CmdLineOptions.FILE), MimeType.PLAIN_TEXT);
		
		LogHandler.logInfo("codiceEnte: " + codiceEnte);

		Richiedente richiedente = readRichiedente(codiceEnte);

		ElaboraFile request = new ElaboraFile();
		request.setRichiedente(richiedente);
		request.setFile(file);
	
		LogHandler.logInfo("Chiamata IQS2 Service");
		
		ElaboraFileResponse response = iqs2ServiceInvoker.elaboraFileIqs2(request);
		
		for (Messaggio m : response.getMessaggi()) {
			LogHandler.logInfo(String.format("Messaggio dal servizio: %s - %s", m.getCodice(), m.getDescrizione()));
		}
		
		for (Errore e : response.getErrori()) {
			LogHandler.logInfo(String.format("Errore dal servizio: %s - %s", e.getCodice(), e.getDescrizione()));
		}
	}
}

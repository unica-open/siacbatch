/*
*SPDX-FileCopyrightText: Copyright 2020 | CSI Piemonte
*SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.siac.siacbatch.batch.emettitore;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import it.csi.siac.siacbatch.batch.BaseBatch;
import it.csi.siac.siacbatch.util.LogHandler;
import it.csi.siac.siaccorser.frontend.webservice.msg.AsyncServiceResponse;
import it.csi.siac.siaccorser.model.Account;
import it.csi.siac.siaccorser.model.AzioneRichiesta;
import it.csi.siac.siaccorser.model.Bilancio;
import it.csi.siac.siaccorser.model.Richiedente;
import it.csi.siac.siacfin2ser.frontend.webservice.msg.EmetteOrdinativiDiPagamentoDaElenco;
import it.csi.siac.siacfin2ser.model.AllegatoAtto;
import it.csi.siac.siacfin2ser.model.ContoTesoreria;
import it.csi.siac.siacfinser.model.Distinta;

@Component
public class EmettitoreBatch extends BaseBatch
{
	@Autowired
	private EmettitoreBatchService emettitoreBatchService;
	
	@Autowired
	private EmettitoreServiceInvoker emettitoreServiceInvoker;
	
	@Override
	protected void execute() throws Exception
	{
		LogHandler.logInfo("[EmisisoneOrdinativiBatch::execute] Start");
		
		// Carico dal db gli atti allegato da emettere
		LogHandler.logInfo("Lettura lista allegato atto - start");
		List<AllegatoAtto> listaAllegatoAtto = emettitoreBatchService.leggiListaAllegatoAtto();
		LogHandler.logInfo("Lettura lista allegato atto - fine");

		if(listaAllegatoAtto!=null && !listaAllegatoAtto.isEmpty()){
			
			
			
			int numeroAllegatiAttoDaEmetterePerVolta = 0;
			
			for (AllegatoAtto allegatoAtto : listaAllegatoAtto) {
				
				LogHandler.logInfo("numeroAllegatiAttoDaEmetterePerVolta: " + numeroAllegatiAttoDaEmetterePerVolta);
				
				LogHandler.logInfo("Filtro la lettura delle info per allegato atto id [ "+allegatoAtto.getUid() +"]");
				
				LogHandler.logInfo("Lettura account - start");
				Account account = emettitoreBatchService.leggiAccount(allegatoAtto.getUid());
				LogHandler.logInfo("Account codice:"+ account.getCodice());
				LogHandler.logInfo("Lettura account - end");
				
				LogHandler.logInfo("Lettura richiedente"); 
				Richiedente richiedente = readRichiedente(account.getCodice());
				
				LogHandler.logInfo("Lettura bilancio");
				Bilancio bilancio = emettitoreBatchService.leggiBilancio(allegatoAtto.getUid());
				
				LogHandler.logInfo("Lettura azione");
				AzioneRichiesta azioneRichiesta= emettitoreBatchService.leggiAzioneRichiesta(allegatoAtto.getUid());
				
				LogHandler.logInfo("Lettura distinta");
				Distinta distinta = emettitoreBatchService.leggiDistinta(allegatoAtto.getUid());
				
				LogHandler.logInfo("Lettura conto tesoreria");
				ContoTesoreria contoTesoreria = emettitoreBatchService.leggiContoTesoriere(allegatoAtto.getUid());
				
				EmetteOrdinativiDiPagamentoDaElenco request = new EmetteOrdinativiDiPagamentoDaElenco();
				
				request.setRichiedente(richiedente);
				request.setBilancio(bilancio);
				request.setDataOra(new Date());
				request.setAllegatoAtto(allegatoAtto);
				request.setContoTesoreria(contoTesoreria);
				request.setDistinta(distinta);
				
				LogHandler.logInfo("Chiamata emissioneOrdinativiService.emetteOrdinativiDiPagamentoDaElenco()"); //async
				
				AsyncServiceResponse res = emettitoreServiceInvoker.emetteOrdinativiDiPagamentoDaElenco(request, azioneRichiesta);
				
				if(!res.isFallimento()){
					
					emettitoreBatchService.impostaDtFineElaborazione(allegatoAtto.getUid(),  account.getCodice());
					
					numeroAllegatiAttoDaEmetterePerVolta ++;
				}
				
				if(numeroAllegatiAttoDaEmetterePerVolta == 3){
				
					Thread.sleep(1000 * //millisecondi
								60 *  		//secondi
								(30*6)); 	// minuti
					
					LogHandler.logInfo(" Sleeping for .... " + (1000 * 60 * (30*10)));
					numeroAllegatiAttoDaEmetterePerVolta = 0;
				}
			}		
		}
		
		
		LogHandler.logInfo("[EmisisoneOrdinativiBatch::execute] End");
	}
	
	
}

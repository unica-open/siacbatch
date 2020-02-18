/*
*SPDX-FileCopyrightText: Copyright 2020 | CSI Piemonte
*SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.siac.siacbatch.batch.durc;

import java.io.File;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import it.csi.siac.siacbatch.batch.BaseBatch;
import it.csi.siac.siacbatch.batch.CmdLineOptions;
import it.csi.siac.siacbatch.util.LogHandler;
import it.csi.siac.siaccommon.util.date.DateConverter;
import it.csi.siac.siaccorser.model.Richiedente;
import it.csi.siac.siacfinser.Constanti;
import it.csi.siac.siacfinser.frontend.webservice.msg.AggiornaDatiDurcSoggetto;
import it.csi.siac.siacfinser.frontend.webservice.msg.RicercaSoggettiOttimizzato;
import it.csi.siac.siacfinser.frontend.webservice.msg.RicercaSoggettiOttimizzatoResponse;
import it.csi.siac.siacfinser.model.TipoFonteDurc;
import it.csi.siac.siacfinser.model.ric.ParametroRicercaSoggetto;
import it.csi.siac.siacfinser.model.ric.SorgenteDatiSoggetto;
import it.csi.siac.siacfinser.model.soggetto.Soggetto;

@Component
public class DurcBatch extends BaseBatch
{
	@Autowired
	private DurcServiceInvoker durcServiceInvoker;
	
	private Richiedente richiedente;
	
	@Override
	public void execute() throws Exception
	{
		Fornitore[] fornitori = readFornitoriFromJson(getOptionValue(DurcCmdLineOptions.JSON_FILE));
		
		String codiceEnte = getOptionValue(CmdLineOptions.CODICE_ENTE);
		LogHandler.logInfo("codiceEnte: " + codiceEnte);

		richiedente = readRichiedente(codiceEnte);
		
		for (Fornitore fornitore : fornitori) {
		//	LogHandler.logInfo(CoreUtils.objectToString(fornitore));
			elaboraFornitore(fornitore);
		}
	}

	private void elaboraFornitore(Fornitore fornitore) {
		LogHandler.logInfo("*********");
		LogHandler.logInfo("Fornitore: id " + fornitore.getIdFornitore());
		
		try {
			checkFornitore(fornitore);
			
			Soggetto soggetto = ricercaSoggetto(fornitore.getPartitaIva());
			
			LogHandler.logInfo("Trovato soggetto, codice " + soggetto.getCodiceSoggetto());
			
			aggiornaDatiDurc(soggetto, fornitore);
		}
		catch (Exception e) {
			LogHandler.logError(String.format("Fornitore scartato, motivo: %s", e.getMessage()));
		}
	}

	private void checkFornitore(Fornitore fornitore) throws Exception {
		if (StringUtils.isBlank(fornitore.getPartitaIva())) {
			throw new Exception("partita IVA assente");
		}
	}
	
	private Soggetto ricercaSoggetto(String partitaIva) throws Exception {
		LogHandler.logInfo("Ricerca soggetto con partita IVA " + partitaIva);

		RicercaSoggettiOttimizzato req = new RicercaSoggettiOttimizzato();
		req.setRichiedente(richiedente);
		req.setEnte(richiedente.getAccount().getEnte());
		req.setCodiceAmbito(Constanti.AMBITO_FIN);
		req.setSorgenteDatiSoggetto(SorgenteDatiSoggetto.SIAC);
		
		ParametroRicercaSoggetto parametroRicercaSoggetto = new ParametroRicercaSoggetto();
		parametroRicercaSoggetto.setPartitaIva(partitaIva);
		req.setParametroRicercaSoggetto(parametroRicercaSoggetto);
		RicercaSoggettiOttimizzatoResponse res = durcServiceInvoker.ricercaSoggettiOttimizzato(req);
		
		List<Soggetto> soggetti = res.getSoggetti();
		
		if (soggetti == null || soggetti.isEmpty()) {
			throw new Exception("nessun soggetto trovato con partita IVA " + partitaIva);
		}
		
		if (soggetti.size() > 1) {
			throw new Exception("trovati piu' soggetti con partita IVA " + partitaIva);
		}
		
		return soggetti.get(0);
	}

	private void aggiornaDatiDurc(Soggetto soggetto, Fornitore fornitore) throws Exception {
		LogHandler.logInfo("Aggiorna dati durc soggetto " + soggetto.getCodiceSoggetto());
		
		AggiornaDatiDurcSoggetto req = new AggiornaDatiDurcSoggetto();
		
		req.setRichiedente(richiedente);
		req.setIdSoggetto(soggetto.getUid());
		req.setTipoFonteDurc(TipoFonteDurc.AUTOMATICA.getCodice());
		req.setDataFineValiditaDurc(fornitore.getDataScadenzaDoc() == null ? 
				null :
				DateConverter.convertFromString(fornitore.getDataScadenzaDoc())
		);
		req.setFonteDurcAutomatica("Albo Fornitori");

		durcServiceInvoker.aggiornaDatiDurcSoggetto(req);
	}

	private Fornitore[] readFornitoriFromJson(String fileName) throws Exception {
		LogHandler.logInfo("Lettura file " + fileName);
		
		String content = FileUtils.readFileToString(new File(fileName));
		
		if (StringUtils.isBlank(content)) {
			throw new Exception("File vuoto");
		}
		
		return new ObjectMapper().readValue(content, Fornitore[].class);
	}
}

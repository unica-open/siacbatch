/*
*SPDX-FileCopyrightText: Copyright 2020 | CSI Piemonte
*SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.siac.siacbatch.batch.oil.xml;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import it.csi.siac.siacbatch.batch.BaseBatch;
import it.csi.siac.siacbatch.batch.CmdLineOptions;
import it.csi.siac.siaccorser.model.Richiedente;

@Component
public class OilXmlBatch extends BaseBatch
{
	@Autowired
	private GeneraXmlOrdinativiHelper generaXmlOrdinativiHelper; 

	@Autowired
	private GeneraXmlOrdinativiSiopePlusHelper generaXmlOrdinativiSiopePlusHelper;

	@Autowired
	private OilXmlBatchService oilBatchService;

	@Override
	protected void execute() throws Exception
	{
		Richiedente richiedente = readRichiedente(getOptionValue(CmdLineOptions.CODICE_ENTE));

		Integer idEnte = richiedente.getAccount().getEnte().getUid();

		Integer idElaborazione = Integer.valueOf(getOptionValue(OilXmlCmdLineOptions.ID_ELABORAZIONE));
		Integer limitOrdinativi = Integer.valueOf(getOptionValue(OilXmlCmdLineOptions.LIM_ORDINATIVI));
		String xmlFilePrefix = getOptionValue(OilXmlCmdLineOptions.XML_NAME);
		String xmlPathFile = getOptionValue(OilXmlCmdLineOptions.PATH);

		(oilBatchService.isEnteSiopePlus(idEnte) ? 
				generaXmlOrdinativiSiopePlusHelper : generaXmlOrdinativiHelper)
				.generaXml(idElaborazione, xmlFilePrefix, xmlPathFile, limitOrdinativi, richiedente);
	}
}

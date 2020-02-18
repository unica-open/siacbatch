/*
*SPDX-FileCopyrightText: Copyright 2020 | CSI Piemonte
*SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.siac.siacbatch.batch.oil.xml;

import java.io.File;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import it.csi.siac.siaccorser.model.Richiedente;
import it.csi.siac.siacfinser.frontend.webservice.msg.CountOrdinativiMif;
import it.csi.siac.siacfinser.frontend.webservice.msg.CountOrdinativiMifSiopePlusResponse;
import it.csi.siac.siacfinser.frontend.webservice.msg.GeneraXmlOrdinativiMif;

@Component
public class GeneraXmlOrdinativiSiopePlusHelper extends BaseGeneraXmlOrdinativiHelper
{
	@Override
	public void generaXml(Integer idElaborazione, String xmlFilePrefix, String xmlPathFile, Integer limitOrdinativi,
			Richiedente richiedente) throws Exception
	{
		GeneraXmlOrdinativiMif req = new GeneraXmlOrdinativiMif();
		req.setIdEnte(richiedente.getAccount().getEnte().getUid());
		req.setIdElaborazione(idElaborazione);
		req.setLimitOrdinativi(limitOrdinativi);
		req.setRichiedente(richiedente);

		Map<Integer, Integer> countOrdinativiPerAnno = readCountOrdinativiMifPerAnnoEsercizio(idElaborazione,
				richiedente);

		for (Entry<Integer, Integer> entry : countOrdinativiPerAnno.entrySet())
		{
			Integer annoEsercizio = entry.getKey();
			Integer countOrdinativi = entry.getValue();

			req.setAnnoEsercizio(annoEsercizio);

			int steps = (countOrdinativi - 1) / limitOrdinativi + 1;

			for (int i = 0; i < steps; i++)
			{
				String xml = generaXmlOrdinativi(req, i * limitOrdinativi);

				String xmlFileName = String.format("%s.%d.%s.xml", xmlFilePrefix, annoEsercizio,
						StringUtils.leftPad(String.valueOf(i + 1), 5, '0'));

				FileUtils.writeStringToFile(new File(xmlPathFile + xmlFileName), xml);
			}
		}
	}

	private Map<Integer, Integer> readCountOrdinativiMifPerAnnoEsercizio(Integer idElaborazione,
			Richiedente richiedente) throws Exception
	{
		CountOrdinativiMif req = new CountOrdinativiMif();

		req.setIdElaborazione(idElaborazione);
		req.setRichiedente(richiedente);

		CountOrdinativiMifSiopePlusResponse res = oilServiceInvoker.countOrdinativiMifSiopePlus(req);

		return res.getNumeroOrdinativiPerAnnoEsercizio();
	}

}

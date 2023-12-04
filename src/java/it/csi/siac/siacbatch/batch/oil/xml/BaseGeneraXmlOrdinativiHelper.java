/*
*SPDX-FileCopyrightText: Copyright 2020 | CSI Piemonte
*SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.siac.siacbatch.batch.oil.xml;

import org.springframework.beans.factory.annotation.Autowired;

import it.csi.siac.siacbatch.batch.oil.OilServiceInvoker;
import it.csi.siac.siaccorser.model.Richiedente;
import it.csi.siac.siacfinser.frontend.webservice.msg.GeneraXmlOrdinativiMif;
import it.csi.siac.siacfinser.frontend.webservice.msg.GeneraXmlOrdinativiMifResponse;

public abstract class BaseGeneraXmlOrdinativiHelper
{
	@Autowired
	protected OilServiceInvoker oilServiceInvoker;
	
	public abstract void generaXml(Integer idElaborazione, String xmlFilePrefix, String xmlPathFile, Integer limitOrdinativi, Richiedente richiedente) throws Exception;
	
	protected String generaXmlOrdinativi(GeneraXmlOrdinativiMif req, int offsetOrdinativi) throws Exception
	{
		req.setOffsetOrdinativi(offsetOrdinativi);

		GeneraXmlOrdinativiMifResponse res = oilServiceInvoker.generaXmlOrdinativiMif(req);

		return res.getXml();
	}
}

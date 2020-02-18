/*
*SPDX-FileCopyrightText: Copyright 2020 | CSI Piemonte
*SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.siac.siacbatch.batch.oil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import it.csi.siac.siacbatch.batch.BaseServiceInvoker;
import it.csi.siac.siacfinser.frontend.webservice.OilService;
import it.csi.siac.siacfinser.frontend.webservice.msg.CountOrdinativiMif;
import it.csi.siac.siacfinser.frontend.webservice.msg.CountOrdinativiMifResponse;
import it.csi.siac.siacfinser.frontend.webservice.msg.CountOrdinativiMifSiopePlusResponse;
import it.csi.siac.siacfinser.frontend.webservice.msg.GeneraXmlOrdinativiMif;
import it.csi.siac.siacfinser.frontend.webservice.msg.GeneraXmlOrdinativiMifResponse;

@Component
public class OilServiceInvoker extends BaseServiceInvoker
{
	@Autowired
	private OilService oilService;

	public GeneraXmlOrdinativiMifResponse generaXmlOrdinativiMif(GeneraXmlOrdinativiMif req) throws Exception
	{
		GeneraXmlOrdinativiMifResponse res = oilService.generaXmlOrdinativiMif(req);

		checkServiceResponse(res);
		
		return res;
	}

	public CountOrdinativiMifSiopePlusResponse countOrdinativiMifSiopePlus(CountOrdinativiMif req) throws Exception
	{
		CountOrdinativiMifSiopePlusResponse res = oilService.countOrdinativiMifSiopePlus(req);
		
		checkServiceResponse(res);
		
		return res;
	}

	public CountOrdinativiMifResponse countOrdinativiMif(CountOrdinativiMif req) throws Exception
	{
		CountOrdinativiMifResponse res = oilService.countOrdinativiMif(req);
		
		checkServiceResponse(res);
		
		return res;
	}
}

/*
*SPDX-FileCopyrightText: Copyright 2020 | CSI Piemonte
*SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.siac.siacbatch.batch.pcc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import it.csi.siac.pcc.frontend.webservice.PCCService;
import it.csi.siac.pcc.frontend.webservice.msg.InviaOperazioniPCC;
import it.csi.siac.pcc.frontend.webservice.msg.InviaOperazioniPCCResponse;
import it.csi.siac.siacbatch.batch.BaseServiceInvoker;

@Component
public class PccServiceInvoker extends BaseServiceInvoker
{
	@Autowired
	protected PCCService pccService;

	public InviaOperazioniPCCResponse inviaOperazioniPCC(InviaOperazioniPCC inviaOperazioniPCC) throws Exception
	{
		InviaOperazioniPCCResponse inviaOperazioniPCCResponse = pccService.inviaOperazioniPCC(inviaOperazioniPCC);
		checkServiceResponse(inviaOperazioniPCCResponse);

		return inviaOperazioniPCCResponse;
	}

}

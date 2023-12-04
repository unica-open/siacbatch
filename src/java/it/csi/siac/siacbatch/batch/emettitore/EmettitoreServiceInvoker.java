/*
*SPDX-FileCopyrightText: Copyright 2020 | CSI Piemonte
*SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.siac.siacbatch.batch.emettitore;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import it.csi.siac.siacbatch.batch.BaseServiceInvoker;
import it.csi.siac.siaccorser.frontend.webservice.msg.AsyncServiceResponse;
import it.csi.siac.siaccorser.model.AzioneRichiesta;
import it.csi.siac.siacfin2ser.frontend.webservice.EmissioneOrdinativiService;
import it.csi.siac.siacfin2ser.frontend.webservice.msg.EmetteOrdinativiDiPagamentoDaElenco;

@Component
public class EmettitoreServiceInvoker extends BaseServiceInvoker
{
	@Autowired
	private EmissioneOrdinativiService emissioneOrdinativiService;
	
	public AsyncServiceResponse emetteOrdinativiDiPagamentoDaElenco(EmetteOrdinativiDiPagamentoDaElenco request,
			AzioneRichiesta azioneRichiesta) throws Exception
	{
		AsyncServiceResponse asyncServiceResponse = emissioneOrdinativiService
				.emetteOrdinativiDiPagamentoDaElenco(wrapRequestToAsync(request, azioneRichiesta));
		checkServiceResponse(asyncServiceResponse);

		return asyncServiceResponse;
	}

}

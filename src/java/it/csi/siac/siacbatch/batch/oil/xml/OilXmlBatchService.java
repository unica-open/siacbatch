/*
*SPDX-FileCopyrightText: Copyright 2020 | CSI Piemonte
*SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.siac.siacbatch.batch.oil.xml;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import it.csi.siac.siacbatch.batch.oil.OilDao;
import it.csi.siac.siaccorser.model.TipologiaGestioneLivelli;

@Component
public final class OilXmlBatchService
{
	@Autowired
	private OilDao oilDao;

	public boolean isEnteSiopePlus(int idEnte)
	{
		return oilDao.countGestioneLivello(idEnte, TipologiaGestioneLivelli.ORDINATIVI_MIF_TRASMETTI_SIOPE_PLUS.getCodice()) > 0;
	}
}

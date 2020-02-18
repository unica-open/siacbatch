/*
*SPDX-FileCopyrightText: Copyright 2020 | CSI Piemonte
*SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.siac.siacbatch.batch.emettitore;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import it.csi.siac.siaccorser.model.Account;
import it.csi.siac.siaccorser.model.AzioneRichiesta;
import it.csi.siac.siaccorser.model.Bilancio;
import it.csi.siac.siacfin2ser.model.AllegatoAtto;
import it.csi.siac.siacfin2ser.model.ContoTesoreria;
import it.csi.siac.siacfinser.model.Distinta;

@Component
public final class EmettitoreBatchService
{
	@Autowired
	private EmettitoreDao emettitoreDao;

	/**
	 * Leggo gli atti amministrativi da cui parte poi l'emissione
	 * 
	 * @return
	 * @throws Exception
	 */
	public List<AllegatoAtto> leggiListaAllegatoAtto() throws Exception
	{
		return  emettitoreDao.findListAllegatoAtto();
		
	}

	
	public Bilancio leggiBilancio(Integer uidAllegatoAtto) throws Exception
	{
		return  emettitoreDao.findBilancio(uidAllegatoAtto);
		
	}
	
	public void impostaDtFineElaborazione(Integer uidAllegatoAtto, String loginOperazione) throws Exception
	{
		emettitoreDao.updateDtFineElaborazione(uidAllegatoAtto, loginOperazione);
		
	}
	
	
	public AzioneRichiesta leggiAzioneRichiesta(Integer uidAllegatoAtto) throws Exception
	{
		return emettitoreDao.findAzioneRichiesta(uidAllegatoAtto);
		
	}


	public Account leggiAccount(Integer uidAllegatoAtto) throws Exception
	{
		return emettitoreDao.findAccount(uidAllegatoAtto);
		
	}	
	
	
	public Distinta leggiDistinta(Integer uidAllegatoAtto) throws Exception
	{
		return emettitoreDao.findDistinta(uidAllegatoAtto);
		
	}	
	
	
	public ContoTesoreria leggiContoTesoriere(Integer uidAllegatoAtto) throws Exception
	{
		return emettitoreDao.findContoTesoreria(uidAllegatoAtto);
		
	}	

}

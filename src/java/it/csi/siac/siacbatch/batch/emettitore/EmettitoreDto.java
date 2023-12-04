/*
*SPDX-FileCopyrightText: Copyright 2020 | CSI Piemonte
*SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.siac.siacbatch.batch.emettitore;

import java.io.Serializable;

import it.csi.siac.siaccorser.model.Account;
import it.csi.siac.siaccorser.model.AzioneRichiesta;
import it.csi.siac.siaccorser.model.Bilancio;
import it.csi.siac.siacfin2ser.model.AllegatoAtto;


public class EmettitoreDto implements Serializable
{
/**
	 * 
	 */
	private static final long serialVersionUID = -6018025049039839206L;

			
	private Account account;
	private AzioneRichiesta azioneRichiesta;
	private AllegatoAtto allegatoAtto;
	private Bilancio bilancio;
	private String contoTesoreria;
	private String distinta;
	/**
	 * @return the account
	 */
	public Account getAccount() {
		return account;
	}
	/**
	 * @param account the account to set
	 */
	public void setAccount(Account account) {
		this.account = account;
	}
	/**
	 * @return the azioneRichiesta
	 */
	public AzioneRichiesta getAzioneRichiesta() {
		return azioneRichiesta;
	}
	/**
	 * @param azioneRichiesta the azioneRichiesta to set
	 */
	public void setAzioneRichiesta(AzioneRichiesta azioneRichiesta) {
		this.azioneRichiesta = azioneRichiesta;
	}
	/**
	 * @return the allegatoAtto
	 */
	public AllegatoAtto getAllegatoAtto() {
		return allegatoAtto;
	}
	/**
	 * @param allegatoAtto the allegatoAtto to set
	 */
	public void setAllegatoAtto(AllegatoAtto allegatoAtto) {
		this.allegatoAtto = allegatoAtto;
	}
	/**
	 * @return the bilancio
	 */
	public Bilancio getBilancio() {
		return bilancio;
	}
	/**
	 * @param bilancio the bilancio to set
	 */
	public void setBilancio(Bilancio bilancio) {
		this.bilancio = bilancio;
	}
	/**
	 * @return the contoTesoreria
	 */
	public String getContoTesoreria() {
		return contoTesoreria;
	}
	/**
	 * @param contoTesoreria the contoTesoreria to set
	 */
	public void setContoTesoreria(String contoTesoreria) {
		this.contoTesoreria = contoTesoreria;
	}
	/**
	 * @return the distinta
	 */
	public String getDistinta() {
		return distinta;
	}
	/**
	 * @param distinta the distinta to set
	 */
	public void setDistinta(String distinta) {
		this.distinta = distinta;
	}

	

}

/*
*SPDX-FileCopyrightText: Copyright 2020 | CSI Piemonte
*SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.siac.siacbatch.batch.emettitore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcDaoSupport;
import org.springframework.stereotype.Component;

import it.csi.siac.siacbatch.util.LogHandler;
import it.csi.siac.siaccorser.model.Account;
import it.csi.siac.siaccorser.model.Azione;
import it.csi.siac.siaccorser.model.AzioneRichiesta;
import it.csi.siac.siaccorser.model.Bilancio;
import it.csi.siac.siacfin2ser.model.AllegatoAtto;
import it.csi.siac.siacfin2ser.model.ContoTesoreria;
import it.csi.siac.siacfinser.model.Distinta;

@Component
public class EmettitoreDao extends NamedParameterJdbcDaoSupport
{
//	private static final String LOGIN_OPERAZIONE = "batch";
	private static final String nomeClasse = "EmettitoreDao";

	@Autowired
	public EmettitoreDao(DataSource dataSource)
	{
		super();
		setDataSource(dataSource);
	}

	public Bilancio findBilancio(Integer uidAllegatoAtto) throws Exception
	{
		String sql = "SELECT bil_id from sked_emettitore where uid_allegato_atto = :uidAllegatoAtto ";
		
		Bilancio bilancio= new Bilancio();
		
		Map<String, Object> paramMap = new HashMap<String, Object>();

		paramMap.put("uidAllegatoAtto", uidAllegatoAtto);
//		paramMap.put("descrizione", abi.getDescrizione());
//		paramMap.put("loginOperazione", LOGIN_OPERAZIONE);

		int uidBilancio =  getNamedParameterJdbcTemplate().queryForInt(sql, paramMap);

		
		if (uidBilancio == 0)
			throw new Exception(" Errore di sistema:  uid bilancio null!");  
		LogHandler.logInfo(nomeClasse+ ".findBilancio :: uidBilancio: " + uidBilancio);
		
		
		bilancio.setUid(uidBilancio);
		
		return bilancio;
		
	}
	
	
	
	public Account findAccount(Integer uidAllegatoAtto) throws Exception
	{
		String sql = "select a.account_code from siac_t_account a where a.account_id IN "
						+ "(SELECT s.account_id from sked_emettitore s where s.uid_allegato_atto = :uidAllegatoAtto) ";
		
		Account account= new Account();
		
		Map<String, Object> paramMap = new HashMap<String, Object>();

		paramMap.put("uidAllegatoAtto", uidAllegatoAtto);
//		paramMap.put("descrizione", abi.getDescrizione());
//		paramMap.put("loginOperazione", LOGIN_OPERAZIONE);

		String codiceAccount =  getNamedParameterJdbcTemplate().queryForObject(sql, paramMap, String.class);
		
		if (StringUtils.isEmpty(codiceAccount))
			throw new Exception(" Errore di sistema: codiceAccount null!");  
		LogHandler.logInfo(nomeClasse+ ".findAccount :: codiceAccount: " + codiceAccount);
		
		System.out.println("codiceAccount: " + codiceAccount);
		account.setCodice(codiceAccount);
		
		return account;
		
	}
	
	
	
	public ContoTesoreria findContoTesoreria(Integer uidAllegatoAtto) throws Exception
	{
		String sql = "select a.contotes_id from siac_d_contotesoreria a where a.contotes_id IN "
					+"(SELECT s.id_conto_tesoreria from sked_emettitore s where s.uid_allegato_atto = :uidAllegatoAtto) ";
		
		ContoTesoreria contoTesoreria= new ContoTesoreria();
		
		Map<String, Object> paramMap = new HashMap<String, Object>();

		paramMap.put("uidAllegatoAtto", uidAllegatoAtto);

		try{
		
			Integer uidContoTesoreria =  getNamedParameterJdbcTemplate().queryForObject(sql, paramMap, Integer.class);
			
			if (uidContoTesoreria !=null )
				LogHandler.logInfo(nomeClasse+ ".findContoTesoreria :: uidContoTesoreria: " + uidContoTesoreria);
			
			contoTesoreria.setUid(uidContoTesoreria);
		
		}catch (Exception e){
			
			LogHandler.logError( nomeClasse+ " Nessun conto tesoreria trovato" );
			 
		}
		
		return contoTesoreria;
		
	}
	
	
	
	public Distinta findDistinta(Integer uidAllegatoAtto) throws Exception
	{
		String sql = "select a.dist_id from siac_d_distinta a where a.dist_id IN "
						+" (SELECT s.id_distinta from sked_emettitore s where s.uid_allegato_atto = :uidAllegatoAtto) ";
		
		Distinta distinta = new Distinta();
		
		Map<String, Object> paramMap = new HashMap<String, Object>();

		paramMap.put("uidAllegatoAtto", uidAllegatoAtto);
		
		try{
		
			Integer uidDistinta =  getNamedParameterJdbcTemplate().queryForObject(sql, paramMap, Integer.class);
		
			if (uidDistinta !=null )
				LogHandler.logInfo(nomeClasse+ ".findDistinta :: uidDistinta: " + uidDistinta);
			
			distinta.setUid(uidDistinta);
		
		}catch (Exception e){
			
			LogHandler.logError( nomeClasse+ " Nessuna distinta trovata" );
			 
		}
		
		return distinta;
		
	}
	
	public void updateDtFineElaborazione(Integer uidAllegatoAtto, String loginOperazione)
	{
		
		LogHandler.logInfo(nomeClasse+ ".updateDtFineElaborazione :: Start ");
	
		
		Map<String, Object> paramMap = new HashMap<String, Object>();

		paramMap.put("uidAllegatoAtto", uidAllegatoAtto);
		paramMap.put("loginOperazione", loginOperazione);
		
		String sql="UPDATE sked_emettitore SET data_fine_elaborazione = now(), login_operazione = :loginOperazione"
				+ " WHERE uid_allegato_atto = :uidAllegatoAtto  ";
		
		getNamedParameterJdbcTemplate().update(	sql,paramMap);
		
		LogHandler.logInfo(nomeClasse+ ".updateDtFineElaborazione :: Fine ");
	}
	
	
	public List<AllegatoAtto> findListAllegatoAtto() throws Exception
	{
		//String sql = "SELECT * from siac_tmp_(:codice, :descrizione, :loginOperazione)";
		List<AllegatoAtto> listaAttoAllegato = new ArrayList<AllegatoAtto>();
		
		String sql = "SELECT * from sked_emettitore where data_fine_elaborazione is null";

		Map<String, Object> paramMap = new HashMap<String, Object>();

		listaAttoAllegato = getNamedParameterJdbcTemplate().query(sql, paramMap, new AllegatoAttoRowMapper());

		
		if (listaAttoAllegato==null)
			throw new Exception("Non ci sono ALLEGATI ATTO da emettere.");    
		LogHandler.logInfo(nomeClasse+ ".findListAllegatoAtto :: Trovati elementi: " + listaAttoAllegato.size());
		
		return listaAttoAllegato;
		
	}
	
	
	public AzioneRichiesta findAzioneRichiesta(Integer uidAllegatoAtto) throws Exception
	{
		//String sql = "SELECT * from siac_tmp_(:codice, :descrizione, :loginOperazione)";
		Azione azione = new Azione();
		AzioneRichiesta azioneRichiesta = new AzioneRichiesta();
		
		String sql = "SELECT uid_azione_richiesta from sked_emettitore where uid_allegato_atto = :uidAllegatoAtto ";

		Map<String, Object> paramMap = new HashMap<String, Object>();

		paramMap.put("uidAllegatoAtto", uidAllegatoAtto);

		int uidAzione =  getNamedParameterJdbcTemplate().queryForInt(sql, paramMap);

		
		if (uidAzione == 0)
			throw new Exception(" Errore di sistema:  uid AzioneRichiesta null!");  
		LogHandler.logInfo(nomeClasse+ ".findAzioneRichiesta :: uidAzione: " + uidAzione);
		
		azione.setUid(uidAzione);
		
		azioneRichiesta.setAzione(azione);
		
		return azioneRichiesta;
		
	}

	

}

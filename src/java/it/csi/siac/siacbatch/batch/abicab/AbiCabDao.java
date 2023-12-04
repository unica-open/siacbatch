/*
*SPDX-FileCopyrightText: Copyright 2020 | CSI Piemonte
*SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.siac.siacbatch.batch.abicab;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcDaoSupport;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Component;

import it.csi.siac.siacbatch.util.LogHandler;

@Component
public class AbiCabDao extends NamedParameterJdbcDaoSupport
{
	private static final String LOGIN_OPERAZIONE = "batch";

	@Autowired
	public AbiCabDao(DataSource dataSource)
	{
		super();
		setDataSource(dataSource);
	}

	public void handleAbi(Abi abi) throws Exception
	{
		String sql = "SELECT fnc_siac_aggiorna_abi(:codice, :descrizione, :loginOperazione)";

		Map<String, Object> paramMap = new HashMap<String, Object>();

		paramMap.put("codice", abi.getCodice());
		paramMap.put("descrizione", abi.getDescrizione());
		paramMap.put("loginOperazione", LOGIN_OPERAZIONE);

		String ret = getNamedParameterJdbcTemplate().queryForObject(sql, paramMap, String.class);

		LogHandler.logInfo(ret);

		if (ret.startsWith("ERR"))
			throw new Exception(ret);  
	}

	public void handleCab(Cab cab) throws Exception
	{
		String sql = "SELECT fnc_siac_aggiorna_cab(:codice, :codiceAbi, :descrizione, :indirizzo, :cap, :citta, :provincia, :loginOperazione)";

		Map<String, Object> paramMap = new HashMap<String, Object>();

		paramMap.put("codice", cab.getCodice());
		paramMap.put("codiceAbi", cab.getCodiceAbi());
		paramMap.put("descrizione", cab.getDescrizione());
		paramMap.put("indirizzo", cab.getIndirizzo());
		paramMap.put("cap", cab.getCap());
		paramMap.put("citta", cab.getCitta());
		paramMap.put("provincia", cab.getProvincia());
		paramMap.put("loginOperazione", LOGIN_OPERAZIONE);

		String ret = getNamedParameterJdbcTemplate().queryForObject(sql, paramMap, String.class);

		LogHandler.logInfo(ret);

		if (ret.startsWith("ERR"))
			throw new Exception(ret);
	}

	public List<Integer> readAllIdEnte()
	{
		return getNamedParameterJdbcTemplate().queryForList(
				"SELECT ente_proprietario_id "
				+ " FROM siac_t_ente_proprietario "
				+ " WHERE data_cancellazione IS NULL "
				+ " AND validita_inizio<=CURRENT_TIMESTAMP "
				+ " AND (validita_fine IS NULL OR validita_fine>NOW()) "
				+ " ORDER BY ente_proprietario_id", (SqlParameterSource) null, Integer.class);
	}
	
	public List<String> readAllAbi()
	{
		return getNamedParameterJdbcTemplate().queryForList(
				"SELECT abi_code FROM siac_t_abi_master ", (SqlParameterSource) null, String.class);
	}
	
	public List<String> readAllCab()
	{
		return getNamedParameterJdbcTemplate().queryForList(
				"SELECT cab_abi||cab_code FROM siac_t_cab_master ",
				(SqlParameterSource) null, String.class);
	}

	public void removeAbiList(List<String> abiList)
	{
		abiList.add("''");
		
		getNamedParameterJdbcTemplate().update(String.format(
				"DELETE FROM siac_t_abi_master "
				+ " WHERE abi_code IN (%s) ",
				StringUtils.join(abiList, ", ")), (SqlParameterSource) null);
	}

	public void removeCabList(List<String> abiList, List<String> abiCabList)
	{
		abiList.add("''");

		getNamedParameterJdbcTemplate().update(String.format(
				"DELETE FROM siac_t_cab_master "
				+ " WHERE ( "
				+ "   cab_abi IN (%s) "
				+ "	  OR (cab_abi, cab_code) IN (%s) "
				+ " ) ",
				StringUtils.join(abiList, ", "), StringUtils.join(abiCabList, ", ")), (SqlParameterSource) null);
	}

	public void replicateAbiCab(int idEnte) throws Exception
	{ 
		String sql = "SELECT fnc_siac_replica_abicab_ente(:idEnte)";

		Map<String, Object> paramMap = new HashMap<String, Object>();

		paramMap.put("idEnte", idEnte);

		String ret = getNamedParameterJdbcTemplate().queryForObject(sql, paramMap, String.class);

		LogHandler.logInfo(ret);

		if (ret.startsWith("ERR"))
			throw new Exception(ret);	
	}
}

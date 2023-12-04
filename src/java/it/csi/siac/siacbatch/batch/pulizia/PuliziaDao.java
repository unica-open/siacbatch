/*
*SPDX-FileCopyrightText: Copyright 2020 | CSI Piemonte
*SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.siac.siacbatch.batch.pulizia;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcDaoSupport;
import org.springframework.stereotype.Component;

import it.csi.siac.siacbatch.util.LogHandler;

@Component
public class PuliziaDao extends NamedParameterJdbcDaoSupport
{
	private static final String LOGIN_OPERAZIONE = "batch";

	@Autowired
	public PuliziaDao(DataSource dataSource)
	{
		super();
		setDataSource(dataSource);
	}

	public void handleAbi() throws Exception
	{
		String sql = "SELECT fnc_siac_aggiorna_abi(:codice, :descrizione, :loginOperazione)";

		Map<String, Object> paramMap = new HashMap<String, Object>();

//		paramMap.put("codice", abi.getCodice());
//		paramMap.put("descrizione", abi.getDescrizione());
//		paramMap.put("loginOperazione", LOGIN_OPERAZIONE);

		String ret = getNamedParameterJdbcTemplate().queryForObject(sql, paramMap, String.class);

		LogHandler.logInfo(ret);

		if (ret.startsWith("ERR"))
			throw new Exception(ret);  
	}

}

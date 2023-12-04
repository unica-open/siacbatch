/*
*SPDX-FileCopyrightText: Copyright 2020 | CSI Piemonte
*SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.siac.siacbatch.batch.oil;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcDaoSupport;
import org.springframework.stereotype.Component;

@Component
public class OilDao extends NamedParameterJdbcDaoSupport
{
	@Autowired
	public OilDao(DataSource dataSource)
	{
		super();
		setDataSource(dataSource);
	}

	public int countGestioneLivello(int idEnte, String codice)
	{
		Map<String, Object> paramMap = new HashMap<String, Object>();
		
		paramMap.put("ente_proprietario_id", idEnte);
		paramMap.put("gestione_livello_code", codice);
		
		return getNamedParameterJdbcTemplate().queryForInt (
				"SELECT COUNT(*) FROM siac_d_gestione_livello gl "
						+ " WHERE gl.ente_proprietario_id=:ente_proprietario_id "
						+ " AND gl.gestione_livello_code=:gestione_livello_code "
						+ " AND gl.data_cancellazione IS NULL "
						+ " AND EXISTS ("
						+ "		SELECT * FROM siac_r_gestione_ente ge "
						+ " 		WHERE ge.data_cancellazione IS NULL "
						+ " 		AND ge.gestione_livello_id=gl.gestione_livello_id "
						+ " )", paramMap);
	}
}

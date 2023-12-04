/*
*SPDX-FileCopyrightText: Copyright 2020 | CSI Piemonte
*SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.siac.siacbatch.batch.test;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcDaoSupport;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Component;

import it.csi.siac.siacbatch.util.LogHandler;

@Component
public class TestDao extends NamedParameterJdbcDaoSupport
{
	@Autowired
	public TestDao(DataSource dataSource)
	{
		super();
		setDataSource(dataSource);
	}

	public void test()
	{
		LogHandler.logInfo(String.format("current_database: %s", getNamedParameterJdbcTemplate()
				.queryForObject("SELECT current_database() || ' - ' || inet_server_addr()", (SqlParameterSource) null, String.class)));
	}
}

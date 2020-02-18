/*
*SPDX-FileCopyrightText: Copyright 2020 | CSI Piemonte
*SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.siac.siacbatch.batch.emettitore;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import it.csi.siac.siaccorser.model.Account;


@SuppressWarnings("rawtypes")
public class AccountRowMapper implements RowMapper {

	@Override
	public Account mapRow(ResultSet rs, int rowNum) throws SQLException {
	      
		Account account = new Account();
		account.setUid(rs.getInt("account_id"));
		account.setCodice(rs.getString("account_code"));
		
		
	    
	    return account;
	}
}

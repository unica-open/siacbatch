/*
*SPDX-FileCopyrightText: Copyright 2020 | CSI Piemonte
*SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.siac.siacbatch.batch.emettitore;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import it.csi.siac.siacattser.model.AttoAmministrativo;
import it.csi.siac.siacattser.model.TipoAtto;
import it.csi.siac.siacfin2ser.model.AllegatoAtto;


public class AllegatoAttoRowMapper implements RowMapper<AllegatoAtto> {

	@Override
	public AllegatoAtto mapRow(ResultSet rs, int rowNum) throws SQLException {
	      
		AllegatoAtto allegatoAtto = new AllegatoAtto();
		allegatoAtto.setUid(rs.getInt("uid_allegato_atto"));
		
		AttoAmministrativo atto= new AttoAmministrativo();
		atto.setUid(rs.getInt("uid_atto_amm"));
	    atto.setAnno(Integer.parseInt(rs.getString("atto_anno")));
	    atto.setNumero(rs.getInt("atto_num"));
//		atto.setAnno((Integer) null);
//		atto.setNumero((Integer) null);
//		
		
	    TipoAtto ta= new TipoAtto();
	    ta.setUid(rs.getInt("atto_tipo_id"));
	    atto.setTipoAtto(ta);
	    
	    allegatoAtto.setAttoAmministrativo(atto);
	    
	    return allegatoAtto;
	}
}

/*
*SPDX-FileCopyrightText: Copyright 2020 | CSI Piemonte
*SPDX-License-Identifier: EUPL-1.2
*/
CREATE OR REPLACE FUNCTION siac.fnc_siac_aggiorna_cab (
  codice varchar,
  codice_abi varchar,
  descrizione varchar,
  indirizzo varchar,
  cap varchar,
  citta varchar,
  provincia varchar,
  login_oper varchar
)
RETURNS varchar AS
$body$
DECLARE
	
	sel_cab_sql varchar;
	ins_cab_sql varchar;
	ins_cab_ente_sql varchar;
	del_cab_sql varchar;
	del_cab_ente_sql varchar;
	cab_rec siac_t_cab%rowtype;    
	ente_arr integer[];
 	id_ente integer;
    diag_message_text text;
    diag_exception_detail text;
    diag_exception_hint text;
    ret text;
    
BEGIN

  sel_cab_sql := 'SELECT * FROM siac_t_cab_:id_ente  
      WHERE ente_proprietario_id=:id_ente 
      AND cab_code=:cab_code 
      AND cab_abi=:cab_abi
      AND data_cancellazione IS NULL
      AND validita_inizio < NOW()
      AND (validita_fine IS NULL OR validita_fine > NOW());';

  ins_cab_sql := 'INSERT INTO siac_t_cab_:id_ente (
      cab_abi,
      cab_code,
      cab_citta,
      cab_indirizzo,
      cab_cap,
      cab_desc,
      cab_provincia,
      abi_id,
      validita_inizio,
      nazione_id,
      ente_proprietario_id,
      data_creazione,
      data_modifica,
      login_operazione)
      VALUES (
        :cab_abi,
        :cab_code,
        :cab_citta,
        :cab_indirizzo,
        :cab_cap,
        :cab_desc,
        :cab_provincia,
        (SELECT abi_id FROM siac_t_abi 
          WHERE ente_proprietario_id=:id_ente 
          AND abi_code=:cab_abi 
          AND data_cancellazione IS NULL
          AND validita_inizio < NOW()
          AND (validita_fine IS NULL OR validita_fine > NOW())),
        NOW(),
        (SELECT n.nazione_id FROM siac_t_nazione n 
          WHERE n.nazione_code=''1'' AND n.ente_proprietario_id=:id_ente),
        :id_ente,
        NOW(),
        NOW(),
        :login_oper
      );';

  del_cab_sql := 'UPDATE siac_t_cab_:id_ente 
            SET data_cancellazione=NOW(),
            data_modifica=NOW(),
            validita_fine=NOW()
            WHERE ente_proprietario_id=:id_ente 
            AND cab_code=:cab_code
            AND cab_abi=:cab_abi
            AND data_cancellazione IS NULL
            AND validita_inizio < NOW()
            AND (validita_fine IS NULL OR validita_fine > NOW());';


  ente_arr := ARRAY(
      SELECT ente_proprietario_id FROM siac_t_ente_proprietario
          WHERE data_cancellazione IS NULL
          AND validita_inizio < NOW()
          AND (validita_fine IS NULL OR validita_fine > NOW())
          ORDER BY ente_proprietario_id
  );

  sel_cab_sql := REPLACE(sel_cab_sql, ':id_ente', ente_arr[1]::varchar);
  sel_cab_sql := REPLACE(sel_cab_sql, ':cab_abi', quote_nullable(codice_abi));
  sel_cab_sql := REPLACE(sel_cab_sql, ':cab_code', quote_nullable(codice));
 
  EXECUTE sel_cab_sql INTO cab_rec;
  
  IF cab_rec IS NULL THEN
      FOREACH id_ente IN ARRAY ente_arr LOOP
      	ins_cab_ente_sql := REPLACE(ins_cab_sql, ':id_ente', id_ente::varchar);
      	ins_cab_ente_sql := REPLACE(ins_cab_ente_sql, ':cab_abi', quote_nullable(codice_abi));
      	ins_cab_ente_sql := REPLACE(ins_cab_ente_sql, ':cab_code', quote_nullable(codice));
      	ins_cab_ente_sql := REPLACE(ins_cab_ente_sql, ':cab_citta', quote_nullable(citta));
      	ins_cab_ente_sql := REPLACE(ins_cab_ente_sql, ':cab_indirizzo', quote_nullable(indirizzo));
      	ins_cab_ente_sql := REPLACE(ins_cab_ente_sql, ':cab_cap', quote_nullable(cap));
      	ins_cab_ente_sql := REPLACE(ins_cab_ente_sql, ':cab_desc', quote_nullable(descrizione));
      	ins_cab_ente_sql := REPLACE(ins_cab_ente_sql, ':cab_provincia', quote_nullable(provincia));
      	ins_cab_ente_sql := REPLACE(ins_cab_ente_sql, ':login_oper', quote_nullable(login_oper));
        EXECUTE ins_cab_ente_sql;
        
        ret := 'INS ABI/CAB ' || codice_abi || '/' || codice;
      END LOOP;
  ELSIF (cab_rec.cab_citta<>citta OR
         cab_rec.cab_indirizzo<>indirizzo OR
         cab_rec.cab_cap::varchar<>cap OR
		 cab_rec.cab_desc<>descrizione OR
  		 cab_rec.cab_provincia<>provincia) THEN
      FOREACH id_ente IN ARRAY ente_arr LOOP
      	del_cab_ente_sql := REPLACE(del_cab_sql, ':id_ente', id_ente::varchar);
      	del_cab_ente_sql := REPLACE(del_cab_ente_sql, ':cab_abi', quote_nullable(codice_abi));
      	del_cab_ente_sql := REPLACE(del_cab_ente_sql, ':cab_code', quote_nullable(codice));
        EXECUTE del_cab_ente_sql;
        
      	ins_cab_ente_sql := REPLACE(ins_cab_sql, ':id_ente', id_ente::varchar);
      	ins_cab_ente_sql := REPLACE(ins_cab_ente_sql, ':cab_abi', quote_nullable(codice_abi));
      	ins_cab_ente_sql := REPLACE(ins_cab_ente_sql, ':cab_code', quote_nullable(codice));
      	ins_cab_ente_sql := REPLACE(ins_cab_ente_sql, ':cab_citta', quote_nullable(citta));
      	ins_cab_ente_sql := REPLACE(ins_cab_ente_sql, ':cab_indirizzo', quote_nullable(indirizzo));
      	ins_cab_ente_sql := REPLACE(ins_cab_ente_sql, ':cab_cap', quote_nullable(cap));
      	ins_cab_ente_sql := REPLACE(ins_cab_ente_sql, ':cab_desc', quote_nullable(descrizione));
      	ins_cab_ente_sql := REPLACE(ins_cab_ente_sql, ':cab_provincia', quote_nullable(provincia));
      	ins_cab_ente_sql := REPLACE(ins_cab_ente_sql, ':login_oper', quote_nullable(login_oper));
        EXECUTE ins_cab_ente_sql;
        
        ret := 'UPD ABI/CAB ' || codice_abi || '/' || codice;
      END LOOP;
  ELSE
      ret := '*** ABI/CAB ' || codice_abi || '/' || codice;
  END IF;

  RETURN 'OK: ' || ret;

EXCEPTION
	WHEN OTHERS THEN
		GET STACKED DIAGNOSTICS diag_message_text = MESSAGE_TEXT,
  			diag_exception_detail = PG_EXCEPTION_DETAIL,
            diag_exception_hint = PG_EXCEPTION_HINT;

    ret := diag_message_text || ' - ' 
        || diag_exception_detail || ' - '  
        || diag_exception_hint;
        
    RAISE NOTICE '%', ret;
    
    RETURN 'ERR: ' || ret;

END;
$body$
LANGUAGE 'plpgsql'
VOLATILE
CALLED ON NULL INPUT
SECURITY INVOKER
COST 100;
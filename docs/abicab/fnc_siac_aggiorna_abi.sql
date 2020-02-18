CREATE OR REPLACE FUNCTION siac.fnc_siac_aggiorna_abi (
  codice varchar,
  descrizione varchar,
  login_oper varchar
)
RETURNS varchar AS
$body$
DECLARE

	ins_abi_sql varchar;
	ins_abi_ente_sql varchar;
	abi_rec siac_t_abi%rowtype;    
	ente_arr integer[];
 	id_ente integer;
    diag_message_text text;
    diag_exception_detail text;
    diag_exception_hint text;
    ret text;
    
BEGIN

 ins_abi_sql := 'INSERT INTO siac_t_abi (
          abi_code,
          abi_desc,
          validita_inizio,
          nazione_id,
          ente_proprietario_id,
          data_creazione,
          data_modifica,
          login_operazione) 
          VALUES (
          :abi_code,
          :abi_desc,
          NOW(),
          (SELECT n.nazione_id FROM siac_t_nazione n 
            WHERE n.nazione_code=''1'' AND n.ente_proprietario_id=:id_ente),
          :id_ente,
          NOW(),
          NOW(),
          :login_oper
      );';          

  ente_arr := ARRAY(
      SELECT ente_proprietario_id FROM siac_t_ente_proprietario
          WHERE data_cancellazione IS NULL
          AND validita_inizio < NOW()
          AND (validita_fine IS NULL OR validita_fine > NOW())
          ORDER BY ente_proprietario_id
  );


  SELECT * INTO abi_rec FROM siac_t_abi 
      WHERE ente_proprietario_id=ente_arr[1] 
      AND abi_code=codice 
      AND data_cancellazione IS NULL
      AND validita_inizio < NOW()
      AND (validita_fine IS NULL OR validita_fine > NOW());
      
  IF abi_rec IS NULL THEN
      FOREACH id_ente IN ARRAY ente_arr LOOP
      	ins_abi_ente_sql := REPLACE(ins_abi_sql, ':id_ente', id_ente::varchar);
      	ins_abi_ente_sql := REPLACE(ins_abi_ente_sql, ':abi_code', quote_nullable(codice));
      	ins_abi_ente_sql := REPLACE(ins_abi_ente_sql, ':abi_desc', quote_nullable(descrizione));
      	ins_abi_ente_sql := REPLACE(ins_abi_ente_sql, ':login_oper', quote_nullable(login_oper));
        EXECUTE ins_abi_ente_sql;
        
        ret := 'INS ABI ' || codice;
      END LOOP;
  ELSIF (abi_rec.abi_desc <> descrizione) THEN
      FOREACH id_ente IN ARRAY ente_arr LOOP
        UPDATE siac_t_abi 
          SET data_cancellazione=NOW(),
          data_modifica=NOW(),
          validita_fine=NOW()
          WHERE ente_proprietario_id=id_ente 
          AND abi_code=codice
          AND data_cancellazione IS NULL
          AND validita_inizio < NOW()
          AND (validita_fine IS NULL OR validita_fine > NOW());
          
      	ins_abi_ente_sql := REPLACE(ins_abi_sql, ':id_ente', id_ente::varchar);
      	ins_abi_ente_sql := REPLACE(ins_abi_ente_sql, ':abi_code', quote_nullable(codice));
      	ins_abi_ente_sql := REPLACE(ins_abi_ente_sql, ':abi_desc', quote_nullable(descrizione));
      	ins_abi_ente_sql := REPLACE(ins_abi_ente_sql, ':login_oper', quote_nullable(login_oper));
        EXECUTE ins_abi_ente_sql;

        ret := 'UPD ABI ' || codice;
      END LOOP;
  ELSE
      ret := '*** ABI ' || codice;
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
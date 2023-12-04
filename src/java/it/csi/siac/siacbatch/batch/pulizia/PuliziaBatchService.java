/*
*SPDX-FileCopyrightText: Copyright 2020 | CSI Piemonte
*SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.siac.siacbatch.batch.pulizia;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import it.csi.siac.siacbatch.util.LogHandler;

@Component
public final class PuliziaBatchService
{
	@Autowired
	private PuliziaDao abiCabDao;

	private Set<String> abiSet;
	private Set<String> cabSet;

	/*public void updateAbiCab(BufferedReader bufferedReader) throws Exception
	{
		LogHandler.logInfo("Lettura ABI");
		abiSet = readAllAbi();
		LogHandler.logInfo(String.format("Attualmente presenti %d ABI", abiSet.size()));

		LogHandler.logInfo("Lettura CAB");
		cabSet = readAllCab();
		LogHandler.logInfo(String.format("Attualmente presenti %d CAB", cabSet.size()));
		
		Cab cab = null;

		String lineStr;
		int abiCount = 0;
		int cabCount = 0;

		while ((lineStr = bufferedReader.readLine()) != null)
		{
			if (lineStr.startsWith("11"))
				handleAbi(abiCount++, lineStr);
			else if (lineStr.startsWith("21"))
				cab = raedCabPart1(lineStr);
			else if (lineStr.startsWith("31"))
				handleCab(cabCount++, cab, lineStr);
			else
				throw new Exception("Errore lettura linea: " + lineStr);
		}
	}

	public void removeAbiCab()
	{
		List<String> abiList = new ArrayList<String>();
		List<String> abiCabList = new ArrayList<String>();

		for (String a : abiSet)
			abiList.add(String.format("'%s'", a));

		for (String c : cabSet)
			abiCabList.add(String.format("('%s', '%s')", c.substring(0, 5), c.substring(5, 10)));

		LogHandler.logInfo("Cancellazione ABI");
		
		if (!abiSet.isEmpty())
			abiCabDao.removeAbiList(abiList);

		LogHandler.logInfo("Cancellazione CAB");
		
		if (!abiSet.isEmpty() || !cabSet.isEmpty())
			abiCabDao.removeCabList(abiList, abiCabList);
	}

	private Set<String> readAllAbi()
	{
		return new HashSet<String>(abiCabDao.readAllAbi());
	}

	private Set<String> readAllCab()
	{
		return new HashSet<String>(abiCabDao.readAllCab());
	}

	private Cab raedCabPart1(String line)
	{
		Cab cab = new Cab();

		cab.setIndirizzo(line.substring(20, 140).trim());
		cab.setCitta(line.substring(140, 180).trim());
		cab.setCap(line.substring(180, 185));
		cab.setProvincia(line.substring(185, 187));

		return cab;
	}

	private void handleAbi(int abiCount, String line) throws Exception
	{
		Abi abi = new Abi();

		abi.setCodice(line.substring(2, 7));
		abi.setDescrizione(line.substring(13, 93).trim());

		LogHandler.logInfo(String.format("ABI %s (lavorati: %d, rimanenti: %d)", abi.getCodice(), abiCount, abiSet.size()));
		
		abiCabDao.handleAbi(abi);

		abiSet.remove(abi.getCodice());
	}
	
	private void handleCab(int cabCount, Cab cab, String line) throws Exception
	{
		if (cab == null)
			throw new Exception("Oggetto CAB null");

		cab.setCodiceAbi(line.substring(2, 7));
		cab.setCodice(line.substring(7, 12));
		cab.setDescrizione(line.substring(12, 52).trim());

		LogHandler.logInfo(String.format("ABI/CAB %s/%s (lavorati: %d, rimanenti: %d)", cab.getCodiceAbi(), cab.getCodice(), cabCount, cabSet.size()));

		abiCabDao.handleCab(cab);

		cabSet.remove(cab.getCodiceAbi() + cab.getCodice());
	}

	public void replicateAbiCab() throws Exception
	{
		LogHandler.logInfo("Lettura enti");
		List<Integer> idEnteList = abiCabDao.readAllIdEnte();
		LogHandler.logInfo(String.format("Id-Ente presenti: %s", StringUtils.join(idEnteList, ", ")));
		
		for (Integer idEnte : idEnteList)
			abiCabDao.replicateAbiCab(idEnte);
	}
}

class Abi
{
	private String codice;
	private String descrizione;

	public String getCodice()
	{
		return codice;
	}

	public void setCodice(String codice)
	{
		this.codice = codice;
	}

	public String getDescrizione()
	{
		return descrizione;
	}

	public void setDescrizione(String descrizione)
	{
		this.descrizione = descrizione;
	}

}

class Cab
{
	private String codice;
	private String descrizione;
	private String indirizzo;
	private String citta;
	private String cap;
	private String provincia;
	private String codiceAbi;

	public String getCodice()
	{
		return codice;
	}

	public void setCodice(String codice)
	{
		this.codice = codice;
	}

	public String getDescrizione()
	{
		return descrizione;
	}

	public void setDescrizione(String descrizione)
	{
		this.descrizione = descrizione;
	}

	public String getIndirizzo()
	{
		return indirizzo;
	}

	public void setIndirizzo(String indirizzo)
	{
		this.indirizzo = indirizzo;
	}

	public String getCitta()
	{
		return citta;
	}

	public void setCitta(String citta)
	{
		this.citta = citta;
	}

	public String getCap()
	{
		return cap;
	}

	public void setCap(String cap)
	{
		this.cap = cap;
	}

	public String getProvincia()
	{
		return provincia;
	}

	public void setProvincia(String provincia)
	{
		this.provincia = provincia;
	}

	public String getCodiceAbi()
	{
		return codiceAbi;
	}

	public void setCodiceAbi(String codiceAbi)
	{
		this.codiceAbi = codiceAbi;
	}
*/
}

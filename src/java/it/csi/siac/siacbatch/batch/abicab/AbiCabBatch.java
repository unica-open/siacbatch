/*
*SPDX-FileCopyrightText: Copyright 2020 | CSI Piemonte
*SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.siac.siacbatch.batch.abicab;

import java.io.BufferedReader;
import java.net.URL;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import it.csi.siac.siacbatch.batch.BaseBatch;
import it.csi.siac.siacbatch.util.LogHandler;

@Component
public class AbiCabBatch extends BaseBatch
{
	@Autowired
	private AbiCabFileReader abiCabFileReader;

	@Autowired
	private AbiCabBatchService abiCabBatchService;

	@Override
	protected void execute() throws Exception
	{
		URL url = new URL(getOptionValue(AbiCabCmdLineOptions.DOWNLOAD_URL));

		LogHandler.logInfo("Download URL: " + url);

		BufferedReader reader = null;
		try {
			reader = abiCabFileReader.getReader(url);

			LogHandler.logInfo("Lettura file"); 
			abiCabBatchService.updateAbiCab(reader);
		} finally {
			IOUtils.closeQuietly(reader);
		}
		
		LogHandler.logInfo("Rimozione codici obsoleti"); 
		abiCabBatchService.removeAbiCab();
				
		LogHandler.logInfo("Duplicazione codici su tutti gli enti"); 
		abiCabBatchService.replicateAbiCab();

		abiCabFileReader.close();
	}
}

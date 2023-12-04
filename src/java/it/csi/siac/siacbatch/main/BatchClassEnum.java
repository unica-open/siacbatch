/*
*SPDX-FileCopyrightText: Copyright 2020 | CSI Piemonte
*SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.siac.siacbatch.main;

import it.csi.siac.siacbatch.batch.Batch;
import it.csi.siac.siacbatch.batch.CmdLineOptions;
import it.csi.siac.siacbatch.batch.abicab.AbiCabBatch;
import it.csi.siac.siacbatch.batch.abicab.AbiCabCmdLineOptions;
import it.csi.siac.siacbatch.batch.durc.DurcBatch;
import it.csi.siac.siacbatch.batch.durc.DurcCmdLineOptions;
import it.csi.siac.siacbatch.batch.emettitore.EmettitoreBatch;
import it.csi.siac.siacbatch.batch.iqs2.Iqs2Batch;
import it.csi.siac.siacbatch.batch.iqs2.Iqs2CmdLineOptions;
import it.csi.siac.siacbatch.batch.oil.uniit.caricoflussi.OilUniItCaricoFlussiBatch;
import it.csi.siac.siacbatch.batch.oil.uniit.caricoflussi.OilUniItCaricoFlussiCmdLineOptions;
import it.csi.siac.siacbatch.batch.oil.uniit.ricevute.OilUniItRicevuteBatch;
import it.csi.siac.siacbatch.batch.oil.uniit.ricevute.OilUniItRicevuteCmdLineOptions;
import it.csi.siac.siacbatch.batch.oil.xml.OilXmlBatch;
import it.csi.siac.siacbatch.batch.oil.xml.OilXmlCmdLineOptions;
import it.csi.siac.siacbatch.batch.pagopa.PagoPABatch;
import it.csi.siac.siacbatch.batch.pagopa.PagoPACmdLineOptions;
import it.csi.siac.siacbatch.batch.pcc.PccBatch;
import it.csi.siac.siacbatch.batch.pcc.PccCmdLineOptions;
import it.csi.siac.siacbatch.batch.pulizia.PuliziaBatch;
import it.csi.siac.siacbatch.batch.pulizia.PuliziaCmdLineOptions;
import it.csi.siac.siacbatch.batch.test.TestBatch;

public enum BatchClassEnum
{
	OIL(OilXmlBatch.class, OilXmlCmdLineOptions.class),
	OIL_UNIIT_CARICO_FLUSSI(OilUniItCaricoFlussiBatch.class, OilUniItCaricoFlussiCmdLineOptions.class),
	OIL_UNIIT_RICEVUTE(OilUniItRicevuteBatch.class, OilUniItRicevuteCmdLineOptions.class),
	ABI_CAB(AbiCabBatch.class, AbiCabCmdLineOptions.class), 
	PCC(PccBatch.class, PccCmdLineOptions.class), 
	PULIZIA(PuliziaBatch.class, PuliziaCmdLineOptions.class), 
	EMETTITORE(EmettitoreBatch.class),
	PAGOPA(PagoPABatch.class, PagoPACmdLineOptions.class),
	IQS2(Iqs2Batch.class, Iqs2CmdLineOptions.class),
	DURC(DurcBatch.class, DurcCmdLineOptions.class),
	//
	TEST(TestBatch.class);

	private Class<? extends Batch> batchClass;
	private Class<? extends CmdLineOptions> cmdLineOptionsClass;

	BatchClassEnum(Class<? extends Batch> batchClass) {
		this(batchClass, null);
	}
	
	BatchClassEnum(Class<? extends Batch> batchClass, Class<? extends CmdLineOptions> cmdLineOptionsClass) {
		this.batchClass = batchClass;
		this.cmdLineOptionsClass = cmdLineOptionsClass;
	}

	public Class<? extends Batch> getBatchClass()
	{
		return batchClass;
	}
	
	public Class<? extends CmdLineOptions> getCmdLineOptionsClass()
	{
		return cmdLineOptionsClass;
	}
}

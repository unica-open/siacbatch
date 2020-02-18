/*
*SPDX-FileCopyrightText: Copyright 2020 | CSI Piemonte
*SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.siac.siacbatch.batch;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.MissingOptionException;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.OptionBuilder;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import it.csi.siac.siacbatch.main.BatchClassEnum;
import it.csi.siac.siacbatch.util.LogHandler;
import it.csi.siac.siaccorser.frontend.webservice.msg.GetRichiedente;
import it.csi.siac.siaccorser.frontend.webservice.msg.GetRichiedenteResponse;
import it.csi.siac.siaccorser.model.Richiedente;

public abstract class BaseBatch implements Batch
{
	@Autowired
	protected CoreServiceInvoker coreServiceInvoker;
	
	private CommandLine commandLine;

	@Override
	public void execute(String batchName, String[] commandLineArgs) throws Exception
	{
		LogHandler.logInfo("Avvio batch " + batchName);

		Options options = getCommandLineOptions(batchName);

		parseCommandLine(options, commandLineArgs);
		
		if (!executeTest()) {
			execute();
		}
		
		LogHandler.logInfo("Fine batch " + batchName);
	}

	private boolean executeTest() throws Exception {
		if (! testMode()) {
			return false;
		}
		
		LogHandler.logInfo("Modalita' TEST");
	
		test();
		
		return true;
	}

	protected final boolean testMode() {
		return commandLine.hasOption(CmdLineOptions.TEST);
	}

	private void parseCommandLine(Options options, String[] commandLineArgs) throws ParseException, Exception
	{
		try
		{
			commandLine = SiacBatchPosixParser.parseCommandLine(options, commandLineArgs);
		}
		catch (MissingOptionException e)
		{
			throw new Exception("Parametri obbligatori omessi: " + StringUtils.join(e.getMissingOptions(), ", "));
		}
	}

	@SuppressWarnings("static-access")
	private Options getCommandLineOptions(String batchName) throws Exception
	{
		Options options = new Options();

		Class<? extends CmdLineOptions> cmdLineOptionsClass = BatchClassEnum.valueOf(batchName.toUpperCase()).getCmdLineOptionsClass();

		if (cmdLineOptionsClass != null) {
			Option[] optionList = (Option[]) cmdLineOptionsClass.getDeclaredField("OPTIONS").get(null);
	
			for (Option option : optionList) {
				options.addOption(option);
			}
		}

		options.addOption(OptionBuilder.withLongOpt(CmdLineOptions.TEST).create());

		return options;
	}

	protected abstract void execute() throws Exception;

	protected void test() throws Exception {/* Empty */}

	protected final String getOptionValue(String opt)
	{
		return commandLine.getOptionValue(opt);
	}

	protected final String[] getOptionValues(String opt)
	{
		return commandLine.getOptionValues(opt);
	}

	protected final Richiedente readRichiedente(String codiceEnte) throws Exception
	{
		GetRichiedente getRichiedente = new GetRichiedente();
		getRichiedente.setCodiceAccount(codiceEnte);

		LogHandler.logInfo("Chiamata coreService.getRichiedente()");

		GetRichiedenteResponse getrRichiedenteResponse = coreServiceInvoker.getRichiedente(getRichiedente);

		Richiedente richiedente = getrRichiedenteResponse.getRichiedente();
		return richiedente;
	}
}

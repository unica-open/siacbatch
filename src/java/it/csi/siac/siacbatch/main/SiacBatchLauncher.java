/*
*SPDX-FileCopyrightText: Copyright 2020 | CSI Piemonte
*SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.siac.siacbatch.main;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.OptionBuilder;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import it.csi.siac.siacbatch.batch.Batch;
import it.csi.siac.siacbatch.batch.CmdLineOptions;
import it.csi.siac.siacbatch.batch.SiacBatchPosixParser;
import it.csi.siac.siacbatch.util.LogHandler;

public class SiacBatchLauncher
{
	private static ApplicationContext appCtx = new ClassPathXmlApplicationContext(
			new String[] { "spring/siacBatchContext.xml" });

	public static void main(String[] args) throws Exception
	{
		Batch batch = null;
		
		try
		{
			String batchName = getBatchName(args);

			Class<? extends Batch> batchClass = BatchClassEnum.valueOf(batchName.toUpperCase()).getBatchClass();

			if (batchClass != null) {
				batch = appCtx.getBean(batchClass);
				batch.execute(batchName, args);
				exit(batch.getExitCode());
			} else {
				exitErr("Batch non valido");
			}
		}
		catch (Throwable t)
		{
			t.printStackTrace();
			exitErr(t.getMessage());
		}
	}

	@SuppressWarnings("static-access")
	public static String getBatchName(String[] args) throws ParseException
	{
		Options options = new Options().addOption(OptionBuilder.withLongOpt(CmdLineOptions.BATCH).hasArgs(1).isRequired().create());

		CommandLine cmd = SiacBatchPosixParser.parseCommandLine(options, args);

		return cmd.getOptionValue(CmdLineOptions.BATCH);
	}

	private static void exitErr(String message)
	{
		exitErr(message, 1);
	}

	private static void exitErr(String message, int exitCode)
	{
		LogHandler.logError("ERRORE: " + message);
		exit(1);
	}

	private static void exit(int x)
	{
		System.exit(x);
	}

}

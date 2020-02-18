/*
*SPDX-FileCopyrightText: Copyright 2020 | CSI Piemonte
*SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.siac.siacbatch.batch;

import java.util.ListIterator;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.cli.PosixParser;

public final class SiacBatchPosixParser extends PosixParser
{
	protected SiacBatchPosixParser()
	{
	}

	public static CommandLine parseCommandLine(Options options, String[] args) throws ParseException
	{
		return new SiacBatchPosixParser().parse(options, args);
	}

	@Override
	protected void processOption(String arg, ListIterator iter) throws ParseException
	{
		if (getOptions().hasOption(arg))
			super.processOption(arg, iter);
	}
}

/*
*SPDX-FileCopyrightText: Copyright 2020 | CSI Piemonte
*SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.siac.siacbatch.batch.durc;

import org.apache.commons.cli.Option;
import org.apache.commons.cli.OptionBuilder;

import it.csi.siac.siacbatch.batch.CmdLineOptions;

public final class DurcCmdLineOptions implements CmdLineOptions
{
	public static final String JSON_FILE = "json";
	
	@SuppressWarnings("static-access")
	public static final Option[] OPTIONS = new Option[]
	{
		OptionBuilder.withLongOpt(CODICE_ENTE).hasArgs(1).isRequired().create(),
		OptionBuilder.withLongOpt(JSON_FILE).hasArgs(1).isRequired().create(),
	};

}

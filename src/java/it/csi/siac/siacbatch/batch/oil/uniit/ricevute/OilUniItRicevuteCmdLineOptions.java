/*
*SPDX-FileCopyrightText: Copyright 2020 | CSI Piemonte
*SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.siac.siacbatch.batch.oil.uniit.ricevute;

import org.apache.commons.cli.Option;
import org.apache.commons.cli.OptionBuilder;

import it.csi.siac.siacbatch.batch.CmdLineOptions;

public final class OilUniItRicevuteCmdLineOptions implements CmdLineOptions
{
	public static final String DATA = "data";
	public static final String PATH = "path";
	public static final String TIPO = "tipo";
	
	@SuppressWarnings("static-access")
	public static final Option[] OPTIONS = new Option[]
	{
			OptionBuilder.withLongOpt(CODICE_ENTE).hasArg().isRequired().create(),
			OptionBuilder.withLongOpt(DATA).hasArg().isRequired().create(),
			OptionBuilder.withLongOpt(PATH).hasArg().isRequired().create(),
			OptionBuilder.withLongOpt(TIPO).hasArgs().isRequired().create(),
	};

}

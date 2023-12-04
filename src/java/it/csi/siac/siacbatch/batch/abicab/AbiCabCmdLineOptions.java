/*
*SPDX-FileCopyrightText: Copyright 2020 | CSI Piemonte
*SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.siac.siacbatch.batch.abicab;

import org.apache.commons.cli.Option;
import org.apache.commons.cli.OptionBuilder;

import it.csi.siac.siacbatch.batch.CmdLineOptions;

public final class AbiCabCmdLineOptions implements CmdLineOptions
{
	public static final String DOWNLOAD_URL = "download_url";
	
	@SuppressWarnings("static-access")
	public static final Option[] OPTIONS = new Option[]
	{
		OptionBuilder.withLongOpt(DOWNLOAD_URL).hasArgs(1).isRequired().create(),
	};

}

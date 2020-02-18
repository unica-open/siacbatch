/*
*SPDX-FileCopyrightText: Copyright 2020 | CSI Piemonte
*SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.siac.siacbatch.batch.oil.xml;

import org.apache.commons.cli.Option;
import org.apache.commons.cli.OptionBuilder;
import org.apache.commons.cli.PatternOptionBuilder;

import it.csi.siac.siacbatch.batch.CmdLineOptions;

public final class OilXmlCmdLineOptions implements CmdLineOptions
{
	public static final String LIM_ORDINATIVI = "limite_ordinativi";
	public static final String XML_NAME = "xml";
	public static final String PATH = "path";
	public static final String ID_ELABORAZIONE = "id_elaborazione";
	
	@SuppressWarnings("static-access")
	public static final Option[] OPTIONS = new Option[]
	{
		OptionBuilder.withLongOpt(CODICE_ENTE).hasArgs(1).isRequired().create(),
		OptionBuilder.withLongOpt(ID_ELABORAZIONE).hasArgs(1).isRequired().create(),
		OptionBuilder.withLongOpt(XML_NAME).hasArgs(1).isRequired().create(),
		OptionBuilder.withLongOpt(PATH).hasArgs(1).isRequired().create(),
		OptionBuilder.withLongOpt(LIM_ORDINATIVI).hasArgs(1).isRequired().withType(PatternOptionBuilder.NUMBER_VALUE).create(),
	};
}

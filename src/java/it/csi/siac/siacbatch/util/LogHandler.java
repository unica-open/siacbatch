/*
*SPDX-FileCopyrightText: Copyright 2020 | CSI Piemonte
*SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.siac.siacbatch.util;

public class LogHandler
{
	public static void logInfo(String msg)
	{
		System.out.println("INFO\t" + msg);
	}
	
	public static void logError(String msg)
	{
		System.out.println("ERROR\t" + msg);
	}
	
}

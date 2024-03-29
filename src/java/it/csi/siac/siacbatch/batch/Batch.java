/*
*SPDX-FileCopyrightText: Copyright 2020 | CSI Piemonte
*SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.siac.siacbatch.batch;

public interface Batch
{
	public void execute(String batchName, String[] args) throws Exception;
	public int getExitCode();
}

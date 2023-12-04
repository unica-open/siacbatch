/*
*SPDX-FileCopyrightText: Copyright 2020 | CSI Piemonte
*SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.siac.siacbatch.batch.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import it.csi.siac.siacbatch.batch.BaseBatch;

@Component
public class TestBatch extends BaseBatch
{
	@Autowired
	private TestDao testDao;
	
	@Override
	public void execute() throws Exception
	{
		testDao.test();
	}
}

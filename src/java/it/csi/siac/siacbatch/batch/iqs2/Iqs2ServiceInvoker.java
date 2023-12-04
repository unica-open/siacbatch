/*
*SPDX-FileCopyrightText: Copyright 2020 | CSI Piemonte
*SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.siac.siacbatch.batch.iqs2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import it.csi.siac.siacbatch.batch.BaseServiceInvoker;
import it.csi.siac.siacbilser.frontend.webservice.Iqs2Service;
import it.csi.siac.siacintegser.frontend.webservice.msg.ElaboraFile;
import it.csi.siac.siacintegser.frontend.webservice.msg.ElaboraFileResponse;

@Component
public class Iqs2ServiceInvoker extends BaseServiceInvoker
{
	@Autowired
	protected Iqs2Service iqs2Service;

	public ElaboraFileResponse elaboraFileIqs2(ElaboraFile request) throws Exception
	{
		ElaboraFileResponse response = iqs2Service.elaboraFileIqs2(request);
		checkServiceResponse(response);

		return response;
	}

}

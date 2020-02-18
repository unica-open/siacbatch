/*
*SPDX-FileCopyrightText: Copyright 2020 | CSI Piemonte
*SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.siac.siacbatch.batch.abicab;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.zip.ZipInputStream;

import org.springframework.stereotype.Component;

@Component
public final class AbiCabFileReader
{
	private ZipInputStream zin;
	private InputStreamReader isr;
	private BufferedReader br;

	public BufferedReader getReader(URL url) throws Exception
	{
		zin = new ZipInputStream(getByteArrayInputStream(url));

		zin.getNextEntry();

		isr = new InputStreamReader(zin);
		br = new BufferedReader(isr);

		return br;
	}

	public void close() throws Exception
	{
		br.close();
		isr.close();
		zin.close();
	}

	private ByteArrayInputStream getByteArrayInputStream(URL url) throws IOException
	{
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		InputStream is = url.openStream();

		byte[] byteChunk = new byte[4096];
		int n;

		while ((n = is.read(byteChunk)) > 0)
			baos.write(byteChunk, 0, n);

		baos.close();
		is.close();

		ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
		return bais;
	}
}

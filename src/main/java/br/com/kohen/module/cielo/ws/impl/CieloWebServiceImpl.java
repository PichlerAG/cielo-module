package br.com.kohen.module.cielo.ws.impl;

import static br.com.kohen.module.cielo.utils.XmlTemplateReader.TemplateTransaction.CHECK;
import static br.com.kohen.module.cielo.utils.XmlTemplateReader.TemplateTransaction.NEW;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.fluent.Form;
import org.apache.http.client.fluent.Request;

import br.com.kohen.module.cielo.entity.BusinessEstablishment;
import br.com.kohen.module.cielo.entity.CieloResponse;
import br.com.kohen.module.cielo.entity.CieloTransaction;
import br.com.kohen.module.cielo.exception.ConnectionFailedException;
import br.com.kohen.module.cielo.utils.PropertiesAcessor;
import br.com.kohen.module.cielo.utils.XmlTemplateReader.TemplateTransaction;
import br.com.kohen.module.cielo.ws.CieloWebService;

public class CieloWebServiceImpl implements CieloWebService {

	private static final int ONE_SECOUND = 1000;
	private static final int _CONNECTION_TIMEOUT = 10 * ONE_SECOUND;


	private static final String URL_WS = PropertiesAcessor.load().getProperty("cielo.url.webservice");
	
	public CieloResponse newTransaction(CieloTransaction transaction) throws IOException {
		
		return callWS(transaction, NEW);
	}

	public CieloResponse findTransaction(String tid, BusinessEstablishment bEstablishment) throws IOException {
		
		CieloTransaction transaction = CieloTransaction.build()
			.withBusinessEstablishment(bEstablishment)
			.withTid(tid);
		
		return callWS(transaction, CHECK);
	}
	
	private CieloResponse callWS(CieloTransaction transaction, TemplateTransaction template)
			throws IOException {
		
		String content = "";
			
			content = Request.Post(URL_WS)
					.connectTimeout(_CONNECTION_TIMEOUT)
					.bodyForm(Form.form().add("mensagem", transaction.toXml(template)).build())
					.execute().returnContent().asString();
		
		return CieloResponse.build(content);
	}

}

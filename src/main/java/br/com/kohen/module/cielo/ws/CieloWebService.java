package br.com.kohen.module.cielo.ws;

import br.com.kohen.module.cielo.entity.BusinessEstablishment;
import br.com.kohen.module.cielo.entity.CieloResponse;
import br.com.kohen.module.cielo.entity.CieloTransaction;

import java.io.IOException;


public interface CieloWebService {

	CieloResponse newTransaction(CieloTransaction transaction) throws IOException;
	
	CieloResponse findTransaction(String tid, BusinessEstablishment bEstablishment) throws IOException;
}

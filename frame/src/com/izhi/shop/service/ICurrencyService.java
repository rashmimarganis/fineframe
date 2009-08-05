package com.izhi.shop.service;

import java.util.List;

import com.izhi.platform.util.PageParameter;
import com.izhi.shop.model.Currency;

public interface ICurrencyService {
	int saveCurrency(Currency obj);
	boolean updateCurrency(Currency obj);
	boolean deleteCurrency(int id);
	boolean deleteCurrencys(List<Integer> ids) ;
	Currency findCurrencyById(int id);
	List<Currency> findPage(PageParameter pp);
	int findTotalCount();
}
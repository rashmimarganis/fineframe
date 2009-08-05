package com.izhi.shop.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springmodules.cache.annotations.CacheFlush;
import org.springmodules.cache.annotations.Cacheable;

import com.izhi.platform.util.PageParameter;
import com.izhi.shop.dao.ICurrencyDao;
import com.izhi.shop.model.Currency;
import com.izhi.shop.service.ICurrencyService;
@Service("currencyService")
public class CurrencyServiceImpl implements ICurrencyService {

	@Resource(name="currencyDao")
	private ICurrencyDao currencyDao;
	public ICurrencyDao getCurrencyDao() {
		return currencyDao;
	}

	public void setCurrencyDao(ICurrencyDao currencyDao) {
		this.currencyDao = currencyDao;
	}

	@Override
	@CacheFlush(modelId="currencyFlushing")
	public boolean deleteCurrency(int id) {
		return currencyDao.deleteCurrency(id);
	}

	@Override
	@CacheFlush(modelId="currencyFlushing")
	public boolean deleteCurrencys(List<Integer> ids) {
		return currencyDao.deleteCurrencys(ids);
	}

	@Override
	@Cacheable(modelId="currencyCaching")
	public Currency findCurrencyById(int id) {
		return currencyDao.findCurrencyById(id);
	}

	@Override
	@Cacheable(modelId="currencyCaching")
	public List<Currency> findPage(PageParameter pp) {
		return currencyDao.findPage(pp);
	}

	@Override
	@Cacheable(modelId="currencyCaching")
	public int findTotalCount() {
		return currencyDao.findTotalCount();
	}

	@Override
	@CacheFlush(modelId="currencyFlushing")
	public int saveCurrency(Currency obj) {
		return currencyDao.saveCurrency(obj);
	}

	@Override
	@CacheFlush(modelId="currencyFlushing")
	public boolean updateCurrency(Currency obj) {
		return currencyDao.updateCurrency(obj);
	}

}

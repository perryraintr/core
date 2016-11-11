package com.raintr.pinshe.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.raintr.pinshe.bean.MerchantBean;
import com.raintr.pinshe.bean.StoreBean;
import com.raintr.pinshe.bean.StoreCashBean;
import com.raintr.pinshe.service.MerchantService;
import com.raintr.pinshe.service.StoreCashService;
import com.raintr.pinshe.service.StoreService;

@Controller
@RequestMapping(value = "/")
public class TestAction extends BaseAction {
	@Autowired
	private StoreCashService storeCashService;
	@Autowired
	private MerchantService merchantService;
	@Autowired
	private StoreService storeService;
	
	@RequestMapping(value = "/test")
    public String Init(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception{
		return super.Init(request, response, model);
	}
	
	protected String Action(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
		int p1 = 1;
		int p2 = 1;
		StoreBean store;
		StoreCashBean storeCash;
		List<StoreCashBean> storeCashs;
		MerchantBean merchant;
		
		List<StoreBean> stores = storeService.By((p1 - 1) * 100);

		while(stores != null && stores.size() > 0){
			
			for(int i = 0; i < stores.size(); i ++){
				store = stores.get(i);
				//store = storeService.ById(76);
				
				merchant = merchantService.ById(store.getMerchant_id());
				if(merchant != null){
					store.setCurrent(merchant.getCurrent());
					storeService.Modify(store, null, null);
					
					double amount = store.getCurrent();
					
					p2 = 1;
					storeCashs = storeCashService.ByStoreId(store.getId(), (p2 - 1) * 100);
					while(storeCashs != null && storeCashs.size() > 0){
						for(int j = 0; j < storeCashs.size(); j ++){
							storeCash = storeCashs.get(j);
							if(storeCash.getType() == -1){
								amount = amount + storeCash.getAmount();
								storeCash.setTotal(amount);
								storeCashService.Modify(storeCash);
								continue;
							}
							
							if(storeCash.getType() == 1){
								amount = amount - storeCash.getAmount();
								storeCash.setTotal(amount);
								storeCashService.Modify(storeCash);
								continue;
							}
						}
						
						storeCashs = storeCashService.ByStoreId(store.getId(), (++p2 - 1) * 100);
					}
				}
			}
			
			stores = storeService.By((++p1 - 1) * 100);
		}
		
		response.getWriter().print("{\"head\":1,\"body\":{}}");
		return null;
	}
}
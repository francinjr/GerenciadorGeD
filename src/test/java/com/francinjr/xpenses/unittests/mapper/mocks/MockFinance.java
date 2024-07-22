package com.francinjr.xpenses.unittests.mapper.mocks;

import java.util.ArrayList;
import java.util.List;

import com.francinjr.xpenses.domain.model.Finance;
import com.francinjr.xpenses.domain.model.FinanceType;
import com.francinjr.xpenses.dto.finance.FinanceDTO;

public class MockFinance {


    public Finance mockEntity() {
        return mockEntity(0);
    }
    
    public FinanceDTO mockVO() {
        return mockVO(0);
    }
    
    public List<Finance> mockEntityList() {
        List<Finance> finances = new ArrayList<Finance>();
        for (int i = 0; i < 14; i++) {
        	finances.add(mockEntity(i));
        }
        return finances;
    }

    public List<FinanceDTO> mockVOList() {
        List<FinanceDTO> finances = new ArrayList<>();
        for (int i = 0; i < 14; i++) {
        	finances.add(mockVO(i));
        }
        return finances;
    }
    
    public Finance mockEntity(Integer number) {
    	Finance finance = new Finance();
		finance.setName("Name Test" + number);
		finance.setValue((Double) 25.75);
		finance.setDescription("Description Test" + number);
		finance.setType(FinanceType.EARNING);
		//finance.setPaiday(LocalDateTime.now());
		finance.setId(number.longValue());
        return finance;
    }

    public FinanceDTO mockVO(Integer number) {
    	FinanceDTO finance = new FinanceDTO();
		finance.setName("Name Test" + number);
		finance.setValue((Double) 25.75);
		finance.setDescription("Description Test" + number);
		//finance.setType(FinanceType.EARNING);
		//finance.setPaiday(LocalDateTime.now());
		finance.setKey(number.longValue());
        return finance;
    }

}

package com.francinjr.xpenses.mapper;

import java.util.ArrayList;
import java.util.List;

import com.francinjr.xpenses.domain.model.Finance;
import com.francinjr.xpenses.dto.finance.FinanceDTO;

import org.modelmapper.ModelMapper;

public class Mapper {
	
	private static ModelMapper mapper = new ModelMapper();
	
	static {
        mapper.createTypeMap(
                Finance.class,
                FinanceDTO.class)
            .addMapping(Finance::getId, FinanceDTO::setKey);
        mapper.createTypeMap(
            FinanceDTO.class,
            Finance.class)
            .addMapping(FinanceDTO::getKey, Finance::setId);
	}
	
	public static <O, D> D parseObject(O origin, Class<D> destination) {
		return mapper.map(origin, destination);
	}
	
	public static <O, D> List<D> parseListObjects(List<O> origin, Class<D> destination) {
		List<D> destinationObjects = new ArrayList<D>();
		for (O o : origin) {
			destinationObjects.add(mapper.map(o, destination));
		}
		return destinationObjects;
	}

}

package com.devthiagofurtado.converter;

import com.github.dozermapper.core.DozerBeanMapperBuilder;
import com.github.dozermapper.core.Mapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.List;
import java.util.stream.Collectors;

public class DozerConverter {

    private static Mapper mapper = DozerBeanMapperBuilder.buildDefault();

    public static <O, D> D parseObject(O origin, Class<D> destination) {

        return mapper.map(origin, destination);
    }

    public static <O, D> List<D> parseListObjects(List<O> origin, Class<D> destination) {

        return origin.stream().map(o -> mapper.map(o, destination)).collect(Collectors.toList());

    }

    public static <O, D> Page<D> parsePageObjects(Page<O> origin, Class<D> destination) {

        return new PageImpl<>(origin.stream().map(o -> mapper.map(o, destination)).collect(Collectors.toList()));

    }
}

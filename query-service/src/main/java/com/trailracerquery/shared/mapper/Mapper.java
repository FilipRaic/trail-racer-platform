package com.trailracerquery.shared.mapper;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;

@Component
@RequiredArgsConstructor
public class Mapper {

    private final ModelMapper modelMapper;

    public <T, S> S map(@NonNull T entity, @NonNull Class<S> destinationClass) {
        return modelMapper.map(entity, destinationClass);
    }

    public <T, S> List<S> mapList(@NonNull Collection<T> entityList, @NonNull Class<S> destinationClass) {
        return entityList.stream().map(entity -> modelMapper.map(entity, destinationClass)).toList();
    }

    public <T, S> Page<@NonNull S> mapPage(@NonNull Page<@NonNull T> entityPage, @NonNull Class<S> destinationClass) {
        List<S> mappedContent = entityPage
                .stream()
                .map(entity -> modelMapper.map(entity, destinationClass))
                .toList();

        return new PageImpl<>(
                mappedContent,
                entityPage.getPageable(),
                entityPage.getTotalElements()
        );
    }
}

package br.com.jitec.aps.commons.payload.mapper;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import org.mapstruct.Mapping;

@Retention(RetentionPolicy.CLASS)
@Mapping(target = "id", ignore = true)
@Mapping(target = "uid", ignore = true)
@Mapping(target = "version", ignore = true)
public @interface ToEntity { }
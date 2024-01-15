package com.apipracticebatch;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ListMapper {
    @Select("select `language` from `list`")
    List<ListDTO> read_data();


    @Select("select `language` from `list`")
    List<ListDTO> read_all_data();
}

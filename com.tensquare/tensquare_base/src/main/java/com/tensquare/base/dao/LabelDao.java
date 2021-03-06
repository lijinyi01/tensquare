package com.tensquare.base.dao;
import com.tensquare.base.entities.Label;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Component;

/**
 * 标签数据访问接口
 */

public interface LabelDao extends  JpaRepository<Label,String>,JpaSpecificationExecutor<Label>{
}

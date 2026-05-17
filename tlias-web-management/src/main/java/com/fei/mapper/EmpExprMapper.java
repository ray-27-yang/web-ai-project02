package com.fei.mapper;

import com.fei.pojo.EmpExpr;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 员工工作经历操作接口
 */
@Mapper
public interface EmpExprMapper {
    /**
     * 批量保存员工工作经历信息
     */
    public void insertBatch(List<EmpExpr> exprList);
}

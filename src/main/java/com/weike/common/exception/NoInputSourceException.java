package com.weike.common.exception;

import lombok.Data;

import java.util.List;

/**
 * @ClassName: NoInputSoureException
 * @Author: YuanDing
 * @Date: 2024/4/12 19:44
 * @Description:
 */

@Data
public class NoInputSourceException extends Exception{

    private List<String> noInputList;

    public NoInputSourceException(String message , List<String> noInputList) {
        super(message);
        this.noInputList = noInputList;
    }
}

package com.xyw.exception;

import com.xyw.result.CodeMsg;

/**
 * Created with IDEA
 * author:RicardoXu
 * Date:2019/5/20
 * Time:00:09
 */
public class GlobalException extends RuntimeException{

    private static final long serialVersionUID = 1L;

    private CodeMsg cm;


    public GlobalException(CodeMsg cm) {
        this.cm = cm;
    }

    public CodeMsg getCm() {
        return cm;
    }
}

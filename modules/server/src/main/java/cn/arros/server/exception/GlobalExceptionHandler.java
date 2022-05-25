package cn.arros.server.exception;

import cn.arros.server.common.CommonResult;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @Author Verge
 * @Date 2021/11/1 16:39
 * @Version 1.0
 */
@RestControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(GitAPIException.class)
    public CommonResult handlerGitAPIException(){
        log.error("error");
        return CommonResult.error();
    }

}

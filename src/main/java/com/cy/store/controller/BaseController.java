package com.cy.store.controller;

import com.cy.store.controller.ex.*;
import com.cy.store.service.ex.*;
import com.cy.store.util.JsonResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
//控制层的基类，用来处理异常
public class BaseController {
    //操作成功的状态码
    public static final int OK = 200;

    //返回异常状态码
    @ExceptionHandler({ServiceException.class, FileUploadException.class})
    public JsonResult<Void> handlerException(Throwable e) {
        JsonResult<Void> result = new JsonResult<>(e);
        if (e instanceof FileEmptyException) {
            result.setState(4000);
        } else if (e instanceof FileSizeException) {
            result.setState((4001));
        } else if (e instanceof FileStateException) {
            result.setState((4002));
        } else if (e instanceof FileTypeException) {
            result.setState((4003));
        } else if (e instanceof FileUploadIOException) {
            result.setState((4004));
        } else if (e instanceof FileUploadException) {
            result.setState((4005));
        } else if (e instanceof AccessDeniedException) {
            result.setState((5001));
        } else if (e instanceof AddressCountLimitException) {
            result.setState((5002));
        } else if (e instanceof AddressNotFoundException) {
            result.setState((5003));
        } else if (e instanceof CartNotFoundException) {
            result.setState((5004));
        } else if (e instanceof DeleteException) {
            result.setState((5005));
        } else if (e instanceof InsertException) {
            result.setState((5006));
        } else if (e instanceof PasswordNotMatchException) {
            result.setState((5007));
        } else if (e instanceof ProductNotFoundException) {
            result.setState((5008));
        } else if (e instanceof UserNotFoundException) {
            result.setState((5009));
        } else if (e instanceof UpdateException) {
            result.setState((5010));
        } else if (e instanceof UsernameDuplicatedException) {
            result.setState((5011));
        } else if (e instanceof ServiceException) {
            result.setState((5012));
        }
        return result;
    }

    protected final Integer getUidFromSession(HttpSession session) {
        return Integer.valueOf(session.getAttribute("uid").toString());
    }

    protected final String getUsernameFromSession(HttpSession session) {
        return session.getAttribute("username").toString();
    }
}

package com.cy.store.util;

import com.cy.store.controller.ex.FileEmptyException;
import com.cy.store.controller.ex.FileSizeException;
import com.cy.store.controller.ex.FileTypeException;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

//检查头像文件是否符合要求
public class FileCheck {

    public static final int AVATAR_MAX_SIZE = 10 * 1024 * 1024;

    //限制上传文件的类型
    public static final List<String> AVATAR_TYPE = new ArrayList<>();

    //向列表加载文件类型
    static {
        AVATAR_TYPE.add("image/jpeg");
        AVATAR_TYPE.add("image/png");
        AVATAR_TYPE.add("image/bmp");
        AVATAR_TYPE.add("image/gif");
        AVATAR_TYPE.add("image/jpg");
    }

    public static void fileCheck(MultipartFile file) {
        if (file.isEmpty()) {
            throw new FileEmptyException("上传文件为空");
        }
        if (file.getSize() > AVATAR_MAX_SIZE) {
            throw new FileSizeException("文件大小超出限制");
        }
        if (!AVATAR_TYPE.contains(file.getContentType())) {
            throw new FileTypeException("上传文件类型不支持");
        }
    }
}

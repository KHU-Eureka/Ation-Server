package com.eureka.ationserver.service;

import com.eureka.ationserver.model.user.User;
import com.eureka.ationserver.utils.image.ImageUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MyPageService {
    @Transactional
    public Long saveImg(User user, MultipartFile myPageImg) throws IOException {

        List<String> pathList = ImageUtil.getImagePath(User.MYPAGE_PREFIX, user.getId());
        File file = new File(pathList.get(1));
        myPageImg.transferTo(file);
        user.setMypageImgPath(pathList.get(0));

        return user.getId();
    }
}

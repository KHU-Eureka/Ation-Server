package com.eureka.ationserver.service;

import com.eureka.ationserver.model.user.User;
import com.eureka.ationserver.utils.image.ImageUtil;
import java.io.File;
import java.io.IOException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class MyPageService {
  
    private final AuthService authService;

    @Transactional
    public Long saveImg(MultipartFile myPageImg) throws IOException {
        User user = authService.auth();

        List<String> pathList = ImageUtil.getImagePath(User.MYPAGE_PREFIX, user.getId());
        File file = new File(pathList.get(1));
        myPageImg.transferTo(file);
        user.setMyPageImgPath(pathList.get(0));

        return user.getId();
    }
}

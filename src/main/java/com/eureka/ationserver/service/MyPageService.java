package com.eureka.ationserver.service;

import com.eureka.ationserver.domain.insight.Pin;
import com.eureka.ationserver.domain.user.User;
import com.eureka.ationserver.dto.pin.PinResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MyPageService {

    @Value("${eureka.app.publicIp}")
    private String HOST;

    @Value("${server.port}")
    private String PORT;

    @Value("${eureka.app.imagePath}")
    private String IMAGEPATH;

    public String getMyPageImageDefaultPath(){
        // set file name
        List<String> pathList = new ArrayList<>();

        String fileName = "mypage.png";
        String url = "http://"+HOST+":"+PORT+"/api/image?path=";
        String apiPath = url + IMAGEPATH+"mypage/" + fileName;
        return apiPath;
    }

    public List<String> getMyPageImagePath(Long pinId){
        // set file name
        List<String> pathList = new ArrayList<>();

        String fileName = "mypage-"+ pinId +".png";
        String url = "http://"+HOST+":"+PORT+"/api/image?path=";
        String apiPath = url +IMAGEPATH+"mypage/"+ fileName;

        String path = IMAGEPATH + "mypage/"+ fileName;
        pathList.add(apiPath);
        pathList.add(path);
        return pathList;
    }


    @Transactional
    public Long saveImg(User user, MultipartFile myPageImg) throws IOException {

        List<String> pathList = getMyPageImagePath(user.getId());
        File file = new File(pathList.get(1));
        myPageImg.transferTo(file);
        user.setMypageImgPath(pathList.get(0));

        return user.getId();
    }
}

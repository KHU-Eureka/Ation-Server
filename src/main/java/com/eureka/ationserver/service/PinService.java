package com.eureka.ationserver.service;

import com.eureka.ationserver.advice.exception.ForbiddenException;
import com.eureka.ationserver.domain.insight.*;
import com.eureka.ationserver.domain.user.User;
import com.eureka.ationserver.dto.pin.InsightPinRequest;
import com.eureka.ationserver.dto.pin.PinRequest;
import com.eureka.ationserver.dto.pin.PinResponse;
import com.eureka.ationserver.dto.insight.InsightRequest;
import com.eureka.ationserver.repository.insight.PinRepository;
import com.eureka.ationserver.repository.insight.InsightRepository;
import com.eureka.ationserver.repository.insight.PinBoardRepository;
import com.eureka.ationserver.repository.insight.PinTagRepository;
import lombok.RequiredArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class PinService {

    private final InsightRepository insightRepository;
    private final PinBoardRepository pinBoardRepository;
    private final PinTagRepository pinTagRepository;
    private final PinRepository pinRepository;
    private final InsightService insightService;


    @Transactional
    public Long saveNewPin(User user, InsightPinRequest insightPinRequest) throws IOException {

        PinBoard pinBoard = pinBoardRepository.getById(insightPinRequest.getPinBoardId());

        if (user.getId() != pinBoard.getPersona().getUser().getId()) {
            throw new ForbiddenException();
        } else {
            // save insgiht
            String crawlingUrl = insightPinRequest.getUrl();
            Document document = Jsoup.connect(crawlingUrl).get();

            String title = document.select("meta[property=og:title]").first().attr("content");

            String description;
            try {
                description = document.select("meta[property=og:description]").get(0).attr("content");

            } catch (Exception e) {
                description = null;
            }

            String imageUrl;
            try {
                imageUrl = document.select("meta[property=og:image]").get(0).attr("content");

            } catch (Exception e) {
                imageUrl = insightService.getInsightImageDefaultPath();
            }

            String siteName;
            try {
                siteName = document.select("meta[property=og:site_name]").get(0).attr("content");

            } catch (Exception e) {
                siteName = "-";
            }


            Insight insight = Insight.builder()
                    .url(insightPinRequest.getUrl())
                    .title(title)
                    .description(description)
                    .imgPath(imageUrl)
                    .siteName(siteName)
                    .insightMainCategory(null)
                    .insightSubCategory(null)
                    .open(false)
                    .build();
            insightRepository.save(insight);


            // save InsightPin
            Pin pin = Pin.builder()
                    .pinBoard(pinBoard)
                    .insight(insight)
                    .build();
            Pin saved = pinRepository.save(pin);

            // save Pin tag
            for(String name : insightPinRequest.getTagList()){
                PinTag pinTag = PinTag.builder()
                        .pin(pin)
                        .name(name)
                        .build();
                pinTagRepository.save(pinTag);
            }

            return saved.getId();

        }
    }

    @Transactional
    public Long pinUp(User user, PinRequest pinRequest){
        PinBoard pinBoard = pinBoardRepository.getById(pinRequest.getPinBoardId());
        if(user.getId() != pinBoard.getPersona().getUser().getId()){
            throw new ForbiddenException();
        }else{
            Insight insight = insightRepository.getById(pinRequest.getInsightId());
            Pin pin = pinRequest.toEnitity(pinBoard, insight);
            Pin saved = pinRepository.save(pin);
            // Copy Tags
            for(InsightTag insightTag : insight.getInsightTagList()){
                PinTag pinTag = PinTag.builder()
                        .pin(pin)
                        .name(insightTag.getName())
                        .build();
                pinTagRepository.save(pinTag);
            }

            return saved.getId();
        }

    }

}

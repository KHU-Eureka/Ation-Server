package com.eureka.ationserver.service;

import com.eureka.ationserver.domain.insight.Insight;
import com.eureka.ationserver.domain.insight.InsightPin;
import com.eureka.ationserver.domain.insight.PinBoard;
import com.eureka.ationserver.domain.insight.PinTag;
import com.eureka.ationserver.dto.insight.InsightRequest;
import com.eureka.ationserver.repository.insight.InsightRepository;
import com.eureka.ationserver.repository.insight.PinBoardRepository;
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


    @Transactional
    public Long saveNewPin(InsightRequest insightRequest, Long pinBoardId) throws IOException {

        String crawlingUrl = insightRequest.getUrl();
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
            imageUrl = null;
        }

        String siteName;
        try {
            siteName = document.select("meta[property=og:site_name]").get(0).attr("content");

        } catch (Exception e) {
            siteName = "-";
        }

        PinBoard pinBoard = pinBoardRepository.getById(pinBoardId);



        Insight insight = Insight.builder()
                .url(insightRequest.getUrl())
                .title(title)
                .description(description)
                .imgPath(imageUrl)
                .siteName(siteName)
                .insightMainCategory(null)
                .insightSubCategory(null)
                .open(false)
                .build();
        Insight saved = insightRepository.save(insight);

        InsightPin insightPin = InsightPin.builder()
                .pinBoard(pinBoard)
                .insight(insight)
                .build();


        for(String tag : insightRequest.getTagList()){
            PinTag pinTag = PinTag.builder()
                    .insightPin(insightPin)
                    .name(tag)
                    .build();
            pinBoardRepository.save(pinBoard);
        }
        return saved.getId();

    }
}

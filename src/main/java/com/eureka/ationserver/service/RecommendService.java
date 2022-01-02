package com.eureka.ationserver.service;

import com.eureka.ationserver.domain.insight.Insight;
import com.eureka.ationserver.domain.insight.InsightView;
import com.eureka.ationserver.domain.user.User;
import com.eureka.ationserver.repository.insight.InsightRepository;
import com.eureka.ationserver.repository.insight.InsightViewRepository;
import com.eureka.ationserver.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RecommendService {
    private final InsightViewRepository insightViewRepository;
    private final UserRepository userRepository;
    private final InsightRepository insightRepository;
    @Transactional(readOnly = true)
    public int[][] createMatrix(){
        List<User> userList= userRepository.findAll();
        List<Insight> insightList = insightRepository.findAll();
        List<InsightView> insightViewList = insightViewRepository.findAll();

        int[][] matrix = new int[userList.size()+1][insightList.size()+1];
        for(InsightView insightView : insightViewList){
            matrix[insightView.getUser().getId().intValue()][insightView.getInsight().getId().intValue()] = 1;
        }
        return matrix;
    }
}

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

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RecommendService {
    private final InsightViewRepository insightViewRepository;
    private final UserRepository userRepository;
    private final InsightRepository insightRepository;
    @Transactional(readOnly = true)
    public int[][] createUserInsightViewMatrix(){
        List<InsightView> insightViewList = insightViewRepository.findAll();

        int[][] matrix = new int[insightViewList.size()][2];
        int i = 0;
        for(InsightView insightView : insightViewList){
            matrix[i][0] = insightView.getUser().getId().intValue();
            matrix[i][1] = insightView.getInsight().getId().intValue();
            i++;
        }
        return matrix;
    }

    @Transactional(readOnly = true)
    public String[][] createInsightMatrix(){
        List<Insight> insightList = insightRepository.findByOpen(Boolean.TRUE);

        String[][] matrix = new String[insightList.size()][2];
        int i = 0;
        for(Insight insight : insightList){
            matrix[i][0] = insight.getId().toString();
            matrix[i][1] = insight.getTitle();
            i++;

        }
        return matrix;
    }

    @Transactional(readOnly = true)
    public List<Long> createUserMatrix(){
        List<User> userList = userRepository.findAll();

        List<Long> matrix = new ArrayList<>();

        int i = 0;
        for(User user : userList){
            matrix.add(user.getId());
        }
        return matrix;
    }
}

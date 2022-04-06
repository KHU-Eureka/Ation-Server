package com.eureka.ationserver.dto.lounge;

import com.eureka.ationserver.dto.category.CategoryResponse;
import com.eureka.ationserver.dto.persona.PersonaResponse;
import com.eureka.ationserver.dto.persona.category.SenseResponse;
import com.eureka.ationserver.model.lounge.Lounge;
import com.eureka.ationserver.model.lounge.LoungeChat;
import com.eureka.ationserver.model.lounge.LoungeImage;
import com.eureka.ationserver.model.lounge.LoungeMember;
import com.eureka.ationserver.model.lounge.LoungePin;
import com.eureka.ationserver.model.lounge.status.LoungeStatus;
import com.eureka.ationserver.utils.image.ImageUtil;
import io.swagger.annotations.ApiModelProperty;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

public class LoungeResponse {

  @Data
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  public static class Out {

    @ApiModelProperty(value = "라운지 id", position = 1)
    private Long id;

    @ApiModelProperty(value = "라운지 제목", position = 2)
    private String title;

    @ApiModelProperty(value = "라운지 멤버 제한", position = 3)
    private Integer limitMember;

    @ApiModelProperty(value = "라운지 상태", position = 4)
    private LoungeStatus status;

    @ApiModelProperty(value = "라운지 소개", position = 5)
    private String introduction;

    @ApiModelProperty(value = "라운지 공지", position = 6)
    private String notice;

    @ApiModelProperty(value = "라운지 총멤버", position = 7)
    private Long totalMember;

    @ApiModelProperty(value = "라운지 이미지", position = 8)
    private String imgPath;

    @ApiModelProperty(value = "라운지 화이트보드", position = 9)
    private String whiteboard;

    @ApiModelProperty(value = "라운지 생성일자", position = 10)
    private LocalDateTime createdAt;

    @ApiModelProperty(value = "라운지 방장 페르소나", position = 11)
    private PersonaResponse.SimpleOut persona;

    @ApiModelProperty(value = "라운지 선호감각", position = 12)
    private SenseResponse.Out sense;

    @ApiModelProperty(value = "라운지 메인 카테고리", position = 13)
    private CategoryResponse.Main mainCategory;

    @ApiModelProperty(value = "라운지 서브 카테고리", position = 14)
    private List<CategoryResponse.Sub> subCategoryList;

    @ApiModelProperty(value = "라운지 멤버", position = 15)
    private List<MemberOut> memberList;
  }

  @Data
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  public static class ChatOut {

    @ApiModelProperty(value = "페르소나", position = 0)
    private PersonaResponse.SimpleOut persona;

    @ApiModelProperty(value = "내용", position = 1)
    private String content;

    @ApiModelProperty(value = "전송일시", position = 2)
    private LocalDateTime createdAt;

  }

  @Data
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  public static class MemberOut {

    @ApiModelProperty(value = "레디여부", position = 0)
    private Boolean ready;

    @ApiModelProperty(value = "방장여부", position = 1)
    private Boolean admin;

    @ApiModelProperty(value = "페르소나", position = 2)
    private PersonaResponse.SimpleOut persona;

  }

  @Data
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  public static class MemberStatusOut {

    @ApiModelProperty(value = "라운지", position = 0)
    private LoungeResponse.Out lounge;

    @ApiModelProperty(value = "페르소나", position = 1)
    private PersonaResponse.SimpleOut persona;

    @ApiModelProperty(value = "레디여부", position = 2)
    private Boolean ready;

    @ApiModelProperty(value = "방장여부", position = 3)
    private Boolean admin;
  }

  @Data
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  public static class PinOut {

    @ApiModelProperty(value = "라운지", position = 0)
    private LoungeResponse.Out lounge;

  }

  @Data
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  public static class IdOut {

    @ApiModelProperty(value = "라운지 id", position = 0)
    private Long id;

  }

  @Data
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  public static class WhiteboardOut {

    @ApiModelProperty(value = "라운지 화이트보드", position = 0)
    private String whiteboard;

  }

  @Data
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  public static class NoticeOut {

    @ApiModelProperty(value = "라운지 공지", position = 0)
    private String notice;

  }

  @Data
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  public static class ImageOut {

    @ApiModelProperty(value = "라운지 이미지 id", position = 0)
    private Long id;

    @ApiModelProperty(value = "라운지 이미지", position = 1)
    private String imgPath;

  }

  public static LoungeResponse.Out toOut(Lounge lounge) {

    if (lounge == null) {
      return null;
    }
    Out out = new Out();

    CategoryResponse.Main mainCategory = CategoryResponse.toMain(
        lounge.getLoungeMainCategory());

    List<CategoryResponse.Sub> subCategoryList = lounge.getLoungeSubCategoryList().stream()
        .map(CategoryResponse::toSub).collect(Collectors.toList());

    List<MemberOut> memberList = lounge.getLoungeMemberList().stream()
        .map(LoungeResponse::toMemberOut).collect(
            Collectors.toList());

    out.id = lounge.getId();
    out.title = lounge.getTitle();
    out.limitMember = lounge.getLimitMember();
    out.status = lounge.getStatus();
    out.introduction = lounge.getIntroduction();
    out.notice = lounge.getNotice();
    out.createdAt = lounge.getCreatedAt();
    out.imgPath = lounge.getImgPath();
    out.whiteboard = lounge.getWhiteboard();
    out.sense = SenseResponse.toOut(lounge.getSense());
    out.persona = PersonaResponse.toSimpleOut(lounge.getPersona());
    out.mainCategory = mainCategory;
    out.subCategoryList = subCategoryList;
    out.memberList = memberList;

    return out;
  }

  public static LoungeResponse.ChatOut toChatOut(LoungeChat loungeChat) {

    if (loungeChat == null) {
      return null;
    }

    ChatOut chatOut = new ChatOut();

    chatOut.persona = PersonaResponse.toSimpleOut(loungeChat.getPersona());
    chatOut.content = loungeChat.getContent();
    chatOut.createdAt = loungeChat.getCreatedAt();

    return chatOut;

  }

  public static LoungeResponse.MemberOut toMemberOut(LoungeMember loungeMember) {
    if (loungeMember == null) {
      return null;
    }

    MemberOut memberOut = new MemberOut();
    memberOut.ready = loungeMember.getReady();
    memberOut.admin = loungeMember.getAdmin();
    memberOut.persona = PersonaResponse.toSimpleOut(loungeMember.getPersona());

    return memberOut;
  }

  public static LoungeResponse.MemberStatusOut toMemberStatusOut(LoungeMember loungeMember) {
    if (loungeMember == null) {
      return null;
    }

    MemberStatusOut memberStatusOut = new MemberStatusOut();
    memberStatusOut.lounge = LoungeResponse.toOut(loungeMember.getLounge());
    memberStatusOut.persona = PersonaResponse.toSimpleOut(loungeMember.getPersona());
    memberStatusOut.ready = loungeMember.getReady();
    memberStatusOut.admin = loungeMember.getAdmin();

    return memberStatusOut;
  }

  public static LoungeResponse.PinOut toPinOut(LoungePin loungePin) {
    if (loungePin == null) {
      return null;
    }
    PinOut pinOut = new PinOut();
    pinOut.lounge = LoungeResponse.toOut(loungePin.getLounge());
    return pinOut;
  }

  public static LoungeResponse.IdOut toIdOut(Long id) {
    if (id == null) {
      return null;
    }

    IdOut idOut = new IdOut();
    idOut.id = id;
    return idOut;
  }

  public static LoungeResponse.WhiteboardOut toWhiteboardOut(String whiteboard) {
    if (whiteboard == null) {
      return null;
    }

    WhiteboardOut whiteboardOut = new WhiteboardOut();
    whiteboardOut.whiteboard = whiteboard;
    return whiteboardOut;
  }

  public static LoungeResponse.NoticeOut toNoticeOut(String notice) {
    if (notice == null) {
      return null;
    }

    NoticeOut noticeOut = new NoticeOut();
    noticeOut.notice = notice;
    return noticeOut;
  }

  public static LoungeResponse.ImageOut toImageOut(LoungeImage loungeImage) {
    if (loungeImage == null) {
      return null;
    }

    ImageOut imageOut = new ImageOut();
    imageOut.id = loungeImage.getId();
    imageOut.imgPath = ImageUtil.getImagePath(Lounge.Lounge_PREFIX, imageOut.id).get(0);

    return imageOut;

  }
}

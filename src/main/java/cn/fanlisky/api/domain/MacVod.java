package cn.fanlisky.api.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.math.BigDecimal;

@TableName("mac_vod")
public class MacVod {
    @TableId("vod_id")
    private Integer vodId;

    private Short typeId;

    private Short typeId1;

    private Short groupId;

    private String vodName;

    private String vodSub;

    private String vodEn;

    private Boolean vodStatus;

    private String vodLetter;

    private String vodColor;

    private String vodTag;

    private String vodClass;

    private String vodPic;

    private String vodPicThumb;

    private String vodPicSlide;

    private String vodActor;

    private String vodDirector;

    private String vodWriter;

    private String vodBlurb;

    private String vodRemarks;

    private String vodPubdate;

    private Integer vodTotal;

    private String vodSerial;

    private String vodTv;

    private String vodWeekday;

    private String vodArea;

    private String vodLang;

    private String vodYear;

    private String vodVersion;

    private String vodState;

    private String vodAuthor;

    private String vodJumpurl;

    private String vodTpl;

    private String vodTplPlay;

    private String vodTplDown;

    private Boolean vodIsend;

    private Boolean vodLock;

    private Boolean vodLevel;

    private Short vodPointsPlay;

    private Short vodPointsDown;

    private Integer vodHits;

    private Integer vodHitsDay;

    private Integer vodHitsWeek;

    private Integer vodHitsMonth;

    private String vodDuration;

    private Integer vodUp;

    private Integer vodDown;

    private BigDecimal vodScore;

    private Integer vodScoreAll;

    private Integer vodScoreNum;

    private Integer vodTime;

    private Integer vodTimeAdd;

    private Integer vodTimeHits;

    private Integer vodTimeMake;

    private Short vodTrysee;

    private Integer vodDoubanId;

    private BigDecimal vodDoubanScore;

    private String vodReurl;

    private String vodRelVod;

    private String vodRelArt;

    private String vodPlayFrom;

    private String vodPlayServer;

    private String vodPlayNote;

    private String vodDownFrom;

    private String vodDownServer;

    private String vodDownNote;

    public Integer getVodId() {
        return vodId;
    }

    public void setVodId(Integer vodId) {
        this.vodId = vodId;
    }

    public Short getTypeId() {
        return typeId;
    }

    public void setTypeId(Short typeId) {
        this.typeId = typeId;
    }

    public Short getTypeId1() {
        return typeId1;
    }

    public void setTypeId1(Short typeId1) {
        this.typeId1 = typeId1;
    }

    public Short getGroupId() {
        return groupId;
    }

    public void setGroupId(Short groupId) {
        this.groupId = groupId;
    }

    public String getVodName() {
        return vodName;
    }

    public void setVodName(String vodName) {
        this.vodName = vodName == null ? null : vodName.trim();
    }

    public String getVodSub() {
        return vodSub;
    }

    public void setVodSub(String vodSub) {
        this.vodSub = vodSub == null ? null : vodSub.trim();
    }

    public String getVodEn() {
        return vodEn;
    }

    public void setVodEn(String vodEn) {
        this.vodEn = vodEn == null ? null : vodEn.trim();
    }

    public Boolean getVodStatus() {
        return vodStatus;
    }

    public void setVodStatus(Boolean vodStatus) {
        this.vodStatus = vodStatus;
    }

    public String getVodLetter() {
        return vodLetter;
    }

    public void setVodLetter(String vodLetter) {
        this.vodLetter = vodLetter == null ? null : vodLetter.trim();
    }

    public String getVodColor() {
        return vodColor;
    }

    public void setVodColor(String vodColor) {
        this.vodColor = vodColor == null ? null : vodColor.trim();
    }

    public String getVodTag() {
        return vodTag;
    }

    public void setVodTag(String vodTag) {
        this.vodTag = vodTag == null ? null : vodTag.trim();
    }

    public String getVodClass() {
        return vodClass;
    }

    public void setVodClass(String vodClass) {
        this.vodClass = vodClass == null ? null : vodClass.trim();
    }

    public String getVodPic() {
        return vodPic;
    }

    public void setVodPic(String vodPic) {
        this.vodPic = vodPic == null ? null : vodPic.trim();
    }

    public String getVodPicThumb() {
        return vodPicThumb;
    }

    public void setVodPicThumb(String vodPicThumb) {
        this.vodPicThumb = vodPicThumb == null ? null : vodPicThumb.trim();
    }

    public String getVodPicSlide() {
        return vodPicSlide;
    }

    public void setVodPicSlide(String vodPicSlide) {
        this.vodPicSlide = vodPicSlide == null ? null : vodPicSlide.trim();
    }

    public String getVodActor() {
        return vodActor;
    }

    public void setVodActor(String vodActor) {
        this.vodActor = vodActor == null ? null : vodActor.trim();
    }

    public String getVodDirector() {
        return vodDirector;
    }

    public void setVodDirector(String vodDirector) {
        this.vodDirector = vodDirector == null ? null : vodDirector.trim();
    }

    public String getVodWriter() {
        return vodWriter;
    }

    public void setVodWriter(String vodWriter) {
        this.vodWriter = vodWriter == null ? null : vodWriter.trim();
    }

    public String getVodBlurb() {
        return vodBlurb;
    }

    public void setVodBlurb(String vodBlurb) {
        this.vodBlurb = vodBlurb == null ? null : vodBlurb.trim();
    }

    public String getVodRemarks() {
        return vodRemarks;
    }

    public void setVodRemarks(String vodRemarks) {
        this.vodRemarks = vodRemarks == null ? null : vodRemarks.trim();
    }

    public String getVodPubdate() {
        return vodPubdate;
    }

    public void setVodPubdate(String vodPubdate) {
        this.vodPubdate = vodPubdate == null ? null : vodPubdate.trim();
    }

    public Integer getVodTotal() {
        return vodTotal;
    }

    public void setVodTotal(Integer vodTotal) {
        this.vodTotal = vodTotal;
    }

    public String getVodSerial() {
        return vodSerial;
    }

    public void setVodSerial(String vodSerial) {
        this.vodSerial = vodSerial == null ? null : vodSerial.trim();
    }

    public String getVodTv() {
        return vodTv;
    }

    public void setVodTv(String vodTv) {
        this.vodTv = vodTv == null ? null : vodTv.trim();
    }

    public String getVodWeekday() {
        return vodWeekday;
    }

    public void setVodWeekday(String vodWeekday) {
        this.vodWeekday = vodWeekday == null ? null : vodWeekday.trim();
    }

    public String getVodArea() {
        return vodArea;
    }

    public void setVodArea(String vodArea) {
        this.vodArea = vodArea == null ? null : vodArea.trim();
    }

    public String getVodLang() {
        return vodLang;
    }

    public void setVodLang(String vodLang) {
        this.vodLang = vodLang == null ? null : vodLang.trim();
    }

    public String getVodYear() {
        return vodYear;
    }

    public void setVodYear(String vodYear) {
        this.vodYear = vodYear == null ? null : vodYear.trim();
    }

    public String getVodVersion() {
        return vodVersion;
    }

    public void setVodVersion(String vodVersion) {
        this.vodVersion = vodVersion == null ? null : vodVersion.trim();
    }

    public String getVodState() {
        return vodState;
    }

    public void setVodState(String vodState) {
        this.vodState = vodState == null ? null : vodState.trim();
    }

    public String getVodAuthor() {
        return vodAuthor;
    }

    public void setVodAuthor(String vodAuthor) {
        this.vodAuthor = vodAuthor == null ? null : vodAuthor.trim();
    }

    public String getVodJumpurl() {
        return vodJumpurl;
    }

    public void setVodJumpurl(String vodJumpurl) {
        this.vodJumpurl = vodJumpurl == null ? null : vodJumpurl.trim();
    }

    public String getVodTpl() {
        return vodTpl;
    }

    public void setVodTpl(String vodTpl) {
        this.vodTpl = vodTpl == null ? null : vodTpl.trim();
    }

    public String getVodTplPlay() {
        return vodTplPlay;
    }

    public void setVodTplPlay(String vodTplPlay) {
        this.vodTplPlay = vodTplPlay == null ? null : vodTplPlay.trim();
    }

    public String getVodTplDown() {
        return vodTplDown;
    }

    public void setVodTplDown(String vodTplDown) {
        this.vodTplDown = vodTplDown == null ? null : vodTplDown.trim();
    }

    public Boolean getVodIsend() {
        return vodIsend;
    }

    public void setVodIsend(Boolean vodIsend) {
        this.vodIsend = vodIsend;
    }

    public Boolean getVodLock() {
        return vodLock;
    }

    public void setVodLock(Boolean vodLock) {
        this.vodLock = vodLock;
    }

    public Boolean getVodLevel() {
        return vodLevel;
    }

    public void setVodLevel(Boolean vodLevel) {
        this.vodLevel = vodLevel;
    }

    public Short getVodPointsPlay() {
        return vodPointsPlay;
    }

    public void setVodPointsPlay(Short vodPointsPlay) {
        this.vodPointsPlay = vodPointsPlay;
    }

    public Short getVodPointsDown() {
        return vodPointsDown;
    }

    public void setVodPointsDown(Short vodPointsDown) {
        this.vodPointsDown = vodPointsDown;
    }

    public Integer getVodHits() {
        return vodHits;
    }

    public void setVodHits(Integer vodHits) {
        this.vodHits = vodHits;
    }

    public Integer getVodHitsDay() {
        return vodHitsDay;
    }

    public void setVodHitsDay(Integer vodHitsDay) {
        this.vodHitsDay = vodHitsDay;
    }

    public Integer getVodHitsWeek() {
        return vodHitsWeek;
    }

    public void setVodHitsWeek(Integer vodHitsWeek) {
        this.vodHitsWeek = vodHitsWeek;
    }

    public Integer getVodHitsMonth() {
        return vodHitsMonth;
    }

    public void setVodHitsMonth(Integer vodHitsMonth) {
        this.vodHitsMonth = vodHitsMonth;
    }

    public String getVodDuration() {
        return vodDuration;
    }

    public void setVodDuration(String vodDuration) {
        this.vodDuration = vodDuration == null ? null : vodDuration.trim();
    }

    public Integer getVodUp() {
        return vodUp;
    }

    public void setVodUp(Integer vodUp) {
        this.vodUp = vodUp;
    }

    public Integer getVodDown() {
        return vodDown;
    }

    public void setVodDown(Integer vodDown) {
        this.vodDown = vodDown;
    }

    public BigDecimal getVodScore() {
        return vodScore;
    }

    public void setVodScore(BigDecimal vodScore) {
        this.vodScore = vodScore;
    }

    public Integer getVodScoreAll() {
        return vodScoreAll;
    }

    public void setVodScoreAll(Integer vodScoreAll) {
        this.vodScoreAll = vodScoreAll;
    }

    public Integer getVodScoreNum() {
        return vodScoreNum;
    }

    public void setVodScoreNum(Integer vodScoreNum) {
        this.vodScoreNum = vodScoreNum;
    }

    public Integer getVodTime() {
        return vodTime;
    }

    public void setVodTime(Integer vodTime) {
        this.vodTime = vodTime;
    }

    public Integer getVodTimeAdd() {
        return vodTimeAdd;
    }

    public void setVodTimeAdd(Integer vodTimeAdd) {
        this.vodTimeAdd = vodTimeAdd;
    }

    public Integer getVodTimeHits() {
        return vodTimeHits;
    }

    public void setVodTimeHits(Integer vodTimeHits) {
        this.vodTimeHits = vodTimeHits;
    }

    public Integer getVodTimeMake() {
        return vodTimeMake;
    }

    public void setVodTimeMake(Integer vodTimeMake) {
        this.vodTimeMake = vodTimeMake;
    }

    public Short getVodTrysee() {
        return vodTrysee;
    }

    public void setVodTrysee(Short vodTrysee) {
        this.vodTrysee = vodTrysee;
    }

    public Integer getVodDoubanId() {
        return vodDoubanId;
    }

    public void setVodDoubanId(Integer vodDoubanId) {
        this.vodDoubanId = vodDoubanId;
    }

    public BigDecimal getVodDoubanScore() {
        return vodDoubanScore;
    }

    public void setVodDoubanScore(BigDecimal vodDoubanScore) {
        this.vodDoubanScore = vodDoubanScore;
    }

    public String getVodReurl() {
        return vodReurl;
    }

    public void setVodReurl(String vodReurl) {
        this.vodReurl = vodReurl == null ? null : vodReurl.trim();
    }

    public String getVodRelVod() {
        return vodRelVod;
    }

    public void setVodRelVod(String vodRelVod) {
        this.vodRelVod = vodRelVod == null ? null : vodRelVod.trim();
    }

    public String getVodRelArt() {
        return vodRelArt;
    }

    public void setVodRelArt(String vodRelArt) {
        this.vodRelArt = vodRelArt == null ? null : vodRelArt.trim();
    }

    public String getVodPlayFrom() {
        return vodPlayFrom;
    }

    public void setVodPlayFrom(String vodPlayFrom) {
        this.vodPlayFrom = vodPlayFrom == null ? null : vodPlayFrom.trim();
    }

    public String getVodPlayServer() {
        return vodPlayServer;
    }

    public void setVodPlayServer(String vodPlayServer) {
        this.vodPlayServer = vodPlayServer == null ? null : vodPlayServer.trim();
    }

    public String getVodPlayNote() {
        return vodPlayNote;
    }

    public void setVodPlayNote(String vodPlayNote) {
        this.vodPlayNote = vodPlayNote == null ? null : vodPlayNote.trim();
    }

    public String getVodDownFrom() {
        return vodDownFrom;
    }

    public void setVodDownFrom(String vodDownFrom) {
        this.vodDownFrom = vodDownFrom == null ? null : vodDownFrom.trim();
    }

    public String getVodDownServer() {
        return vodDownServer;
    }

    public void setVodDownServer(String vodDownServer) {
        this.vodDownServer = vodDownServer == null ? null : vodDownServer.trim();
    }

    public String getVodDownNote() {
        return vodDownNote;
    }

    public void setVodDownNote(String vodDownNote) {
        this.vodDownNote = vodDownNote == null ? null : vodDownNote.trim();
    }
}
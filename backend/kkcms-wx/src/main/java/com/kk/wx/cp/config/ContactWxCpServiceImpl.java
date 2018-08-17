package com.kk.wx.cp.config;

import me.chanjar.weixin.common.bean.menu.WxMenu;
import me.chanjar.weixin.common.bean.result.WxMediaUploadResult;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.cp.api.*;
import me.chanjar.weixin.cp.api.impl.WxCpServiceApacheHttpClientImpl;
import me.chanjar.weixin.cp.bean.WxCpDepart;
import me.chanjar.weixin.cp.bean.WxCpTag;
import me.chanjar.weixin.cp.bean.WxCpUser;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * <pre>
 *  默认接口实现类，使用apache httpclient实现
 * Created by Binary Wang on 2017-5-27.
 * @author <a href="https://github.com/binarywang">binarywang(Binary Wang)</a>
 * </pre>
 */
public class ContactWxCpServiceImpl extends WxCpServiceApacheHttpClientImpl implements ContactWxCpService {
    @Override
    public void menuCreate(WxMenu menu) throws WxErrorException {

    }

    @Override
    public void menuCreate(Integer agentId, WxMenu menu) throws WxErrorException {

    }

    @Override
    public void menuDelete() throws WxErrorException {

    }

    @Override
    public void menuDelete(Integer agentId) throws WxErrorException {

    }

    @Override
    public WxMenu menuGet() throws WxErrorException {
        return null;
    }

    @Override
    public WxMenu menuGet(Integer agentId) throws WxErrorException {
        return null;
    }

    @Override
    public Integer departCreate(WxCpDepart depart) throws WxErrorException {
        return null;
    }

    @Override
    public void departUpdate(WxCpDepart group) throws WxErrorException {

    }

    @Override
    public void departDelete(Integer departId) throws WxErrorException {

    }

    @Override
    public List<WxCpDepart> departGet() throws WxErrorException {
        return null;
    }

    @Override
    public WxMediaUploadResult mediaUpload(String mediaType, String fileType, InputStream inputStream) throws WxErrorException, IOException {
        return null;
    }

    @Override
    public WxMediaUploadResult mediaUpload(String mediaType, File file) throws WxErrorException {
        return null;
    }

    @Override
    public File mediaDownload(String mediaId) throws WxErrorException {
        return null;
    }

    @Override
    public void userAuthenticated(String userId) throws WxErrorException {

    }

    @Override
    public void userCreate(WxCpUser user) throws WxErrorException {

    }

    @Override
    public void userUpdate(WxCpUser user) throws WxErrorException {

    }

    @Override
    public void userDelete(String userid) throws WxErrorException {

    }

    @Override
    public void userDelete(String[] userids) throws WxErrorException {

    }

    @Override
    public WxCpUser userGet(String userid) throws WxErrorException {
        return null;
    }

    @Override
    public List<WxCpUser> userList(Integer departId, Boolean fetchChild, Integer status) throws WxErrorException {
        return null;
    }

    @Override
    public List<WxCpUser> departGetUsers(Integer departId, Boolean fetchChild, Integer status) throws WxErrorException {
        return null;
    }

    @Override
    public String tagCreate(String tagName) throws WxErrorException {
        return null;
    }

    @Override
    public void tagUpdate(String tagId, String tagName) throws WxErrorException {

    }

    @Override
    public void tagDelete(String tagId) throws WxErrorException {

    }

    @Override
    public List<WxCpTag> tagGet() throws WxErrorException {
        return null;
    }

    @Override
    public List<WxCpUser> tagGetUsers(String tagId) throws WxErrorException {
        return null;
    }

    @Override
    public void tagAddUsers(String tagId, List<String> userIds, List<String> partyIds) throws WxErrorException {

    }

    @Override
    public void tagRemoveUsers(String tagId, List<String> userIds) throws WxErrorException {

    }

    @Override
    public String oauth2buildAuthorizationUrl(String state) {
        return null;
    }

    @Override
    public String oauth2buildAuthorizationUrl(String redirectUri, String state) {
        return null;
    }

    @Override
    public String[] oauth2getUserInfo(String code) throws WxErrorException {
        return new String[0];
    }

    @Override
    public String[] oauth2getUserInfo(Integer agentId, String code) throws WxErrorException {
        return new String[0];
    }

    @Override
    public int invite(String userId, String inviteTips) throws WxErrorException {
        return 0;
    }

    @Override
    public void setUserService(WxCpUserService userService) {

    }

    @Override
    public void setDepartmentService(WxCpDepartmentService departmentService) {

    }

    @Override
    public void setMediaService(WxCpMediaService mediaService) {

    }

    @Override
    public void setMenuService(WxCpMenuService menuService) {

    }

    @Override
    public void setOauth2Service(WxCpOAuth2Service oauth2Service) {

    }

    @Override
    public void setTagService(WxCpTagService tagService) {

    }
}

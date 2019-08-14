/**
 * 
 */
package com.cream.social.connect;

import org.springframework.social.connect.ApiAdapter;
import org.springframework.social.connect.ConnectionValues;
import org.springframework.social.connect.UserProfile;
import org.springframework.social.connect.UserProfileBuilder;

import com.cream.social.api.QQ;
import com.cream.social.api.QQUser;

/**
 * @author cream
 *
 */
public class QQAdapter implements ApiAdapter<QQ> {

    @Override
    public boolean test(QQ qq) {
        try {
            qq.userOperations().getUserInfo();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public void setConnectionValues(QQ qq, ConnectionValues values) {
        QQUser profile = qq.userOperations().getUserInfo();
        values.setDisplayName(profile.getNickname());
        values.setProfileUrl(null);
        values.setImageUrl(profile.getFigureurl_qq_1());
    }

    @Override
    public UserProfile fetchUserProfile(QQ qq) {
        QQUser profile = qq.userOperations().getUserInfo();
        return new UserProfileBuilder().setName(profile.getNickname()).setId(profile.getOpenId()).build();
    }

    @Override
    public void updateStatus(QQ qq, String message) {
        // TODO qq没有实现
    }
}

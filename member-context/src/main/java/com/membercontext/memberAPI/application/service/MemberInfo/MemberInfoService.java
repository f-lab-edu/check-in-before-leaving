package com.membercontext.memberAPI.application.service.MemberInfo;

import com.membercontext.memberAPI.domain.entity.member.Member;

public interface MemberInfoService {

    Member getMemberInfo(String id);
}

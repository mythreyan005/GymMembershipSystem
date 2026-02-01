package com.gym.service;

import java.util.Date;
import java.sql.Connection;
import com.gym.bean.Member;
import com.gym.bean.Membership;
import com.gym.dao.MemberDAO;
import com.gym.dao.MembershipDAO;
import com.gym.util.DBUtil;
import com.gym.util.ValidationException;

public class GymService {

    MemberDAO memberDAO = new MemberDAO();
    MembershipDAO membershipDAO = new MembershipDAO();

    public boolean enrollMembership(String memberID, String type, Date start, Date end) throws Exception {

        if(memberID == null || type == null || start == null || end == null || start.after(end))
            throw new ValidationException();

        Member m = memberDAO.findMember(memberID);
        if(m == null)
            return false;

        Connection con = DBUtil.getDBConnection();

        int id = membershipDAO.generateMembershipID();

        Membership ms = new Membership();
        ms.setMembershipID(id);
        ms.setMemberID(memberID);
        ms.setMembershipType(type);
        ms.setStartDate(start);
        ms.setEndDate(end);
        ms.setStatus("ACTIVE");

        boolean r = membershipDAO.recordMembership(ms);

        if(r) {
            con.commit();
            return true;
        }

        con.rollback();
        return false;
    }

    public boolean cancelMembership(int id) throws Exception {

        if(id <= 0)
            throw new ValidationException();

        Connection con = DBUtil.getDBConnection();

        boolean r = membershipDAO.cancelMembership(id);

        if(r) {
            con.commit();
            return true;
        }

        con.rollback();
        return false;
    }
}

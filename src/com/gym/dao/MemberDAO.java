package com.gym.dao;

import java.sql.*;
import java.util.*;
import com.gym.bean.Member;
import com.gym.util.DBUtil;

public class MemberDAO {

    public Member findMember(String memberID) throws Exception {
        Connection con = DBUtil.getDBConnection();
        String sql = "select * from MEMBER_TBL where MEMBER_ID=?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, memberID);
        ResultSet rs = ps.executeQuery();

        if(rs.next()) {
            Member m = new Member();
            m.setMemberID(rs.getString(1));
            m.setFullName(rs.getString(2));
            m.setDob(rs.getDate(3));
            m.setPhone(rs.getString(4));
            m.setEmail(rs.getString(5));
            m.setStatus(rs.getString(6));
            return m;
        }
        return null;
    }

    public List<Member> viewAllMembers() throws Exception {
        List<Member> list = new ArrayList<>();
        Connection con = DBUtil.getDBConnection();
        String sql = "select * from MEMBER_TBL";
        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery(sql);

        while(rs.next()) {
            Member m = new Member();
            m.setMemberID(rs.getString(1));
            m.setFullName(rs.getString(2));
            m.setDob(rs.getDate(3));
            m.setPhone(rs.getString(4));
            m.setEmail(rs.getString(5));
            m.setStatus(rs.getString(6));
            list.add(m);
        }
        return list;
    }

    public boolean insertMember(Member m) throws Exception {
        Connection con = DBUtil.getDBConnection();
        String sql = "insert into MEMBER_TBL values(?,?,?,?,?,?)";
        PreparedStatement ps = con.prepareStatement(sql);

        ps.setString(1, m.getMemberID());
        ps.setString(2, m.getFullName());
        ps.setDate(3, new java.sql.Date(m.getDob().getTime()));
        ps.setString(4, m.getPhone());
        ps.setString(5, m.getEmail());
        ps.setString(6, m.getStatus());

        int r = ps.executeUpdate();
        if(r > 0) {
            con.commit();
            return true;
        }
        return false;
    }

    public boolean deleteMember(String memberID) throws Exception {
        Connection con = DBUtil.getDBConnection();
        String sql = "delete from MEMBER_TBL where MEMBER_ID=?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, memberID);

        int r = ps.executeUpdate();
        if(r > 0) {
            con.commit();
            return true;
        }
        return false;
    }
}

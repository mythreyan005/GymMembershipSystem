package com.gym.dao;

import java.sql.*;
import com.gym.bean.Membership;
import com.gym.util.DBUtil;

public class MembershipDAO {

    public int generateMembershipID() throws Exception {
        Connection con = DBUtil.getDBConnection();
        String sql = "select NVL(max(MEMBERSHIP_ID),80000)+1 from MEMBERSHIP_TBL";
        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery(sql);
        rs.next();
        return rs.getInt(1);
    }

    public boolean recordMembership(Membership m) throws Exception {
        Connection con = DBUtil.getDBConnection();
        String sql = "insert into MEMBERSHIP_TBL values(?,?,?,?,?,?)";
        PreparedStatement ps = con.prepareStatement(sql);

        ps.setInt(1, m.getMembershipID());
        ps.setString(2, m.getMemberID());
        ps.setString(3, m.getMembershipType());
        ps.setDate(4, new java.sql.Date(m.getStartDate().getTime()));
        ps.setDate(5, new java.sql.Date(m.getEndDate().getTime()));
        ps.setString(6, m.getStatus());

        return ps.executeUpdate() > 0;
    }

    public boolean cancelMembership(int id) throws Exception {
        Connection con = DBUtil.getDBConnection();

        String check = "select count(*) from MEMBERSHIP_TBL where MEMBERSHIP_ID=?";
        PreparedStatement ps1 = con.prepareStatement(check);
        ps1.setInt(1, id);
        ResultSet rs = ps1.executeQuery();
        rs.next();

        if(rs.getInt(1) == 0)
            return false;

        String sql = "update MEMBERSHIP_TBL set STATUS='CANCELLED' where MEMBERSHIP_ID=?";
        PreparedStatement ps2 = con.prepareStatement(sql);
        ps2.setInt(1, id);

        return ps2.executeUpdate() > 0;
    }

    public boolean hasActiveMembership(String memberID) throws Exception {
        Connection con = DBUtil.getDBConnection();
        String sql = "select count(*) from MEMBERSHIP_TBL where MEMBER_ID=? and STATUS='ACTIVE'";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, memberID);
        ResultSet rs = ps.executeQuery();
        rs.next();
        return rs.getInt(1) > 0;
    }
}

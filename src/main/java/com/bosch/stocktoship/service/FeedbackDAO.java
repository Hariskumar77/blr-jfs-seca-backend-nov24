package com.bosch.stocktoship.service;
/**
 * @author HWE1COB
 * */ 
import java.sql.*;
import com.bosch.stocktoship.repository.DBConnection;
 
public class FeedbackDAO {
 
    
	private static final String INSERT_FEEDBACK_QUERY =
		     "INSERT INTO Feedback (feedbackID, partNumber, sampleID, partDescription, feedbackType, defectType, remarks) " +
		     "VALUES (Feedback_SEQ.NEXTVAL, ?, ?, ?, ?, ?, ?)";
 
   
    private static final String SELECT_PART_SAMPLE_DETAILS_QUERY =
        "SELECT pd.partNumber, pd.partDescription, sd.sampleID " +
        "FROM SampleDetails sd " +
        "JOIN PartDetails pd ON sd.partNumber = pd.partNumber " +
        "WHERE pd.partNumber = ? AND sd.sampleID = ?";
 
    
    private static final String BOM_QUERY =
        "SELECT pd.partNumber, pd.partName, pd.partDescription, sd.sampleID, sd.stageName, " +
        "       f.feedbackType, f.defectType " +
        "FROM SampleDetails sd " +
        "JOIN PartDetails pd ON sd.partNumber = pd.partNumber " +
        "LEFT JOIN Feedback f ON f.partNumber = pd.partNumber AND f.sampleID = sd.sampleID " +
        "ORDER BY pd.partNumber, sd.sampleID";
 
    
    public boolean insertFeedback(int partNumber, int sampleID,String partDiscription, String feedbackType,String defectType, String remarks) {
        String partDescription = getPartDescription(partNumber, sampleID);
 
        if (partDescription == null) {
            return false;
        }
 
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(INSERT_FEEDBACK_QUERY)) {
 
           
            stmt.setInt(1, partNumber);
            stmt.setInt(2, sampleID);
            stmt.setString(3, partDescription);   
            stmt.setString(4, feedbackType);      
            stmt.setString(5, defectType);        
            stmt.setString(6, remarks);    
 
            
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
 
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
 
    
    public String getPartDescription(int partNumber, int sampleID) {
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SELECT_PART_SAMPLE_DETAILS_QUERY)) {
 
            stmt.setInt(1, partNumber);
            stmt.setInt(2, sampleID);
 
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getString("partDescription");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
 
    
    public String getBOMQuery() {
        return BOM_QUERY;
    }
 
   
    
}
 
package first;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class GoogleDao {
	
	public List<GoogleB> selectGoogle(String keyword) throws SQLException, ClassNotFoundException{
		Class.forName("com.mysql.jdbc.Driver");//絶対必要！！！！！！
        final String URL  ="jdbc:mysql://localhost:3306/natsumi";
       // int result =0;

        final String USER = "root";
        final String PASS = "Bakabaka1234K";
        
        List<GoogleB> googlelist= new ArrayList<>();
        GoogleB google=null;
        
        try(Connection con = DriverManager.getConnection(URL, USER,PASS)){
        	
        	String sql="select * from natsumi.ItemTable2 where name like ?";
    		
            PreparedStatement ps = con.prepareStatement(sql);
            
            ps.setString(1, "%"+keyword+"%");//?にセットする

            ResultSet rs=ps.executeQuery();
            
            while(rs.next()) {
            	google= new GoogleB();
            	google.setId(rs.getInt("id"));
            	google.setName(rs.getString("name"));
            	google.setZaikosu(rs.getInt("zaikosu"));
            	google.setTanka(rs.getInt("tanka"));
            	google.setUrl(rs.getString("url"));
            	google.setHanbaiFlag(rs.getInt("Hanbai_Flag"));
            	
            	googlelist.add(google);

            }

            ps.close();
    		con.close();
    		
        }catch(SQLException ex) {
        	
        	ex.printStackTrace();
        	
        }
		return googlelist;
  
	} 
	
	
	//現在のポイント確認
	public List<GooglePoint> selectPoint(String username) throws SQLException, ClassNotFoundException{
		Class.forName("com.mysql.jdbc.Driver");//絶対必要！！！！！！
        final String URL  ="jdbc:mysql://localhost:3306/natsumi";
       
        // int result =0;

         final String USER = "root";
         final String PASS = "Bakabaka1234K";
        
        List<GooglePoint> googlelist= new ArrayList<>();
        GooglePoint google=null;
        
        try(Connection con = DriverManager.getConnection(URL, USER,PASS)){
        	
        	String sql="select point from natsumi.reg where username = ?";
    		
            PreparedStatement ps = con.prepareStatement(sql);
            
            ps.setString(1, username);//?にセットする

            ResultSet rs=ps.executeQuery();
            
            while(rs.next()) {
            	google= new GooglePoint();
            	google.setPoint(rs.getInt("point"));
            	
            	
            	googlelist.add(google);

            }

           

            ps.close();
    		con.close();
    		
        }catch(SQLException ex) {
        	
        	ex.printStackTrace();
        	
        }
		return googlelist;
  
	} 
	
	
	
	//獲得後ポイント更新用
	
		public int UPD(int point,String username) throws SQLException, ClassNotFoundException{
			Class.forName("com.mysql.jdbc.Driver");//絶対必要！！！！！！
	        final String URL  ="jdbc:mysql://localhost:3306/natsumi";
	        int result =0;
	        
	        
	        final String USER = "root";
	        final String PASS = "Bakabaka1234K";
	        
	        try(Connection con = DriverManager.getConnection(URL, USER,PASS)){
	        	
	        	String sql="update natsumi.reg set point = ? where reg.username = ?;";

	            PreparedStatement ps = con.prepareStatement(sql);
	            
	            ps.setInt(1, point);
	            ps.setString(2, username);

	             result=ps.executeUpdate();
	            
	            ps.close();
	    		con.close();
	    		
	        }catch(SQLException ex) {
	        	ex.printStackTrace();
	        	
	        }
			return result;

		}
	
	//購入履歴登録用
		
		 public int insertRireki_Item(int id, String username, String productname, int kosu, int tanka, String url,LocalDate date) throws SQLException, ClassNotFoundException{
			 
			 	Class.forName("com.mysql.jdbc.Driver");//絶対必要！！！！！！
		        final String URL  ="jdbc:mysql://localhost:3306/natsumi";
		        int result =0;
		        
		        
		        final String USER = "root";
		        final String PASS = "Bakabaka1234K";
		        
		        try(Connection con = DriverManager.getConnection(URL, USER,PASS)){
		        	
		        	String sql = "INSERT INTO natsumi.ItemHistory4 (id, username,productname, kosu, tanka, url,date) VALUES (?, ?, ?, ?, ?, ?,?)"; 

		            PreparedStatement ps = con.prepareStatement(sql);
		            
		            ps.setInt(1, id); 
		            ps.setString(2, username);
		            ps.setString(3, productname);
		            ps.setInt(4, kosu);
		            ps.setInt(5, tanka);
		            ps.setString(6, url);
		            ps.setDate(7, Date.valueOf(date));

		            result=ps.executeUpdate();
		            
		            ps.close();
		    		con.close();
		    		
		        }catch(SQLException ex) {
		        	ex.printStackTrace();
		        	
		        	return 0;
		        	
		        }
		        

		        return result;
			 
		 }
		 
		 
		 
		//カード情報更新用
			
			public int UPDCard(String username,
					String cardNumber, String cardNm,
					String expiredate, String cvv,String address) throws SQLException, ClassNotFoundException{
				Class.forName("com.mysql.jdbc.Driver");//絶対必要！！！！！！
		        final String URL  ="jdbc:mysql://localhost:3306/natsumi";
		        int result =0;
		        cardNumber= cardNumber.substring(0,4)+" "+"****"+" "+"****"+cardNumber.substring(14);
		        
		        final String USER = "root";
		        final String PASS = "Bakabaka1234K";
		        
		        try(Connection con = DriverManager.getConnection(URL, USER,PASS)){
		        	
		        	String sql = "UPDATE natsumi.reg "
		        			+ "SET cardNumber = ?, cardNm = ?, expiredate = ?, cvv = ?, address = ? "
		        			+ "WHERE username = ?";

		            PreparedStatement ps = con.prepareStatement(sql);
		            	
		            ps.setString(1, cardNumber);
		            ps.setString(2, cardNm);
		            ps.setString(3,expiredate); // "YYYY-MM" 形式で渡す
		            ps.setString(4, cvv);
		                       
		            ps.setString(5, address);//住所も
		            
		            ps.setString(6, username);	//順番大事！！！	
		            

		            result=ps.executeUpdate();
		            
		            ps.close();
		    		con.close();
		    		
		        }catch(SQLException ex) {
		        	ex.printStackTrace();
		        	
		        }
				return result;

			}
		 
			//登録ある場合カード情報を引っ張る
			public List<CardSelectBuhin> SelectCardinfo(String username) throws SQLException, ClassNotFoundException{
				Class.forName("com.mysql.jdbc.Driver");//絶対必要！！！！！！
		        final String URL  ="jdbc:mysql://localhost:3306/natsumi";
		        int result =0;
		        
		        
		        final String USER = "root";
		        final String PASS = "Bakabaka1234K";
		        
		        List<CardSelectBuhin> cardSelectBuhin= new ArrayList<>();
		        CardSelectBuhin cardsel=null;
		        
		        try(Connection con = DriverManager.getConnection(URL, USER,PASS)){
		        	
		        	String sql = "select * from natsumi.reg where username = ?;";

		            PreparedStatement ps = con.prepareStatement(sql);
		            	
		            ps.setString(1, username);
		            
		            ResultSet rs=ps.executeQuery();
		            
		            while(rs.next()) {
		            	cardsel= new CardSelectBuhin();
		            	
		            	cardsel.setCardNumber(rs.getString("cardNumber"));
		            	cardsel.setCardNm(rs.getString("cardNm"));		            	
		            	cardsel.setExpiredate(rs.getString("expiredate"));		            	
		            	cardsel.setCvv(rs.getString("cvv"));
		            	
		            	cardsel.setAddress(rs.getString("address"));//住所
		            	
		            	cardSelectBuhin.add(cardsel);

		            }

		            
		            ps.close();
		    		con.close();
		    		
		        }catch(SQLException ex) {
		        	ex.printStackTrace();
		        	
		        }
				return cardSelectBuhin;

			}
			
			
			
			
			
		 
		 
			//複数個購入更新用
			
			public int UPD_dupli(int kosu,String proname) throws SQLException, ClassNotFoundException{
				Class.forName("com.mysql.jdbc.Driver");//絶対必要！！！！！！
		        final String URL  ="jdbc:mysql://localhost:3306/natsumi";
		        int result =0;
		        
		        
		        final String USER = "root";
		        final String PASS = "Bakabaka1234K";
		        
		        try(Connection con = DriverManager.getConnection(URL, USER,PASS)){
		        	
		        	String sql="update natsumi.ItemHistory4 set kosu = ?, tanka = kosu*tanka where ItemHistory4.productname = ?";

		            PreparedStatement ps = con.prepareStatement(sql);
		            
		            ps.setInt(1, kosu);
		            ps.setString(2, proname);

		             result=ps.executeUpdate();
		            
		            ps.close();
		    		con.close();
		    		
		        }catch(SQLException ex) {
		        	ex.printStackTrace();
		        	
		        }
				return result;

			}
	
			
			//購買履歴参照用
			public List<ItemHistory> selectHis(String username) throws SQLException, ClassNotFoundException{
				Class.forName("com.mysql.jdbc.Driver");//絶対必要！！！！！！
		        final String URL  ="jdbc:mysql://localhost:3306/natsumi";
		       // int result =0;

		        final String USER = "root";
		        final String PASS = "Bakabaka1234K";
		        
		        List<ItemHistory> googlelist= new ArrayList<>();
		        ItemHistory google=null;
		        
		        try(Connection con = DriverManager.getConnection(URL, USER,PASS)){
		        	
		        	String sql="select * from natsumi.ItemHistory4 where username = ?";
		    		
		            PreparedStatement ps = con.prepareStatement(sql);
		            
		            ps.setString(1, username);//?にセットする

		            ResultSet rs=ps.executeQuery();
		            
		            while(rs.next()) {
		            	google = new ItemHistory();
		            	google.setId(rs.getInt("id"));
		            	google.setUsername(rs.getString("username"));
		            	google.setProductname(rs.getString("productname"));
		            	google.setKosu(rs.getInt("kosu"));
		            	google.setTanka(rs.getInt("tanka"));
		            	google.setUrl(rs.getString("url"));
		            	google.setDate(rs.getString("date"));
		            	
		            	googlelist.add(google);

		            }

		            ps.close();
		    		con.close();
		    		
		        }catch(SQLException ex) {
		        	
		        	ex.printStackTrace();
		        	
		        }
				return googlelist;
		  
			} 
			
			
	//パスワード更新用
			public int UPD_pass(String password,String username) throws SQLException, ClassNotFoundException{
				Class.forName("com.mysql.jdbc.Driver");//絶対必要！！！！！！
		        final String URL  ="jdbc:mysql://localhost:3306/natsumi";
		        int result =0;
		        
		        
		        final String USER = "root";
		        final String PASS = "Bakabaka1234K";
		        
		        try(Connection con = DriverManager.getConnection(URL, USER,PASS)){
		        	
		        	String sql="update natsumi.reg set password = ? where reg.username = ?;";

		            PreparedStatement ps = con.prepareStatement(sql);
		            
		            ps.setString(1, password);
		            ps.setString(2, username);

		             result=ps.executeUpdate();
		            
		            ps.close();
		    		con.close();
		    		
		        }catch(SQLException ex) {
		        	ex.printStackTrace();
		        	
		        }
				return result;

			}
			
			
			
			//履歴テーブルから最大値IDを取得する
			
			public int getMaxid() throws SQLException, ClassNotFoundException{

				Class.forName("com.mysql.jdbc.Driver");//絶対必要！！！！！！
		       
		        int result =0;
		        final String URL  ="jdbc:mysql://localhost:3306/natsumi";
		        
		        final String USER = "root";
		        final String PASS = "Bakabaka1234K";
				
				
				try(Connection con = DriverManager.getConnection(URL, USER,PASS)){
					
					String sql="select MAX(id) as max_id from natsumi.ItemHistory4;";
					PreparedStatement ps = con.prepareStatement(sql);
					 ResultSet rs=ps.executeQuery();
					 
					 if(rs.next()) {
						 
						 result=rs.getInt("max_id");
						 
					 }else {
						 throw new SQLException("最大ID取得失敗");
					 }
					
					
				}catch(SQLException ex ) {
					
					ex.printStackTrace();
					
				}
				
				return result;
			
			}
			
			
			//キャンセル用
			
			public int DEL(Integer ID) throws SQLException, ClassNotFoundException{
				Class.forName("com.mysql.jdbc.Driver");//絶対必要！！！！！！
		        final String URL  ="jdbc:mysql://localhost:3306/natsumi";
		        int result =0;
		        
		        
		        final String USER = "root";
		        final String PASS = "Bakabaka1234K";
		        
		        try(Connection con = DriverManager.getConnection(URL, USER,PASS)){
		        	
		        	String sql="delete from natsumi.ItemHistory4 where id = ?;";

		            PreparedStatement ps = con.prepareStatement(sql);
		            
		            ps.setInt(1, ID);
		         

		             result=ps.executeUpdate();
		            
		            ps.close();
		    		con.close();
		    		
		        }catch(SQLException ex) {
		        	ex.printStackTrace();
		        	
		        }
				return result;

			}
			
			//キャンセル対象確認用
			//購買履歴参照用
			public List<ItemHistory> selectHis_ID(int id) throws SQLException, ClassNotFoundException{
				Class.forName("com.mysql.jdbc.Driver");//絶対必要！！！！！！
		        final String URL  ="jdbc:mysql://localhost:3306/natsumi";
		       // int result =0;

		        final String USER = "root";
		        final String PASS = "Bakabaka1234K";
		        
		        List<ItemHistory> googlelist= new ArrayList<>();
		        ItemHistory google=null;
		        
		        try(Connection con = DriverManager.getConnection(URL, USER,PASS)){
		        	
		        	String sql="select * from natsumi.ItemHistory4 where id = ?";
		    		
		            PreparedStatement ps = con.prepareStatement(sql);
		            
		            ps.setInt(1, id);//?にセットする

		            ResultSet rs=ps.executeQuery();
		            
		            while(rs.next()) {
		            	google = new ItemHistory();
		            	google.setId(rs.getInt("id"));
		            	google.setUsername(rs.getString("username"));
		            	google.setProductname(rs.getString("productname"));
		            	google.setKosu(rs.getInt("kosu"));
		            	google.setTanka(rs.getInt("tanka"));
		            	google.setUrl(rs.getString("url"));
		            	google.setDate(rs.getString("date"));
		            	
		            	googlelist.add(google);

		            }

		            ps.close();
		    		con.close();
		    		
		        }catch(SQLException ex) {
		        	
		        	ex.printStackTrace();
		        	
		        }
				return googlelist;
		  
			} 

}

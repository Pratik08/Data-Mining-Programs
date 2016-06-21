import java.sql.*;
import java.lang.*;
import java.util.*;

public class DMBI4
{
	public static void main (String args[])
	{
		try
		{
			Class.forName("org.postgresql.Driver");
			Connection con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/DMBI","postgres","postgres");
			Statement st = con.createStatement();
			ResultSet rs;
			Scanner sc = new Scanner(System.in);
			double probYes, probNo;
			double durNorm_Yes, durNorm_No, durLong_Yes, durLong_No;
			double popLow_Yes, popLow_No, popAvg_Yes, popAvg_No, popHigh_Yes, popHigh_No;
			double probAward_Yes, probAward_No;
			double temp = 0, yes = 0, no = 0, total = 0;
			int durInp, popInp;
			
			rs = st.executeQuery("select count(*) as count from movie where awards like '%Yes%'");
            while (rs.next()) 
			{
				yes = rs.getInt("count");
            }
			rs = st.executeQuery("select count(*) as count from movie where awards like '%No%'");
            while (rs.next()) 
			{
				no = rs.getInt("count");                
            }
			total = yes + no;
			probYes = ((double) yes / total);
			probNo = ((double) no / total);
			
			rs= st.executeQuery("select count(*) as count from movie where awards like '%Yes%' and duration <= 100 ");
            while (rs.next()) 
			{
            	temp = rs.getInt("count");
            }
            durNorm_Yes = ((double) temp / yes);
            rs= st.executeQuery("select count(*) as count from movie where awards like '%No%' and duration <= 100");
            while (rs.next()) 
			{
				temp = rs.getInt("count");
            }
            durNorm_No = ((double) temp / no);
			
			rs= st.executeQuery("select count(*) as count from movie where awards like '%Yes%' and duration >100 ");
            while (rs.next()) 
			{
            	temp = rs.getInt("count");
            }
            durLong_Yes = ((double) temp / yes);
            rs= st.executeQuery("select count(*) as count from movie where awards like '%No%' and duration > 100 ");
            while (rs.next()) 
			{
				temp = rs.getInt("count");
            }
            durLong_No = ((double) temp / no);
			
			rs= st.executeQuery("select count(*) as count from movie where awards like '%Yes%' and pop_asia <= 40 ");
            while (rs.next()) 
			{
            	temp = rs.getInt("count");
            }
            popLow_Yes = ((double) temp / yes);
            rs= st.executeQuery("select count(*) as count from movie where awards like '%No%' and pop_asia <= 40 ");
            while (rs.next()) 
			{
				temp = rs.getInt("count");
            }
            popLow_No = ((double) temp / no);
			
			rs= st.executeQuery("select count(*) as count from movie where awards like '%Yes%' and pop_asia > 40 and pop_asia <= 70 ");
            while (rs.next()) 
			{
            	temp = rs.getInt("count");
            }
            popAvg_Yes = ((double) temp / yes);
            rs= st.executeQuery("select count(*) as count from movie where awards like '%No%' and pop_asia > 40 and pop_asia <= 70 ");
            while (rs.next()) 
			{
				temp = rs.getInt("count");
            }
            popAvg_No = ((double) temp / no);
			
			rs= st.executeQuery("select count(*) as count from movie where awards like '%Yes%' and pop_asia > 70 ");
            while (rs.next()) 
			{
            	temp = rs.getInt("count");
            }
            popHigh_Yes = ((double) temp / yes);
            rs= st.executeQuery("select count(*) as count from movie where awards like '%No%' and pop_asia > 70 ");
            while (rs.next()) 
			{
				temp = rs.getInt("count");
            }
            popHigh_No = ((double) temp / no);
			
			System.out.println("Select Input for duration of movie:\n1.Normal\n2.Long");
			durInp = sc.nextInt();
			System.out.println("Select Input for popularity of movie:\n1.Low\n2.Average\n3.High");
			popInp = sc.nextInt();
			probAward_Yes = probYes;
			probAward_No = probNo;
			switch (durInp)
			{
				case 1:	probAward_Yes *= durNorm_Yes;
							probAward_No *= durNorm_No;
							break;
							
				case 2:	probAward_Yes *= durLong_Yes;
							probAward_No *= durLong_No;
							break;
			}
			switch (popInp)
			{
				case 1:	probAward_Yes *= popLow_Yes;
							probAward_No *= popLow_No;
							break;
							
				case 2:	probAward_Yes *= popAvg_Yes;
							probAward_No *= popAvg_No;
							break;
							
				case 3:	probAward_Yes *= popHigh_Yes;
							probAward_No *= popHigh_No;
							break;
			}
			System.out.println("Probability of sample winning an Oscar: " + probAward_Yes);
            System.out.println("Probability of sample not winning an Oscar: " + probAward_No);
			if (probAward_Yes > probAward_No)
			{
				System.out.println("The sample will win an Oscar.");
			}
			else
			{
				System.out.println("The sample will not win an Oscar.");
			}
		}
		catch (Exception e)
		{
			System.out.println(e);
		}
	}
}

import java.io.*;
import java.sql.*;
import java.util.*;
class DMBI5
{
    public static void main(String args[]) throws IOException, SQLException, ClassNotFoundException 
	{
        try
		{
            Scanner sc=new Scanner(System.in);
            Class.forName("org.postgresql.Driver");
            Connection con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/DMBI","postgres","postgres");
            ResultSet rs;
            Statement st = con.createStatement();
            double w1, w0, xMean, yMean, x, y;
            double sumX = 0, sumY = 0, total = 0;
			double temp1 = 0, temp2 = 0;
            rs = st.executeQuery("select count(pop_asia) from movie");
			while (rs.next())
			{
				total = rs.getInt(1);
            }		
			rs = st.executeQuery("select pop_asia, boxoff_earning from movie");
            while (rs.next())
			{
				sumX += rs.getInt(1);
				sumY += rs.getInt(2);
            }		            
			xMean = (sumX / total);
			yMean = (sumY / total);
			rs = st.executeQuery("select pop_asia, boxoff_earning from movie");
            while (rs.next()) 
			{
				temp1 += ((rs.getInt(1) - xMean) * (rs.getInt(2) - yMean));
				temp2 += (Math.pow((rs.getInt(1) - xMean), 2));
            }
			w1 = (temp1 / temp2);
			w0 = (yMean - (w1 * xMean));
			System.out.println("Regression Coefficients are as follows:\nw1 = " + w1 + "\nw0 = " + w0);
			System.out.println("Enter value of x to calculate y: ");
			x = sc.nextInt();
			y = (w0 + (w1 * x));
			System.out.println("The value of y is: " + y);
        }
        catch (Exception e) 
		{
            System.out.println(e);
        }
    }
}

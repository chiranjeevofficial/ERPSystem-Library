public class NewSQL{
    public static void main(String[] args){
        Connection con = null;
        String url = "jdbc:mysql://localhost:3306/library";

        con = DriverManager.getConnection(url,"root","Chiranjeev@1805");
        if(con!=null)
            System.out.println("Connection Established");
        String query = "select * from book";
        PreparedStatement ps = con.PreparedStatement(query);
        ResultSet res = ps.executeQuery();
        while(rs.next()){
            System.out.print(rs.getString(1)+" ");
            System.out.print(rs.getString(2)+" ");
            System.out.print(rs.getString(3)+" ");
            System.out.print(rs.getString(4)+" ");
            System.out.print(rs.getDouble(5)+" ");
            System.out.print(rs.getInt(6)+" ");
            System.out.print(rs.getInt(7)+" ");
            System.out.println();
        }
    }
}
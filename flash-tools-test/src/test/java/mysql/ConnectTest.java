package mysql;

import com.mysql.jdbc.Driver;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * grant
 * 23/8/2019 6:02 PM
 * 描述：
 */
public class ConnectTest {

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        String url = "jdbc:mysql://106.15.190.158:3306/yili?allowMultiQueries=true&useUnicode=true&characterEncoding=UTF-8";
        String username = "root";
        String password = "root123/*-";

        Class.forName("com.mysql.jdbc.Driver");
        Connection con = DriverManager.getConnection(url, username, password);
        System.out.println(con.getSchema());
        System.out.println(con.getAutoCommit());
        System.out.println(con.getCatalog());
        ResultSet rs = con.getMetaData().getTables("yili", null, "file%", new String[]{"TABLE"});
        boolean isFirst = true;
        List<String> tables = new ArrayList<>();
        int num = 0;
        while (rs.next()){
            if (isFirst){
                isFirst = false;
                num = rs.getMetaData().getColumnCount();
                for (int i=1;i<=num; i++){
                    String str = rs.getMetaData().getColumnName(i);
                    System.out.print(str + "\t");
                }
                System.out.println();
            }
            for (int i=1;i<=num; i++){
                String str = rs.getString(i);
                System.out.print(str + "\t");
                if (i == 3){
                    tables.add(str);
                }
            }
            System.out.println();
        }
        rs.close();

        for (String t : tables){
            System.out.println("表结构：" + t);
            rs = con.getMetaData().getColumns("yili", null, t, null);
            isFirst = true;
            while (rs.next()){
                if (isFirst){
                    isFirst = false;
                    num = rs.getMetaData().getColumnCount();
                    for (int i=1;i<=num; i++){
                        String str = rs.getMetaData().getColumnName(i);
                        System.out.print(i +"："+ str + "\t");
                    }
                    System.out.println();
                }
                for (int i=1;i<=num; i++){
                    String str = rs.getString(i);
                    System.out.print(i +"："+ str + "\t");
                }
                System.out.println();
            }

        }
        //是否主键
//        con.getMetaData().getPrimaryKeys()
        rs.close();
        con.close();
    }
}

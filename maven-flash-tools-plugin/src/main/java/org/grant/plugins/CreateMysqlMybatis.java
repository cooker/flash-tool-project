package org.grant.plugins;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.plugins.annotations.ResolutionScope;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * grant
 * 23/8/2019 3:56 PM
 * 描述：
 */
@Mojo(name = "mysql-mybatis", defaultPhase = LifecyclePhase.PACKAGE, requiresProject = true, threadSafe = true,
        requiresDependencyResolution = ResolutionScope.RUNTIME)
public class CreateMysqlMybatis extends AbstractMojo {

    @Parameter(name = "url")
    private String url;
    @Parameter(name = "username")
    private String username;
    @Parameter(name = "password")
    private String password;
    @Parameter(name = "tableName", defaultValue = "%")
    private String tableName;
    @Parameter(name = "db")
    private String db;


    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        getLog().info("Mysql create java code ...");
        getLog().info("url=" + url);
        getLog().info("username=" + username);
        getLog().info("password=" + password);
        getLog().info("tableName=" + tableName);
        getLog().info("db=" + db);
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            getLog().error(e);
            return;
        }
        Connection con = null;
        try {
            con = DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            getLog().error(e);
            return;
        }
        try {
            if (con.isClosed()) return;
        } catch (SQLException e) {
            getLog().error(e);
        }
        try {
            ResultSet rs = con.getMetaData().getTables(null, null, null, new String[]{"TABLE"});
            while (rs.next()){
                getLog().info(rs.getRow() + "");
                getLog().info(rs.toString());
            }
        } catch (SQLException e) {
            getLog().error(e);
        }

        try {
            con.close();
        } catch (SQLException e) {
            getLog().error(e);
        }
    }
}

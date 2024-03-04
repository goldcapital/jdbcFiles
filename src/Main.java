import java.io.*;
import java.sql.*;

public class Main {
    public static void main(String[] args) throws FileNotFoundException, SQLException {
        String str = "C:/Users/Muhammad/Pictures/Camera Roll/mmm.jpg";
//writeToDb(str);
       // writeToDb(str);
readFromDB();
    }

    public static void writeToDb1(String url) throws SQLException, FileNotFoundException {
        File file = new File("2_5469756403256140556.mp4");
        FileInputStream fil = new FileInputStream(file);
        Connection con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/jdbc_db",
                "jdbc_user", "123456");
        String sql = "insert into image_attach (f_name,f_type,f_data) values(?,?,?)";
        PreparedStatement ps = con.prepareStatement(sql);
        String[] arr = url.split("\\.");
        String fileType = arr[arr.length - 1];
        String fileName = file.getName().split("." + fileType)[0];

        ps.setString(1, fileName);
        ps.setString(2, fileType);
        ps.setBinaryStream(3, fil);
        ps.setString(1, "2_5469756403256140556");
        ps.setString(2, "mp4");
        ps.setBinaryStream(3, fil);

        ps.executeUpdate();
        con.close();
    }

    public static void readFromDB() {
        try {

            Connection con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/jdbc_db",
                    "jdbc_user", "123456"); // <2>
            String sql = "select f_name, f_type, f_data from image_attach ";
            Statement statement = con.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                String fileName = resultSet.getString("f_name");
                String fileType = resultSet.getString("f_type");
                InputStream inputStream = resultSet.getBinaryStream("f_data");

                if (inputStream != null) {
                    byte[] buffer = new byte[inputStream.available()];
                    inputStream.read(buffer);

                    File targetFile = new File("images/" + fileName + "_mazgi." + fileType);
                    OutputStream outputStream = new FileOutputStream(targetFile);
                    outputStream.write(buffer);
                    outputStream.close();
                }
            }
            con.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
        public static void writeToDb (String url){
            File file = new File(url);
            if (!file.exists() || !file.isFile()) {
                return;
            }
            FileInputStream fis = null;
            try {
                fis = new FileInputStream(file);
                Connection con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/jdbc_db",
                        "jdbc_user", "123456"); // <2>
                String sql = "insert into image_attach (f_name,f_type,f_data) values(?,?,?)";
                PreparedStatement ps = con.prepareStatement(sql);

       /* int dotIndex = url.lastIndexOf(".");
        String type = url.substring(dotIndex + 1);*/

                String[] arr = url.split("\\.");
                String fileType = arr[arr.length - 1];
                String fileName = file.getName().split("." + fileType)[0];

                ps.setString(1, fileName);
                ps.setString(2, fileType);
                ps.setBinaryStream(3, fis);

                ps.executeUpdate();
                con.close();
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }


        }

    }


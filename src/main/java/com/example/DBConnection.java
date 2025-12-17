package com.example;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {

    public static Connection getConnection() throws Exception {

        String url =
            "jdbc:postgresql://ep-rough-union-ah305dmx-pooler.c-3.us-east-1.aws.neon.tech/neondb"
          + "?user=neondb_owner"
          + "&password=npg_ip2eA7gNRyTs"
          + "&sslmode=require"
          + "&channelBinding=require";

        Class.forName("org.postgresql.Driver");
        return DriverManager.getConnection(url);
    }
}

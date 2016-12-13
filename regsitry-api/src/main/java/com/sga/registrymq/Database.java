package com.sga.registrymq;

import com.mongodb.MongoClient;

/**
 * Created by navee on 12/11/2016.
 * Singleton Mongo Connection to be used across the application
 */
public class Database {
    private static Database instance = null;
    protected MongoClient mongoClient;
    private Database(){
        this.mongoClient = new MongoClient("52.42.119.121");
    }
    public static Database getInstance(){
        if (instance == null){
            synchronized (Database.class){
                if (instance == null){
                    instance = new Database();
                }
            }
        }
        return instance;
    }
}

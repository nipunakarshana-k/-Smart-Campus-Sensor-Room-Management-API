package com.smartcampus.store;

import com.smartcampus.models.Room;
import com.smartcampus.models.Sensor;

import java.util.HashMap;
import java.util.Map;

public class DataStore {

    public static Map<String, Room> rooms = new HashMap<>();
    public static Map<String, Sensor> sensors = new HashMap<>();
}
/*
 *
 *  *    Copyright (C) 2019 Amit Shekhar
 *  *    Copyright (C) 2011 Android Open Source Project
 *  *
 *  *    Licensed under the Apache License, Version 2.0 (the "License");
 *  *    you may not use this file except in compliance with the License.
 *  *    You may obtain a copy of the License at
 *  *
 *  *        http://www.apache.org/licenses/LICENSE-2.0
 *  *
 *  *    Unless required by applicable law or agreed to in writing, software
 *  *    distributed under the License is distributed on an "AS IS" BASIS,
 *  *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  *    See the License for the specific language governing permissions and
 *  *    limitations under the License.
 *
 */

package com.amitshekhar;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.content.Context;
import android.util.Log;
import android.util.Pair;

import com.amitshekhar.server.ClientServer;
import com.amitshekhar.sqlite.DBFactory;
import com.amitshekhar.utils.NetworkUtils;

import java.io.File;
import java.util.HashMap;

/**
 * Created by amitshekhar on 15/11/16.
 */

public class DebugDB {

    private static final String TAG = DebugDB.class.getSimpleName();
    private static ClientServer clientServer;
    private static String addressLog = "not available";

    private DebugDB() {
        // This class in not publicly instantiable
    }

    public static void initialize(Context context, DBFactory dbFactory,int port) {
        clientServer = new ClientServer(context, port, dbFactory);
        clientServer.start();
        addressLog = NetworkUtils.getAddressLog(context, port);
        Log.d(TAG, addressLog);
    }

    public static String getAddressLog() {
        Log.d(TAG, addressLog);
        return addressLog;
    }

    public static void shutDown() {
        if (clientServer != null) {
            clientServer.stop();
            clientServer = null;
        }
    }

    public static void setCustomDatabaseFiles(HashMap<String, Pair<File, String>> customDatabaseFiles) {
        if (clientServer != null) {
            clientServer.setCustomDatabaseFiles(customDatabaseFiles);
        }
    }

    public static void setInMemoryRoomDatabases(HashMap<String, SupportSQLiteDatabase> databases) {
        if (clientServer != null) {
            clientServer.setInMemoryRoomDatabases(databases);
        }
    }

    public static boolean isServerRunning() {
        return clientServer != null && clientServer.isRunning();
    }

}

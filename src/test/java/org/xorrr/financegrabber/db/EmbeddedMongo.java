package org.xorrr.financegrabber.db;

import java.io.IOException;
import java.net.UnknownHostException;

import de.flapdoodle.embed.mongo.MongodExecutable;
import de.flapdoodle.embed.mongo.MongodStarter;
import de.flapdoodle.embed.mongo.config.IMongodConfig;
import de.flapdoodle.embed.mongo.config.MongodConfigBuilder;
import de.flapdoodle.embed.mongo.config.Net;
import de.flapdoodle.embed.mongo.distribution.Version;
import de.flapdoodle.embed.process.runtime.Network;

public class EmbeddedMongo {

    private static MongodExecutable mongodExecutable = null;
    private static boolean isStarted = false;

    private EmbeddedMongo() {}

    public static void startEmbeddedMongo(int port)
            throws UnknownHostException, IOException {
        if (!isStarted) {
            IMongodConfig mongodConfig = new MongodConfigBuilder()
                    .version(Version.Main.PRODUCTION)
                    .net(new Net(port, Network.localhostIsIPv6())).build();

            MongodStarter runtime = MongodStarter.getDefaultInstance();
            startMongoExecutable(runtime, mongodConfig);
        } else {
            System.out.println("EmbeddedMongo has already been started");
        }
    }

    public static void stopEmbeddedMongo() {
        if (mongodExecutable != null) {
            mongodExecutable.stop();
        }
        isStarted = false;
    }

    private static void startMongoExecutable(MongodStarter runtime,
            IMongodConfig mongodConfig) throws IOException {
        mongodExecutable = runtime.prepare(mongodConfig);
        mongodExecutable.start();
        isStarted = true;
    }
}
